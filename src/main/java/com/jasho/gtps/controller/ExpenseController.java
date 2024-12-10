package com.jasho.gtps.controller;

import com.jasho.gtps.dto.EventWithExpense;
import com.jasho.gtps.dto.ExpenseForm;
import com.jasho.gtps.dto.ExpenseReportDto;
import com.jasho.gtps.dto.ExpensesDto;
import com.jasho.gtps.entity.EventEntity;
import com.jasho.gtps.entity.ExpenseEntity;
import com.jasho.gtps.entity.User;
import com.jasho.gtps.service.EventService;
import com.jasho.gtps.service.ExpenseService;
import com.jasho.gtps.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jashobanta Patra
 * crated on 07-11-2024
 */
@Controller
@RequestMapping("/expenses")
@Slf4j
@SessionAttributes("expenseList")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private EventService eventService;

    @Autowired
    private IUserService userService;

    private List<ExpenseForm> expenseList = new ArrayList<>();

    @GetMapping
    public String getEvent(Model model) {

        List<EventEntity> eventEntities = eventService.fetchAllEvent();

        List<EventWithExpense> eventsWithExpenses = eventEntities.stream()
                .map(event -> {
                    BigDecimal totalTripAmount = expenseService.getTotalExpenseByEventId(event.getId());
                    return new EventWithExpense(event, totalTripAmount);
                })
                .collect(Collectors.toList());

        model.addAttribute("eventsWithExpenses", eventsWithExpenses);

        return "expenses/eventlist";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        ExpensesDto expensesDto = new ExpensesDto();
        model.addAttribute("expensesDto", expensesDto);
        return "expenses/create";
    }

    @GetMapping("/expenseForm")
    public String getExpenseForm(Model model, @RequestParam("eventId") Long eventId, @RequestParam("userId") Long userId) {
        model.addAttribute("eventId", eventId);
        model.addAttribute("userId", userId);
        model.addAttribute("expenseList", expenseList);
        model.addAttribute("newExpense", new ExpenseForm());

        BigDecimal totalAmount = expenseList.stream()
                .filter(expense -> expense.getAmount() != null)
                .map(ExpenseForm::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("expenseList", expenseList);

        return "expenses/expenseForm";
    }

    @PostMapping("/addExpense")
    public String addExpense(RedirectAttributes redirectAttributes,
            @ModelAttribute("newExpense") ExpenseForm newExpense,
            @RequestParam("eventId") Long eventId,
            @RequestParam("userId") Long userId) {
        log.info("Adding expense for event ID {} and user ID {}: {}", eventId, userId, newExpense);
        newExpense.setEventId(eventId);
        newExpense.setUserId(userId);
        expenseList.add(newExpense);
        BigDecimal totalAmount = expenseList.stream()
                .filter(expense -> expense.getAmount() != null)
                .map(ExpenseForm::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        log.info("totalAmount : {}", totalAmount);
        redirectAttributes.addAttribute("eventId", eventId);
        redirectAttributes.addAttribute("userId", userId);
        redirectAttributes.addFlashAttribute("totalAmount", totalAmount);
        //model.addAttribute("expenseList", expenseList);
        return "redirect:/expenses/expenseForm?eventId=" + eventId + "&userId=" + userId;
    }

    @PostMapping("/submitExpenses")
    public String submitExpenses(Model model, @RequestParam("eventId") Long eventId, @RequestParam("userId") Long userId) {
        log.info("Submitting expenses for event ID {} and user ID {}: {}", eventId, userId, expenseList);

        EventEntity event = eventService.fetchEventById(eventId);
        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<ExpenseEntity> expenseEntities = expenseList.stream()
                .map(expenseForm -> mapExpenseFormToEntity(expenseForm, event, user))
                .toList();

        expenseService.saveAll(expenseEntities);
        log.info("Saved {} expenses successfully.", expenseEntities.size());

        expenseList.clear();

        return "redirect:/expenses";
    }

    private ExpenseEntity mapExpenseFormToEntity(ExpenseForm form, EventEntity event, User user) {
        return ExpenseEntity.builder()
                .description(form.getDescription())
                .amount(form.getAmount())
                .expenseDate(form.getExpenseDate() != null ? form.getExpenseDate() : LocalDate.now())
                .event(event)
                .user(user)
                .build();
    }

    @GetMapping("/eventUsers")
    public String getEventUser(Model model, @RequestParam("eventId") Long eventId) {
        log.info("Fetching Event Users for Event ID: {}", eventId);

        EventEntity event = eventService.fetchEventById(eventId);
        if (event == null) {
            log.error("Event with ID {} not found!", eventId);
            return "redirect:/events?error=event_not_found";
        }

        List<ExpenseEntity> expenseEntities = expenseService.findExpensesByEventId(eventId);
        log.info("Expense details: {}", expenseEntities);

        Map<User, List<ExpenseEntity>> userExpenses = expenseEntities.stream()
                .collect(Collectors.groupingBy(ExpenseEntity::getUser));

        BigDecimal totalTripExpenses = expenseEntities.stream()
                .map(ExpenseEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalMembers = userExpenses.size();
        BigDecimal costPerHead = totalTripExpenses.divide(new BigDecimal(totalMembers), 2, RoundingMode.HALF_UP);

        List<ExpenseReportDto> userExpenseSummaries = userExpenses.entrySet().stream()
                .map(entry -> {
                    Map<LocalDate, List<ExpenseEntity>> expensesByDate = entry.getValue().stream()
                            .collect(Collectors.groupingBy(expense -> expense.getExpenseDate()));

                    BigDecimal totalAmount = entry.getValue().stream()
                            .map(ExpenseEntity::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum of BigDecimal amounts

                    BigDecimal balance = costPerHead.subtract(totalAmount);

                    return new ExpenseReportDto(
                            entry.getKey(),
                            expensesByDate,
                            totalAmount,
                            balance

                    );
                })
                .toList();

        log.info("Total Trip Expenses : {}", totalTripExpenses);
        log.info("Total count : {}", totalMembers);
        log.info("Per head cost : {}", costPerHead);

        model.addAttribute("event", event);
        model.addAttribute("userExpenseSummaries", userExpenseSummaries);
        model.addAttribute("totalTripExpenses", totalTripExpenses);
        model.addAttribute("totalMembers", totalMembers);
        model.addAttribute("costPerHead", costPerHead);

        return "expenses/expenseReport";
    }

    @GetMapping("/editExpense")
    public String editExpense(@RequestParam("index") int index, Model model) {
        if (index < 0 || index >= expenseList.size()) {
            throw new IllegalArgumentException("Invalid expense index");
        }
        ExpenseForm expenseToEdit = expenseList.get(index);
        log.info("expenseToEdit : {}", expenseToEdit);
        model.addAttribute("editExpense", expenseToEdit);
        model.addAttribute("index", index);
        return "expenses/editExpenseForm";
    }

    @PostMapping("/updateExpense")
    public String updateExpense(
            @ModelAttribute("editExpense") ExpenseForm editExpense,
            @RequestParam("index") int index,
            RedirectAttributes redirectAttributes,
            @RequestParam("eventId") Long eventId,
            @RequestParam("userId") Long userId) {
        if (index < 0 || index >= expenseList.size()) {
            throw new IllegalArgumentException("Invalid expense index");
        }
        editExpense.setEventId(eventId);
        editExpense.setUserId(userId);
        expenseList.set(index, editExpense);

        redirectAttributes.addAttribute("eventId", eventId);
        redirectAttributes.addAttribute("userId", userId);
        return "redirect:/expenses/expenseForm?eventId=" + eventId + "&userId=" + userId;
    }

    @GetMapping("/deleteExpense")
    public String deleteExpense(@RequestParam("index") int index,
                                RedirectAttributes redirectAttributes,
                                @RequestParam("eventId") Long eventId,
                                @RequestParam("userId") Long userId) {
        if (index < 0 || index >= expenseList.size()) {
            throw new IllegalArgumentException("Invalid expense index");
        }
        expenseList.remove(index);

        redirectAttributes.addAttribute("eventId", eventId);
        redirectAttributes.addAttribute("userId", userId);
        return "redirect:/expenses/expenseForm?eventId=" + eventId + "&userId=" + userId;
        //return "redirect:/expenses/expenseForm";
    }


}

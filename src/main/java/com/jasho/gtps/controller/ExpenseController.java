package com.jasho.gtps.controller;

import com.jasho.gtps.dto.ExpenseForm;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("events", eventEntities);
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
        return "expenses/expenseForm";
    }

    @PostMapping("/addExpense")
    public String addExpense(
            @ModelAttribute("newExpense") ExpenseForm newExpense,
            @RequestParam("eventId") Long eventId,
            @RequestParam("userId") Long userId) {
        log.info("Adding expense for event ID {} and user ID {}: {}", eventId, userId, newExpense);
        newExpense.setEventId(eventId);
        newExpense.setUserId(userId);
        expenseList.add(newExpense);
        return "redirect:/expenses/expenseForm?eventId=" + eventId + "&userId=" + userId;
    }

    @PostMapping("/submitExpenses")
    public String submitExpenses(@RequestParam("eventId") Long eventId, @RequestParam("userId") Long userId) {
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

}

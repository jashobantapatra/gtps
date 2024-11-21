package com.jasho.gtps.controller;

import com.jasho.gtps.dto.EventDto;
import com.jasho.gtps.entity.EventEntity;
import com.jasho.gtps.entity.EventMembersEntity;
import com.jasho.gtps.entity.ExpenseEntity;
import com.jasho.gtps.entity.User;
import com.jasho.gtps.service.EventMembersService;
import com.jasho.gtps.service.EventService;
import com.jasho.gtps.service.ExpenseService;
import com.jasho.gtps.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Jashobanta Patra
 * crated on 04-11-2024
 */
@Controller
@RequestMapping("/events")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private IUserService userService;

    @Autowired
    private EventMembersService eventMembersService;

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public String getEvent(Model model) {
        List<EventEntity> eventEntities = eventService.fetchAllEvent();
        model.addAttribute("events", eventEntities);
        return "events/eventlist";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        EventDto eventDto = new EventDto();
        model.addAttribute("eventDto", eventDto);
        return "events/create";
    }

    @PostMapping("/create")
    public String createEvent(@Validated @ModelAttribute EventDto eventDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "events/create";
        }

        if (eventDto.getEventName() == null || eventDto.getEventName().isEmpty()) {
            return "events/create";
        }

        EventEntity eventEntity = new EventEntity().builder()
                .eventName(eventDto.getEventName())
                .eventDescription(eventDto.getEventDescription())
                .eventDate(eventDto.getEventDate())
                .createdBy(1)
                .createdDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                .build();

        eventService.saveEvent(eventEntity);

        return "redirect:/events?create_success";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        EventEntity eventEntity = eventService.fetchEventById(id);
        if (eventEntity == null) {
            return "redirect:/events";
        }

        model.addAttribute("eventEntity", eventEntity);

        EventDto eventDto = new EventDto().builder()
                .eventName(eventEntity.getEventName())
                .eventDescription(eventEntity.getEventDescription())
                .eventDate(eventEntity.getEventDate())
                .build();

        model.addAttribute("eventDto", eventDto);
        return "events/edit";
    }

    @PostMapping("/edit")
    public String updateEvent(
            Model model,
            @RequestParam int id,
            @Validated @ModelAttribute EventDto eventDto,
            BindingResult bindingResult
    ) {

        EventEntity eventEntity = eventService.fetchEventById(id);
        if (eventEntity == null) {
            return "redirect:/events";
        }
        model.addAttribute("events", eventEntity);
        if (bindingResult.hasErrors()) {
            return "events/edit";
        }

        eventEntity.setEventName(eventDto.getEventName());
        eventEntity.setEventDescription(eventDto.getEventDescription());
        eventEntity.setEventDate(eventDto.getEventDate());
        eventEntity.setUpdatedBy(1);
        eventEntity.setUpdatedDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        eventService.saveEvent(eventEntity);

        return "redirect:/events?update_success";
    }

    @GetMapping("/delete")
    public String deleteEvent(@RequestParam int id) {
        eventService.deleteEvent(id);
        return "redirect:/events?delete_success";
    }

    @GetMapping("/users")
    public String getUser(Model model, @RequestParam int id) {
        log.info("eventid: {}",id);
        EventEntity event = eventService.fetchEventById(id);
        log.info("EVENT : {}", event);
        List<User> userEntityList = userService.getAllUsers();
        model.addAttribute("event", event);
        model.addAttribute("users", userEntityList);
        long membersTaggedCount = event.getMembers().stream().count();
        log.info("membersTaggedCount : {}", membersTaggedCount);
        model.addAttribute("membersTaggedCount", membersTaggedCount);
        return "events/userlist";
    }

    @PostMapping("/users")
    public String mapUsers(Model model, @ModelAttribute("event") EventEntity event) {

        EventEntity persistedEvent = eventService.fetchEventById(event.getId());
        log.info("persistedEvent : {}", persistedEvent);

        List<User> selectedUsers = userService.fetchUsersByIds(event.getSelectedMemberIds());
        log.info("selectedUsers : {}", selectedUsers);

        List<EventMembersEntity> members = selectedUsers.stream().map(user -> {
            EventMembersEntity member = new EventMembersEntity();
            member.setUser(user);
            member.setEventEntity(persistedEvent);
            member.setCreatedDate(LocalDateTime.now());
            return member;
        }).collect(Collectors.toList());

        log.info("members : {}", members);

        eventMembersService.saveAll(members);

        Long membersTaggedCount = members.stream().count();
        if(membersTaggedCount == null)
            membersTaggedCount = 0L;

        model.addAttribute("membersTaggedCount", membersTaggedCount);
        log.info("membersTaggedCount : {}", membersTaggedCount);

        log.info("User mapping complete for event: {}", persistedEvent.getId());
        return "redirect:/events";
    }

    @GetMapping("/mappedUsers")
    public String getMappedUser(Model model, @RequestParam("eventId") Long eventId) {
        log.info("Fetching Mapped Users for Event ID: {}", eventId);
        EventEntity event = eventService.fetchEventById(eventId);

        if (event == null) {
            log.error("Event with ID {} not found!", eventId);
            return "redirect:/events?error=event_not_found";
        }

        List<EventMembersEntity> members = event.getMembers();
        log.info("Members for Event ID {}: {}", eventId, members);

        if (members == null) {
            members = List.of();
        }

        List<EventMembersEntity> finalMembers = members;
        List<User> userEntityList = userService.getAllUsers().stream()
                .filter(user -> finalMembers.stream()
                        .anyMatch(member -> member.getUser().getId().equals(user.getId())))
                .toList();

        log.info("Mapped Users for Event ID {}: {}", eventId, userEntityList);

        List<ExpenseEntity> expenseEntities = expenseService.findExpensesByEventId(eventId);

        Map<Long, BigDecimal> userExpenseTotals = expenseEntities.stream()
                .collect(Collectors.groupingBy(
                        expense -> expense.getUser().getId(),
                        Collectors.mapping(ExpenseEntity::getAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        model.addAttribute("event", event);
        model.addAttribute("users", userEntityList);
        model.addAttribute("members", members);
        model.addAttribute("userExpenseTotals", userExpenseTotals);

        return "events/mappedUserlist";
    }
}

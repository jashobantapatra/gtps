package com.jasho.gtps.controller;

import com.jasho.gtps.dto.EventDto;
import com.jasho.gtps.facade.EventFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 24-10-2024
 */
@RestController
@RequestMapping("api/events")
public class EventController {

    @Autowired
    private EventFacade eventFacade;

    @PostMapping
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        EventDto eventDto1 = eventFacade.saveEvent(eventDto);
        return new ResponseEntity<>(eventDto1, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<EventDto>> fetchAllEvents() {
        List<EventDto> eventDtos = eventFacade.fetchAllEvents();
        return ResponseEntity.ok(eventDtos);
    }
}

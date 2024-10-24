package com.jasho.gtps.facade;

import com.jasho.gtps.dto.EventDto;
import com.jasho.gtps.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 24-10-2024
 */

@Component
@Slf4j
public class EventFacadeImpl implements EventFacade {

    @Autowired
    private EventService eventService;

    @Override
    public EventDto saveEvent(EventDto eventDto) {
        log.info("saving event details...");
        EventDto eventDto1 = eventService.saveEvent(eventDto);
        log.info("Event details saved successfully.");
        return eventDto1;
    }

    @Override
    public List<EventDto> fetchAllEvents() {
        return List.of();
    }

    @Override
    public EventDto fetchEventById(Long id) {
        return null;
    }
}

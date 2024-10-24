package com.jasho.gtps.service;

import com.jasho.gtps.dto.EventDto;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 24-10-2024
 */
public interface EventService {

    EventDto saveEvent(EventDto eventDto);

    List<EventDto> fetchAllEvents();

    EventDto fetchEventById(Long id);
}

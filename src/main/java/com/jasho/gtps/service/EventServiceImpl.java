package com.jasho.gtps.service;

import com.jasho.gtps.dto.EventDto;
import com.jasho.gtps.entity.EventEntity;
import com.jasho.gtps.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 24-10-2024
 */
@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public EventDto saveEvent(EventDto eventDto) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventName(eventDto.getEventName());
        EventEntity savedEventEntity = eventRepository.save(eventEntity);
        EventDto eventDto1 = new EventDto();
        eventDto1.setEventId(savedEventEntity.getEventId());
        eventDto1.setEventName(savedEventEntity.getEventName());
        eventDto1.setCreatedBy(String.valueOf(savedEventEntity.getCreatedBy()));
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

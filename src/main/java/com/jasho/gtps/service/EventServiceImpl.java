package com.jasho.gtps.service;

import com.jasho.gtps.entity.EventEntity;
import com.jasho.gtps.repository.EventRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Jashobanta Patra
 * crated on 04-11-2024
 */
@Service
@AllArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService{

    @Autowired
    private EventRepository eventRepository;
    @Override
    public EventEntity saveEvent(EventEntity eventEntity) {
        log.info("saving event details...");
        return eventRepository.save(eventEntity);
    }

    @Override
    public List<EventEntity> fetchAllEvent() {
        log.info("fetching event details...");
        return eventRepository.findAll();
    }

    @Override
    public EventEntity fetchEventById(long id) {
        log.info("fetching event details with id : {}",id);
        return eventRepository.findById(id).get();
    }

    @Override
    public void deleteEvent(long id) {
        log.info("deleting event with id : {}", id);
        eventRepository.deleteById(id);
    }
}

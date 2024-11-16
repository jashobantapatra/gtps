package com.jasho.gtps.service;

import com.jasho.gtps.entity.EventEntity;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 04-11-2024
 */
public interface EventService {

    EventEntity saveEvent(EventEntity eventEntity);

    List<EventEntity> fetchAllEvent();

    EventEntity fetchEventById(long id);

    void deleteEvent(long id);
}

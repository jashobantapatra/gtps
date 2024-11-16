package com.jasho.gtps.service;

import com.jasho.gtps.entity.EventMembersEntity;
import com.jasho.gtps.repository.EventMembersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 06-11-2024
 */
@Service
@AllArgsConstructor
@Slf4j
public class EventMembersServiceImpl implements EventMembersService{

    @Autowired
    private EventMembersRepository eventMembersRepository;
    @Override
    public void saveAll(List<EventMembersEntity> members) {
        log.info("saving event members details ...");
        eventMembersRepository.saveAll(members);
    }
}

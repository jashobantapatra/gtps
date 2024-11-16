package com.jasho.gtps.service;

import com.jasho.gtps.entity.EventMembersEntity;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 06-11-2024
 */
public interface EventMembersService {
    void saveAll(List<EventMembersEntity> members);
}

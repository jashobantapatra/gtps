package com.jasho.gtps.service;

import com.jasho.gtps.dto.GroupDto;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 13-08-2024
 */
public interface GroupService {
    GroupDto saveGroup(GroupDto groupDto);

    List<GroupDto> fetchAllGroup();

    GroupDto fetchGroupById(Long id);
}

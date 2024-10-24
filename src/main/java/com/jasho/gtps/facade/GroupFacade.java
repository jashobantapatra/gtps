package com.jasho.gtps.facade;

import com.jasho.gtps.dto.GroupDto;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 13-08-2024
 */
public interface GroupFacade {
    GroupDto saveGroup(GroupDto groupDto);

    List<GroupDto> fetchAllGroups();

    GroupDto findGroupById(Long id);
}

package com.jasho.gtps.facade;

import com.jasho.gtps.dto.GroupDto;
import com.jasho.gtps.service.GroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 13-08-2024
 */
@Component
@Slf4j
@AllArgsConstructor
public class GroupFacadeImpl implements GroupFacade {

    private GroupService groupService;

    @Override
    public GroupDto saveGroup(GroupDto groupDto) {
        log.info("saving group details...");
        GroupDto groupDto1 = groupService.saveGroup(groupDto);
        log.info("group details saved successfully.");
        return groupDto1;
    }

    @Override
    public List<GroupDto> fetchAllGroups() {
        log.info("fetching of all groups...");
        return groupService.fetchAllGroup();
    }

    @Override
    public GroupDto findGroupById(Long id) {
        log.info("fetching group with id: {}", id);
        return groupService.fetchGroupById(id);
    }
}

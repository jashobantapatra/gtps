package com.jasho.gtps.controller;

import com.jasho.gtps.dto.GroupDto;
import com.jasho.gtps.facade.GroupFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 13-08-2024
 */
@RestController
@RequestMapping("api/groups")
@AllArgsConstructor
public class GroupController {

    private GroupFacade groupFacade;

    @PostMapping()
    public ResponseEntity<GroupDto> saveGroup(@RequestBody GroupDto groupDto) {
        GroupDto responseGroupDto = groupFacade.saveGroup(groupDto);
        return new ResponseEntity<>(responseGroupDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> fetchAllGroups() {
        return ResponseEntity.ok(groupFacade.fetchAllGroups());
    }
}

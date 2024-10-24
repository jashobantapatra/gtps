package com.jasho.gtps.service;

import com.jasho.gtps.dto.GroupDto;
import com.jasho.gtps.entity.GroupEntity;
import com.jasho.gtps.exception.ResourceNotFoundException;
import com.jasho.gtps.repository.GroupRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jashobanta Patra
 * crated on 13-08-2024
 */
@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private ModelMapper modelMapper;

    @Override
    public GroupDto saveGroup(GroupDto groupDto) {
        GroupEntity groupEntity = groupRepository.save(modelMapper.map(groupDto, GroupEntity.class));
        return modelMapper.map(groupEntity, GroupDto.class);
    }

    @Override
    public List<GroupDto> fetchAllGroup() {
        return groupRepository.findAll()
                .stream()
                .map(groupEntity -> modelMapper.map(groupEntity, GroupDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GroupDto fetchGroupById(Long id) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Group Not Found with id: " + id));
        return modelMapper.map(groupEntity, GroupDto.class);
    }
}

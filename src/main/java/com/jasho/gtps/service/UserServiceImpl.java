package com.jasho.gtps.service;

import com.jasho.gtps.dto.UserDto;
import com.jasho.gtps.entity.UserEntity;
import com.jasho.gtps.exception.ResourceNotFoundException;
import com.jasho.gtps.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDto saveUser(UserDto userDto) {
        UserEntity responseEntity = userRepository.save(modelMapper.map(userDto, UserEntity.class));
        return modelMapper.map(responseEntity, UserDto.class);
    }

    @Override
    public List<UserDto> fetchAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto fetchUserById(long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found with ID: " + id));
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public void deleteUser(long id) {

    }
}

package com.jasho.gtps.service;

import com.jasho.gtps.dto.UserDto;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
public interface UserService {
    UserDto saveUser(UserDto userDto);

    List<UserDto> fetchAllUsers();

    UserDto fetchUserById(long id);

    void deleteUser(long id);
}

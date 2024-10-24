package com.jasho.gtps.facade;

import com.jasho.gtps.dto.UserDto;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
public interface UserFacade {
    UserDto saveUserDetails(UserDto userDto);

    List<UserDto> fetchAllUsers();

    UserDto fetchUserbyId(long id);

    String deleteUser(long id);
}

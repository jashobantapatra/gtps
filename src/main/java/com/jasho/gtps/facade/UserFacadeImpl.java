package com.jasho.gtps.facade;

import com.jasho.gtps.dto.UserDto;
import com.jasho.gtps.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
@Component
@AllArgsConstructor
@Slf4j
public class UserFacadeImpl implements UserFacade {

    private UserService userService;

    @Override
    public UserDto saveUserDetails(UserDto userDto) {
        log.info("saving user details...");
        UserDto userDto1 = userService.saveUser(userDto);
        log.info("User details saved successfully.");
        return userDto1;
    }

    @Override
    public List<UserDto> fetchAllUsers() {
        return userService.fetchAllUsers();
    }

    @Override
    public UserDto fetchUserbyId(long id) {
        return userService.fetchUserById(id);
    }

    @Override
    public String deleteUser(long id) {
        return "";
    }
}

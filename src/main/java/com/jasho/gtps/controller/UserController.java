package com.jasho.gtps.controller;

import com.jasho.gtps.dto.UserDto;
import com.jasho.gtps.facade.UserFacade;
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
 * crated on 09-08-2024
 */
@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {

    private UserFacade userFacade;

    @PostMapping()
    public ResponseEntity<UserDto> saveUserDetails(@RequestBody UserDto userDto) {
        UserDto userDto1 = userFacade.saveUserDetails(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<UserDto>> fetchAllUsers() {
        List<UserDto> userDtos = userFacade.fetchAllUsers();
        return ResponseEntity.ok(userDtos);
    }
}

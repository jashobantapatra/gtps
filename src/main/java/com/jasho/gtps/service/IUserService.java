package com.jasho.gtps.service;

import com.jasho.gtps.dto.RegistrationRequest;
import com.jasho.gtps.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */
public interface IUserService {
    List<User> getAllUsers();

    User registerUser(RegistrationRequest registrationRequest);

    User findByEmail(String email);

    Optional<User> findById(Long id);

    void updateUser(Long id, String firstName, String lastName, String email);

    void deleteUser(Long id);
}

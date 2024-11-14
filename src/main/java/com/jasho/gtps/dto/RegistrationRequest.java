package com.jasho.gtps.dto;

import com.jasho.gtps.entity.Role;
import lombok.Data;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */

@Data
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Role> roles;
}

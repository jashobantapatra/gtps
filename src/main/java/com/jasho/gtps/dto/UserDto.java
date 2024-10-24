package com.jasho.gtps.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;
    //private List<Long> expensesDtos;
}

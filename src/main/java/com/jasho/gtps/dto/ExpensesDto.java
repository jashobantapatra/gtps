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
public class ExpensesDto {
    private Long id;
    private Long userId;
    private String expensesName;
    private Double amount;
    /*private String createdBy;
    private String createdDate;
    private String updatedBy;
    private String updatedDate;*/

}

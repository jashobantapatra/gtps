package com.jasho.gtps.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author Jashobanta Patra
 * crated on 07-11-2024
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ExpensesDto {

    private Long eventId;    // Event the expense is associated with

    private Long userId;

    private String description;

    private BigDecimal amount;

    private String expenseDate;


}

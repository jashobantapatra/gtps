package com.jasho.gtps.dto;

import com.jasho.gtps.entity.ExpenseEntity;
import com.jasho.gtps.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Jashobanta Patra
 * crated on 20-11-2024
 */
@Data
@AllArgsConstructor
public class ExpenseReportDto {
    private User user;
    private Map<LocalDate, List<ExpenseEntity>> expensesByDate;
    private BigDecimal totalAmount;
    private BigDecimal balance;
}

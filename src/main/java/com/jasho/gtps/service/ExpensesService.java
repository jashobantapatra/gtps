package com.jasho.gtps.service;

import com.jasho.gtps.dto.ExpensesDto;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
public interface ExpensesService {
    ExpensesDto saveExpenses(ExpensesDto expensesDto);

    List<ExpensesDto> fetchAllExpenses();
}

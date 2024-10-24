package com.jasho.gtps.facade;

import com.jasho.gtps.dto.ExpensesDto;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
public interface ExpensesFacade {
    ExpensesDto saveExpenses(ExpensesDto expensesDto);

    List<ExpensesDto> fetchAllExpenses();
}

package com.jasho.gtps.service;

import com.jasho.gtps.entity.ExpenseEntity;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
public interface ExpenseService {
    ExpenseEntity saveExpenses(ExpenseEntity expenseEntity);

    List<ExpenseEntity> fetchAllExpenses();

    ExpenseEntity fetchExpenseById(long id);

    void saveAll(List<ExpenseEntity> expenseEntities);
}

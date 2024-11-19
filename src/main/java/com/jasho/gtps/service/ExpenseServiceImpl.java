package com.jasho.gtps.service;

import com.jasho.gtps.entity.ExpenseEntity;
import com.jasho.gtps.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
@Service
@AllArgsConstructor
@Slf4j
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public ExpenseEntity saveExpenses(ExpenseEntity expenseEntity) {
        log.info("saving expenses data...{}", expenseEntity);
        return expenseRepository.save(expenseEntity);
    }

    @Override
    public List<ExpenseEntity> fetchAllExpenses() {
        return List.of();
    }

    @Override
    public ExpenseEntity fetchExpenseById(long id) {
        return null;
    }

    @Override
    public void saveAll(List<ExpenseEntity> expenseEntities) {
        log.info("saving all the expenses...");
        expenseRepository.saveAll(expenseEntities);
    }
}

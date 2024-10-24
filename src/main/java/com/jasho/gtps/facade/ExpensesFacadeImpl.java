package com.jasho.gtps.facade;

import com.jasho.gtps.dto.ExpensesDto;
import com.jasho.gtps.service.ExpensesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
@Component
@AllArgsConstructor
@Slf4j
public class ExpensesFacadeImpl implements ExpensesFacade {

    private ExpensesService expensesService;

    @Override
    public ExpensesDto saveExpenses(ExpensesDto expensesDto) {
        log.info("saving expenses details...");
        ExpensesDto expensesDto1 = expensesService.saveExpenses(expensesDto);
        log.info("expenses details saved successfully.");
        return expensesDto1;
    }

    @Override
    public List<ExpensesDto> fetchAllExpenses() {
        log.info("fetching expenses details...");
        return expensesService.fetchAllExpenses();
    }
}

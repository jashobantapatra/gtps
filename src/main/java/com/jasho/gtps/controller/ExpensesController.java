package com.jasho.gtps.controller;

import com.jasho.gtps.dto.ExpensesDto;
import com.jasho.gtps.facade.ExpensesFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 13-08-2024
 */
@RestController
@RequestMapping("api/expenses")
@AllArgsConstructor
public class ExpensesController {

    private ExpensesFacade expensesFacade;

    @PostMapping
    public ResponseEntity<ExpensesDto> saveExpenses(@RequestBody ExpensesDto expensesDto) {
        ExpensesDto expensesDto1 = expensesFacade.saveExpenses(expensesDto);
        return new ResponseEntity<>(expensesDto1, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ExpensesDto>> fetchAllExpenses() {
        List<ExpensesDto> expensesDtos = expensesFacade.fetchAllExpenses();
        return ResponseEntity.ok(expensesDtos);
    }
}

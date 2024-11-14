package com.jasho.gtps.service;

import com.jasho.gtps.dto.ExpensesDto;
import com.jasho.gtps.entity.ExpensesEntity;
import com.jasho.gtps.exception.ResourceNotFoundException;
import com.jasho.gtps.repository.ExpensesRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
@Service
@AllArgsConstructor
public class ExpensesServiceImpl implements ExpensesService {

    private ModelMapper modelMapper;
    private ExpensesRepository expensesRepository;

    @Override
    public ExpensesDto saveExpenses(ExpensesDto expensesDto) {
        ExpensesEntity expensesEntity = expensesRepository.save(modelMapper.map(expensesDto, ExpensesEntity.class));
        return modelMapper.map(expensesEntity, ExpensesDto.class);
    }

    @Override
    public List<ExpensesDto> fetchAllExpenses() {
        return expensesRepository.findAll()
                .stream()
                .map(expensesEntity -> modelMapper.map(expensesEntity, ExpensesDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ExpensesDto fetchExpenseById(long id) {
        ExpensesEntity expensesEntity = expensesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expenses Not Found with ID: " + id));
        return modelMapper.map(expensesEntity, ExpensesDto.class);
    }
}

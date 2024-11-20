package com.jasho.gtps.dto;

import com.jasho.gtps.entity.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Jashobanta Patra
 * crated on 20-11-2024
 */
@Data
@AllArgsConstructor
public class EventWithExpense {
    private EventEntity event;
    private BigDecimal totalExpense;
}

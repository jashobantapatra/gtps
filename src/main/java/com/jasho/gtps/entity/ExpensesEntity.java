package com.jasho.gtps.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "expenses")
public class ExpensesEntity extends BaseEntity {

    @Column(name = "expenses_name")
    private String expensesName;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "user_id")
    private Long userId;
}

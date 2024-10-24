package com.jasho.gtps.repository;

import com.jasho.gtps.entity.ExpensesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
public interface ExpensesRepository extends JpaRepository<ExpensesEntity, Long> {
}

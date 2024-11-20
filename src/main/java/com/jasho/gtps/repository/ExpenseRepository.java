package com.jasho.gtps.repository;

import com.jasho.gtps.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    // Fetch total expense amount for a specific event
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM ExpenseEntity e WHERE e.event.id = :eventId")
    BigDecimal findTotalAmountByEventId(@Param("eventId") Long eventId);

    @Query("SELECT e FROM ExpenseEntity e where e.event.id =:eventId")
    List<ExpenseEntity> findExpensesByEventId(@Param("eventId") Long eventId);
}

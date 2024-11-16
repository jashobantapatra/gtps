package com.jasho.gtps.repository;

import com.jasho.gtps.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jashobanta Patra
 * crated on 04-11-2024
 */
public interface EventRepository extends JpaRepository<EventEntity, Long> {
}

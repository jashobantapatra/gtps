package com.jasho.gtps.repository;

import com.jasho.gtps.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jashobanta Patra
 * crated on 12-08-2024
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}

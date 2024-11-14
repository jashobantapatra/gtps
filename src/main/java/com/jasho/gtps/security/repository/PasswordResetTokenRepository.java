package com.jasho.gtps.security.repository;

import com.jasho.gtps.security.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Jashobanta Patra
 * crated on 12-11-2024
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String theToken);
}

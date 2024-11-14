package com.jasho.gtps.security.repository;

import com.jasho.gtps.security.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    void deleteByUserId(Long id);
}

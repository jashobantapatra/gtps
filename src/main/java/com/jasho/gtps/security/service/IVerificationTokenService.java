package com.jasho.gtps.security.service;

import com.jasho.gtps.entity.User;
import com.jasho.gtps.security.entity.VerificationToken;

import java.util.Optional;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */
public interface IVerificationTokenService {
    String validateToken(String token);

    void saveVerificationTokenForUser(User user, String token);

    Optional<VerificationToken> findByToken(String token);

    void deleteUserToken(Long id);
}

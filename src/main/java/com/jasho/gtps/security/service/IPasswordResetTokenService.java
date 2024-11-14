package com.jasho.gtps.security.service;

import com.jasho.gtps.entity.User;

import java.util.Optional;

/**
 * @author Jashobanta Patra
 * crated on 12-11-2024
 */
public interface IPasswordResetTokenService {
    String validatePasswordResetToken(String theToken);

    Optional<User> findUserByPasswordResetToken(String theToken);

    void resetPassword(User theUser, String password);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);
}

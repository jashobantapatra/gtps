package com.jasho.gtps.security.service;

import com.jasho.gtps.entity.User;
import com.jasho.gtps.repository.UserRepository;
import com.jasho.gtps.security.entity.PasswordResetToken;
import com.jasho.gtps.security.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

/**
 * @author Jashobanta Patra
 * crated on 12-11-2024
 */
@Service
@RequiredArgsConstructor
public class PasswordResetTokenService implements IPasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String validatePasswordResetToken(String theToken) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(theToken);
        if (passwordResetToken.isEmpty()) {
            return "invalid";
        }
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.get().getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "expired";
        }

        return "valid";
    }

    @Override
    public Optional<User> findUserByPasswordResetToken(String theToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(theToken).get().getUser());
    }

    @Override
    public void resetPassword(User theUser, String newPassword) {
        theUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(theUser);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordResetToken) {
        PasswordResetToken resetToken = new PasswordResetToken(passwordResetToken, user);
        passwordResetTokenRepository.save(resetToken);

    }
}

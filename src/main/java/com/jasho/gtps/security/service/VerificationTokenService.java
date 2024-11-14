package com.jasho.gtps.security.service;

import com.jasho.gtps.entity.User;
import com.jasho.gtps.repository.UserRepository;
import com.jasho.gtps.security.entity.VerificationToken;
import com.jasho.gtps.security.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */
@Service
@RequiredArgsConstructor
public class VerificationTokenService implements IVerificationTokenService {
    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Override
    public String validateToken(String token) {
        Optional<VerificationToken> verificationToken = tokenRepository.findByToken(token);
        if (verificationToken.isEmpty()) {
            return "INVALID";
        }
        User user = verificationToken.get().getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.get().getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "EXPIRED";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "VALID";
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        var verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void deleteUserToken(Long id) {
        tokenRepository.deleteByUserId(id);
    }
}

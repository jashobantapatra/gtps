package com.jasho.gtps.security.service;

import com.jasho.gtps.repository.UserRepository;
import com.jasho.gtps.security.GtpsUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */
@Service
@RequiredArgsConstructor
public class EndToEndUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(GtpsUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

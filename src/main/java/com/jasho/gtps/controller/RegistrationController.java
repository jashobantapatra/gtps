package com.jasho.gtps.controller;

import com.jasho.gtps.dto.RegistrationRequest;
import com.jasho.gtps.entity.User;
import com.jasho.gtps.event.RegistrationCompleteEvent;
import com.jasho.gtps.event.listener.RegistrationCompleteEventListener;
import com.jasho.gtps.security.entity.VerificationToken;
import com.jasho.gtps.security.service.IPasswordResetTokenService;
import com.jasho.gtps.security.service.VerificationTokenService;
import com.jasho.gtps.service.IUserService;
import com.jasho.gtps.utility.UrlUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */
@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final IUserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenService tokenService;
    private final IPasswordResetTokenService passwordResetTokenService;
    private final RegistrationCompleteEventListener eventListener;

    @GetMapping("/registration-form")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegistrationRequest registration,
                               HttpServletRequest request) {
        if(userService.isEmailExist(registration.getEmail())){
            return "redirect:/registration/registration-form?duplicateEmail";
        }
        User user = userService.registerUser(registration);
        //publish the verification email event here
        publisher.publishEvent(new RegistrationCompleteEvent(user, UrlUtil.getApplicatiionUrl(request)));
        return "redirect:/registration/registration-form?success";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        Optional<VerificationToken> theToken = tokenService.findByToken(token);
        if (theToken.isPresent() && theToken.get().getUser().isEnabled()) {
            return "redirect:/login?verified";
        }
        String verificationResult = tokenService.validateToken(token);
        switch (verificationResult.toLowerCase()) {
            case "expired":
                return "redirect:/error?expired";
            case "valid":
                return "redirect:/login?valid";
            default:
                return "redirect:/error?invalid";
        }
    }

    @GetMapping("/forgot-password-request")
    public String forgotPasswordForm() {
        return "forgot-password-form";
    }

    @PostMapping("/forgot-password")
    public String resetPasswordRequest(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        User user = userService.findByEmail(email);
        if (user == null) {
            return "redirect:/registration/forgot-password-request?not_found";
        }
        String passwordResetToken = UUID.randomUUID().toString();
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordResetToken);
        //send password reset verification email to the user
        String url = UrlUtil.getApplicatiionUrl(request) + "/registration/password-reset-form?token=" + passwordResetToken;
        try {
            eventListener.sendPasswordResetVerificationEmail(url, user);
        } catch (MessagingException | UnsupportedEncodingException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/registration/forgot-password-request?success";
    }

    @GetMapping("/password-reset-form")//reset-password-form
    public String passwordResetForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "password-reset-form";
    }

    @PostMapping("/reset-password")
    public String resetPassword(HttpServletRequest request) {
        String theToken = request.getParameter("token");
        String password = request.getParameter("password");
        String tokenVerificationResult = passwordResetTokenService.validatePasswordResetToken(theToken);
        if (!tokenVerificationResult.equalsIgnoreCase("valid")) {
            return "redirect:/error?invalid_token";
        }
        Optional<User> theUser = passwordResetTokenService.findUserByPasswordResetToken(theToken);
        if (theUser.isPresent()) {
            passwordResetTokenService.resetPassword(theUser.get(), password);
            return "redirect:/login?reset_success";
        }
        return "redirect:/error?not_found";
    }
}

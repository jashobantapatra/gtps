package com.jasho.gtps.event.listener;

import com.jasho.gtps.entity.User;
import com.jasho.gtps.event.RegistrationCompleteEvent;
import com.jasho.gtps.security.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender mailSender;
    private User user;

    private static void emailMessage(String subject, String senderName,
                                     String mailContent, JavaMailSender mailSender, User user) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("jashobantapatra@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //1. get the user
        user = event.getUser();

        //2. generate a token for the user
        String vToken = UUID.randomUUID().toString();

        //3. save the token for the user
        verificationTokenService.saveVerificationTokenForUser(user, vToken);

        //4. Build the verification url
        String url = event.getConfirmationUrl() + "/registration/verifyEmail?token=" + vToken;

        //5. send the email to the user
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Verification Service";
        String mailContent = "<p> Hi, " + user.getFirstName() + ", </p>" +
                "<p> Thank you for registering with us," + "" +
                "Please, follow the link below to complete your registration.</p>" +
                "<a href=\"" + url + "\">Verify your email to activat your account</a>" +
                "<p> Thank you <br> Users Registration Portal Service";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }

    public void sendPasswordResetVerificationEmail(String url, User user) throws MessagingException, UnsupportedEncodingException {
        String subject = "Password Reset Reset Verification";
        String senderName = "Users Verification Service";
        String mailContent = "<p> Hi, " + user.getFirstName() + ", </p>" +
                "<p> You recently requested to reset your password,</b>" + "" +
                "Please, follow the link below to complete the action.</p>" +
                "<a href=\"" + url + "\">Reset password</a>" +
                "<p> Thank you <br> Users Registration Portal Service";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}

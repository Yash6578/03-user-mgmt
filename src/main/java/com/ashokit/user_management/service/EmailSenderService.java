package com.ashokit.user_management.service;

import javax.mail.MessagingException;

public interface EmailSenderService {
    boolean sendEmail(String to, String subject, String body);
    String getActivationMailBody(String name, String password);
    String getForgetPasswordMailBody(String name, String password);
}

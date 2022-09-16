package com.ashokit.user_management.service.impl;

import com.ashokit.user_management.service.EmailSenderService;
import lombok.AllArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;


@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    final JavaMailSender mailSender;

    @Override
    public boolean sendEmail(String to, String subject, String body)  {
        boolean isMailSent = false;

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            mailSender.send(mimeMessage);
            isMailSent = true;

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return isMailSent;
    }

    public String getActivationMailBody(String name, String password) {
        try(FileReader fileReader = new FileReader("src/main/java/com/ashokit/user_management/email/template/ActivationTemplate.txt")
        ;BufferedReader buffer = new BufferedReader(fileReader)) {

            String mailBody;
            String line;
            StringBuilder sb = new StringBuilder();

            while(null != (line = buffer.readLine()))
                sb.append(line);

            mailBody = sb.toString();
            mailBody = mailBody.replace("{NAME}", name);
            mailBody = mailBody.replace("{PASSWORD}", password);
            mailBody = mailBody.replace("{URL}", "");

            return mailBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getForgetPasswordMailBody(String name, String password) {
        try(FileReader fileReader = new FileReader("src/main/java/com/ashokit/user_management/email/template/ForgetMailTemplate.txt")
            ;BufferedReader buffer = new BufferedReader(fileReader)) {

            String mailBody;
            String line;
            StringBuilder sb = new StringBuilder();

            while(null != (line = buffer.readLine()))
                sb.append(line);

            mailBody = sb.toString();
            mailBody = mailBody.replace("{NAME}", name);
            mailBody = mailBody.replace("{PASSWORD}", password);

            return mailBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

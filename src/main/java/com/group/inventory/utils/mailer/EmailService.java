package com.group.inventory.utils.mailer;

import com.group.inventory.common.exception.InventoryBusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

public interface EmailService {
    Email sendMail(Email email);

    Email sendMail(Email email, MultipartFile attachment);

    Email sendMail(Email email, MultipartFile[] attachments);
}

@Service
class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public Email sendMail(Email email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setFrom(sender);
            mailMessage.setTo(email.getRecipients().toArray(String[]::new));
            mailMessage.setText(email.getContent());
            mailMessage.setSubject(email.getSubject());
            mailMessage.setCc(email.getCarbonCopy().toArray(String[]::new));
            mailMessage.setBcc(email.getBlindCarbonCopy().toArray(String[]::new));
            mailSender.send(mailMessage);
        } catch (MailException e) {
            throw new InventoryBusinessException(e.getMessage());
        }
        return email;
    }

    @Override
    public Email sendMail(Email email, MultipartFile attachment) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(email.getRecipients().toArray(String[]::new));
            mimeMessageHelper.setText(email.getContent());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setCc(email.getCarbonCopy().toArray(String[]::new));
            mimeMessageHelper.setBcc(email.getBlindCarbonCopy().toArray(String[]::new));

            mimeMessageHelper.addAttachment(
                    Objects.requireNonNull(attachment.getName()), attachment);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new InventoryBusinessException(e.getMessage());
        }

        return email;
    }

    @Override
    public Email sendMail(Email email, MultipartFile[] attachments) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {

            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(email.getRecipients().toArray(String[]::new));
            mimeMessageHelper.setText(email.getContent());
            mimeMessageHelper.setSubject(email.getSubject());
            mimeMessageHelper.setCc(email.getCarbonCopy().toArray(String[]::new));
            mimeMessageHelper.setBcc(email.getBlindCarbonCopy().toArray(String[]::new));

            for (MultipartFile attachment : attachments
            ) {
                mimeMessageHelper.addAttachment(
                        Objects.requireNonNull(attachment.getName()), attachment);
            }

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new InventoryBusinessException(e.getMessage());
        }

        return email;
    }
}

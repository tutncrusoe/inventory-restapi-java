package com.group.inventory.utils.mailer;

import com.group.inventory.common.util.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/emails")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/messages")
    public Object sendMessageEmail(@RequestBody @Valid Email email) {
        return ResponseHelper.getResponse(
                emailService.sendMail(email),
                HttpStatus.OK
        );
    }

    @PostMapping("/attachment")
    public Object sendAttachmentEmail(@ModelAttribute @Valid Email email,
                                      @RequestParam MultipartFile attachment) {
        return ResponseHelper.getResponse(
                emailService.sendMail(email, attachment),
                HttpStatus.OK
        );
    }

    @PostMapping("/attachments")
    public Object sendAttachmentsEmail(@ModelAttribute @Valid Email email,
                                      @RequestParam MultipartFile[] attachments) {
        return ResponseHelper.getResponse(
                emailService.sendMail(email, attachments),
                HttpStatus.OK
        );
    }
}

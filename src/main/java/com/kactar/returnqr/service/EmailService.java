package com.kactar.returnqr.service;

import com.kactar.returnqr.model.Parcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final String from;
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender mailSender,
                        @Value("${app.mail.from:returnqrapp@gmail.com}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    @Async
    public void sendParcelStatusEmail(String to, Parcel parcel) {
        if (to == null || to.isBlank()) {
            logger.warn("sendParcelStatusEmail: empty delivery address, parcel id={}", parcel != null ? safeId(parcel) : "null");
            return;
        }

        String subject = "ReturnQR - parcel status update";
        String body = buildBody(parcel);

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(from);
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(body);
            mailSender.send(msg);
            logger.info("Email sent for parcel with id {}", parcel != null ? safeId(parcel) : "?");
        } catch (Exception e) {
            logger.error("Error sending email to {} for parcel {}", to, parcel != null ? safeId(parcel) : "?", e);
        }
    }

    private String buildBody(Parcel parcel) {
        String id = parcel != null ? safeId(parcel) : "-";
        String status = parcel != null && parcel.getParcelStatus() != null ? parcel.getParcelStatus().name() : "update";
        return "Your parcel (id: " + id + ") changed status to: " + status + ".\nReturnQR";
    }

    private String safeId(Parcel p) {
        if (p == null) return "-";
        try {
            Object raw = p.getId();
            return raw != null ? String.valueOf(raw) : "-";
        } catch (Throwable t) {
            return "-";
        }
    }
}

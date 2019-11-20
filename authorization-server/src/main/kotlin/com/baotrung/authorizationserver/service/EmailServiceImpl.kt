package com.baotrung.authorizationserver.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl(private val mailSender: JavaMailSender) : EmailService {

    @Async
    override fun sendEmail(email: SimpleMailMessage) {
        mailSender.send(email);
    }
}

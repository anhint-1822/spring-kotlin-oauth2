package com.baotrung.springkotlincrud.authorizationserver.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class EmailServiceImpl : EmailService {

    @Autowired
    private val mailSender: JavaMailSender? = null

    @Async
    override fun sendEmail(email: SimpleMailMessage) {
        mailSender!!.send(email);
    }
}
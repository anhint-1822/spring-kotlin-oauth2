package com.baotrung.authorizationserver.service

import org.springframework.mail.SimpleMailMessage

interface EmailService {
    fun sendEmail(email: SimpleMailMessage)
}

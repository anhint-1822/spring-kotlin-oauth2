package com.baotrung.springkotlincrud.authorizationserver.config

import org.apache.tomcat.jni.SSL.setPassword
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.JavaMailSender


@Configuration
class EmailConfig {

    @Value("\${email.host}")
    private val emailHost: String = ""


    @Value("\${email.port}")
    private val emailPort: Int? = null


    @Value("\${email.username}")
    private val userName: String? = null


    @Value("\${email.password}")
    private val password: String? = null

    @Value("\${email.smtp}")
    private val protocol: String? = null

    @Value("\${email.auth}")
    private val auth: String? = null

    @Value("\${email.tls}")
    private val tls: String? = null


    @Value("\${email.debug}")
    private val debug: String? = null


    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = this.emailHost
        mailSender.port = this.emailPort!!

        mailSender.username = this.userName
        mailSender.password = this.password

        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = this.protocol
        props["mail.smtp.auth"] = this.auth
        props["mail.smtp.starttls.enable"] = this.tls
        props["mail.debug"] = this.debug

        return mailSender
    }



}
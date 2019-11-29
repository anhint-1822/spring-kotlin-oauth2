package com.baotrung.authorizationserver.service

import com.baotrung.authorizationserver.dto.request.OtpAuthenticateReqDto
import com.baotrung.authorizationserver.dto.request.OtpValidateReqDto
import com.baotrung.authorizationserver.security.OtpAuthenticationProvider
import com.baotrung.authorizationserver.security.OtpAuthenticationToken
import com.baotrung.authorizationserver.util.RandomUtil
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class OtpService(private val otpAuthenticationProvider: OtpAuthenticationProvider,
                 private val redisTemplate: RedisTemplate<String, String>,
                 private val emailService: EmailService) {

    fun authenticate(reqDto: OtpAuthenticateReqDto) {
        val authenticationToken = OtpAuthenticationToken(reqDto.email, mutableListOf())
        val authentication = otpAuthenticationProvider.authenticate(authenticationToken)
        val otp = generateOtp()
        storeOtp(authentication.name, otp)
        sendEmail(authentication.name, otp)
    }

    fun validateOtp(reqDto: OtpValidateReqDto): Boolean {
        val cachedOtp = redisTemplate.opsForValue().get(reqDto.email)
        if (cachedOtp.isNullOrBlank() || cachedOtp != reqDto.otp) {
            return false
        }
        redisTemplate.delete(reqDto.email)
        return true
    }

    private fun generateOtp(): String {
        return RandomUtil.generateNumeric(6)
    }

    private fun storeOtp(userId: String, otp: String) {
        redisTemplate.opsForValue().set(userId, otp, 60, TimeUnit.SECONDS)
    }

    private fun sendEmail(email: String, otp: String) {
        val generateOtpMailMessage = SimpleMailMessage()
        generateOtpMailMessage.setFrom("support@demo.com")
        generateOtpMailMessage.setTo(email)
        generateOtpMailMessage.setSubject("Generate OTP")
        generateOtpMailMessage.setText("Your OTP was expired after 60 seconds: $otp")
        emailService.sendEmail(generateOtpMailMessage)
    }
}

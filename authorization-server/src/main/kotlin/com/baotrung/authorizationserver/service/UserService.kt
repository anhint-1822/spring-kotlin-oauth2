package com.baotrung.authorizationserver.service

import com.baotrung.authorizationserver.dto.request.ChangePasswordDto
import com.baotrung.authorizationserver.dto.request.RegisterReqDto
import com.baotrung.authorizationserver.entity.RoleEntity
import com.baotrung.authorizationserver.entity.UserEntity
import com.baotrung.authorizationserver.repository.RoleRepository
import com.baotrung.authorizationserver.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import javax.servlet.http.HttpServletRequest


@Service
class UserService(private val userRepository: UserRepository,
                  private val roleRepository: RoleRepository,
                  private val passwordEncoder: PasswordEncoder) {

    private val LOGGER = LoggerFactory.getLogger(UserService::class.java)

    @Autowired
    private val emailService: EmailService? = null


    @Transactional
    fun register(reqDto: RegisterReqDto) {
        val userOptional = userRepository.findByEmail(reqDto.email)
        if (userOptional.isPresent) {
            throw RuntimeException("Email already exists")
        }

        val user = UserEntity(
                UUID.randomUUID().toString(),
                reqDto.email,
                passwordEncoder.encode(reqDto.password),
                null,
                getRoles(reqDto.roles)
        )
        userRepository.save(user)
    }

    fun getRoles(roles: Set<String>): Set<RoleEntity> {
        return roles.map { role -> roleRepository.findByName(role) }.toSet()
    }

    fun forgotPassword(email: String, httpServletRequest: HttpServletRequest): Boolean {
        val userOptional = userRepository.findByEmail(email)
        if (!userOptional.isPresent) {
            throw RuntimeException("Email not exists")
        }
        val user = UserEntity(
                userOptional.get().id,
                userOptional.get().email,
                userOptional.get().password!!,
                UUID.randomUUID().toString(),
                userOptional.get().roles
        )

        userRepository.save(user)

        val appUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.localPort

        val passwordResetEmail = SimpleMailMessage()
        passwordResetEmail.setFrom("support@demo.com")
        passwordResetEmail.setTo(user.email)
        passwordResetEmail.setSubject("Password Reset Request")
        passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                + "/api/users/reset-password?token=" + user.resetToken)
        var isSent = false
        try {
            emailService!!.sendEmail(passwordResetEmail)
            isSent = true
        } catch (e: Exception) {
            LOGGER.error("Sending e-mail error: {}", e.message)
        }
        return isSent
    }

    fun resetPassword(token: String): Boolean {
        val userOptional = userRepository.findByResetToken(token)
        if (!userOptional.isPresent) {
            throw RuntimeException("Token not correct")
        }

        val passwordReset = UUID.randomUUID().toString().substring(0, 8)
        val user = UserEntity(
                userOptional.get().id,
                userOptional.get().email,
                passwordEncoder.encode(passwordReset),
                null,
                userOptional.get().roles
        )

        userRepository.save(user)

        val passwordResetEmail = SimpleMailMessage()
        passwordResetEmail.setFrom("support@demo.com")
        passwordResetEmail.setTo(user.email)
        passwordResetEmail.setSubject("Password Reset Request")
        passwordResetEmail.setText("Your password has been reset. This is new password : " + passwordReset);

        var isSent = false
        try {
            emailService!!.sendEmail(passwordResetEmail)
            isSent = true
        } catch (e: Exception) {
            LOGGER.error("Sending e-mail error: {}", e.message)
        }
        return isSent

    }

    fun changePassword(changePasswordDto: ChangePasswordDto): String {

        if (changePasswordDto.password != changePasswordDto.confirmPassword) {
            throw IllegalArgumentException("Password and confirm password not match");
        }

        val users = userRepository.findByEmail(changePasswordDto.email)

        if (!users.isPresent) {
            throw IllegalArgumentException("Email not found");
        }
        val userFinal = users.get()
        if (passwordEncoder.matches(changePasswordDto.oldPassword, users.get().password)) {
            users.get().password = passwordEncoder.encode(changePasswordDto.password)
            userRepository.save(userFinal)
            return "Password change success"

        }
        throw IllegalArgumentException("Password not correct")

    }
}

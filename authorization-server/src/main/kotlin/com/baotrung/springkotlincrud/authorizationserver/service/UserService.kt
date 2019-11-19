package com.baotrung.springkotlincrud.authorizationserver.service

import com.baotrung.springkotlincrud.authorizationserver.dto.request.RegisterReqDto
import com.baotrung.springkotlincrud.authorizationserver.entity.RoleEntity
import com.baotrung.springkotlincrud.authorizationserver.entity.UserEntity
import com.baotrung.springkotlincrud.authorizationserver.repository.RoleRepository
import com.baotrung.springkotlincrud.authorizationserver.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import org.springframework.mail.SimpleMailMessage
import org.springframework.beans.factory.annotation.Autowired








@Service
class UserService(private val userRepository: UserRepository,
                  private val roleRepository: RoleRepository,
                  private val passwordEncoder: PasswordEncoder) {

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

    fun forgotPassword(email: String, httpServletRequest: HttpServletRequest) {
        val userOptional = userRepository.findByEmail(email)
        if (!userOptional.isPresent) {
            throw RuntimeException("Email not exists")
        }
        val user = UserEntity(
                userOptional.get().id,
                userOptional.get().email!!,
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
                + "/reset?token=" + user.resetToken)

        emailService!!.sendEmail(passwordResetEmail)

    }

}
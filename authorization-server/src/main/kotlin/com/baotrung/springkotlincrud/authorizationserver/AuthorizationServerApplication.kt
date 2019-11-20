package com.baotrung.springkotlincrud.authorizationserver

import com.baotrung.springkotlincrud.authorizationserver.entity.RoleEntity
import com.baotrung.springkotlincrud.authorizationserver.entity.UserEntity
import com.baotrung.springkotlincrud.authorizationserver.repository.RoleRepository
import com.baotrung.springkotlincrud.authorizationserver.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import org.bouncycastle.cms.RecipientId.password
import org.apache.tomcat.jni.SSL.setPassword
import org.apache.tomcat.jni.User.username
import org.springframework.context.annotation.Bean
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.JavaMailSender
import java.util.*


@SpringBootApplication
class AuthorizationServer(private val userRepository: UserRepository,
                          private val roleRepository: RoleRepository,
                          private val passwordEncoder: PasswordEncoder) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        roleRepository.deleteAll();
        val adminRole = RoleEntity(UUID.randomUUID().toString(), "ROLE_ADMIN");
        val memberRole = RoleEntity(UUID.randomUUID().toString(), "ROLE_MEMBER");
        roleRepository.saveAll(listOf(adminRole, memberRole))

        userRepository.deleteAll()
        userRepository.save(UserEntity(
                UUID.randomUUID().toString(),
                "admin@example.com",
                passwordEncoder.encode("1234"),
                null,
                setOf(adminRole, memberRole)));
        userRepository.save(UserEntity(
                UUID.randomUUID().toString(),
                "member@example.com",
                "admin@example.com",
                null,
                setOf(memberRole)));
    }

}

fun main(args: Array<String>) {
    runApplication<AuthorizationServer>(*args)
}

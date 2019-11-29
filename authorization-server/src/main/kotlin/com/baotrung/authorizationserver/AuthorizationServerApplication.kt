package com.baotrung.authorizationserver

import com.baotrung.authorizationserver.entity.RoleEntity
import com.baotrung.authorizationserver.entity.UserEntity
import com.baotrung.authorizationserver.repository.RoleRepository
import com.baotrung.authorizationserver.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.annotation.Transactional
import java.util.UUID


@SpringBootApplication
class AuthorizationServerApplication(private val userRepository: UserRepository,
                          private val roleRepository: RoleRepository,
                          private val passwordEncoder: PasswordEncoder) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        roleRepository.deleteAll();
        val adminRole = RoleEntity(UUID.randomUUID().toString(), "ROLE_ADMIN")
        val memberRole = RoleEntity(UUID.randomUUID().toString(), "ROLE_MEMBER")
        roleRepository.saveAll(listOf(adminRole, memberRole))

        userRepository.deleteByEmail("admin@example.com")
        userRepository.save(UserEntity(
                UUID.randomUUID().toString(),
                "admin@example.com",
                passwordEncoder.encode("1234"),
                null,
                setOf(adminRole, memberRole)))

        userRepository.deleteByEmail("member@example.com")
        userRepository.save(UserEntity(
                UUID.randomUUID().toString(),
                "member@example.com",
                passwordEncoder.encode("1234"),
                null,
                setOf(memberRole)))
    }

}

fun main(args: Array<String>) {
    runApplication<AuthorizationServerApplication>(*args)
}

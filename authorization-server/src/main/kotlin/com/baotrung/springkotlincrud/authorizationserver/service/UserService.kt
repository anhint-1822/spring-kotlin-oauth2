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

@Service
class UserService(private val userRepository: UserRepository,
                  private val roleRepository: RoleRepository,
                  private val passwordEncoder: PasswordEncoder) {

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
                getRoles(reqDto.roles)
        )
        userRepository.save(user)
    }

    fun getRoles(roles: Set<String>): Set<RoleEntity> {
        return roles.map { role -> roleRepository.findByName(role) }.toSet()
    }
}
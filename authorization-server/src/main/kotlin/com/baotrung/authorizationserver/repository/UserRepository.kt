package com.baotrung.authorizationserver.repository

import com.baotrung.authorizationserver.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<UserEntity, String> {

    fun findByEmail(email: String): Optional<UserEntity>

    fun findByResetToken(resetToken: String): Optional<UserEntity>

    fun deleteByEmail(email: String)
}

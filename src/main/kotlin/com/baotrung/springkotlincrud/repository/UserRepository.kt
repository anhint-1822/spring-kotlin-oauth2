package com.baotrung.springkotlincrud.repository

import com.baotrung.springkotlincrud.jwt.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    fun existsByUsername(@Param("username") username: String): Boolean

    fun findByUsername(@Param("username") username: String): Optional<User>

    fun findByEmail(@Param(value = "email") email: String): Optional<User>

    fun deleteByUsername(@Param("username") username: String)
}
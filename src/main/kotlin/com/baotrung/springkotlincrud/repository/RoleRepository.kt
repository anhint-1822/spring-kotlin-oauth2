package com.baotrung.springkotlincrud.repository

import com.baotrung.springkotlincrud.jwt.Role
import com.baotrung.springkotlincrud.jwt.RoleName
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role,Long> {

    fun  findByName(name: String): Role
}
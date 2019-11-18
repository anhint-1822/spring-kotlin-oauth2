package com.baotrung.springkotlincrud.authorizationserver.repository

import com.baotrung.springkotlincrud.authorizationserver.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<RoleEntity, String> {

    fun  findByName(name: String): RoleEntity
}
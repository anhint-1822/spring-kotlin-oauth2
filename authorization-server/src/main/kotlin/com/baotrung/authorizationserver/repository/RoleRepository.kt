package com.baotrung.authorizationserver.repository

import com.baotrung.authorizationserver.entity.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<RoleEntity, String> {

    fun  findByName(name: String): RoleEntity
}

package com.baotrung.springkotlincrud.authorizationserver.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "roles")
data class RoleEntity(

        @Id
        var id: String,

        @Column(name = "name", nullable = false)
        var name: String
)
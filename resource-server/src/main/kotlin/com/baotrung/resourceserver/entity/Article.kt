package com.baotrung.resourceserver.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "article")
data class Article(

        @Id
        var id: String? = null,

        @field: NotBlank
        var title: String,

        @field: NotBlank
        var content: String
)

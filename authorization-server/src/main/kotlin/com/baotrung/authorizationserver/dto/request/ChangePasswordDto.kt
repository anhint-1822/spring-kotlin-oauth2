package com.baotrung.authorizationserver.dto.request

import javax.validation.constraints.NotBlank

data class ChangePasswordDto(


        @field: NotBlank
        val oldPassword: String,

        @field: NotBlank
        val email: String,

        @field: NotBlank
        val password: String,

        @field: NotBlank
        val confirmPassword: String
)
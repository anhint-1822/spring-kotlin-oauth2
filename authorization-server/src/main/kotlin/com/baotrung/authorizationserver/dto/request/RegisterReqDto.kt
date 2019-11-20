package com.baotrung.authorizationserver.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class RegisterReqDto(

        @field: NotBlank
        val email: String,

        @field: NotBlank
        val password: String,

        @field: NotEmpty
        val roles: Set<String>
)

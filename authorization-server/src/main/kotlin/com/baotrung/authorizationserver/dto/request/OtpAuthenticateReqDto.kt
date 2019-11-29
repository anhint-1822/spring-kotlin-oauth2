package com.baotrung.authorizationserver.dto.request

import javax.validation.constraints.NotBlank

data class OtpAuthenticateReqDto(

        @field: NotBlank
        val email: String
)

package com.baotrung.authorizationserver.dto.request

import javax.validation.constraints.NotBlank

data class OtpValidateReqDto(

        @field: NotBlank
        val email: String,

        @field: NotBlank
        val otp: String
)

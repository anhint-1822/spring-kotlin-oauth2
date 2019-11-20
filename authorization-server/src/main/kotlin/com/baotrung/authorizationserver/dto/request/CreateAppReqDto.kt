package com.baotrung.authorizationserver.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

data class CreateAppReqDto(

        @field: NotBlank
        val clientId: String,

        @field: NotBlank
        val scope: String,

        @field: NotEmpty
        val webServerRedirectUri: String
)

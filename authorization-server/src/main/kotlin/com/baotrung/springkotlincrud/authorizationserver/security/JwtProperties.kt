package com.baotrung.springkotlincrud.authorizationserver.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.jwt")
data class JwtProperties(

        val signingKey: String
)
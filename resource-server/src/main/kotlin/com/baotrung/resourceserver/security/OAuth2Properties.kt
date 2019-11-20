package com.baotrung.resourceserver.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.oauth2")
data class OAuth2Properties(

        val clientId: String,
        val clientSecret: String,
        val tokenInfoUri: String
)

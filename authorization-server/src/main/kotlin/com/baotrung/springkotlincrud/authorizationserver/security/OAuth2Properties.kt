package com.baotrung.springkotlincrud.authorizationserver.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app.oauth2")
data class OAuth2Properties(

        val clientId: String,
        val clientSecret: String,
        val grantTypes: List<String>,
        val scopes: List<String>,
        val accessTokenValiditySeconds: Int,
        val refreshTokenValiditySeconds: Int,
        val resourceIds: List<String>,
        val redirectUris: List<String>
)
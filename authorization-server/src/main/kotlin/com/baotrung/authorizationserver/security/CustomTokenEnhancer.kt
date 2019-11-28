package com.baotrung.authorizationserver.security

import org.apache.commons.lang3.RandomStringUtils
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import java.util.*

class CustomTokenEnhancer : TokenEnhancer {

    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val additionalInfo = HashMap<String, Any>()
        additionalInfo["organization"] = authentication.name + " :" + RandomStringUtils.randomAlphabetic(4)
        (accessToken as DefaultOAuth2AccessToken).setAdditionalInformation(additionalInfo)
        return accessToken
    }
}
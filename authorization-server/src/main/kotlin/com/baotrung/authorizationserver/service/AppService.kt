package com.baotrung.authorizationserver.service

import com.baotrung.authorizationserver.dto.request.CreateAppReqDto
import com.baotrung.authorizationserver.entity.OAuthClientDetailsEntity
import com.baotrung.authorizationserver.repository.OAuthClientDetailsRepository
import com.baotrung.authorizationserver.util.RandomUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
class AppService(private val passwordEncoder: PasswordEncoder,
                 private val oAuthClientDetailsRepository: OAuthClientDetailsRepository) {

    @Transactional
    fun createApp(reqDto: CreateAppReqDto): String {
        if (oAuthClientDetailsRepository.findById(reqDto.clientId).isPresent) {
            throw RuntimeException("Client ID exists")
        }

        val entity = OAuthClientDetailsEntity()
        entity.clientId = reqDto.clientId
        val clientSecret = RandomUtil.generateAlphanumeric(10)
        entity.clientSecret = passwordEncoder.encode(clientSecret)
        entity.scope = reqDto.scope
        entity.authorizedGrantTypes = "authorization_code,password,refresh_token"
        entity.webServerRedirectUri = reqDto.webServerRedirectUri
        entity.accessTokenValidity = 2592000 // 30 days
        entity.refreshTokenValidity = 2592000
        oAuthClientDetailsRepository.save(entity)
        return clientSecret
    }
}

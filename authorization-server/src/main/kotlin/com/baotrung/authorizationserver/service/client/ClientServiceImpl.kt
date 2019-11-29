package com.baotrung.authorizationserver.service.client

import com.baotrung.authorizationserver.dto.request.CreateAppReqDto
import com.baotrung.authorizationserver.entity.OAuthClientDetailsEntity
import com.baotrung.authorizationserver.repository.OAuthClientDetailsRepository
import com.baotrung.authorizationserver.service.RedisService
import com.baotrung.authorizationserver.util.RandomUtil
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ClientServiceImpl(private val passwordEncoder: PasswordEncoder,
                        private val redisService: RedisService,
                        private val oAuthClientDetailsRepository: OAuthClientDetailsRepository) : ClientService {

    companion object {
        private val log = LoggerFactory.getLogger(ClientServiceImpl::class.java)
    }


    override fun generateClientSecret(reqDto: CreateAppReqDto): String {
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

    @Transactional
    override fun deleteClient(clientId: String) {
        if (StringUtils.isEmpty(clientId)) {
            throw RuntimeException("Client ID empty")
        }

        val key = "Cache:clients:" + clientId;
        if (redisService.exists(key)) {
            if (log.isDebugEnabled) {
                log.debug("Remove client:{} from redis.", key)
            }
            redisService.del(key)
        }
        oAuthClientDetailsRepository.deleteByClientId(clientId)
    }

    override fun getClient(clientId: String): OAuthClientDetailsEntity {
        if (StringUtils.isEmpty(clientId)) {
            throw RuntimeException("Client ID empty")
        }
        return oAuthClientDetailsRepository.findByClientId(clientId)
    }

    override fun updateClient(client: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
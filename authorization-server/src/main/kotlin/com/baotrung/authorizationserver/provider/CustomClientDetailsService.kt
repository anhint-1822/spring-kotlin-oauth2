package com.baotrung.authorizationserver.provider

import com.baotrung.authorizationserver.service.RedisService
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.security.oauth2.common.exceptions.InvalidClientException
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
import java.io.IOException
import javax.sql.DataSource

class CustomClientDetailsService : JdbcClientDetailsService {

    private val prefix: String
    private val redisService: RedisService
    private val objectMapper: ObjectMapper

    constructor(prefix: String, dataSource: DataSource, redisService: RedisService, objectMapper: ObjectMapper) : super(dataSource) {
        this.prefix = prefix
        this.redisService = redisService
        this.objectMapper = objectMapper
    }

    companion object {
        private val log = LoggerFactory.getLogger(CustomClientDetailsService::class.java)
    }

    @Throws(InvalidClientException::class)
    override fun loadClientByClientId(clientId: String): ClientDetails {
        val key = prefix + clientId
        val value = redisService[key]
        if (value != null) {
            try {
                if (log.isDebugEnabled) {
                    log.debug("Get clientDetails:{} from redis", value)
                }
                return objectMapper.readValue(value!!, BaseClientDetails::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        val clientDetails = super.loadClientByClientId(clientId)
        try {
            if (clientDetails is BaseClientDetails) {
                if (log.isDebugEnabled) {
                    log.debug("Put clientDetails:{} into redis", clientDetails.toString())
                }
                redisService[key] = objectMapper.writeValueAsString(clientDetails)
            }
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }

        return clientDetails
    }
}
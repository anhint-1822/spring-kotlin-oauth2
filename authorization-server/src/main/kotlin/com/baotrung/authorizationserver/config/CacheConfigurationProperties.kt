package com.baotrung.authorizationserver.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.HashMap

@ConfigurationProperties(prefix = "cache")
data class CacheConfigurationProperties(
        var timeoutSeconds: Long = 60,
        val redisPort: Int = 6379,
        val redisHost: String = "localhost",
        // Mapping of cacheNames to expira-after-write timeout in seconds
        val cacheExpirations: HashMap<String, Long> = HashMap<String, Long>()

)
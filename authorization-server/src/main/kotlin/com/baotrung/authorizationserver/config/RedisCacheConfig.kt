package com.baotrung.springkotlincrud.authorizationserver.config

import com.baotrung.authorizationserver.config.CacheConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import java.time.Duration
import java.util.*


@Configuration
@EnableConfigurationProperties(CacheConfigurationProperties::class)
class RedisCacheConfig {


    private fun createCacheConfiguration(timeoutInSeconds: Long): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(timeoutInSeconds))
    }

    @Bean
    fun redisConnectionFactory(properties: CacheConfigurationProperties): LettuceConnectionFactory {

        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
        redisStandaloneConfiguration.hostName = properties.redisHost
        redisStandaloneConfiguration.port = properties.redisPort
        return LettuceConnectionFactory(redisStandaloneConfiguration)
    }

    @Bean
    fun redisTemplate(cf: RedisConnectionFactory): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.setConnectionFactory(cf)
        return redisTemplate
    }

    @Bean
    fun cacheConfiguration(properties: CacheConfigurationProperties): RedisCacheConfiguration {
        return createCacheConfiguration(properties.timeoutSeconds)
    }

    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory, properties: CacheConfigurationProperties): CacheManager {
        val cacheConfigurations = HashMap<String, RedisCacheConfiguration>()

        for (cacheNameAndTimeout in properties.cacheExpirations) {
            cacheConfigurations[cacheNameAndTimeout.key] = createCacheConfiguration(cacheNameAndTimeout.value)
        }

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(cacheConfiguration(properties))
                .withInitialCacheConfigurations(cacheConfigurations).build()
    }

    //    @Bean
//    fun tokenStore(redisConnectionFactory: RedisConnectionFactory): TokenStore {
//        return RedisTokenStore(redisConnectionFactory)
//    }
}
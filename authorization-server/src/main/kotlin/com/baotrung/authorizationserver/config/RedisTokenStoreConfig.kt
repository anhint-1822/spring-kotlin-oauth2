//package com.baotrung.authorizationserver.config
//
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.data.redis.cache.RedisCacheManager
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
//
//
//
//
//class RedisTokenStoreConfig {
//
//    @Value("\${redis.hostname}")
//    private val redisHostName: String = null
//
//    @Value("\${redis.port}")
//    private val redisPort: Int = 0
//
//    @Bean
//    fun propertySourcesPlaceholderConfigurer(): PropertySourcesPlaceholderConfigurer {
//        return PropertySourcesPlaceholderConfigurer()
//    }
//
//    @Bean
//    fun jedisConnectionFactory(): JedisConnectionFactory {
//        val factory = JedisConnectionFactory()
//        factory.setHostName(redisHostName)
//        factory.port = redisPort
//        factory.usePool = true
//        return factory
//    }
//
//    @Bean
//    fun redisTemplate(): RedisTemplate<Any, Any> {
//        val redisTemplate = RedisTemplate<Any, Any>()
//        redisTemplate.setConnectionFactory(jedisConnectionFactory())
//        return redisTemplate
//    }
//
//    @Bean
//    fun cacheManager(): RedisCacheManager {
//        return RedisCacheManager(redisTemplate())
//    }
//
////    private fun RedisCacheManager(redisTemplate: RedisTemplate<Any, Any>): RedisCacheManager {
//
//    }
//}
//package com.baotrung.springkotlincrud.authorizationserver.config
//
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.data.redis.cache.RedisCacheManager
//import java.util.concurrent.TimeUnit
//
//@Configuration
//class RedisCacheConfig {
//
//    @Value("\${redis.host}")
//    private val redisHost: String = ""
//
//    @Value("\${redis.port}")
//    private val redisPort: Int? = null
//
//
//    @Bean
//    fun jedisConnectionFactory(): JedisConnectionFactory {
//        val factory = JedisConnectionFactory()
//        factory.hostName = redisHost
//        factory.port = this.redisPort!!
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
//    fun cacheManager(redisTemplate: RedisTemplate<Any, Any>): RedisCacheManager {
//        // Open the most key prefix with the cache name
//        val redisCache = RedisCacheManager(redisTemplate.expire("access",1,TimeUnit.MINUTES))
////        redisCacheManager.setUsePrefix(true)
////        //Here you can set a default expiration time unit in seconds.
////        redisCacheManager.setDefaultExpiration(redisDefaultExpiration)
////
////        // Setting the expiration time of the cache
////        val map = redisson.getMapCache("anyMap")
////
////// ttl = 10 minutes,
////        map.put("key1", SomeObject(), 10, TimeUnit.MINUTES)
////// ttl = 10 minutes, maxIdleTime = 10 seconds
////        map.put("key1", SomeObject(), 10, TimeUnit.MINUTES, 10, TimeUnit.SECONDS)
//
//        return redisCache
//    }
//
//    private fun RedisCacheManager(redisTemplate: Boolean): RedisCacheManager {
//            return RedisCacheManager(redisTemplate)
//    }
//}
//package com.baotrung.springkotlincrud.authorizationserver.config
//
//import com.baotrung.authorizationserver.config.CacheConfigurationProperties
//import org.springframework.boot.context.properties.EnableConfigurationProperties
//import org.springframework.cache.CacheManager
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.redis.cache.RedisCacheConfiguration
//import org.springframework.data.redis.cache.RedisCacheManager
//import org.springframework.data.redis.connection.RedisConnectionFactory
//import java.time.Duration
//import java.util.HashMap
//
//
//
//
//@Configuration
//@EnableConfigurationProperties(CacheConfigurationProperties::class)
//class RedisCacheConfig {
//
////
////    private fun createCacheConfiguration(timeoutInSeconds: Long): RedisCacheConfiguration {
////        return RedisCacheConfiguration.defaultCacheConfig()
////                .entryTtl(Duration.ofSeconds(timeoutInSeconds))
////    }
////
////    @Bean
////    fun redisConnectionFactory(properties: CacheConfigurationProperties): LettuceConnectionFactory {
////
////        val redisStandaloneConfiguration = RedisStandaloneConfiguration()
////        redisStandaloneConfiguration.hostName = properties.redisHost
////        redisStandaloneConfiguration.port = properties.redisPort
////        return LettuceConnectionFactory(redisStandaloneConfiguration)
////    }
////
////    @Bean
////    fun redisTemplate(cf: RedisConnectionFactory): RedisTemplate<String, String> {
////        val redisTemplate = RedisTemplate<String, String>()
////        redisTemplate.setConnectionFactory(cf)
////        return redisTemplate
////    }
////
////    @Bean
////    fun cacheConfiguration(properties: CacheConfigurationProperties): RedisCacheConfiguration {
////        return createCacheConfiguration(properties.timeoutSeconds)
////    }
//
//    @Bean(name = ["cacheManager"])
//    fun cacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager {
//        val conf_ready_info = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMillis(50000))
//
//        val conf_base_info = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofMillis(60000))
//
//        val cacheConfigurations = HashMap<String, RedisCacheConfiguration>()
//        cacheConfigurations["client_id_to_access"] = conf_base_info
//        cacheConfigurations["client_id_to_access"] = conf_ready_info
//
//        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
//                .withInitialCacheConfigurations(cacheConfigurations).build()
//    }
//
//
//    //    @Bean
////    fun tokenStore(redisConnectionFactory: RedisConnectionFactory): TokenStore {
////        return RedisTokenStore(redisConnectionFactory)
////    }
//}
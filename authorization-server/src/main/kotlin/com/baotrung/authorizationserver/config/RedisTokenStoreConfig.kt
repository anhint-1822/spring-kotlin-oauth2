//package com.baotrung.authorizationserver.config
//
//import org.springframework.context.annotation.Bean
//import org.springframework.data.redis.cache.RedisCache
//import org.springframework.data.redis.core.RedisTemplate
//import org.springframework.security.core.userdetails.UserCache
//import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache
//
//
//class RedisTokenStoreConfig {
//
//    val REDIS_CACHE_NAME = "redis_cache_name"//不为null即可
//    val REDIS_PREFIX = "redis_cache_prefix"//不为null即可
//    val EXPIRE: Long? = 60 * 60L//缓存有效时间
//
//    /**
//     * 配置用以存储用户认证信息的缓存
//     */
//    @Bean
//    fun redisCache(redisTemplate: RedisTemplate<*, *>): RedisCache {
//        return  RedisCache(REDIS_CACHE_NAME, REDIS_PREFIX.toByteArray(), redisTemplate, EXPIRE)
//    }
//
//    /**
//     *
//     * 创建UserDetails存储服务的Bean：使用Redis作为缓存介质
//     * UserDetails user = this.userCache.getUserFromCache(username)
//     */
//    @Bean
//    @Throws(Exception::class)
//    fun userCache(redisCache: RedisCache): UserCache {
//        val userCache = SpringCacheBasedUserCache(redisCache)
//        return userCache
//    }
//}
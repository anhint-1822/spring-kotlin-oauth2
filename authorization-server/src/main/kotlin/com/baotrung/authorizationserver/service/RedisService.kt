package com.baotrung.authorizationserver.service

import org.slf4j.LoggerFactory.getLogger
import org.springframework.data.redis.connection.RedisConnection
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Component
import java.nio.charset.Charset

@Component
class RedisService {

    private val reddisTemplate: RedisTemplate<String, String>

    private val DEFAULT_CHARSET: Charset = Charset.forName("UTF-8");


    private val STRING_SERIALIZER: StringRedisSerializer = StringRedisSerializer();


    private val OBJECT_SERIALIZER: JdkSerializationRedisSerializer = JdkSerializationRedisSerializer();

    constructor(reddisTemplate: RedisTemplate<String, String>) {
        this.reddisTemplate = reddisTemplate
        this.reddisTemplate.setKeySerializer(STRING_SERIALIZER)
        this.reddisTemplate.setValueSerializer(OBJECT_SERIALIZER)
    }

    companion object {
        private val log = getLogger(RedisService::class.java)
    }


    fun getConnectionFactory(): RedisConnectionFactory? {
        return this.reddisTemplate.getConnectionFactory()
    }

    fun getRedisTemplate(): RedisTemplate<String, String>? {
        return reddisTemplate
    }

    operator fun get(key: String): String? {
        val resultStr = reddisTemplate.execute { connection: RedisConnection ->
            val serializer = getRedisSerializer()
            val keys = serializer.serialize(key)
            val values = connection.get(keys!!)
            serializer.deserialize(values)
        }
        log.info("[redisTemplate redis]Cache Get  url:{} ", key)
        return resultStr
    }

    protected fun getRedisSerializer(): RedisSerializer<String> {
        return reddisTemplate.getStringSerializer()
    }

    operator fun set(key: String, value: String) {
        reddisTemplate.execute { connection: RedisConnection ->
            val serializer = getRedisSerializer()
            val keys = serializer.serialize(key)
            val values = serializer.serialize(value)
            connection.set(keys!!, values!!)
            log.info("[redisTemplate redis]Insert to cache  url:{}", key)
            1L
        }
    }

    fun exists(key: String): Boolean {
        return reddisTemplate.execute { connection: RedisConnection -> connection.exists(key.toByteArray(DEFAULT_CHARSET)) }!!
    }

    fun del(vararg keys: String): Long {
        return reddisTemplate.execute { connection: RedisConnection ->
            var result: Long = 0
            for (key in keys) {
                result = connection.del(key.toByteArray(DEFAULT_CHARSET))!!
            }
            result
        }!!
    }


}
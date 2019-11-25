package com.baotrung.authorizationserver.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration(private val authenticationManager: AuthenticationManager,
                                       private val userDetailsService: UserDetailsService,
                                       private val dataSource: DataSource) : AuthorizationServerConfigurerAdapter() {

    @Value("\${jwt.signing-key}")
    private val jwtSigningKey: String? = null

    // =================================================================================================================
    // JWT configuration
    // =================================================================================================================

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(jwtSigningKey)
        return converter
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JdbcTokenStore(dataSource)
    }

    @Bean
    fun tokenStore(redisConnectionFactory: RedisConnectionFactory): TokenStore {
        return RedisTokenStore(redisConnectionFactory)
    }

    // =================================================================================================================
    // Authorization server configuration
    // =================================================================================================================

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource)
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .accessTokenConverter(accessTokenConverter())
                .tokenStore(tokenStore())
    }

    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("isAuthenticated()")
    }
}

package com.baotrung.authorizationserver.security

import com.baotrung.authorizationserver.exception.CustomExceptionEntryPoint
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
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore
import java.util.*
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfiguration(private val authenticationManager: AuthenticationManager,
                                       private val userDetailsService: UserDetailsService,
                                       private val customExceptionEntryPoint: CustomExceptionEntryPoint,
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
                .tokenServices(defaultTokenServices())
    }

    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
                .authenticationEntryPoint(customExceptionEntryPoint)
    }

    @Bean
    fun clientDetails(): ClientDetailsService {
        return JdbcClientDetailsService(dataSource)
    }

    @Bean
    fun tokenEnhancer(): TokenEnhancer {
        return CustomTokenEnhancer()
    }

    @Bean
    fun authorizationCodeServices(): AuthorizationCodeServices {
        return JdbcAuthorizationCodeServices(dataSource)
    }

    @Bean
    fun approvalStore(): ApprovalStore {
        val store = TokenApprovalStore()
        store.setTokenStore(tokenStore())
        return store
    }

    @Bean
    fun defaultTokenServices(): DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setSupportRefreshToken(true)
        defaultTokenServices.setTokenStore(tokenStore())
        defaultTokenServices.setAccessTokenValiditySeconds(60 * 60 * 24)
        defaultTokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24)
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter(), tokenEnhancer()))
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain)
        return defaultTokenServices;
    }
}

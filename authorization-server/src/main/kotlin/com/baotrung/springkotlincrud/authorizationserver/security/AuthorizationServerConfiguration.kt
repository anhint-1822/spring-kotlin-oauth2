package com.baotrung.springkotlincrud.authorizationserver.security

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(OAuth2Properties::class, JwtProperties::class)
@EnableAuthorizationServer
class AuthorizationServerConfiguration(private val oAuth2Properties: OAuth2Properties,
                                       private val jwtProperties: JwtProperties,
                                       private val passwordEncoder: PasswordEncoder,
                                       private val authenticationManager: AuthenticationManager,
                                       private val userDetailsService: UserDetailsService,
                                       private val dataSource: DataSource) : AuthorizationServerConfigurerAdapter() {

    // =================================================================================================================
    // JWT configuration
    // =================================================================================================================

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(jwtProperties.signingKey)
        return converter
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JdbcTokenStore(dataSource)
    }

    // =================================================================================================================
    // Authorization server configuration
    // =================================================================================================================

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
                .jdbc(dataSource)
                .withClient(oAuth2Properties.clientId)
                .secret(passwordEncoder.encode(oAuth2Properties.clientSecret))
                .authorizedGrantTypes(*oAuth2Properties.grantTypes.toTypedArray())
                .scopes(*oAuth2Properties.scopes.toTypedArray())
                .accessTokenValiditySeconds(oAuth2Properties.accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(oAuth2Properties.refreshTokenValiditySeconds)
                .resourceIds(*oAuth2Properties.resourceIds.toTypedArray())
                .redirectUris(*oAuth2Properties.redirectUris.toTypedArray())
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
package com.baotrung.springkotlincrud.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.approval.ApprovalStore
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import javax.sql.DataSource

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {

    @Value("\${security.jwt.client-id}")
    private val clientId: String? = null

    @Value("\${security.jwt.client-secret}")
    private val clientSecret: String? = null

    @Value("\${security.jwt.grant-type}")
    private val grantType: String? = null

    @Value("\${security.jwt.scope-read}")
    private val scopeRead: String? = null

    @Value("\${security.jwt.scope-write}")
    private val scopeWrite = "write"

    @Value("\${security.jwt.resource-ids}")
    private val resourceIds: String? = null

    @Autowired
    private val tokenStore: TokenStore? = null

    @Autowired
    private val accessTokenConverter: JwtAccessTokenConverter? = null

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun oauthDataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }

    @Bean
    fun clientDetailsService(): JdbcClientDetailsService {
        return JdbcClientDetailsService(oauthDataSource())
    }

    @Bean
    fun tokenStore(): TokenStore {
        return JdbcTokenStore(oauthDataSource())
    }

    @Bean
    fun approvalStore(): ApprovalStore {
        return JdbcApprovalStore(oauthDataSource())
    }

    @Bean
    fun authorizationCodeServices(): AuthorizationCodeServices {
        return JdbcAuthorizationCodeServices(oauthDataSource())
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.withClientDetails(clientDetailsService())
    }

    @Throws(Exception::class)
    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer?) {

    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val enhancerChain = TokenEnhancerChain()
        enhancerChain.setTokenEnhancers(listOf(accessTokenConverter))
        endpoints.tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(enhancerChain)
                .authenticationManager(authenticationManager)
    }



}
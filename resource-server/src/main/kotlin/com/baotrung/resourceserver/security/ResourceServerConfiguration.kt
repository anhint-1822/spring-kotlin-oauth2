package com.baotrung.resourceserver.security

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.RemoteTokenServices

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@EnableConfigurationProperties(OAuth2Properties::class)
class ResourceServerConfiguration(private val oAuth2Properties: OAuth2Properties) : ResourceServerConfigurerAdapter() {

    @Bean
    fun remoteTokenServices(): RemoteTokenServices {
        val remoteTokenServices = RemoteTokenServices()
        remoteTokenServices.setCheckTokenEndpointUrl(oAuth2Properties.tokenInfoUri)
        remoteTokenServices.setClientId(oAuth2Properties.clientId)
        remoteTokenServices.setClientSecret(oAuth2Properties.clientSecret)
        return remoteTokenServices
    }

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.tokenServices(remoteTokenServices())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().authenticated()
    }
}

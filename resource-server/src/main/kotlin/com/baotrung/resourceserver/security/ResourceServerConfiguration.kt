package com.baotrung.resourceserver.security

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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

    @Throws(Exception::class)
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.tokenServices(remoteTokenServices())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
                .and()

                // Add headers required for CORS requests.
                .headers().addHeaderWriter { request, response ->
                    response.addHeader("Access-Control-Allow-Origin", "*")

                    if (request.method.equals("OPTIONS")) {
                        response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"))
                        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"))
                    }
                }
    }

}

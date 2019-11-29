package com.baotrung.authorizationserver.security

import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity(debug = true)
class WebSecurityConfig(private val userDetailsService: UserDetailsService): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests().anyRequest().permitAll().and()
            .formLogin().permitAll().and()
            .logout().permitAll()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder(8)
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    fun defaultDaoAuthenticationProvider(): DaoAuthenticationProvider {
        val defaultDaoAuthenticationProvider = DaoAuthenticationProvider()
        defaultDaoAuthenticationProvider.setUserDetailsService(userDetailsService)
        defaultDaoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        return defaultDaoAuthenticationProvider
    }

    @Order(Ordered.LOWEST_PRECEDENCE)
    @Bean
    fun otpAuthenticationProvider(): OtpAuthenticationProvider {
        return OtpAuthenticationProvider(userDetailsService)
    }

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(defaultDaoAuthenticationProvider())
        auth.authenticationProvider(otpAuthenticationProvider())
    }
}

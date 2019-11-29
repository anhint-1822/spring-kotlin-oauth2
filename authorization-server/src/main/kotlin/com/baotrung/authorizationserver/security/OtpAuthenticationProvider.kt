package com.baotrung.authorizationserver.security

import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.Collections

class OtpAuthenticationProvider: DaoAuthenticationProvider {

    constructor(userDetailsService: UserDetailsService) {
        this.userDetailsService = userDetailsService
    }

    override fun authenticate(authentication: Authentication): Authentication {
        val otpAuthenticationToken: OtpAuthenticationToken = authentication as OtpAuthenticationToken

        if (otpAuthenticationToken.email.isNullOrBlank()) {
            throw InternalAuthenticationServiceException("Email must not be empty")
        }

        userDetailsService.loadUserByUsername(otpAuthenticationToken.email)

        val preOtpAuthenticatedGrantedAuthority = SimpleGrantedAuthority("ROLE_PRE_OTP_AUTHENTICATED")
        return OtpAuthenticationToken(otpAuthenticationToken.email, Collections.singletonList(preOtpAuthenticatedGrantedAuthority))
    }

    override fun supports(authentication: Class<*>): Boolean {
        return OtpAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}
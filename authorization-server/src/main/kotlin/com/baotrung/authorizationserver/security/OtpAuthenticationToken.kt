package com.baotrung.authorizationserver.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class OtpAuthenticationToken(val email: String?, authorities: MutableCollection<out GrantedAuthority>?) : AbstractAuthenticationToken(authorities) {

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return email
    }
}
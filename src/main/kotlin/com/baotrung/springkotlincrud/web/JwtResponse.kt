package com.baotrung.springkotlincrud.web

import org.springframework.security.core.GrantedAuthority

class JwtResponse(val accessToken: String?, var username: String?, val authories: Collection<GrantedAuthority>) {
    val type = "Bearer"
}
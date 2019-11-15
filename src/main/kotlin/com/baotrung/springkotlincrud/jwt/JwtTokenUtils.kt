package com.baotrung.springkotlincrud.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value

class JwtTokenUtils {
    @Value("\${jwt.secret}")
    private val secret: String? = null

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null


    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken<String>(token, Function<Claims, String> { it.getSubject() })
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: java.util.function.Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
    }
}
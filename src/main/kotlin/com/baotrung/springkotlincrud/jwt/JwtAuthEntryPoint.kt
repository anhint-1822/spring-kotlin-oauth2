package com.baotrung.springkotlincrud.jwt

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthEntryPoint : AuthenticationEntryPoint {

    companion object {
        private val logger = LoggerFactory.getLogger(JwtAuthEntryPoint::class.java)
    }

    override fun commence(request: HttpServletRequest, reponse: HttpServletResponse, e: AuthenticationException?) {
        logger.error("Unauthorized error. Message - {}", e!!.message)
        reponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials")
    }
}
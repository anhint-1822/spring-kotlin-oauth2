package com.baotrung.authorizationserver.controller

import com.baotrung.authorizationserver.dto.request.RegisterReqDto
import com.baotrung.authorizationserver.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @PostMapping("/users/register")
    fun register(@Valid @RequestBody reqDto: RegisterReqDto) {
        userService.register(reqDto)
    }

    @GetMapping("/users/forgot-password")
    fun forgotPassword(@Valid @RequestParam("email") email: String, httpServletRequest: HttpServletRequest): ResponseEntity<String> {
        userService.forgotPassword(email, httpServletRequest)
        return ResponseEntity.ok("Email send success")
    }

    @GetMapping("/users/reset-password")
    fun resetPassword(@Valid @RequestParam("token") token: String): ResponseEntity<String> {
        userService.resetPassword(token)
        return ResponseEntity.ok("Password reset success")
    }

}

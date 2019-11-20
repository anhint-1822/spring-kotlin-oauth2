package com.baotrung.springkotlincrud.authorizationserver.controller

import com.baotrung.springkotlincrud.authorizationserver.dto.request.RegisterReqDto
import com.baotrung.springkotlincrud.authorizationserver.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
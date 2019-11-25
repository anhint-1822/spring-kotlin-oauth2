package com.baotrung.authorizationserver.controller

import com.baotrung.authorizationserver.dto.request.ChangePasswordDto
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
        val forgotPassword = userService.forgotPassword(email, httpServletRequest)
        if (forgotPassword) {
            return ResponseEntity.ok("Email send success")
        }
        return ResponseEntity.ok("Email send error")
    }

    @GetMapping("/users/reset-password")
    fun resetPassword(@Valid @RequestParam("token") token: String): ResponseEntity<String> {
        val isResetPassword = userService.resetPassword(token)
        if (isResetPassword) {
            return ResponseEntity.ok("Password reset success")
        }
        return ResponseEntity.ok("Password reset fail")
    }

    @PostMapping("/users/change-password")
    fun changePassword(@Valid @RequestBody changePasswordDto: ChangePasswordDto): ResponseEntity<String> {
        return ResponseEntity.ok(userService.changePassword(changePasswordDto))
    }

}

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
    fun forgotPassword(@Valid @RequestParam("email") email: String, httpServletRequest: HttpServletRequest){
        userService.forgotPassword(email, httpServletRequest)
    }
}
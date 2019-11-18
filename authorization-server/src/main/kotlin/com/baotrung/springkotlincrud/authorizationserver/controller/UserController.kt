package com.baotrung.springkotlincrud.authorizationserver.controller

import com.baotrung.springkotlincrud.authorizationserver.dto.request.RegisterReqDto
import com.baotrung.springkotlincrud.authorizationserver.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @PostMapping("/users/register")
    fun register(@Valid @RequestBody reqDto: RegisterReqDto) {
        userService.register(reqDto)
    }
}
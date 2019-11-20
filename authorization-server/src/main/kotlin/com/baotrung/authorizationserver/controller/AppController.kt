package com.baotrung.authorizationserver.controller

import com.baotrung.authorizationserver.dto.request.CreateAppReqDto
import com.baotrung.authorizationserver.service.AppService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class AppController(private val appService: AppService) {

    @PostMapping("/apps")
    fun register(@Valid @RequestBody reqDto: CreateAppReqDto): String {
        return appService.createApp(reqDto)
    }
}

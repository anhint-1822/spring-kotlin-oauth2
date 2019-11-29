package com.baotrung.authorizationserver.controller

import com.baotrung.authorizationserver.dto.request.OtpAuthenticateReqDto
import com.baotrung.authorizationserver.dto.request.OtpValidateReqDto
import com.baotrung.authorizationserver.service.OtpService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class OtpController(private val otpService: OtpService) {

    @PostMapping("/otp/authenticate")
    fun authenticate(@Valid @RequestBody reqDto: OtpAuthenticateReqDto) {
        otpService.authenticate(reqDto)
    }

    @PostMapping("/otp/validate")
    fun validate(@Valid @RequestBody reqDto: OtpValidateReqDto): Boolean {
        return otpService.validateOtp(reqDto)
    }
}

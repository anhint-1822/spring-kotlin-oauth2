package com.baotrung.authorizationserver.controller

import com.baotrung.authorizationserver.dto.request.CreateAppReqDto
import com.baotrung.authorizationserver.service.client.ClientService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/clients")
class ClientController(val clientService: ClientService) {


    @PostMapping
    fun generateClientSecret(@Valid @RequestBody reqDto: CreateAppReqDto): ResponseEntity<String> {
        return ResponseEntity.ok(clientService.generateClientSecret(reqDto))
    }

    @GetMapping("/{clientId}")
    fun getClient(@PathVariable("clientId") clientId: String): ResponseEntity<*> {

        return ResponseEntity.ok(clientService.getClient(clientId))
    }

    @DeleteMapping("/{clientId}")
    fun deleteClient(@PathVariable("clientId") clientId: String): ResponseEntity<*> {

        return ResponseEntity.ok(clientService.deleteClient(clientId))
    }


}
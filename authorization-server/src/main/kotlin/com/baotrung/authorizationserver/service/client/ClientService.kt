package com.baotrung.authorizationserver.service.client

import com.baotrung.authorizationserver.dto.request.CreateAppReqDto
import com.baotrung.authorizationserver.entity.OAuthClientDetailsEntity

interface ClientService {

    fun generateClientSecret(reqDto: CreateAppReqDto): String

    fun deleteClient(clientId: String)

    fun getClient(clientId: String) : OAuthClientDetailsEntity

    fun updateClient(client: String)

}
package com.baotrung.authorizationserver.repository

import com.baotrung.authorizationserver.entity.OAuthClientDetailsEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OAuthClientDetailsRepository: JpaRepository<OAuthClientDetailsEntity, String> {
}

package com.baotrung.authorizationserver.service

import com.baotrung.authorizationserver.entity.RoleEntity
import com.baotrung.authorizationserver.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
                .orElseThrow{ UsernameNotFoundException("User $username not found") }
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.password)
                .authorities(getGrantedAuthorities(user.roles))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build()
    }

    fun getGrantedAuthorities(roles: Set<RoleEntity>): Set<GrantedAuthority> {
        return roles.map{ role -> SimpleGrantedAuthority(role.name) }.toSet()
    }
}

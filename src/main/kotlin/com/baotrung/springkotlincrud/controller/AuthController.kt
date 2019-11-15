package com.baotrung.springkotlincrud.controller

import com.baotrung.springkotlincrud.jwt.JwtProvider
import com.baotrung.springkotlincrud.jwt.RoleName
import com.baotrung.springkotlincrud.jwt.User
import com.baotrung.springkotlincrud.model.LoginUser
import com.baotrung.springkotlincrud.model.NewUser
import com.baotrung.springkotlincrud.repository.RoleRepository
import com.baotrung.springkotlincrud.repository.UserRepository
import com.baotrung.springkotlincrud.web.JwtResponse
import com.baotrung.springkotlincrud.web.ReponseMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var jwtProvider: JwtProvider

    @Autowired
    lateinit var encoder: PasswordEncoder;

    @PostMapping("/signin")
    fun login(@Valid @RequestBody loginRequest: LoginUser): ResponseEntity<*> {

        var userCandidate: Optional<User> = userRepository.findByUsername(loginRequest.username!!)

        if (userCandidate.isPresent) {
            val user: User = userCandidate.get()
            val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password))
            SecurityContextHolder.getContext().authentication = authentication
            val jwt: String = jwtProvider.generateJwtToken(user.username!!)
            val authorities: List<GrantedAuthority> = user.roles!!.stream().map({ role -> SimpleGrantedAuthority(role.name) }).collect(Collectors.toList<GrantedAuthority>())
            return ResponseEntity.ok(JwtResponse(jwt, user.username, authorities))
        } else {
            return ResponseEntity(ReponseMessage("User not found"), HttpStatus.OK)
        }
    }

    @PostMapping("/signup")
    fun registerUser(@Valid @RequestBody newUser: NewUser): ResponseEntity<*> {

        val userCandidate: Optional<User> = userRepository.findByUsername(newUser.username!!)

        if (!userCandidate.isPresent) {
            if (usernameExists(newUser.username!!)) {
                return ResponseEntity(ReponseMessage("Username is already taken!"),
                        HttpStatus.BAD_REQUEST)
            } else if (emailExists(newUser.email!!)) {
                return ResponseEntity(ReponseMessage("Email is already in use!"),
                        HttpStatus.BAD_REQUEST)
            }

            // Creating user's account
            val user = User(
                    0,
                    newUser.username!!,
                    newUser.firstname!!,
                    newUser.lastName!!,
                    newUser.email!!,
                    encoder.encode(newUser.password),
                    true
            )
            user!!.roles = Arrays.asList(roleRepository.findByName("ROLE_USER"))

            userRepository.save(user)

            return ResponseEntity(ReponseMessage("User registered successfully!"), HttpStatus.OK)
        } else {
            return ResponseEntity(ReponseMessage("User already exists!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    fun resetToken(httpServletRequest: HttpServletRequest): ResponseEntity<*> {
        var token: String = httpServletRequest.getHeader("Authorization")
        if (token != null && token.contains("Bearer")) {
            token = token.substring("Bearer ".length, token.length)
        }
        return ResponseEntity(ReponseMessage("Token success response"), HttpStatus.OK)
    }

    private fun emailExists(email: String): Boolean {
        return userRepository.findByUsername(email).isPresent
    }

    private fun usernameExists(username: String): Boolean {
        return userRepository.findByUsername(username).isPresent
    }


}
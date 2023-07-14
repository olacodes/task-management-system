package com.olacodes.authservice.controller

import com.olacodes.authservice.dto.AuthenticationRequest
import com.olacodes.authservice.dto.AuthenticationResponse
import com.olacodes.authservice.dto.UserCreationDto
import com.olacodes.authservice.service.AuthenticationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(private val service: AuthenticationService) {

    @PostMapping("/register")
    fun register(@RequestBody request: UserCreationDto): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(service.register(request))
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody request: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(service.authenticate(request))
    }

    @PostMapping("/refresh-token")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        service.refreshToken(request, response)
    }
}

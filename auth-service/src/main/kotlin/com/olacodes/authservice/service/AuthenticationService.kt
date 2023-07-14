package com.olacodes.authservice.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.olacodes.authservice.dto.AuthenticationRequest
import com.olacodes.authservice.dto.AuthenticationResponse
import com.olacodes.authservice.dto.TokenType
import com.olacodes.authservice.dto.UserCreationDto
import com.olacodes.authservice.persistence.entity.TokenEntity
import com.olacodes.authservice.persistence.entity.UserEntity
import com.olacodes.authservice.persistence.repository.TokenRepository
import com.olacodes.authservice.persistence.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthenticationService(
    private val repository: UserRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun register(request: UserCreationDto): AuthenticationResponse {
        val user = UserEntity(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            userRole = request.userRole,
        )

        val savedUser = repository.save(user)
        val jwtToken = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        saveUserToken(savedUser, jwtToken)
        return AuthenticationResponse(
            accessToken = jwtToken,
            refreshToken = refreshToken
        )
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )
        val user = repository.findByEmail(request.email).orElseThrow()
        val jwtToken = jwtService.generateToken(user)
        val refreshToken = jwtService.generateRefreshToken(user)
        revokeAllUserTokens(user)
        saveUserToken(user, jwtToken)
        return AuthenticationResponse(
            accessToken = jwtToken,
            refreshToken = refreshToken
        )
    }

    private fun saveUserToken(user: UserEntity, jwtToken: String) {
        val token = TokenEntity(
            user = user,
            token=jwtToken,
            tokenType=TokenType.BEARER,
            expired=false,
            revoked=false
        )
        tokenRepository.save(token)
    }

    private fun revokeAllUserTokens(user: UserEntity) {
        val validUserTokens = tokenRepository.findAllValidTokenByUser(user.id)
        if (validUserTokens.isEmpty()) return
        validUserTokens.forEach { token ->
            token.expired = true
            token.revoked = true
        }
        tokenRepository.saveAll(validUserTokens)
    }

    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val userEmail: String
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return
        }
        val refreshToken: String = authHeader.substring(7)
        userEmail = jwtService.extractUsername(refreshToken) ?: return
        val user = repository.findByEmail(userEmail).orElseThrow()
        if (jwtService.isTokenValid(refreshToken, user)) {
            val accessToken = jwtService.generateToken(user)
            revokeAllUserTokens(user)
            saveUserToken(user, accessToken)
            val authResponse = AuthenticationResponse(
                accessToken = accessToken,
                refreshToken = refreshToken
            )

            response.outputStream.use { outputStream ->
                ObjectMapper().writeValue(outputStream, authResponse)
            }
        }
    }
}

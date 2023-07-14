package com.olacodes.authservice.dto


data class AuthenticationRequest(
    val email: String,
    val password: String,
)

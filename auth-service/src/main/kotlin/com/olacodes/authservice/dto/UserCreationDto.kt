package com.olacodes.authservice.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UserCreationDto(
    @NotNull
    val name: String,

    @Size(min = 8, max = 200, message = "Password must be minimum of 8 characters")
    var password: String,

    @Email(message = "Email should be valid", regexp = ".+[@].+[\\.].+")
    var email: String,

    var userRole: UserRole = UserRole.USER
)


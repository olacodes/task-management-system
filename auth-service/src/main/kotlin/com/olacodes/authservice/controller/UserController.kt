package com.olacodes.authservice.controller

import com.olacodes.authservice.dto.UserCreationDto
import com.olacodes.authservice.dto.UserResponseDto
import com.olacodes.authservice.service.UserService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/users", produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(val userService: UserService) {

    // Create User
    @PostMapping
    fun createUser(@Valid @RequestBody userCreationDto: UserCreationDto): UserResponseDto{
        return userService.createUser(userCreationDto)
    }

    // Get All user
    @GetMapping
    fun getUsers(): List<UserResponseDto> {
        return userService.getUsers()
    }

    // Get A User
    @GetMapping("/{userId}")
    fun getUser(@PathVariable("userId") userId: UUID): UserResponseDto{
        return userService.getUser(userId)
    }

    // Update User
    @PutMapping("/{userId}")
    fun updateUser(@PathVariable("userId") userId: UUID, @RequestBody userCreationDto: UserCreationDto): UserResponseDto {
        return userService.updateUser(userId, userCreationDto)
    }

    // Delete User
}
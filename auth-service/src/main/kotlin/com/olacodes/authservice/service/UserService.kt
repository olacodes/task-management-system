package com.olacodes.authservice.service

import com.olacodes.authservice.dto.UserCreationDto
import com.olacodes.authservice.dto.UserResponseDto
import com.olacodes.authservice.persistence.entity.UserEntity
import com.olacodes.authservice.persistence.repository.UserRepository
import jakarta.validation.Valid
import jakarta.validation.executable.ValidateOnExecution
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service
import java.util.UUID

@ValidateOnExecution
@Service
class UserService(
    val userRepository: UserRepository,
    val modelMapper: ModelMapper
) {

    fun getUser(userId: UUID): UserResponseDto {
        val user =
            userRepository.findById(userId).orElseThrow{ NoSuchElementException("User not found with ID: $userId") }
        return modelMapper.map(user, UserResponseDto::class.java)
    }

    fun getUsers(): List<UserResponseDto> {
        val users = userRepository.findAll()
        return users.map { modelMapper.map(it, UserResponseDto::class.java) }
    }

    fun updateUser(userId: UUID, userCreationDto: UserCreationDto): UserResponseDto {
        val existingUser =
            userRepository.findById(userId).orElseThrow { NoSuchElementException("User not found with ID: $userId") }

        existingUser.name = userCreationDto.name
        existingUser.email = userCreationDto.email
        existingUser.userRole = userCreationDto.userRole

        val updatedUser = userRepository.save(existingUser)

        return modelMapper.map(updatedUser, UserResponseDto::class.java)

    }

    fun createUser(@Valid userCreationDto: UserCreationDto): UserResponseDto{
        val userEntity = modelMapper.map(userCreationDto, UserEntity::class.java)
        val user = userRepository.save(userEntity)
        return modelMapper.map(user, UserResponseDto::class.java)
    }
}
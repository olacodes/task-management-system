package com.olacodes.authservice.persistence.repository

import com.olacodes.authservice.persistence.entity.UserEntity
import jakarta.validation.constraints.Email
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<UserEntity, UUID> {
    fun findByEmail(email: String): Optional<UserEntity>
    fun existsByEmail(email: String): Boolean
}
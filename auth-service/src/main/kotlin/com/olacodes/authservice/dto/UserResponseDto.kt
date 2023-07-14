package com.olacodes.authservice.dto

import java.time.ZonedDateTime
import java.util.UUID

data class UserResponseDto(
    var id: UUID,
    var name: String,
    var email: String,
    var userRole: UserRole,
    var createdAt: ZonedDateTime,
    var updatedAt: ZonedDateTime,
)
{
    constructor(): this(UUID.randomUUID(), "", "", UserRole.USER, ZonedDateTime.now(), ZonedDateTime.now())
}
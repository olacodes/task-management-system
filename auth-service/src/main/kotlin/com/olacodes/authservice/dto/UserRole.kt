package com.olacodes.authservice.dto

import org.springframework.security.core.authority.SimpleGrantedAuthority
import com.olacodes.authservice.dto.Permission.ADMIN_READ
import com.olacodes.authservice.dto.Permission.ADMIN_UPDATE
import com.olacodes.authservice.dto.Permission.ADMIN_DELETE
import com.olacodes.authservice.dto.Permission.ADMIN_CREATE
import com.olacodes.authservice.dto.Permission.MANAGER_READ
import com.olacodes.authservice.dto.Permission.MANAGER_UPDATE
import com.olacodes.authservice.dto.Permission.MANAGER_DELETE
import com.olacodes.authservice.dto.Permission.MANAGER_CREATE


enum class UserRole(private val permissions: Set<Any>) {
    USER(emptySet()),
    ADMIN(
        setOf(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE,
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_DELETE,
            MANAGER_CREATE
        )
    ),
    MANAGER(
        setOf(
            MANAGER_READ,
            MANAGER_UPDATE,
            MANAGER_DELETE,
            MANAGER_CREATE
        )
    );

    fun getAuthorities(): List<SimpleGrantedAuthority> {
        val authorities = permissions.map { permission ->
            SimpleGrantedAuthority(permission.toString())
        }.toMutableList()
        authorities.add(SimpleGrantedAuthority("ROLE_${this.name}"))
        return authorities
    }
}
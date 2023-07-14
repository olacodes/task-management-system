package com.olacodes.authservice.persistence.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.olacodes.authservice.dto.UserRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.ZonedDateTime
import java.util.UUID

@JsonInclude(JsonInclude.Include.ALWAYS)
@Entity(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    @NotEmpty
    var name: String,

    @Email(message = "Email should be valid", regexp = ".+[@].+[\\.].+")
    var email: String,

    @get:JvmName("getEntityPassword")
    @Column(nullable = false)
    @Size(min = 8, max = 200, message = "Password must be minimum of 8 characters")
    var password: String,

    var userRole: UserRole = UserRole.USER,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    var createdAt: ZonedDateTime? = null,

    @UpdateTimestamp
    @Column(nullable = false)
    var updatedAt: ZonedDateTime? = null
): UserDetails {

    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return userRole.getAuthorities()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

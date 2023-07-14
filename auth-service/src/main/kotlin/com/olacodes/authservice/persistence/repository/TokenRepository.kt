package com.olacodes.authservice.persistence.repository

import com.olacodes.authservice.persistence.entity.TokenEntity

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional
import java.util.UUID


interface TokenRepository : JpaRepository<TokenEntity, UUID> {
    @Query("""
        SELECT t FROM token t INNER JOIN t.user u
        WHERE u.id = :id AND (t.expired = false OR t.revoked = false)
    """)
    fun findAllValidTokenByUser(id: UUID): List<TokenEntity>
    fun findByToken(token: String): Optional<TokenEntity>
}
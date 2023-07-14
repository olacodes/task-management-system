package com.olacodes.authservice.persistence.entity

import com.olacodes.authservice.dto.TokenType
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.UUID

@Entity(name = "token")
data class TokenEntity (
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: UUID? = null,

    @Column(unique = true)
    var token: String? = null,

    @Enumerated(EnumType.STRING)
    var tokenType: TokenType = TokenType.BEARER,

    var revoked: Boolean = false,

    var expired: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserEntity? = null
)
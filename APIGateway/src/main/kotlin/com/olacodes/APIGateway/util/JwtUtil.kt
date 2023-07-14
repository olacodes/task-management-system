package com.olacodes.APIGateway.util

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtUtil {
    @Value("\${security.jwt.key}")
    private val secretKey: String? = ""

    fun validateToken(token: String) {
        Jwts.parserBuilder().setSigningKey(signInKey).build().parseClaimsJws(token)
    }

    private val signInKey: Key
        private get() {
            val keyBytes: ByteArray = Decoders.BASE64.decode(secretKey)
            return Keys.hmacShaKeyFor(keyBytes)
        }
}
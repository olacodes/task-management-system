package com.olacodes.authservice.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service
import java.lang.System.currentTimeMillis
import java.util.*
import kotlin.collections.HashMap
import java.security.Key;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;


@Service
class JwtService {
    @Value("\${security.jwt.key}")
    private val secretKey: String? = ""

    @Value("\${security.jwt.expiration}")
    private val jwtExpiration: Long = 300000L

    @Value("\${security.jwt.refresh-token.expiration}")
    private val refreshExpiration: Long = 300000000L

    fun extractUsername(token: String): String {
        return extractClaim(token) { claims -> claims.subject }
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver.invoke(claims)
    }

    fun generateToken(userDetails: UserDetails): String {
        return generateToken(HashMap(), userDetails)
    }

    fun generateToken(
        extraClaims: Map<String?, Any?>,
        userDetails: UserDetails
    ): String {
        return buildToken(extraClaims, userDetails, jwtExpiration)
    }

    fun generateRefreshToken(userDetails: UserDetails): String {
        return buildToken(HashMap(), userDetails, refreshExpiration)
    }

    private fun buildToken(
        extraClaims: Map<String?, Any?>,
        userDetails: UserDetails,
        expiration: Long
    ): String {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(currentTimeMillis()))
            .setExpiration(Date(currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS256, signInKey)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) {claims -> claims.expiration }
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(signInKey)
            .parseClaimsJws(token)
            .body
    }

    private val signInKey: Key
         get() {
            val keyBytes: ByteArray = Decoders.BASE64.decode(secretKey)
            return Keys.hmacShaKeyFor(keyBytes)
        }
}
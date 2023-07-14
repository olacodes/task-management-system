package com.olacodes.APIGateway.filter

import com.olacodes.APIGateway.util.JwtUtil
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component


@Component
class AuthenticationFilter(private val validator: RouteValidator, private val jwtUtil: JwtUtil) : AbstractGatewayFilterFactory<AuthenticationFilter.Config>(Config::class.java) {

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            if (validator.isSecured.test(exchange.request)) {
                // Check if the request contains the Authorization header
                val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
                if (authHeader.isNullOrEmpty()) {
                    throw RuntimeException("Missing authorization header")
                }

                // Extract the token from the Authorization header
                val token = if (authHeader.startsWith("Bearer ")) {
                    authHeader.substring(7)
                } else {
                    throw RuntimeException("Invalid authorization header")
                }

                try {
                    // Call the validateToken method of the JwtUtil class
                    jwtUtil.validateToken(token)
                } catch (e: Exception) {
                    println("Invalid access!")
                    throw RuntimeException("Unauthorized access to application")
                }
            }
            chain.filter(exchange)
        }
    }

    class Config
}

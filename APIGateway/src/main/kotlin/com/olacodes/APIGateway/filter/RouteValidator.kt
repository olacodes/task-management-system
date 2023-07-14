package com.olacodes.APIGateway.filter


import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import java.util.function.Predicate

@Component
class RouteValidator {

    companion object {
        val openApiEndpoints = listOf(
            "/api/v1/auth/register",
            "/api/v1/auth/token",
            "/api/v1/auth/authenticate",
            "/eureka"
        )
    }

    val isSecured: Predicate<ServerHttpRequest> = Predicate { request ->
        openApiEndpoints.none { uri -> request.uri.path.contains(uri) }
    }
}

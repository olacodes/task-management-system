package com.olacodes.authservice.configuration

import com.olacodes.authservice.dto.Permission
import com.olacodes.authservice.dto.UserRole
import org.springframework.boot.autoconfigure.security.reactive.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration(
    private val jwtAuthFilter: JwtAuthenticationFilter,
    private val authenticationProvider: AuthenticationProvider,
    private val logoutHandler: LogoutHandler
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeRequests()
            .requestMatchers(
                AntPathRequestMatcher("/api/v1/auth/**"),
                AntPathRequestMatcher("/v2/api-docs"),
                AntPathRequestMatcher("/v3/api-docs"),
                AntPathRequestMatcher("/v3/api-docs/**"),
                AntPathRequestMatcher("/swagger-resources"),
                AntPathRequestMatcher("/swagger-resources/**"),
                AntPathRequestMatcher("/configuration/ui"),
                AntPathRequestMatcher("/configuration/security"),
                AntPathRequestMatcher("/swagger-ui/**"),
                AntPathRequestMatcher("/webjars/**"),
                AntPathRequestMatcher("/swagger-ui.html")
            ).permitAll()
            .requestMatchers(AntPathRequestMatcher("/api/v1/management/**")).hasAnyRole(UserRole.ADMIN.name, UserRole.MANAGER.name)
            .requestMatchers(HttpMethod.GET, "/api/v1/management/**")
            .hasAnyAuthority(Permission.ADMIN_READ.permission, Permission.MANAGER_READ.permission)
            .requestMatchers(HttpMethod.POST, "/api/v1/management/**")
            .hasAnyAuthority(Permission.ADMIN_CREATE.permission, Permission.MANAGER_CREATE.permission)
            .requestMatchers(HttpMethod.PUT, "/api/v1/management/**")
            .hasAnyAuthority(Permission.ADMIN_UPDATE.permission, Permission.MANAGER_UPDATE.permission)
            .requestMatchers(HttpMethod.DELETE, "/api/v1/management/**")
            .hasAnyAuthority(Permission.ADMIN_DELETE.permission, Permission.MANAGER_DELETE.permission)
            .anyRequest().authenticated()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .logout()
            .logoutUrl("/api/v1/auth/logout")
            .addLogoutHandler(logoutHandler)
            .logoutSuccessHandler { _, _, _ ->
                SecurityContextHolder.clearContext()
            }

        return http.build()
    }
}

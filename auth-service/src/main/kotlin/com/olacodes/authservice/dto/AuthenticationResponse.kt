package com.olacodes.authservice.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthenticationResponse(
    @JsonProperty("access_token")
    var accessToken: String,

    @JsonProperty("refresh_token")
    var refreshToken: String
)

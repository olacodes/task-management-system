package com.olacodes.authservice.exception

import org.springframework.web.server.ResponseStatusException

class APIException(code: Int, message: String): ResponseStatusException(code, message, null)
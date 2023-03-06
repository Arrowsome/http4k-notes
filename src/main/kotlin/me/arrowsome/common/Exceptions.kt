package me.arrowsome.common

sealed class ApiException(msg: String) : RuntimeException(msg) {
    class Invalid(msg: String) : ApiException(msg)
    class NotFound(msg: String) : ApiException(msg)
    class ServerError(msg: String) : ApiException(msg)
}


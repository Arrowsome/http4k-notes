package me.arrowsome.common

sealed class ApiException(val msg: String) : RuntimeException() {
    class Invalid(msg: String) : ApiException(msg)
    class NotFound(msg: String) : ApiException(msg)
}


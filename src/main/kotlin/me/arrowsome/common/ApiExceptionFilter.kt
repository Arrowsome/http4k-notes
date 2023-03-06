package me.arrowsome.common

import org.http4k.core.*
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.core.Status.Companion.CONFLICT
import org.http4k.core.Status.Companion.NOT_FOUND
import org.http4k.format.Moshi.auto

val exceptionFilter = Filter { handler ->
    val wrapper: HttpHandler = { request ->
        try {
            handler(request)
        } catch (exc: ApiException.NotFound) {
            Response(NOT_FOUND).with(errorLens of ApiError(exc.message))
        } catch (exc: ApiException.Invalid) {
            Response(BAD_REQUEST).with(errorLens of ApiError(exc.message))
        } catch (exc: ApiException.Conflict) {
            Response(CONFLICT).with(errorLens of ApiError(exc.message))
        }
    }

    return@Filter wrapper
}

private class ApiError(private val error: String?)

private val errorLens = Body.auto<ApiError>().toLens()
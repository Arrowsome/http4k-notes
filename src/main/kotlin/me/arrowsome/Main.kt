package me.arrowsome

import me.arrowsome.common.LOGIN_URL
import me.arrowsome.common.USERS_URL
import me.arrowsome.common.di
import me.arrowsome.common.exceptionFilter
import me.arrowsome.user.UserController
import org.http4k.core.Method
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.kodein.di.instance

val backend: RoutingHttpHandler
    get() {
        val userController: UserController by di.instance()

        return routes(
            // Users routes
            USERS_URL bind Method.POST to userController::registerUser,
            LOGIN_URL bind Method.POST to userController::loginUser,
        )
    }

fun main() {
    DebuggingFilters
        .PrintRequestAndResponse()
        .then(exceptionFilter)
        .then(backend)
        .asServer(SunHttp(9000))
        .start()
}



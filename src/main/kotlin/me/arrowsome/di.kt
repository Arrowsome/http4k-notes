package me.arrowsome

import me.arrowsome.user.UserController
import me.arrowsome.user.UserService
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    bindSingleton { UserController(instance("userService")) }
    bindSingleton("userService") { UserService() }
}
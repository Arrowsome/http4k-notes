package me.arrowsome.common

import me.arrowsome.user.UserController
import me.arrowsome.user.UserRepository
import me.arrowsome.user.UserService
import me.arrowsome.common.JwtHelper
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    bindSingleton { UserController(instance("userService")) }
    bindSingleton("jwtHelper") { JwtHelper() }
    bindSingleton("userRepository") { UserRepository() }
    bindSingleton("userService") { UserService(instance("jwtHelper"), instance("userRepository")) }
}
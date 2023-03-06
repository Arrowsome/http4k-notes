package me.arrowsome.common

import com.mongodb.client.MongoDatabase
import me.arrowsome.user.UserController
import me.arrowsome.user.UserDao
import me.arrowsome.user.UserService
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val di = DI {
    bindSingleton { UserController(instance("userService")) }
    bindSingleton("jwtHelper") { JwtHelper() }
    bindSingleton("database") { createMongoDb() }
    bindSingleton("usersCollection") { instance<MongoDatabase>("database").usersCollection }
    bindSingleton("userRepository") { UserDao(instance("usersCollection")) }
    bindSingleton("userService") { UserService(instance("jwtHelper"), instance("userRepository")) }
}
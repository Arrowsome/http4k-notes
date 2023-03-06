package me.arrowsome.user

import com.mongodb.client.MongoCollection
import org.litote.kmongo.and
import org.litote.kmongo.eq
import org.litote.kmongo.findOne

class UserDao(private val usersCollection: MongoCollection<UserEntity>) {

    fun anyUser(email: String): Boolean {
        val exists = usersCollection
            .findOne(UserEntity::email eq email)

        return exists != null
    }

    fun anyUserWithCredentials(email: String, password: String): Boolean {
        return usersCollection
            .findOne(
                and(
                    UserEntity::email eq email,
                    UserEntity::password eq password,
                ),
            ) != null
    }
}
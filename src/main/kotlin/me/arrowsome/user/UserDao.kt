package me.arrowsome.user

import com.mongodb.client.MongoCollection
import me.arrowsome.common.ApiException
import org.litote.kmongo.*

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

    fun insertUser(entity: UserEntity): UserEntity {
        val result = usersCollection.insertOne(entity)
        if (!result.wasAcknowledged())
            throw ApiException.ServerError("Server malfunction!")
        return usersCollection.findOneById(entity.id)!!
    }
}
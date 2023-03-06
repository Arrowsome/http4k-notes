package me.arrowsome.common

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import me.arrowsome.user.UserEntity
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

fun createMongoDb(): MongoDatabase {
    return KMongo
        .createClient()
        .getDatabase(NOTES_DB)
}

val MongoDatabase.usersCollection: MongoCollection<UserEntity>
    get() = getCollection<UserEntity>("users")

private const val NOTES_DB = "notes"
package me.arrowsome.user

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class UserEntity(
    @BsonId
    val id: Id<UserEntity> = newId(),
    val email: String,
    val password: String,
)

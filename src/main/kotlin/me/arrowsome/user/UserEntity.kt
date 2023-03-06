package me.arrowsome.user

import org.bson.codecs.pojo.annotations.BsonId

data class UserEntity(
    @BsonId
    val id: String? = null,
    val email: String,
    val password: String,
)

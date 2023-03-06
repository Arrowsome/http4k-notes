package me.arrowsome.user

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

class UserDaoTests {
    private lateinit var userDao: UserDao
    private lateinit var usersCollection: MongoCollection<UserEntity>

    @BeforeEach
    fun setup() {
        usersCollection = KMongo
            .createClient()
            .getDatabase(NOTES_TEST_DB)
            .getCollection<UserEntity>(USERS_TEST_COLLECTION)
        userDao = UserDao(usersCollection)
        usersCollection.insertOne(USER_ENTITY)
    }

    @AfterEach
    fun cleanup() {
        usersCollection.drop()
    }

    @Test
    fun `anyUser returns boolean on query for an existing user`() {
        val userOneExists = userDao.anyUser(EMAIL)
        assertThat(userOneExists, equalTo(true))

        val userTwoExists = userDao.anyUser(EMAIL.substring(1))
        assertThat(userTwoExists, equalTo(false))
    }

    @Test
    fun `anyUserWithCredentials returns boolean on credential check`() {
        val userOneAuthenticated = userDao.anyUserWithCredentials(EMAIL, PASSWORD)
        assertThat(userOneAuthenticated, equalTo(true))

        val userTwoAuthenticated = userDao.anyUserWithCredentials(EMAIL.substring(1), PASSWORD)
        assertThat(userTwoAuthenticated, equalTo(false))

        val userThreeAuthenticated = userDao.anyUserWithCredentials(EMAIL, PASSWORD.substring(1))
        assertThat(userThreeAuthenticated, equalTo(false))
    }

    @Test
    fun `insert user in users collection`() {
        usersCollection.drop()
        val user = userDao.insertUser(USER_ENTITY)
        assertNotNull(user)
    }

    companion object {
        private const val EMAIL = "john.doe@gmail.com"
        private const val PASSWORD = "John@123!"

        private val USER_ENTITY = UserEntity(
            email = EMAIL,
            password = PASSWORD,
        )

        private const val NOTES_TEST_DB = "test"
        private const val USERS_TEST_COLLECTION = "testUser"
    }
}
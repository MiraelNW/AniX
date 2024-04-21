package com.miraeldev.impl.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import com.miraeldev.Database
import com.miraeldev.api.UserDao
import com.miraeldev.impl.mapper.toModel
import com.miraeldev.impl.models.user.toDbModel
import com.miraeldev.models.user.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class UserDaoImpl(private val database: Database) : UserDao {

    private val query = database.user_tableQueries
    private val ioDispatcher = Dispatchers.IO

    override fun getUserFlow(): Flow<User> {
        return query.getUserFlow().asFlow().mapToOne(ioDispatcher).map { it.toModel() }
    }

    override suspend fun getUser(): User? {
        return query.getUser().executeAsOneOrNull()?.toModel()
    }

    override suspend fun insertUser(user: User) {
        query.insertUser(
            id = user.id,
            username = user.username,
            name = user.name,
            image = user.image,
            email = user.email,
            lastWatchedAnimeDbModel = user.lastWatchedAnime?.toDbModel()
        )
    }

    override suspend fun deleteOldUser() {
        query.deleteOldUser()
    }
}
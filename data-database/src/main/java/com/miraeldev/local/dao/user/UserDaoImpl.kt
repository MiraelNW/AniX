package com.miraeldev.local.dao.user

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import com.miraeldev.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject
import tables.UserDbModel

@Inject
class UserDaoImpl(private val database: Database) : UserDao {

    private val query = database.user_tableQueries
    private val ioDispatcher = Dispatchers.IO

    override fun getUserFlow(): Flow<UserDbModel> {
        return query.getUserFlow().asFlow().mapToOne(ioDispatcher)
    }

    override suspend fun getUser(): UserDbModel? {
        return query.getUser().executeAsOneOrNull()
    }

    override suspend fun insertUser(user: UserDbModel) {
        query.insertUser(
            id = user.id,
            username = user.username,
            name = user.name,
            image = user.image,
            email = user.email,
            lastWatchedAnimeDbModel = user.lastWatchedAnimeDbModel
        )
    }

    override suspend fun deleteOldUser() {
        query.deleteOldUser()
    }
}
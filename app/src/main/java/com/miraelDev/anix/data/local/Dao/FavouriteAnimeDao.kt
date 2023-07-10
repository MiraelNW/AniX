package com.miraelDev.anix.data.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraelDev.anix.data.local.models.FavouriteAnimeDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteAnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeInfoDb(favouriteAnimeDbModel: FavouriteAnimeDbModel)

    @Query("SELECT * FROM FavouriteAnimeTable")
    fun getFavouriteAnimeList(): Flow<List<FavouriteAnimeDbModel>>
}
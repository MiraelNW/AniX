package com.miraelDev.anix.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miraelDev.anix.data.local.Dao.FavouriteAnimeDao
import com.miraelDev.anix.data.local.Dao.SearchAnimeDao
import com.miraelDev.anix.data.local.models.FavouriteAnimeDbModel
import com.miraelDev.anix.data.local.models.SearchAnimeDbModel

@Database(entities = [SearchAnimeDbModel::class,FavouriteAnimeDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "AppDatabase"
        private val lock = Any()
        private var db: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            synchronized(lock) {
                db?.let { return it }
                val instance = Room.databaseBuilder(context, AppDataBase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }

    abstract fun searchAnimeDao(): SearchAnimeDao

    abstract fun favouriteAnimeDao(): FavouriteAnimeDao
}
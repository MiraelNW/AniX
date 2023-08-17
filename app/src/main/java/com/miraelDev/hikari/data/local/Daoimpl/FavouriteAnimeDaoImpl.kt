package com.miraelDev.hikari.data.local.Dao

import android.util.Log
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.google.common.collect.ImmutableList
import com.miraelDev.database.AppDatabase
import com.miraelDev.hikari.data.local.models.AnimeInfoDbModel
import com.miraelDev.hikari.domain.models.VideoInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import okhttp3.internal.toImmutableList
import javax.inject.Inject


class FavouriteAnimeDaoImpl @Inject constructor(
        private val db: AppDatabase
) : FavouriteAnimeDao {

    private val queries = db.favouriteAnimeQueries

    private val dispatcher = Dispatchers.IO

//    override fun getFavouriteAnimeListFlow(): Flow<List<AnimeInfoDbModel>> {
//        return queries.getFavouriteAnimeList { id, name_ru, name_en, kind, status, rating, episodes, duration, description, genres, score, image_url ->
//            AnimeInfoDbModel(
//                    id = id.toInt(),
//                    nameRu = name_ru,
//                    nameEn = name_en,
//                    kind = kind,
//                    status = status,
//                    rating = rating,
//                    episodes = episodes,
//                    duration = duration,
//                    image = image_url,
//                    genres = genres,
//                    description = description,
//                    score = score,
//                    videoUrls = VideoInfo(),
//            )
//        }.asFlow().mapToList(dispatcher)
//    }

    override fun getFavouriteAnimeList(): List<AnimeInfoDbModel> {
        Log.d("tag","from dao impl "+queries.getFavouriteAnimeList().executeAsList().toString())
        return queries.getFavouriteAnimeList { id, name_ru, name_en, kind, status, rating, episodes, duration, description, genres, score, image_url ->
            AnimeInfoDbModel(
                    id = id.toInt(),
                    nameRu = name_ru,
                    nameEn = name_en,
                    kind = kind,
                    status = status,
                    rating = rating,
                    episodes = episodes,
                    duration = duration,
                    image = image_url,
                    genres = genres,
                    description = description,
                    score = score,
                    videoUrls = VideoInfo(),
            )
        }.executeAsList()
    }

    override suspend fun insertFavouriteAnimeItem(animeItem: AnimeInfoDbModel) {
        queries.insertFavouriteAnimeItem(
                id = animeItem.id.toLong(),
                name_ru = animeItem.nameRu,
                name_en = animeItem.nameEn,
                image_url = animeItem.image,
                description = animeItem.description,
                kind = animeItem.kind,
                score = animeItem.score,
                status = animeItem.status,
                rating = animeItem.rating,
                episodes = animeItem.episodes,
                duration = animeItem.duration,
                genres = animeItem.genres
        )
    }

    override suspend fun deleteFavouriteAnimeItem(animeItemId: Int) {
        queries.deleteFavouriteAnimeItem(animeItemId.toLong())
    }

    override suspend fun searchAnimeItem(name: String): List<AnimeInfoDbModel> {

        return try {
            queries.getFavouriteAnimeItemByName(name = name) { id, name_ru, name_en, kind, status, rating, episodes, duration, description, genres, score, image_url ->
                AnimeInfoDbModel(
                        id = id.toInt(),
                        nameRu = name_ru,
                        nameEn = name_en,
                        kind = kind,
                        status = status,
                        rating = rating,
                        episodes = episodes,
                        duration = duration,
                        image = image_url,
                        genres = genres,
                        description = description,
                        score = score,
                        videoUrls = VideoInfo(),
                )
            }.executeAsList()
        } catch (e: Exception) {
            emptyList()
        }
    }

}
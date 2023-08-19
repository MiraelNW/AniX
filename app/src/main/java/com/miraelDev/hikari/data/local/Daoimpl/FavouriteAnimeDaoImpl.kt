package com.miraelDev.hikari.data.local.Dao

import com.miraelDev.database.AppDatabase
import com.miraelDev.hikari.data.local.models.AnimeInfoDbModel
import com.miraelDev.hikari.domain.models.VideoInfo
import javax.inject.Inject


class FavouriteAnimeDaoImpl @Inject constructor(
        private val db: AppDatabase
) : FavouriteAnimeDao {

    private val queries = db.favouriteAnimeQueries

    override fun getFavouriteAnimeList(): List<AnimeInfoDbModel> {
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
package com.miraeldev.impl.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.miraeldev.Database
import com.miraeldev.api.FavouriteAnimeDao
import com.miraeldev.impl.mapper.toAnimeInfo
import com.miraeldev.impl.mapper.toFavouriteAnimeDbModel
import com.miraeldev.models.anime.AnimeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class FavouriteAnimeDaoImpl(private val database: Database) : FavouriteAnimeDao {

    private val query = database.favourite_tableQueries
    private val ioDispatcher = Dispatchers.IO

    override fun getFavouriteAnimeList(offset: Int): Flow<List<AnimeInfo>> {
        return query.getFavouriteAnimeList(offset.toLong())
            .asFlow()
            .mapToList(ioDispatcher)
            .map { list ->
                list.map { it.toAnimeInfo() }
            }
    }

    override suspend fun insertFavouriteAnimeItem(animeItem: AnimeInfo) {
        val insertItem = animeItem.toFavouriteAnimeDbModel()
        query.insertFavouriteAnimeItem(
            id = insertItem.id,
            nameEn = insertItem.nameEn,
            nameRu = insertItem.nameRu,
            image = insertItem.image,
            kind = insertItem.kind,
            score = insertItem.score,
            status = insertItem.status,
            rating = insertItem.rating,
            releasedOn = insertItem.releasedOn,
            episodes = insertItem.episodes,
            duration = insertItem.duration,
            description = insertItem.description,
            videoUrl = insertItem.videoUrls,
            genres = insertItem.genres,
            isFavourite = insertItem.isFavourite,
            page = insertItem.page
        )
    }

    override suspend fun deleteFavouriteAnimeItem(animeItemId: Int) {
        query.deleteFavouriteAnimeItem(animeItemId.toLong())
    }

    override suspend fun searchAnimeItem(name: String): List<AnimeInfo> {
        return query.searchAnimeItem(name).executeAsList().map { it.toAnimeInfo() }
    }
}
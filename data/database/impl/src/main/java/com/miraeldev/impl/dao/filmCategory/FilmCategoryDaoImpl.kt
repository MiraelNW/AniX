package com.miraeldev.impl.dao.filmCategory

import com.miraeldev.Database
import com.miraeldev.api.filmCategory.FilmCategoryDao
import com.miraeldev.impl.mapper.mapToFilmCategoryModel
import com.miraeldev.impl.mapper.toAnimeInfo
import com.miraeldev.models.anime.AnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto
import me.tatarka.inject.annotations.Inject

@Inject
class FilmCategoryDaoImpl(private val database: Database) : FilmCategoryDao {

    private val query = database.film_category_tableQueries
    override suspend fun insertAll(anime: List<AnimeInfoDto>) {
        val insertList = anime.map { it.mapToFilmCategoryModel() }
        query.transaction {
            insertList.forEach {
                query.insert(
                    id = it.id,
                    nameEn = it.nameEn,
                    nameRu = it.nameRu,
                    image = it.image,
                    kind = it.kind,
                    score = it.score,
                    status = it.status,
                    rating = it.rating,
                    releasedOn = it.releasedOn,
                    episodes = it.episodes,
                    duration = it.duration,
                    description = it.description,
                    videoUrls = it.videoUrls,
                    genres = it.genres,
                    isFavourite = it.isFavourite
                )
            }
        }
    }

    override suspend fun getAnimeList(): List<AnimeInfo> {
        return query.getAnimeList().executeAsList().map { it.toAnimeInfo() }
    }

    override suspend fun isEmpty(): Boolean {
        return query.isEmpty().executeAsOne()
    }
}
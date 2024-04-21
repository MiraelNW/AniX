package com.miraeldev.impl.dao.newCategory

import com.miraeldev.Database
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.api.newCategory.NewCategoryDao
import com.miraeldev.impl.mapper.mapToNewCategoryModel
import com.miraeldev.impl.mapper.toAnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto
import me.tatarka.inject.annotations.Inject

@Inject
class NewCategoryDaoImpl(private val database: Database) : NewCategoryDao {

    private val query = database.new_category_tableQueries
    override suspend fun insertAll(anime: List<AnimeInfoDto>) {
        val insertList = anime.map { it.mapToNewCategoryModel() }
        query.transaction {
            insertList.forEach {
                query.insert(
                    id =  it.id,
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
                    isFavourite = it.isFavourite,
                    createTime = it.createTime
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

    override suspend fun getCreateTime(): Long {
        return query.getCreateTime().executeAsOneOrNull() ?: 0
    }
}
package com.miraeldev.local.dao.popularCategory.impl

import com.miraeldev.Database
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.extensions.toLong
import com.miraeldev.local.dao.popularCategory.api.PopularCategoryDao
import com.miraeldev.local.dao.popularCategory.api.PopularCategoryPagingDao
import com.miraeldev.local.mapper.mapToNewCategoryDbModel
import com.miraeldev.local.mapper.mapToNewCategoryModel
import com.miraeldev.local.mapper.mapToPopularCategoryModel
import com.miraeldev.local.mapper.toAnimeInfo
import com.miraeldev.local.mapper.toLastDbNode
import com.miraeldev.local.mapper.toPagingAnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto
import com.miraeldev.models.paging.LastDbNode
import com.miraeldev.models.paging.PagingAnimeInfo
import me.tatarka.inject.annotations.Inject

@Inject
class PopularCategoryDaoImpl(private val database: Database) : PopularCategoryDao {

    private val query = database.popular_category_tableQueries
    override suspend fun insertAll(anime: List<AnimeInfoDto>) {
        val insertList = anime.map { it.mapToPopularCategoryModel() }
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
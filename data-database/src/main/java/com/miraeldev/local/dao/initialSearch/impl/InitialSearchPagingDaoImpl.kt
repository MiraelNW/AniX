package com.miraeldev.local.dao.initialSearch.impl

import com.miraeldev.Database
import com.miraeldev.extensions.toLong
import com.miraeldev.local.dao.initialSearch.api.InitialSearchPagingDao
import com.miraeldev.local.mapper.mapToInitialSearchModel
import com.miraeldev.local.mapper.toLastDbNode
import com.miraeldev.local.mapper.toPagingAnimeInfo
import com.miraeldev.models.dto.AnimeInfoDto
import com.miraeldev.models.paging.LastDbNode
import com.miraeldev.models.paging.PagingAnimeInfo
import me.tatarka.inject.annotations.Inject

@Inject
class InitialSearchPagingDaoImpl(private val database: Database) : InitialSearchPagingDao {

    private val query = database.initial_search_paging_tableQueries

    override suspend fun insertAll(
        anime: List<AnimeInfoDto>,
        page: Long,
        isLast: Boolean,
        insertTime: Long
    ) {
        val insertList = anime.map { it.mapToInitialSearchModel() }
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
                    page = page,
                    isLast = isLast.toLong(),
                    createTime = insertTime
                )
            }
        }
    }

    override fun getLastNode(): LastDbNode {
        return query.getLastNode().executeAsOneOrNull().toLastDbNode()
    }

    override fun getAnimeByPage(page: Long): List<PagingAnimeInfo> {
        return query.getPagingAnime(page = page).executeAsList().map { it.toPagingAnimeInfo() }
    }

    override suspend fun clearAllAnime() {
        query.clearAllAnime()
    }
}
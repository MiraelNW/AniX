package com.miraeldev.local.dao.newCategory.impl

import androidx.paging.PagingSource
import com.miraeldev.Database
import com.miraeldev.anime.AnimeInfo
import com.miraeldev.local.dao.newCategory.api.NewCategoryPagingDao
import com.miraeldev.local.models.newCategory.PagingNewCategoryAnimeInfoDbModel
import kotlinx.coroutines.flow.Flow

//class NewCategoryPagingDaoImpl(
//    private val database: Database
//) : NewCategoryPagingDao {
//
//    private val query = database.new_category_paging_tableQueries
//    override suspend fun insertAll(anime: List<PagingNewCategoryAnimeInfoDbModel>) {
//        query.transaction {
//            anime.forEach {
//                query.insert(
//                    id =  it.id,
//                    nameEn = it.nameEn,
//                    nameRu = it.nameRu,
//                    image = it.image,
//                    kind = it.kind,
//                    score = it.score,
//                    status = it.status,
//                    rating = it.rating,
//                    releasedOn = it.releasedOn,
//                    episodes = it.episodes,
//                    duration = it.duration,
//                    description = it.description,
//                    videoUrls = it.videoUrls,
//                    genres = it.genres,
//                    isFavourite = it.isFavourite,
//                    createTime = it.createTime
//                )
//            }
//        }
//    }
//
//    override fun getAnime(): PagingSource<Int, AnimeInfo> {
//
//    }
//
//    override suspend fun isEmpty(): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun clearAllAnime() {
//        TODO("Not yet implemented")
//    }
//}
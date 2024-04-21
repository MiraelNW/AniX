package com.miraeldev.impl.repository

import com.miraeldev.api.AnimeListDataRepository
import com.miraeldev.api.AppNetworkClient
import com.miraeldev.api.filmCategory.FilmCategoryPagingDao
import com.miraeldev.api.nameCategory.NameCategoryPagingDao
import com.miraeldev.api.newCategory.NewCategoryPagingDao
import com.miraeldev.api.popularCategory.PopularCategoryPagingDao
import com.miraeldev.impl.mapper.mapToPagingModel
import com.miraeldev.impl.pagingController.PagingController
import com.miraeldev.models.dto.Response
import com.miraeldev.models.paging.PagingState
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class AnimeListDataRepositoryImpl(
    private val appNetworkClient: AppNetworkClient,

    private val newCategoryPagingDao: NewCategoryPagingDao,
    private val popularCategoryPagingDao: PopularCategoryPagingDao,
    private val nameCategoryPagingDao: NameCategoryPagingDao,
    private val filmCategoryPagingDao: FilmCategoryPagingDao,
) : AnimeListDataRepository {

    private val newPagingController = PagingController(
        pagingRequest = {
            appNetworkClient.getNewCategoryList(it).body<Response>().mapToPagingModel()
        },
        lastNodeInDb = { newCategoryPagingDao.getLastNode() },
        getAnimeListByPage = { newCategoryPagingDao.getAnimeByPage(it) },
        cashSuccessNetworkResult = { animeList, page, isLast ->
            if (page == 0L) newCategoryPagingDao.clearAllAnime()
            newCategoryPagingDao.insertAll(
                anime = animeList,
                insertTime = System.currentTimeMillis(),
                page = page,
                isLast = isLast
            )
        },
        currentTime = System.currentTimeMillis()
    )

    private val popularPagingController = PagingController(
        pagingRequest = {
            appNetworkClient.getPopularCategoryList(it).body<Response>().mapToPagingModel()
        },
        lastNodeInDb = { popularCategoryPagingDao.getLastNode() },
        getAnimeListByPage = { popularCategoryPagingDao.getAnimeByPage(it) },
        cashSuccessNetworkResult = { animeList, page, isLast ->
            if (page == 0L) popularCategoryPagingDao.clearAllAnime()
            popularCategoryPagingDao.insertAll(
                anime = animeList,
                insertTime = System.currentTimeMillis(),
                page = page,
                isLast = isLast
            )
        },
        currentTime = System.currentTimeMillis()
    )

    private val namePagingController = PagingController(
        pagingRequest = {
            appNetworkClient.getNameCategoryList(it).body<Response>().mapToPagingModel()
        },
        lastNodeInDb = { nameCategoryPagingDao.getLastNode() },
        getAnimeListByPage = { nameCategoryPagingDao.getAnimeByPage(it) },
        cashSuccessNetworkResult = { animeList, page, isLast ->
            if (page == 0L) nameCategoryPagingDao.clearAllAnime()
            nameCategoryPagingDao.insertAll(
                anime = animeList,
                insertTime = System.currentTimeMillis(),
                page = page,
                isLast = isLast
            )
        },
        currentTime = System.currentTimeMillis()
    )

    private val filmPagingController = PagingController(
        pagingRequest = {
            appNetworkClient.getFilmCategoryList(it).body<Response>().mapToPagingModel()
        },
        lastNodeInDb = { filmCategoryPagingDao.getLastNode() },
        getAnimeListByPage = { filmCategoryPagingDao.getAnimeByPage(it) },
        cashSuccessNetworkResult = { animeList, page, isLast ->
            if (page == 0L) filmCategoryPagingDao.clearAllAnime()
            filmCategoryPagingDao.insertAll(
                anime = animeList,
                insertTime = System.currentTimeMillis(),
                page = page,
                isLast = isLast
            )
        },
        currentTime = System.currentTimeMillis()
    )


    override fun getPagingNewAnimeList(): Flow<PagingState> = newPagingController.flow
    override fun getPagingPopularAnimeList(): Flow<PagingState> = popularPagingController.flow
    override fun getPagingNameAnimeList(): Flow<PagingState> = namePagingController.flow
    override fun getPagingFilmsAnimeList(): Flow<PagingState> = filmPagingController.flow

    override suspend fun loadNewCategoryNextPage() {
        newPagingController.loadNextPage()
    }

    override suspend fun loadPopularCategoryNextPage() {
        popularPagingController.loadNextPage()
    }

    override suspend fun loadNameCategoryNextPage() {
        namePagingController.loadNextPage()
    }

    override suspend fun loadFilmCategoryNextPage() {
        filmPagingController.loadNextPage()
    }

}
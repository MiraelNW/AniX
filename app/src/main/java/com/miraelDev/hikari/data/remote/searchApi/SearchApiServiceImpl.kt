package com.miraelDev.hikari.data.remote.searchApi

import com.miraelDev.hikari.data.remote.ApiRoutes
import com.miraelDev.hikari.data.remote.dto.AnimeInfoDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class SearchApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : com.miraelDev.hikari.data.remote.searchApi.SearchApiService {
    override suspend fun searchAnimeByName(name: String): List<AnimeInfoDto> {
        return client.get<Array<AnimeInfoDto>>("${ApiRoutes.SEARCH_URL}$name").toList()
    }

}
package com.miraelDev.vauma.data.remote

object ApiRoutes {

    private const val BASE_URL = "https://vauma.fun/api"

    const val SEARCH_URL_ANIME_LIST = "$BASE_URL/animes/?multiname="

    const val SEARCH_URL_ANIME_ID = "$BASE_URL/animes/"

    const val GET_NEW_CATEGORY_LIST = "$BASE_URL/animes/?sort=date&format=json&"

    const val GET_POPULAR_CATEGORY_LIST = "$BASE_URL/animes/?sort=score&format=json&"

    const val GET_FILMS_CATEGORY_LIST = "$BASE_URL/animes/?sort=film&format=json&"

    const val GET_NAME_CATEGORY_LIST = "$BASE_URL/animes/?sort=name&format=json&"
}
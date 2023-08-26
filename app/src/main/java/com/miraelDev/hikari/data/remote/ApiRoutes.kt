package com.miraelDev.hikari.data.remote

object ApiRoutes {
//    http://158.160.112.4/api/v1/search/?multi=
    private const val BASE_URL = "http://158.160.112.4/api/v1"

    const val SEARCH_URL_ANIME_LIST = "$BASE_URL/search/?multi="

    const val SEARCH_URL_ANIME_ID = "$BASE_URL/animelist/"

    const val GET_NEW_CATEGORY_LIST = "$BASE_URL/category/new/?"

    const val GET_POPULAR_CATEGORY_LIST = "$BASE_URL/category/popular/?"

    const val GET_FILMS_CATEGORY_LIST = "$BASE_URL/category/film/?"

    const val GET_NAME_CATEGORY_LIST = "$BASE_URL/category/name/?"
}
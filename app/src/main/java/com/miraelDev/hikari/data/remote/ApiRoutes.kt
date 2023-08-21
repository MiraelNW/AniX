package com.miraelDev.hikari.data.remote

object ApiRoutes {

    const val BASE_URL = "http://158.160.112.4"


    const val SEARCH_URL_ANIME_LIST = "$BASE_URL/api/v1/search/?russian="

    const val SEARCH_URL_ANIME_ID = "$BASE_URL/api/v1/animelist/"

    const val GET_NEW_CATEGORY_LIST = "$BASE_URL/category/new/"

    const val GET_POPULAR_CATEGORY_LIST = "$BASE_URL/category/popular/"
}
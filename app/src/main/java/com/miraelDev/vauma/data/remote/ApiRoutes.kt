package com.miraelDev.vauma.data.remote

object ApiRoutes {

    const val SEARCH_URL_ANIME_LIST = "api/animes/?multiname="

    const val SEARCH_URL_ANIME_ID = "api/animes/"

    const val GET_NEW_CATEGORY_LIST = "api/animes/?sort=date&format=json&"

    const val GET_POPULAR_CATEGORY_LIST = "api/animes/?sort=score&format=json&"

    const val GET_FILMS_CATEGORY_LIST = "api/animes/?sort=film&format=json&"

    const val GET_NAME_CATEGORY_LIST = "api/animes/?sort=name&format=json&"
}
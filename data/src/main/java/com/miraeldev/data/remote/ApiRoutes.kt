package com.miraeldev.data.remote

import com.miraeldev.data.BuildConfig

internal object ApiRoutes {

    const val SEARCH_URL_ANIME_LIST_ROUTE = BuildConfig.SEARCH_URL

    const val SEARCH_URL_ANIME_ID_ROUTE = BuildConfig.GET_BY_ID

    const val SET_ANIME_FAV_STATUS = BuildConfig.SET_ANIME_FAV_STATUS

    const val GET_NEW_CATEGORY_LIST_ROUTE = BuildConfig.NEW_CATEGORY_URL

    const val GET_POPULAR_CATEGORY_LIST_ROUTE = BuildConfig.POPULAR_CATEGORY_URL

    const val GET_FILMS_CATEGORY_LIST_ROUTE = BuildConfig.FILMS_CATEGORY_URL

    const val GET_NAME_CATEGORY_LIST_ROUTE = BuildConfig.NAME_CATEGORY_URL


    const val GET_USER_INFO = BuildConfig.GET_USER_INFO

    const val CHANGE_PASSWORD = BuildConfig.CHANGE_PASSWORD
}
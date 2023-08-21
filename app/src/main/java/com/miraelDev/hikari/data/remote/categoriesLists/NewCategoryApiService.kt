package com.miraelDev.hikari.data.remote.categoriesLists

interface NewCategoryApiService {

    suspend fun loadNewCategoryList()

    fun getNewCategoryList()

}
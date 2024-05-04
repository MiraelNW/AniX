package com.miraeldev.impl

import android.content.Context
import coil3.network.ktor.KtorNetworkFetcherFactory
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.miraeldev.api.VaumaImageLoader
import me.tatarka.inject.annotations.Inject

@Inject
class VaumaImageLoaderImpl(
    private val context: Context,
    private val appNetworkClient: com.miraeldev.api.AppNetworkClient
) : VaumaImageLoader {
    override fun load(config: ImageRequest.Builder.() -> ImageRequest.Builder): ImageRequest = ImageRequest
        .Builder(context)
        .fetcherFactory(KtorNetworkFetcherFactory(appNetworkClient.client))
        .crossfade(true)
        .config()
        .build()
}
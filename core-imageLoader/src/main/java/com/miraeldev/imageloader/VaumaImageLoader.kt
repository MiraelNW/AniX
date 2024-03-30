package com.miraeldev.imageloader

import coil3.request.ImageRequest

interface VaumaImageLoader {
    fun load(config: ImageRequest.Builder.() -> ImageRequest.Builder): ImageRequest
}
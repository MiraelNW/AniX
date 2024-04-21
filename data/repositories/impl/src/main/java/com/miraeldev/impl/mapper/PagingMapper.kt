package com.miraeldev.impl.mapper

import android.util.Log
import com.miraeldev.anime.VideoInfo
import com.miraeldev.models.dto.Response
import com.miraeldev.models.dto.toModel
import com.miraeldev.models.paging.PagingAnimeInfo

fun Response.mapToPagingModel(): List<PagingAnimeInfo> {
    return this.results.map {
        PagingAnimeInfo(
            id = it.id,
            nameEn = it.name,
            nameRu = it.russianName,
            image = it.image.toModel(),
            kind = it.kind,
            score = it.score,
            releasedOn = it.releasedOn,
            status = it.status,
            episodes = it.episodes,
            rating = it.rating,
            description = it.description,
            duration = it.duration,
            genres = it.genres.map { it.toModel() },
            videos = it.videos.map { it.toModel() }.firstOrNull() ?: VideoInfo(),
            isFavourite = it.isFavourite,
            isLast = this.isLast,
            createAt = 0L,
            page = 0
        )
    }
}
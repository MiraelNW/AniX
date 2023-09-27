package com.miraelDev.vauma.domain.downloader

interface Downloader {
    suspend fun downloadVideo(url: String, videoName: String):Long
}
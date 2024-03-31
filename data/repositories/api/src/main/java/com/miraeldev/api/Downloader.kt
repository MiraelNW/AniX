package com.miraeldev.api

interface Downloader {
    suspend fun downloadVideo(url: String, videoName: String):Long
}
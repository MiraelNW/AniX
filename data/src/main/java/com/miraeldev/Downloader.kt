package com.miraeldev

interface Downloader {
    suspend fun downloadVideo(url: String, videoName: String):Long
}
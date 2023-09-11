package com.miraelDev.vauma.domain.downloader

interface Downloader {
    fun downloadVideo(url: String, videoName: String): Long
}
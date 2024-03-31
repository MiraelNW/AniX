package com.miraeldev.data.downloadMananger

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.miraeldev.Downloader
import com.miraeldev.api.PreferenceClient
import kotlinx.coroutines.flow.first
import me.tatarka.inject.annotations.Inject

@Inject
class AndroidDownloader(
    private val context: Context,
    private val preferenceClient: PreferenceClient
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override suspend fun downloadVideo(url: String, videoName: String): Long {

        val onlyWifi = preferenceClient.getIsWifiOnly().first()

        val request = DownloadManager.Request(url.toUri())
            .setMimeType("video/mp4")
            .setAllowedOverMetered(!onlyWifi)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setTitle("Vauma: episode $videoName")
//            .addRequestHeader("Authorization", "Bearer <token>")
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "Vauma/episode $videoName.mp4"
            )

        return downloadManager.enqueue(request)
    }
}
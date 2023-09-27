package com.miraelDev.vauma.data.downloadMananger

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.core.net.toUri
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.miraelDev.vauma.data.dataStore.PreferenceManager
import com.miraelDev.vauma.domain.downloader.Downloader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single

class AndroidDownloader(
    private val context: Context,
    private val preferenceManager: PreferenceManager
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    private val isWifiOnlyKey = booleanPreferencesKey(IS_WIFI_ONLY_KEY)

    private val onlyWifiFlow = preferenceManager.getPreference(isWifiOnlyKey, true)

    override suspend fun downloadVideo(url: String, videoName: String): Long {

        val onlyWifi = onlyWifiFlow.first()

        val request = DownloadManager.Request(url.toUri())
            .setMimeType("video/mp4")
            .setAllowedOverMetered(!onlyWifi)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setTitle("Vauma: episode $videoName")
//            .addRequestHeader("Authorization", "Bearer <token>")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Vauma/episode $videoName.mp4")

        return downloadManager.enqueue(request)
    }

    companion object {
        private const val IS_WIFI_ONLY_KEY = "is_wifi_only_key"
    }
}
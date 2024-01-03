package com.miraeldev.detailinfo.data.repositories

interface Logger {
    fun logError(msg: String, error: Throwable)
}
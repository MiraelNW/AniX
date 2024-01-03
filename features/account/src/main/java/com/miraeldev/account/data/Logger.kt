package com.miraeldev.account.data

interface Logger {
    fun logError(msg: String, error: Throwable)
}
package com.miraeldev.signin.data.repositories

interface Logger {
    fun logError(msg: String, error: Throwable)
}
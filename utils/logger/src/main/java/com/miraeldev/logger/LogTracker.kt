package com.miraeldev.logger

interface LogTracker {
    fun sendErrorLog(logError: LogError)
}
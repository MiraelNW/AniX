package com.miraeldev.logger.impl

import com.miraeldev.logger.LogError
import com.miraeldev.logger.LogTracker
import com.miraeldev.logger.LoggerApi
import javax.inject.Inject

internal class LoggerImpl @Inject constructor(
    private val logTrackers: LogTracker
) : LoggerApi {

    override fun logError(logError: LogError) {
        logTrackers.sendErrorLog(logError)
    }
}
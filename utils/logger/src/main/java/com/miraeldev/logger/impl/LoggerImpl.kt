package com.miraeldev.logger.impl

import com.miraeldev.logger.LogError
import com.miraeldev.logger.LogTracker
import com.miraeldev.logger.LoggerApi
import me.tatarka.inject.annotations.Inject

@Inject
class LoggerImpl(
    private val logTrackers: LogTracker
) : LoggerApi {

    override fun logError(logError: LogError) {
        logTrackers.sendErrorLog(logError)
    }
}
package com.miraelDev.vauma.glue.detailInfo.repositories

import com.miraeldev.detailinfo.data.repositories.Logger
import com.miraeldev.logger.LogError
import com.miraeldev.logger.LoggerApi
import me.tatarka.inject.annotations.Inject

@Inject
class LoggerDetailInfoImpl(
    private val logger: LoggerApi
) : Logger {

    override fun logError(msg: String, error: Throwable) {
        logger.logError(LogError(msg, error))
    }
}
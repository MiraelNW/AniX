package com.miraelDev.vauma.glue.detailInfo.repositories

import com.miraeldev.logger.LogError
import com.miraeldev.logger.LoggerApi
import com.miraeldev.detailinfo.data.repositories.Logger
import javax.inject.Inject

class LoggerDetailInfoImpl @Inject constructor(
    private val logger: LoggerApi
) : Logger {

    override fun logError(msg: String, error: Throwable) {
        logger.logError(LogError(msg,error))
    }

}
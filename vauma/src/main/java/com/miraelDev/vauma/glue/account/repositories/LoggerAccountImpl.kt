package com.miraelDev.vauma.glue.account.repositories

import com.miraeldev.logger.LogError
import com.miraeldev.logger.LoggerApi
import com.miraeldev.account.data.Logger
import javax.inject.Inject

class LoggerAccountImpl @Inject constructor(
    private val logger: LoggerApi
) : Logger {

    override fun logError(msg: String, error: Throwable) {
        logger.logError(LogError(msg,error))
    }

}
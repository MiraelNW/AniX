package com.miraelDev.vauma.glue.account.repositories

import com.miraeldev.account.data.Logger
import com.miraeldev.logger.LogError
import com.miraeldev.logger.LoggerApi
import me.tatarka.inject.annotations.Inject

@Inject
class LoggerAccountImpl(
    private val logger: LoggerApi
) : Logger {

    override fun logError(msg: String, error: Throwable) {
        logger.logError(LogError(msg, error))
    }

}
package com.miraelDev.vauma.glue.signIn.repositories

import com.miraeldev.logger.LogError
import com.miraeldev.logger.LoggerApi
import com.miraeldev.signin.data.repositories.Logger
import javax.inject.Inject

class LoggerSignInImpl @Inject constructor(
    private val logger: LoggerApi
) : Logger {

    override fun logError(msg: String, error: Throwable) {
        logger.logError(LogError(msg, error))
    }
}
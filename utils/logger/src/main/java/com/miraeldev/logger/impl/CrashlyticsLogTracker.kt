package com.miraeldev.logger.impl

import com.miraeldev.logger.LogError
import com.miraeldev.logger.LogTracker
import javax.inject.Inject

class CrashlyticsLogTracker @Inject constructor() : LogTracker {

    private val crashlytics: ExtensionCrashlytics by lazy { ExtensionCrashlytics.getInstance() }

    override fun sendErrorLog(logError: LogError) {
        crashlytics.log(logError.msg)
        crashlytics.recordException(logError.error)
    }
}
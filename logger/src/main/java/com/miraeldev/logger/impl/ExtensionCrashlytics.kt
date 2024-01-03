package com.miraeldev.logger.impl

import com.google.firebase.crashlytics.FirebaseCrashlytics

internal class ExtensionCrashlytics {

    val crashlytics by lazy { FirebaseCrashlytics.getInstance() }

    fun log(param0: String?) {
        crashlytics.log(param0 ?: "")
    }

    fun recordException(param0: Throwable?) {
        crashlytics.recordException(param0!!)
    }

    fun setCustomKey(param0: String, param1: Boolean) {
        crashlytics.setCustomKey(param0, param1)
    }

    fun setUserId(param0: String) {
        crashlytics.setUserId(param0)
    }

    companion object {
        fun getInstance() = ExtensionCrashlytics()
    }

}
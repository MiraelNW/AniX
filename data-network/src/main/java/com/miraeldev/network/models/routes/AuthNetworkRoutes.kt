package com.miraeldev.network.models.routes

import com.miraeldev.data_network.BuildConfig


object AuthNetworkRoutes {
    const val AUTH_REFRESH_URL = BuildConfig.AUTH_REFRESH_URL
    const val AUTH_LOGOUT_URL = BuildConfig.AUTH_LOGOUT_URL
    const val VK_LOGIN = BuildConfig.VK_LOGIN
    const val GOOGLE_LOGIN = BuildConfig.GOOGLE_LOGIN
    const val AUTH_LOGIN_URL = BuildConfig.AUTH_LOGIN_URL
    const val AUTH_VERIFY_OTP_URL = BuildConfig.AUTH_VERIFY_OTP_URL
    const val AUTH_REGISTER_URL = BuildConfig.AUTH_REGISTER_URL
    const val CHECK_EMAIL = BuildConfig.CHECK_EMAIL
    const val VERIFY_OTP_FORGOT_PASSWORD = BuildConfig.VERIFY_OTP_FORGOT_PASSWORD
    const val CREATE_NEW_PASSWORD = BuildConfig.CREATE_NEW_PASSWORD
}
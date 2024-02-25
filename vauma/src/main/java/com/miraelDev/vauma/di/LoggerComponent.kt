package com.miraelDev.vauma.di

import com.miraelDev.vauma.glue.account.di.LoggerAccountModule
import com.miraelDev.vauma.glue.detailInfo.di.LoggerDetailInfoModule
import com.miraeldev.logger.di.LoggerFeature

interface LoggerComponent :
    LoggerFeature,
    LoggerAccountModule
package com.miraelDev.vauma.di

import com.miraelDev.vauma.glue.account.di.LoggerAccountModule
import com.miraeldev.logger.di.LoggerFeature

interface LoggerComponent :
    LoggerFeature,
    LoggerAccountModule
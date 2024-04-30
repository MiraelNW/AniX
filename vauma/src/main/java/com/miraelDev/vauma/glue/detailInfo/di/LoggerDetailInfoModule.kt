package com.miraelDev.vauma.glue.detailInfo.di

import com.miraeldev.detailinfo.data.repositories.Logger

interface LoggerDetailInfoModule {

    val provideLogger: Logger
}
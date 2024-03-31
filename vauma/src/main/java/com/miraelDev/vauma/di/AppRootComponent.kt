package com.miraelDev.vauma.di

import com.arkivanov.decompose.ComponentContext
import com.miraelDev.vauma.presentation.appRootComponent.DefaultAppRootComponent
import me.tatarka.inject.annotations.Component

@Component
abstract class AppRootComponent(@Component val parent: ApplicationComponentt) {
    abstract fun appRootFactory(): (ComponentContext) -> DefaultAppRootComponent
}
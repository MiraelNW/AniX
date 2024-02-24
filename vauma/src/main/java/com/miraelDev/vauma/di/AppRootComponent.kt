package com.miraelDev.vauma.di

import com.arkivanov.decompose.ComponentContext
import com.miraelDev.vauma.navigation.mainComponent.DefaultMainRootComponent
import com.miraelDev.vauma.presentation.appRootComponent.DefaultAppRootComponent
import me.tatarka.inject.annotations.Component

@Component
abstract class AppRootComponent(@Component val parent: ApplicationComponent) {
    abstract fun create(): (ComponentContext) -> DefaultAppRootComponent
}

@Component
abstract class MainRootComponent(@Component val parent: AppRootComponent) {
    abstract fun create(): (ComponentContext) -> DefaultMainRootComponent
}
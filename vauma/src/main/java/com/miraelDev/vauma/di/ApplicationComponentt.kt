package com.miraelDev.vauma.di

import android.content.Context
import com.miraeldev.models.di.scope.Singleton
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@Singleton
abstract class ApplicationComponentt(
    @get:Provides val context: Context,
) : GlueFeatureComponentComponentt

interface ApplicationComponentProvider {
    val component: ApplicationComponentt
}

val Context.applicationComponent get() = (applicationContext as ApplicationComponentProvider).component
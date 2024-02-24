package com.miraelDev.vauma.di

import android.content.Context
import com.miraelDev.vauma.domain.repository.MainRepository
import com.miraeldev.di.DataComponent
import com.miraeldev.di.create
import com.miraeldev.di.scope.Singleton
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides

@Component
@Singleton
abstract class ApplicationComponent(
    @get:Provides val context: Context,
    @Component val storeFactoryComponent: StoreFactoryComponent = StoreFactoryComponent::class.create(),
    @Component val glueComponent: GlueComponent = GlueComponent::class.create()
)

interface ApplicationComponentProvider {
    val component: ApplicationComponent
}

val Context.applicationComponent get() = (applicationContext as ApplicationComponentProvider).component
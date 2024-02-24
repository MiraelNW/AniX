package com.miraelDev.vauma

import android.app.Application
import androidx.compose.runtime.Stable
import com.miraelDev.vauma.di.ApplicationComponent
import com.miraelDev.vauma.di.ApplicationComponentProvider
import com.miraelDev.vauma.di.create

@Stable
class VaumaApplication : Application(), ApplicationComponentProvider {


    override val component: ApplicationComponent by lazy {
        ApplicationComponent::class.create(context = applicationContext)
    }
}

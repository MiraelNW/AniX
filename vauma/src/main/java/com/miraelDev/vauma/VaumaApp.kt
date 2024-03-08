package com.miraelDev.vauma

import android.app.Application
import androidx.compose.runtime.Stable
import com.miraelDev.vauma.di.ApplicationComponent
import com.miraelDev.vauma.di.ApplicationComponentProvider
import com.miraelDev.vauma.di.create
import com.pluto.Pluto
import com.pluto.plugins.datastore.pref.PlutoDatastorePreferencesPlugin
import com.pluto.plugins.network.PlutoNetworkPlugin

@Stable
class VaumaApp : Application(), ApplicationComponentProvider {


    override val component: ApplicationComponent by lazy {
        ApplicationComponent::class.create(context = applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        Pluto.Installer(this)
            .addPlugin(PlutoNetworkPlugin())
            .addPlugin(PlutoDatastorePreferencesPlugin())
            .install()
    }
}

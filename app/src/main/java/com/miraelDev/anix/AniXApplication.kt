package com.miraelDev.anix

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.miraelDev.anix.di.ApplicationComponent
import com.miraelDev.anix.di.DaggerApplicationComponent

class AniXApplication : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as AniXApplication).component
}
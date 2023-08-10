package com.miraelDev.hikari.data.remote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NetworkHandler @Inject constructor(private val context: Context) {

    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkConnectionCallback: ConnectivityManager.NetworkCallback

    private var _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    init {
        updateNetworkConnection()
        connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
    }

//    override fun onInactive() {
//        super.onInactive()
//        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback())
//    }

    private fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback {

        networkConnectionCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onLost(network: Network) {
                super.onLost(network)
                _isConnected.value = false
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                _isConnected.value = true
            }

        }

        return networkConnectionCallback
    }

    private fun updateNetworkConnection() {
        val activeNetworkConnection = connectivityManager.activeNetworkInfo
        _isConnected.value = activeNetworkConnection?.isConnected == true

    }


}
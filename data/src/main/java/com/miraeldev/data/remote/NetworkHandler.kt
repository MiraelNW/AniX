package com.miraeldev.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.tatarka.inject.annotations.Inject

@Inject
class NetworkHandler constructor(private val context: Context) {

    private val connectivityManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkConnectionCallback: ConnectivityManager.NetworkCallback

    private var _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    init {
        updateNetworkConnection()
        connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
    }

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
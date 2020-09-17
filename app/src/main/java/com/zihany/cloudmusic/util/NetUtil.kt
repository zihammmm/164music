package com.zihany.cloudmusic.util

import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.zihany.cloudmusic.App

class NetUtil {
    companion object {
        private val TAG = "NetUtil"

        fun isConnected(): Boolean {
            val connectivity: ConnectivityManager
                    = App.getContext().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivity.activeNetworkInfo
            return activeNetwork != null
        }

        fun isWifi(): Boolean {
            val cm = App.getContext().getSystemService(CONNECTIVITY_SERVICE)!! as ConnectivityManager
            val network = cm.activeNetwork
            val capabilities = cm.getNetworkCapabilities(network)
            return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }
}
package com.example.newsapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.newsapp.NewsApplication


class ConnectivityUtil {
    companion object {
        fun isNetworkAvailable(): Boolean {
            val cm =
                NewsApplication.getInstance()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
                return if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                } else {
                    false
                }
            } else {
                val activeNetwork = cm.activeNetworkInfo
                return if (activeNetwork != null) {
                    // connected to the internet
                    when {
                        activeNetwork.type == ConnectivityManager.TYPE_WIFI -> true
                        activeNetwork.type == ConnectivityManager.TYPE_MOBILE -> true
                        else -> false
                    }
                } else {
                    false
                }
            }
        }

    }
}
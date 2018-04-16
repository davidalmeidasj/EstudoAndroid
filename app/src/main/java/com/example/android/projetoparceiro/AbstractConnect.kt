package com.example.android.projetoparceiro

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.net.ConnectivityManager


abstract class AbstractConnect : AppCompatActivity() {

    protected fun isOnline(): Boolean {

        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}
package com.zihany.cloudmusic.base

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class NotifyHelper(
        private val context: Context
) {
    fun createChannel(channelID: String, channelName: CharSequence, description: String) {
        val channel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
            this.description = description
        }
        val notificationManager = 
    }
}
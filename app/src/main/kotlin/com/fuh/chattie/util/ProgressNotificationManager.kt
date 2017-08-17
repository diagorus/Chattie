package com.fuh.chattie.util

import android.app.Notification
import android.app.NotificationManager
import android.content.Context

/**
 * Created by lll on 14.07.2017.
 */
class ProgressNotificationManager(private val context: Context) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val notificationBuilder by lazy {
        Notification.Builder(context)
    }

    fun startWaiting(id: Int, options: Options) {
        val notification = notificationBuilder
                .setContentTitle(options.contentTitle)
                .setContentText(options.contentText)
                .setSmallIcon(options.smallIconId)
                .setProgress(0, 0, false)
                .setOngoing(true)
                .build()

        notificationManager.notify(id, notification)
    }

    fun startIntermediate(id: Int, options: Options) {

        val notification = notificationBuilder
                .setContentTitle(options.contentTitle)
                .setContentText(options.contentText)
                .setSmallIcon(options.smallIconId)
                .setProgress(0, 0, true)
                .setOngoing(true)
                .build()

        notificationManager.notify(id, notification)
    }

    fun startDeterminate(id: Int, options: Options) {
        val notification = notificationBuilder
                .setContentTitle(options.contentTitle)
                .setContentText(options.contentText)
                .setSmallIcon(options.smallIconId)
                .setProgress(100, 0, false)
                .setOngoing(true)
                .build()

        notificationManager.notify(id, notification)
    }

    fun progress(id: Int, progress: Int) {
        val notification = notificationBuilder
                .setProgress(100, progress, false)
                .build()

        notificationManager.notify(id, notification)
    }

    fun complete(id: Int, options: Options) {
        val notification = notificationBuilder
                .setContentTitle(options.contentTitle)
                .setContentText(options.contentText)
                .setSmallIcon(options.smallIconId)
                .setProgress(0, 0, false)
                .setOngoing(false)
                .build()

        notificationManager.notify(id, notification)
    }

    fun fail(id: Int, options: Options) {
        val notification = notificationBuilder
                .setContentTitle(options.contentTitle)
                .setContentText(options.contentText)
                .setSmallIcon(options.smallIconId)
                .setProgress(0, 0, false)
                .setOngoing(false)
                .build()

        notificationManager.notify(id, notification)
    }

    fun cancel(id: Int) {
        notificationManager.cancel(id)
    }

    data class Options(
            val contentTitle: String,
            val contentText: String,
            val smallIconId: Int
    )
}
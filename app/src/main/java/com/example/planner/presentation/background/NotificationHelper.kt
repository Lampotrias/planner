package com.example.planner.presentation.background

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.planner.R
import javax.inject.Inject


class NotificationHelper @Inject constructor(private val context: Context) : ContextWrapper(context) {

    companion object {
        const val DEFAULT_NOTIFY_CHANNEL_ID = "2"
    }

    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    fun createChannels() {
        notificationManager.deleteNotificationChannel("1")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_def_notify_name)
            val descriptionText = getString(R.string.channel_def_notify_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(DEFAULT_NOTIFY_CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableLights(true)
                setShowBadge(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                lightColor = Color.RED
                enableVibration(true)
            }

            notificationManager.createNotificationChannel(mChannel)
        }
    }

    fun sendShortNotify(
        id: Int,
        time: Long,
        textTitle: String,
        textContent: String,
        channelId: String = DEFAULT_NOTIFY_CHANNEL_ID
    ) {
        val intent = Intent(this, NotifyBroadcast::class.java).apply {

        }
        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, id, intent, FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_alarm_black_24dp)
            .setLargeIcon(getBitmap(context, R.drawable.ic_notifications_active_black_24dp))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setWhen(time)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        sendNotify(id, builder)
    }
//    fun getBigNotify(textTitle: String, textContent: String, bigText: String, channelId: String = DEFAULT_NOTIFY_CHANNEL_ID) : NotificationCompat.Builder{
//        return getShortNotify(textTitle, textContent, channelId)
//            .setStyle(NotificationCompat.BigTextStyle().bigText(bigText))
//    }

    private fun sendNotify(id: Int, builder: NotificationCompat.Builder) {
        notificationManager.notify(id, builder.build())
    }

    fun cancelNotify(id: Int) {
        notificationManager.cancel(id)
    }

    private fun getBitmap(context: Context, vectorDrawableId: Int): Bitmap {
        val bitmap: Bitmap?
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            val vectorDrawable: Drawable = context.getDrawable(vectorDrawableId)!!
            bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
            vectorDrawable.draw(canvas)
        } else {
            bitmap = BitmapFactory.decodeResource(context.resources, vectorDrawableId);
        }
        return bitmap;

    }
}
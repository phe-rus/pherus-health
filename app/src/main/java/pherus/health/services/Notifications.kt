package pherus.health.services

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.Person
import pherus.health.R
import kotlin.random.Random

class Notifications(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    fun basic(
        chnl: String,
        title: String,
        content: String,
        category: String
    ) {
        notificationManager.notify(
            Random.nextInt(),
            NotificationCompat
                .Builder(context, chnl)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.pherus)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setCategory(category)
                .setAutoCancel(true)
                .build()
        )
    }

    fun expandable(
        chnl: String,
        title: String,
        content: String,
        category: String,
        @DrawableRes resId: Int
    ) {
        notificationManager.notify(
            Random.nextInt(),
            NotificationCompat.Builder(context, chnl)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.pherus)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setCategory(category)
                .setAutoCancel(true)
                .setStyle(
                    NotificationCompat
                        .BigPictureStyle()
                        .bigPicture(
                            context.bitmapFromResource(resId)
                        )
                )
                .build()
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calls(
        onCall: Boolean,
        chnl: String,
        title: String,
        content: String,
        category: String
    ) {
        val incomingCaller = Person.Builder()
            .setName("Jane Doe")
            .setImportant(true)
            .build()

        when {
            onCall -> {

            }

            else -> {

            }
        }
    }

    private fun Context.bitmapFromResource(
        @DrawableRes resId: Int
    ) = BitmapFactory.decodeResource(
        resources,
        resId
    )
}
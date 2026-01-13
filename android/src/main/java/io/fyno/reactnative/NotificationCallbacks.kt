package io.fyno.reactnative

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.modules.core.DeviceEventManagerModule
import java.util.Objects

class NotificationCallbacks : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        handleIncomingCallback(context, intent)
    }

    private fun handleIncomingCallback(context: Context, intent: Intent) {
        val action = intent.getStringExtra("io.fyno.pushlibrary.notification.action")
        val payload = intent.getStringExtra("io.fyno.pushlibrary.notification.payload")

        val application = context.applicationContext as ReactApplication
        val reactHost = application.reactHost
        val reactContext = reactHost?.currentReactContext

        if (reactContext != null && action != null) {
            when (action) {
                "dismissed" -> {
                    reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                        .emit("onNotificationDismissed", payload)
                }

                "received" -> {
                    reactContext
                        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                        .emit("onNotificationReceived", payload)
                }
            }
        } else {
            Log.w("NotificationReceiver", "ReactContext is null, launching app")

            try {
                val launchIntent =
                    context.packageManager.getLaunchIntentForPackage(context.packageName)
                launchIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                payload?.let {
                    launchIntent?.putExtra(
                        "io.fyno.pushlibrary.notification.payload",
                        it
                    )
                }
                launchIntent?.let { context.startActivity(it) }
            } catch (ex: Exception) {
                Log.w(
                    "NotificationReceiver",
                    "Failed to launch app for notification payload",
                    ex
                )
            }
        }
    }
}
package io.fyno.reactnative

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.facebook.react.ReactApplication
import com.facebook.react.modules.core.DeviceEventManagerModule
import java.util.Objects

class NotificationCallbacks : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        handleIncomingCallback(context, intent)
    }

    private fun handleIncomingCallback(context: Context, intent: Intent) {
        val action = intent.getStringExtra("io.fyno.pushlibrary.notification.action")
        val payload = intent.getStringExtra("io.fyno.pushlibrary.notification.payload")
        val reactApplication = context.applicationContext as ReactApplication
        val reactContext = reactApplication.reactNativeHost.reactInstanceManager.currentReactContext
        if (reactContext != null) {
            when (Objects.requireNonNull(action)) {
                "dismissed" -> {
                    reactContext.getJSModule(
                        DeviceEventManagerModule.RCTDeviceEventEmitter::class.java
                    )
                        .emit("onNotificationDismissed", payload)
                }

                "received" -> {
                    reactContext.getJSModule(
                        DeviceEventManagerModule.RCTDeviceEventEmitter::class.java
                    )
                        .emit("onNotificationReceived", payload)
                }
            }
        } else {
            Log.e("NotificationReceiver", "ReactContext is null, app might not be initialized")
        }
    }
}
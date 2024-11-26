package io.fyno.reactnative

import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.facebook.react.bridge.LifecycleEventListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.modules.core.DeviceEventManagerModule
import io.fyno.callback.models.MessageStatus
import io.fyno.kotlin_sdk.FynoSdk
import io.fyno.pushlibrary.FynoCallbacks
import io.fyno.pushlibrary.FynoPush
import io.fyno.pushlibrary.helper.NotificationHelper.rawMessage
import io.fyno.pushlibrary.models.PushRegion
import org.json.JSONObject

class FynoSdkServiceManager(reactContext: ReactApplicationContext):ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String {
        return "FynoReactNative";
    }

    init {
        val filter = IntentFilter()
        filter.addAction("io.fyno.pushlibrary.NOTIFICATION_ACTION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            reactApplicationContext.registerReceiver(NotificationCallbacks(), filter,  RECEIVER_EXPORTED)
        }
    }

    override fun initialize() {
        super.initialize()
        handleInitialIntent()
    }

    private fun handleInitialIntent() {
        val activity = currentActivity ?: return
        val intent = activity.intent
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent?) {
        val notificationPayload = intent?.getStringExtra("io.fyno.pushlibrary.notification.payload")
        if (notificationPayload != null) {
            sendEvent("onNotificationClicked", notificationPayload)
        }
    }

    private fun sendEvent(eventName: String, params: String) {
        reactApplicationContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
            .emit(eventName, params)
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun initialise(
        workspaceId:String,
        integrationId:String,
        userId:String?,
        version:String,
    ) {
        try {
            FynoSdk.initialize(
                this.reactApplicationContext.applicationContext,
                workspaceId,
                integrationId,
                userId,
                version
            )
        } catch (e:Exception){
            println(e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun registerPush(
        xiaomiApplicationId:String,
        xiaomiApplicationKey:String,
        pushRegion:String,
        provider:String,
    ){
        try {
            FynoPush().showPermissionDialog()
            FynoSdk.registerPush(
                xiaomiApplicationId,
                xiaomiApplicationKey,
                PushRegion.values().find { it.name == pushRegion },
           )
        } catch(e:Exception){
            println(e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun identify(
        uniqueId:String,
        userName:String,
    ){
        try {
            FynoSdk.identify(
                uniqueId,
                userName
            )
        } catch (e:Exception){
            println(e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun registerInapp(
        inappIntegrationId:String,
    ){
        try {
            FynoSdk.registerInapp(
                inappIntegrationId
            )
        } catch (e:Exception){
            println(e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun mergeProfile(
        oldDistinctId:String,
        newDistinctId:String,
    ){
        try {
            FynoSdk.mergeProfile(
                oldDistinctId,
                newDistinctId
            )
        } catch (e:Exception){
            println(e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun updateStatus(
        callbackURL:String,
        status:String,
    ){
        try {
            MessageStatus.values().find { it.name == status }?.let {
                FynoSdk.updateStatus(
                    this.reactApplicationContext.applicationContext,
                    callbackURL,
                    it
                )
            }
        } catch (e:Exception){
            println(e.toString())
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun resetUser(){
        try{
            FynoSdk.resetUser()
        } catch (e:Exception){
            println(e.toString())
        }
    }
}
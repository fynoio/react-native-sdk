package io.fyno.reactnative

import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import io.fyno.callback.models.MessageStatus
import io.fyno.kotlin_sdk.FynoSdk
import io.fyno.pushlibrary.FynoPush
import io.fyno.pushlibrary.helper.NotificationHelper.renderFCMMessage
import io.fyno.pushlibrary.models.PushRegion
import io.fyno.pushlibrary.notification.Actions
import io.fyno.pushlibrary.notification.NotificationActionType
import io.fyno.pushlibrary.notification.RawMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
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
            FynoPush().showPermissionDialog(this.reactApplicationContext.applicationContext)
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
    fun updateName(
        userName:String,
    ){
        try {
            FynoSdk.updateName(
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

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun isFynoNotification(messageData: ReadableMap): Boolean {
        return try {
            val message = messageData.getMap("data")
            message?.hasKey("fyno_push") == true
        } catch (e: Exception) {
            false
        }
    }

    private fun String?.toNotificationObject(): JSONObject {
        return try {
            if (isNullOrBlank()) {
                Log.w("NotificationHelper", "toNotificationObject: Input string is null or blank", )
                return JSONObject()
            }

            // Clean up unnecessary escape characters
            val cleanedString = this.replace("\\n", "")

            // Convert to JSONObject
            val jsonObject = JSONObject(cleanedString)

            // Handle nested `additional_data` field if present
            if (jsonObject.has("additional_data")) {
                val additionalDataString = jsonObject.getString("additional_data")
                try {
                    val additionalDataJson = JSONObject(additionalDataString)
                    jsonObject.put("additional_data", additionalDataJson) // Replace string with JSON object
                } catch (e: Exception) {
                    Log.w("NotificationHelper", "toNotificationObject: Failed to parse nested additional_data JSON", e)
                }
            }

            jsonObject
        } catch (e: Exception) {
            Log.w("NotificationHelper", "toNotificationObject: Error while converting notification string to JSON object", e)
            JSONObject()
        }
    }

    private fun JSONObject.safeString(key: String): String? {
        return if (!isNull(key))
            getString(key)
        else null
    }

    private fun JSONObject.safeBoolean(key: String): Boolean? {
        return if (!isNull(key))
            getBoolean(key)
        else null
    }

    private fun JSONObject.safeLong(key: String): Long? {
        return if (!isNull(key))
            getLong(key)
        else null
    }

    private fun JSONObject.safeJsonArray(key: String): JSONArray? {
        return if (!isNull(key))
            getJSONArray(key)
        else null
    }

    private fun getActions(notificationPayloadJO: JSONObject): List<Actions>? {
        val safeActions = notificationPayloadJO.safeJsonArray("actions")
        safeActions ?: return null
        val actionsList = arrayListOf<Actions>()
        for (i in 0 until safeActions.length()) {
            val actionObj = safeActions.getJSONObject(i)
            actionsList.add(
                Actions(
                    id = actionObj.safeString("id"),
                    title = actionObj.safeString("title"),
                    link = actionObj.safeString("link"),
                    iconDrawableName = actionObj.safeString("iconIdentifierName"),
                    notificationId = actionObj.safeString("notificationId"),
                    notificationActionType = when(actionObj.safeString("notificationActionType")){
                        "button" -> NotificationActionType.BUTTON
                        "body" -> NotificationActionType.BODY
                        else -> NotificationActionType.BODY
                    }
                )
            )
        }
        return actionsList
    }

    private fun String?.createNotification(): RawMessage {
        this ?: return RawMessage("1", "1")

        val notificationPayloadJO = toNotificationObject()

        val id = notificationPayloadJO.safeString("id") ?: ""
        return RawMessage(
            id = id,
            channelId = notificationPayloadJO.safeString("channel"),
            channelName = notificationPayloadJO.safeString("channelName"),
            channelDescription = notificationPayloadJO.safeString("channelDescription"),
            showBadge = notificationPayloadJO.safeBoolean("badge"),
            cSound = notificationPayloadJO.safeString("cSound"),
            smallIconDrawable = notificationPayloadJO.safeString("icon"),
            color = notificationPayloadJO.safeString("color"),
            notificationTitle = notificationPayloadJO.safeString("title"),
            subTitle = notificationPayloadJO.safeString("subTitle"),
            shortDescription = notificationPayloadJO.safeString("content"),
            longDescription = notificationPayloadJO.safeString("longDescription"),
            iconUrl = notificationPayloadJO.safeString("bigIcon"),
            imageUrl = notificationPayloadJO.safeString("bigPicture"),
            action = notificationPayloadJO.safeString("action"),
            sound = notificationPayloadJO.safeString("sound"),
            callback = notificationPayloadJO.safeString("callback"),
            category = notificationPayloadJO.safeString("category"),
            group = notificationPayloadJO.safeString("group"),
            groupSubText = notificationPayloadJO.safeString("groupSubText"),
            groupShowWhenTimeStamp = notificationPayloadJO.safeBoolean("groupShowWhenTimeStamp"),
            groupWhenTimeStamp = notificationPayloadJO.safeLong("groupWhenTimeStamp"),
            sortKey = notificationPayloadJO.safeString("sortKey"),
            onGoing = notificationPayloadJO.safeBoolean("sticky"),
            autoCancel = notificationPayloadJO.safeBoolean("autoCancel"),
            timeoutAfter = notificationPayloadJO.safeLong("timeoutAfter"),
            showWhenTimeStamp = notificationPayloadJO.safeBoolean("showWhenTimeStamp"),
            whenTimeStamp = notificationPayloadJO.safeLong("whenTimeStamp"),
            actions = getActions(notificationPayloadJO),
            template = notificationPayloadJO.safeString("template"),
            additional_data = notificationPayloadJO.safeString("additional_data")
        )
    }

    @ReactMethod
    fun handleFynoNotification(messageData: ReadableMap, promise: Promise) {
        try {
            val message = messageData.getMap("data")
            val fynoMessage = message?.getString("fyno_push") ?: ""

            CoroutineScope(Dispatchers.IO).launch {
                renderFCMMessage(reactApplicationContext, fynoMessage.createNotification())
            }

            promise.resolve(null)
        } catch (e: Exception) {
            promise.reject("HANDLE_NOTIFICATION_ERROR", e)
        }
    }
}
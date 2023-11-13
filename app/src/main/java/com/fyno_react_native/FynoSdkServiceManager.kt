package com.fyno_react_native

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod
import io.fyno.callback.models.MessageStatus
import io.fyno.kotlin_sdk.FynoSdk
import io.fyno.pushlibrary.FynoPush
import io.fyno.pushlibrary.models.PushRegion
import org.json.JSONObject

class FynoSdkServiceManager(reactContext: ReactApplicationContext):ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String {
        return "FynoSdkServiceManager";
    }

    @ReactMethod
    fun initialise(
        workspaceId:String,
        token:String,
        userId:String?,
        version:String,
        errorCallback: Callback,
    ) {
        try {
            FynoSdk.initialize(
                this.reactApplicationContext.applicationContext,
                workspaceId,
                token,
                userId,
                version
            )
        } catch (e:Exception){
            errorCallback(e.toString())
        }
    }

    @ReactMethod
    fun registerPush(
        xiaomiApplicationId:String,
        xiaomiApplicationKey:String,
        pushRegion:String,
        integrationId:String,
        errorCallback: Callback,
    ){
        try {
            FynoPush().showPermissionDialog()
            FynoSdk.registerPush(
                xiaomiApplicationId,
                xiaomiApplicationKey,
                PushRegion.values().find { it.name == pushRegion },
                integrationId
           )
        } catch(e:Exception){
            errorCallback(e.toString())
        }
    }

    @ReactMethod
    fun registerFCMPush(
        integrationId: String,
        errorCallback: Callback,
    ){
        try{
            FynoPush().showPermissionDialog()
            FynoSdk.registerPush(
                null,
                null,
                null,
                integrationId
            )
        }  catch (e:Exception){
            errorCallback(e.toString())
        }
    }

    @ReactMethod
    fun identify(
        uniqueId:String,
        userName:String,
        errorCallback: Callback,
    ){
        try {
            FynoSdk.identify(
                uniqueId,
                userName
            )
        } catch (e:Exception){
            errorCallback(e.toString())
        }
    }

    @ReactMethod
    fun mergeProfile(
        oldDistinctId:String,
        newDistinctId:String,
        errorCallback: Callback,
    ){
        try {
            FynoSdk.mergeProfile(
                oldDistinctId,
                newDistinctId
            )
        } catch (e:Exception){
            errorCallback(e.toString())
        }
    }

    @ReactMethod
    fun updateStatus(
        callbackURL:String,
        status:String,
        action:String,
        errorCallback: Callback,
    ){
        try {
            MessageStatus.values().find { it.name == status }?.let {
                FynoSdk.updateStatus(
                    callbackURL,
                    it,
                    JSONObject(action)
                )
            }
        } catch (e:Exception){
            errorCallback(e.toString())
        }
    }

    @ReactMethod
    fun resetUser(errorCallback: Callback){
        try{
            FynoSdk.resetUser()
        } catch (e:Exception){
            errorCallback(e.toString())
        }
    }
}
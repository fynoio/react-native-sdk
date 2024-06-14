package io.fyno.reactnative

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod
import io.fyno.callback.models.MessageStatus
import io.fyno.kotlin_sdk.FynoSdk
import io.fyno.pushlibrary.FynoPush
import io.fyno.core.FynoUser
import io.fyno.pushlibrary.models.PushRegion
import org.json.JSONObject

class FynoSdkServiceManager(reactContext: ReactApplicationContext):ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String {
        return "FynoReactNative";
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
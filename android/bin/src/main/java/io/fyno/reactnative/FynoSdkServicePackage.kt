package io.fyno.reactnative

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import java.util.Collections

class FynoSdkServicePackage :ReactPackage{
    override fun createNativeModules(reactContext: ReactApplicationContext): MutableList<NativeModule> =
        listOf(FynoSdkServiceManager(reactContext)).toMutableList()

    override fun createViewManagers(p0: ReactApplicationContext): List<ViewManager<*,*>> {
        return Collections.emptyList()
    }
}
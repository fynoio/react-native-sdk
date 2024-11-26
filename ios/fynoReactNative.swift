import Foundation
import React
import fyno_push_ios
 
@objc(FynoReactNative)
class FynoReactNative: RCTEventEmitter {
    private var listenerCounts: [String: Int] = [:]
    
    override init() {
        super.init()

        let notificationNames: [NSNotification.Name] = [
            .init("onNotificationReceived"),
            .init("onNotificationClicked"),
            .init("onNotificationDismissed"),
        ]

        for name in notificationNames {
            NotificationCenter.default.addObserver(
                self,
                selector: #selector(handleNotificationEvent),
                name: name,
                object: nil
            )
        }
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }
    
    let fynosdk = fyno.app
    
    private func emitEventWithRetry(notification: Notification, attempt: Int = 1, maxAttempts: Int = 3) {
        DispatchQueue.main.async {
            if self.listenerCounts[notification.name.rawValue, default: 0] > 0 {
                // If listeners exist, send the event
                self.sendEvent(withName: notification.name.rawValue, body: notification.object)
            } else if attempt < maxAttempts {
                // Retry with exponential backoff
                let delay = pow(1.0, Double(attempt)) // Exponential backoff: 1^attempt seconds
                print("No listeners for \(notification.name.rawValue). Retrying in \(delay) seconds (Attempt \(attempt))...")
                DispatchQueue.main.asyncAfter(deadline: .now() + delay) {
                    self.emitEventWithRetry(notification: notification, attempt: attempt + 1, maxAttempts: maxAttempts)
                }
            } else {
                // Max attempts reached, log a failure
                print("Failed to emit \(notification.name.rawValue) after \(maxAttempts) attempts. No listeners registered.")
            }
        }
    }

    @objc func handleNotificationEvent(notification: Notification) {
        emitEventWithRetry(notification: notification)
    }

    open override func supportedEvents() -> [String] {
        return [
            "onNotificationReceived",
            "onNotificationClicked",
            "onNotificationDismissed",
        ]
    }

    override func addListener(_ eventName: String) {
        super.addListener(eventName)
        listenerCounts[eventName, default: 0] += 1
        print("Added listener for \(eventName). Count: \(listenerCounts[eventName]!)")
    }
    
    override func removeListeners(_ count: Double) {
        super.removeListeners(count)
        for eventName in supportedEvents() {
            listenerCounts[eventName] = max((listenerCounts[eventName] ?? 0) - Int(count), 0)
            print("Removed listener(s) from \(eventName). Count: \(listenerCounts[eventName]!)")
        }
    }
    
    @objc
    func initialise(
        _ workspaceId: String,
        integrationID: String,
        distinctID: String,
        version: String
    ) {
        fynosdk.initializeApp(workspaceID: workspaceId, integrationID: integrationID, distinctId: distinctID, version: version){
            initResult in
            switch initResult {
            case .success(_):
                print("Initialization successful")
                return
            case .failure(let error):
                print(error)
                return
            }
        }
    }
    
    @objc
    func registerPush(
        _ xiaomiApplicationId:String,
        xiaomiApplicationKey:String,
        pushRegion:String,
        provider: String
    ) {
        let isAPNs = String.lowercased(provider)() == "apns" ? true : false
        fynosdk.registerPush(isAPNs: isAPNs) {
            registerPushResult in
            switch registerPushResult {
            case .success(_):
                print("Register Push successful")
                return
            case .failure(let error):
                print(error)
                return
            }
        }
    }
    
    @objc
    func registerInapp(
        _ integrationID: String
        ) {
        fynosdk.registerInapp(integrationID: integrationID){
            registerInappResult in
            switch registerInappResult {
            case .success(_):
                print("registerInapp successful")
                return
            case .failure(let error):
                print(error)
                return
            }
        }
    }
    
    @objc
    func mergeProfile(
        _ oldDistinctID: String,
        newDistinctID: String
    ){
        fynosdk.mergeProfile(newDistinctId: newDistinctID){
            mergeProfileResult in
            switch mergeProfileResult {
            case .success(_):
                print("Merge profile successful")
                return
            case .failure(let error):
                print(error)
                return
            }
        }
    }
    
    @objc
    func identify(
        _ distinctID: String,
        userName: String
    ){
        fynosdk.identify(newDistinctId: distinctID, userName: userName){
            identifyResult in
            switch identifyResult {
            case .success(_):
                print("Identify User successful")
                return
            case .failure(let error):
                print(error)
                return
            }
        }
    }
    
    @objc
    func updateStatus(
        _ callBackUrl:String,
        status: String
    ){
        fynosdk.updateStatus(callbackUrl: callBackUrl, status: status){
            updateStatusResult in
            switch updateStatusResult {
            case .success(_):
                print("Update status successful")
                return
            case .failure(let error):
                print(error)
                return
            }
        }
    }
    
    @objc
    func resetUser(){
        fynosdk.resetUser() {
            resetUserResult in
            switch resetUserResult {
            case .success(_):
                print("Reset user successful")
                return
            case .failure(let error):
                print(error)
                return
            }
        }
    }
    
    @objc
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
}

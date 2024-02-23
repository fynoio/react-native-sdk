import Foundation
import fyno_push_ios
 
@objc(FynoReactNative)
class FynoReactNative: NSObject {
    
    let fynosdk = fyno.app
    
    @objc
    func initialise(
        _ workspaceId: String,
        apiKey: String,
        distinctID: String,
        version: String
    ) {
        fynosdk.initializeApp(workspaceID: workspaceId, apiKey: apiKey, distinctId: distinctID, version: version){
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
        integrationID: String,
        provider: String
    ) {
        let isAPNs = String.lowercased(provider)() == "apns" ? true : false
        fynosdk.registerPush(integrationID: integrationID, isAPNs: isAPNs){
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
    func identifyUser(
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
    static func requiresMainQueueSetup() -> Bool {
        return true
    }
}

import Foundation
import fyno_push_ios

@objc(FynoSdkServiceManager)
class FynoSdkServiceManager: NSObject {
  let fynosdk = fyno.app
  
  @objc
  func initialise(
    workspaceId: String,
    apiKey: String,
    distinctID: String,
    version: String,
    errorCallback: @escaping RCTResponseSenderBlock
  ) {
    fynosdk.initializeApp(workspaceID: workspaceId, apiKey: apiKey, distinctId: distinctID){
      initResult in
      switch initResult {
      case .success(_):
        print("Initialization successful")
        errorCallback([])
        return
      case .failure(let error):
        print(error)
        errorCallback([error.localizedDescription])
        return
      }
    }
  }
  
  @objc
  func registerPush(
    integrationID: String,
    isApns: Bool,
    errorCallback: @escaping RCTResponseSenderBlock
  ) {
    fynosdk.registerPush(integrationID: integrationID, isAPNs: isApns){
      registerPushResult in
      switch registerPushResult {
      case .success(_):
        print("Register Push successful")
        errorCallback([])
        return
      case .failure(let error):
        print(error)
        errorCallback([error.localizedDescription])
        return
      }
    }
  }
  
  @objc
  func mergeProfile(
    oldDistinctID: String,
    newDistinctID: String,
    errorCallback: @escaping RCTResponseSenderBlock
  ){
    fynosdk.mergeProfile(newDistinctId: newDistinctID){
      mergeProfileResult in
      switch mergeProfileResult {
      case .success(_):
        print("Merge profile successful")
        errorCallback([])
        return
      case .failure(let error):
        print(error)
        errorCallback([error.localizedDescription])
        return
      }
    }
  }
  
  @objc
  func identifyUser(
    distinctID: String,
    userName: String,
    errorCallback: @escaping RCTResponseSenderBlock
  ){
    fynosdk.identify(newDistinctId: distinctID, userName: userName){
      identifyResult in
      switch identifyResult {
      case .success(_):
        print("Identify User successful")
        errorCallback([])
        return
      case .failure(let error):
        print(error)
        errorCallback([error.localizedDescription])
        return
      }
    }
  }
  
  @objc
  func updateStatus(
    callBackUrl:String,
    status: String,
    errorCallback: @escaping RCTResponseSenderBlock
  ){
    fynosdk.updateStatus(callbackUrl: callBackUrl, status: status){
      updateStatusResult in
      switch updateStatusResult {
      case .success(_):
        print("Update status successful")
        errorCallback([])
        return
      case .failure(let error):
        print(error)
        errorCallback([error.localizedDescription])
        return
      }
    }
  }
  
  @objc
  func resetUser(errorCallback: @escaping RCTResponseSenderBlock){
    fynosdk.resetUser() {
      resetUserResult in
      switch resetUserResult {
      case .success(_):
        print("Reset user successful")
        errorCallback([])
        return
      case .failure(let error):
        print(error)
        errorCallback([error.localizedDescription])
        return
      }
    }
  }
  
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }
}

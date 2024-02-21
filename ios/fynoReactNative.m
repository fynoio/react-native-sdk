#import "React/RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(FynoSdkServiceManager, NSObject)

RCT_EXTERN_METHOD(initialise:(NSString *)workspaceId
                  apiKey:(NSString *)apiKey
                  distinctID:(NSString *)distinctID
                  version:(NSString *)version
                  errorCallback:(RCTResponseSenderBlock)callback)

RCT_EXTERN_METHOD(registerPush:(NSString *)xiaomiApplicationId
                  xiaomiApplicationKey:(NSString *)xiaomiApplicationKey
                  pushRegion:(NSString *)pushRegion
                  integrationID:(NSString *)integrationID
                  errorCallback:(RCTResponseSenderBlock)callback
                  isApns:(BOOL)isApns)

RCT_EXTERN_METHOD(mergeProfile:(NSString *)oldDistinctID
                  newDistinctID:(NSString *)newDistinctID
                  errorCallback:(RCTResponseSenderBlock)callback)

RCT_EXTERN_METHOD(identifyUser:(NSString *)distinctID
                  userName:(NSString *)userName
                  errorCallback:(RCTResponseSenderBlock)callback)

RCT_EXTERN_METHOD(updateStatus:(NSString *)callBackUrl
                  status:(NSString *)status
                  errorCallback:(RCTResponseSenderBlock)callback)

RCT_EXTERN_METHOD(resetUser:(RCTResponseSenderBlock)callback)

@end

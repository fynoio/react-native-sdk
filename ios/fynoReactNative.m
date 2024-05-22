#import "React/RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(FynoReactNative, NSObject)

RCT_EXTERN_METHOD(initialise:(NSString *)workspaceId
                  integrationID:(NSString *)integrationID
                  distinctID:(NSString *)distinctID
                  version:(NSString *)version)

RCT_EXTERN_METHOD(registerPush:(NSString *)xiaomiApplicationId
                  xiaomiApplicationKey:(NSString *)xiaomiApplicationKey
                  pushRegion:(NSString *)pushRegion
                  provider:(NSString *)provider)

RCT_EXTERN_METHOD(registerInapp:(NSString *)integrationID)

RCT_EXTERN_METHOD(mergeProfile:(NSString *)oldDistinctID
                  newDistinctID:(NSString *)newDistinctID)

RCT_EXTERN_METHOD(identify:(NSString *)distinctID
                  userName:(NSString *)userName)

RCT_EXTERN_METHOD(updateStatus:(NSString *)callBackUrl
                  status:(NSString *)status)

RCT_EXTERN_METHOD(resetUser)

@end

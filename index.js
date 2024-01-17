import { NativeModules } from "react-native";

const FynoSdkServiceManager = NativeModules.FynoSdkServiceManager;

function initialise(workspaceId, token, userId, version) {
  FynoSdkServiceManager.initialise(
    workspaceId,
    token,
    userId,
    version || "live",
    (err) => {
      console.log(err);
    }
  );
}

function registerPush(
  xiaomiApplicationId,
  xiaomiApplicationKey,
  pushRegion,
  integrationId
) {
  FynoSdkServiceManager.registerPush(
    xiaomiApplicationId,
    xiaomiApplicationKey,
    pushRegion || "INDIA",
    integrationId,
    (err) => {
      console.log(err);
    }
  );
}

function registerFCM(integrationId) {
  FynoSdkServiceManager.registerFCMPush(integrationId, (err) => {
    console.log(err);
  });
}

function identifyUser(uniqueID, userName) {
  FynoSdkServiceManager.identify(uniqueID, userName, (err) => {
    console.log(err);
  });
}

function updateStatus(callBackUrl, status) {
  FynoSdkServiceManager.updateStatus(callBackUrl, status, (err) => {
    console.log(err);
  });
}

function resetUser() {
  FynoSdkServiceManager.resetUser((err) => {
    console.log(err);
  });
}

export default FynoReactNative = {
  initialise,
  registerPush,
  registerFCM,
  identifyUser,
  updateStatus,
  resetUser,
};

module.exports = {
  initialise,
  registerPush,
  registerFCM,
  identifyUser,
  updateStatus,
  resetUser,
};

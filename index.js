import { NativeModules } from "react-native";

const { FynoSdkServiceManager } = NativeModules;

console.log("FynoSdkServiceManager in index.js =>", FynoSdkServiceManager);

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
  integrationId,
  isApns = false
) {
  FynoSdkServiceManager.registerPush(
    xiaomiApplicationId,
    xiaomiApplicationKey,
    pushRegion || "INDIA",
    integrationId,
    isApns,
    (err) => {
      console.log(err);
    }
  );
}

function identifyUser(uniqueID, userName) {
  FynoSdkServiceManager.identify(uniqueID, userName, (err) => {
    console.log(err);
  });
}

function mergeProfile(oldDistinctId, newDistinctId) {
  FynoSdkServiceManager.mergeProfile(oldDistinctId, newDistinctId, (err) => {
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
  identifyUser,
  mergeProfile,
  updateStatus,
  resetUser,
};

module.exports = {
  initialise,
  registerPush,
  identifyUser,
  mergeProfile,
  updateStatus,
  resetUser,
};

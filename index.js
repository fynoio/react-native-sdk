import { NativeModules } from "react-native";

const { FynoReactNative } = NativeModules;

console.log("FynoReactNative in index.js =>", FynoReactNative);

function initialise(workspaceId, integrationID, userId, version) {
  return new Promise((resolve, reject) => {
    try {
      FynoReactNative.initialise(
        workspaceId,
        integrationID,
        userId,
        version || "live"
      );
      resolve();
    } catch (err) {
      reject(err);
    }
  });
}

function registerPush(
  xiaomiApplicationId,
  xiaomiApplicationKey,
  pushRegion,
  provider
) {
  return new Promise((resolve, reject) => {
    try {
      FynoReactNative.registerPush(
        xiaomiApplicationId,
        xiaomiApplicationKey,
        pushRegion || "INDIA",
        provider
      );
      resolve();
    } catch (err) {
      reject(err);
    }
  });
}

function identifyUser(uniqueID, userName) {
  return new Promise((resolve, reject) => {
    try {
      FynoReactNative.identify(uniqueID, userName);
      resolve();
    } catch (err) {
      reject(err);
    }
  });
}

function registerInapp(uniqueID, userName) {
  return new Promise((resolve, reject) => {
    try {
      FynoReactNative.registerInapp(uniqueID, userName);
      resolve();
    } catch (err) {
      reject(err);
    }
  });
}

function mergeProfile(oldDistinctId, newDistinctId) {
  return new Promise((resolve, reject) => {
    try {
      FynoReactNative.mergeProfile(oldDistinctId, newDistinctId);
      resolve();
    } catch (err) {
      reject(err);
    }
  });
}

function updateStatus(callBackUrl, status) {
  return new Promise((resolve, reject) => {
    try {
      FynoReactNative.updateStatus(callBackUrl, status);
      resolve();
    } catch (err) {
      reject(err);
    }
  });
}

function resetUser() {
  return new Promise((resolve, reject) => {
    try {
      FynoReactNative.resetUser();
      resolve();
    } catch (err) {
      reject(err);
    }
  });
}

export default FynoRN = {
  initialise,
  registerPush,
  identifyUser,
  mergeProfile,
  updateStatus,
  resetUser,
  registerInapp
};

module.exports = {
  initialise,
  registerPush,
  identifyUser,
  mergeProfile,
  updateStatus,
  resetUser,
  registerInapp
};

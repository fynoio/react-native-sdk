import { NativeModules, Platform } from "react-native";

const { FynoReactNative } = NativeModules;

console.log("FynoReactNative in index.js =>", FynoReactNative);

function initialise(workspaceId, integrationID, userId, version) {
  return new Promise((resolve, reject) => {
    try {
      const user = Platform.OS === "android" && userId === "" ? null : userId;
      FynoReactNative.initialise(
        workspaceId,
        integrationID,
        user,
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

function registerInapp(integrationID) {
  return new Promise((resolve, reject) => {
    try {
      FynoReactNative.registerInapp(integrationID);
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

function updateName(userName) {
  return new Promise((resolve, reject) => {
    try {
      FynoReactNative.updateName(userName);
      resolve();
    } catch (err) {
      reject(err);
    }
  });
}

function isFynoNotification(remoteMessage) {
  return new Promise((resolve, reject) => {
    try {
      if (Platform.OS !== "android") {
        throw new Error("isFynoNotification is only supported on Android");
      }

      resolve(FynoReactNative.isFynoNotification(remoteMessage));
    } catch (err) {
      reject(err);
    }
  });
}

function handleFynoNotification(remoteMessage) {
  return new Promise((resolve, reject) => {
    try {
      if (Platform.OS !== "android") {
        throw new Error("handleFynoNotification is only supported on Android");
      }

      FynoReactNative.handleFynoNotification(remoteMessage);
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
  registerInapp,
  updateName,
  isFynoNotification,
  handleFynoNotification,
};

module.exports = {
  initialise,
  registerPush,
  identifyUser,
  mergeProfile,
  updateStatus,
  resetUser,
  registerInapp,
  updateName,
  isFynoNotification,
  handleFynoNotification,
};

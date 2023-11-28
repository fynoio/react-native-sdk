# Fyno React Native Android Push SDK

Fyno's React Native Push Notification SDK comes with the ability to provide its users with a multitude of notification features, all bundled into one, allowing it to smartly provide the best and optimised message delivery service from within your application itself.

## Prerequisites

In order to get started, there are a few prerequisites that needs to be in place:

1. **Firebase Setup:** Setup Firebase and create application in Firebase Console. You can refer to the [FCM Documentation](https://docs.fyno.io/docs/push-fcm) for more details.
2. **Xiaomi Setup:** Setup _Xiaomi Developer Account_ and create application in Xiaomi push console. You may refer to the [Mi Push Documentation](https://docs.fyno.io/docs/push-mi-push) for more details.
3. **Configuration:** Configure your Fyno Push provider in [Fyno App](https://app.fyno.io/integrations)
4. Download the google-services.json from Firebase console and place it in the root folder as per [FCM Documentations](https://firebase.google.com/docs/android/setup).

## Installation

This step essentially explains how and where to place the Fyno SDK in your repository in order to have the SDK correctly initialised.

## Initializing the Platform

Follow the steps below to integrate Fyno React Native Push SDK in your application.

1. Install the library by running the following command

```bash
npm install @fyno/react-native
```

2. Import the SDK

```javascript
import FynoReactNative from "@fyno/react-native";
```

3. Initiase the SDK

```javascript
FynoReactNative.initialise("workspaceID", "apiKey", "userID", "version");
```

## Identifying the User

```javascript
FynoReactNative.identifyUser("uniqueID", "userName");
```

## Registering Push Notifications with Xiaomi services

```javascript
FynoReactNative.registerPush(
  "xiaomiApplicationId",
  "xiaomiApplicationKey",
  "pushRegion",
  "integrationId"
);
```

## Registering Push Notifications with Firebase Cloud Messaging (FCM)

```javascript
FynoReactNative.registerFCMPush("integrationId");
```

## Merge Profile

```javascript
FynoReactNative.mergeProfile("oldDistinctId", "newDistinctId");
```

## Update Message Status

```javascript
FynoReactNative.updateStatus("callbackURL", "status");
```

## Resetting User Information

```javascript
FynoReactNative.resetUser();
```

## Sample Initialisation of Xiaomi Push

```javascript
// Initialize Fyno SDK
FynoReactNative.initialise("workspace123", "token123", "user123", "test");

// Identify user
FynoReactNative.identify("unique123", "John Doe");

// Register push notification
FynoReactNative.registerPush(
  "xiaomiId",
  "xiaomiKey",
  "region",
  "integrationId"
);
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

# Fyno React Native Android Push SDK

Fyno's React Native Push Notification SDK provides a powerful solution for managing push notifications in your Android React Native applications. This SDK offers various features for optimizing message delivery and user identification within your application.

## Prerequisites

Before you start using the Fyno React Native Push SDK, make sure you have the following prerequisites in place:

1. **Firebase Setup:** Set up Firebase and create an application in the Firebase Console. Refer to the [FCM Documentation](https://docs.fyno.io/docs/push-fcm) for detailed instructions.

2. **Xiaomi Setup:** Create a Xiaomi Developer Account and set up an application in the Xiaomi push console. Refer to the [Mi Push Documentation](https://docs.fyno.io/docs/push-mi-push) for more details.

3. **Configuration:** Configure your Fyno Push provider in the [Fyno App](https://app.fyno.io/integrations).

4. Download the `google-services.json` file from the Firebase console and place it in the root folder of your project as described in the [FCM Documentation](https://firebase.google.com/docs/android/setup).

## Installation

To integrate the Fyno React Native Push SDK into your application, follow these steps:

1. Install the library using npm:

   ```bash
   npm install @fyno/react-native
   ```

2. Import the SDK in your JavaScript file:

   ```javascript
   import FynoReactNative from "@fyno/react-native";
   ```

3. Initialize the SDK with your credentials:

   ```javascript
   FynoReactNative.initialise(workspaceId, apiKey, userId, version);
   ```

   - `workspaceId`: Your Fyno workspace ID.
   - `apiKey`: Your Fyno API key.
   - `userId` (optional): Unique user ID (can be `null` if not available).
   - `version`: SDK version.

## User Identification

You can identify a user by calling:

```javascript
FynoReactNative.identifyUser(distinctId, userName);
```

- `distinctId`: A unique identifier associated with an individual user or entity within your system or application.
- `userName` (optional): The user's name.

## Registering Push Notifications with Xiaomi Services

To register push notifications with Xiaomi services, use the following method:

```javascript
FynoReactNative.registerPush(
  xiaomiApplicationId,
  xiaomiApplicationKey,
  pushRegion,
  integrationId
);
```

- `xiaomiApplicationId` (only if using mipush, can be `null`): Xiaomi Application ID.
- `xiaomiApplicationKey` (only if using mipush, can be `null`): Xiaomi Application Key.
- `pushRegion` (only if using mipush, can be `null`): Region for push notifications.
- `integrationId`: Integration ID.

## Registering Push Notifications with Firebase Cloud Messaging (FCM)

To register push notifications with Firebase Cloud Messaging (FCM), use:

```javascript
FynoReactNative.registerFCMPush(integrationId);
```

- `integrationId`: Integration ID.

## Merge Profile

In case of change in the distinct id, You can merge user profiles using the `mergeProfile` method. We recomend not to use this instead use your customers internal unique identifier which will not change once created as a distinct id:

```javascript
FynoReactNative.mergeProfile(oldDistinctId, newDistinctId);
```

- `oldDistinctId`: The old distinct ID to be merged.
- `newDistinctId`: The new distinct ID to merge into.

## Update Message Status

If you are using your own Message handlers then to update the message status, use:

```javascript
FynoReactNative.updateStatus(callbackURL, status);
```

- `callbackURL`: The callback URL for status updates, this can be captured .
- `status`: The message status.

## Resetting User Information

When user logs out, You can reset user information by calling `resetUser` method. This will remove the channel data against the identified user and creates a unique fyno profile with the channel data, by doing this if you want to send communication to logged out users you can use the temp profile to target to those devices

```javascript
FynoReactNative.resetUser();
```

## Sample Initialization for Xiaomi Push

Here's a sample initialization for Xiaomi Push:

```javascript
// Initialize Fyno SDK
FynoReactNative.initialise("workspace123", "token123", "user123", "test");

// Identify user
FynoReactNative.identifyUser("unique123", "John Doe");

// Register push notification - Both Xiaomi and FCM Push
FynoReactNative.registerPush("xiaomiId", "xiaomiKey", "region", "integrationId");

// Register push notification - only FCM Push
FynoReactNative.registerPush(null, null, null, "integrationId");
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

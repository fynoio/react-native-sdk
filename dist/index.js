function ___$insertStyle(e){var t;if(e&&"undefined"!=typeof window)return(t=document.createElement("style")).setAttribute("type","text/css"),t.innerHTML=e,document.head.appendChild(t),e}Object.defineProperty(exports,"__esModule",{value:!0});var reactNative=require("react-native");const FynoReactNative=reactNative.NativeModules["FynoReactNative"];function initialise(r,i,n,a){return new Promise((e,t)=>{try{FynoReactNative.initialise(r,i,n,a||"live"),e()}catch(e){t(e)}})}function registerPush(r,i,n,a){return new Promise((e,t)=>{try{FynoReactNative.registerPush(r,i,n||"INDIA",a),e()}catch(e){t(e)}})}function identifyUser(r,i){return new Promise((e,t)=>{try{FynoReactNative.identify(r,i),e()}catch(e){t(e)}})}function registerInapp(r){return new Promise((e,t)=>{try{FynoReactNative.registerInapp(r),e()}catch(e){t(e)}})}function mergeProfile(r,i){return new Promise((e,t)=>{try{FynoReactNative.mergeProfile(r,i),e()}catch(e){t(e)}})}function updateStatus(r,i){return new Promise((e,t)=>{try{FynoReactNative.updateStatus(r,i),e()}catch(e){t(e)}})}function resetUser(){return new Promise((e,t)=>{try{FynoReactNative.resetUser(),e()}catch(e){t(e)}})}console.log("FynoReactNative in index.js =>",FynoReactNative);var index=FynoRN={initialise:initialise,registerPush:registerPush,identifyUser:identifyUser,mergeProfile:mergeProfile,updateStatus:updateStatus,resetUser:resetUser,registerInapp:registerInapp};module.exports={initialise:initialise,registerPush:registerPush,identifyUser:identifyUser,mergeProfile:mergeProfile,updateStatus:updateStatus,resetUser:resetUser,registerInapp:registerInapp},exports.default=index;
//# sourceMappingURL=index.js.map

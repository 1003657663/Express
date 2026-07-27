# Express

An Android application designed for managing and tracking express delivery services. The app allows users to create new shipment requests, search for existing packages, and track the real-time logistics path of their deliveries using map integration.

## Features

- **Shipment Creation**: Allows users to select sender and receiver addresses and submit new express delivery requests.
- **Real-time Tracking**: Integration with Baidu Maps API to visualize the logistics path, including historical tracks and current location of the package.
- **Package Search**: Ability to search for packages via text input or by scanning a QR code/barcode using the device camera.
- **Address Management**: Support for managing and selecting multiple sender and receiver addresses.
- **User Authentication**: Login system to personalize user data and shipment history.

## Technical Stack

- **Language**: Java
- **Platform**: Android (Min SDK 19)
- **Architecture**: Model-View-Presenter (MVP)
- **Libraries & APIs**:
    - **Baidu Maps SDK**: For map rendering, geolocation, and logistics tracking.
    - **Volley**: For handling network requests and API communication.
    - **ZXing**: For barcode/QR code scanning.
    - **SMSSDK**: For SMS-based verification/notifications.

## Installation

### Prerequisites
- Android Studio
- A Baidu Maps API Key (The app currently references one in `AndroidManifest.xml`, but a custom key may be required for production).

### Steps
1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. Ensure you have the necessary permissions granted on the target device (Camera, Location, Internet, Storage).
5. Build and run the app on an Android emulator or physical device.

## Usage

1. **Creating a Shipment**: 
   - Navigate to the "Send" section from the main dashboard.
   - Select or enter the sender's address and the receiver's address.
   - Click "Submit" to generate a tracking ID.
2. **Tracking a Package**:
   - Enter the tracking ID in the search bar or use the camera icon to scan a package label.
   - Once the package is found, view the logistics path on the map to see the real-time movement of your shipment.
3. **Account Management**:
   - Access the "Me" section to manage user profiles and saved addresses.

## Permissions Required

The application requires the following Android permissions:
- `CAMERA`: For scanning QR codes.
- `ACCESS_FINE_LOCATION` & `ACCESS_COARSE_LOCATION`: For map tracking and user positioning.
- `INTERNET` & `ACCESS_NETWORK_STATE`: For communicating with the backend server.
- `READ_PHONE_STATE`: For device identification.
- `WRITE_EXTERNAL_STORAGE`: For caching map and location data.

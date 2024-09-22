# SocketCommApp

SocketCommApp is an Android application that demonstrates network connectivity and socket communication. It allows users to check their network connection status, perform socket requests, and execute HTTP GET requests to fetch data from a server.

## Features
- **Network Connection Check**: Users can verify if the device is connected to the internet.
- **Socket Requests**: Connects to a time server and retrieves the current time using socket communication.
- **HTTP GET Requests**: Fetches data from a specified URL and displays the response.

### Requirements
- Android Studio
- An Android device or emulator

## Installation
1. Clone this repository:
   ```bash
   git clone https://github.com/alexsandro007/AndroidFileSystem.git
   ```
2. Open the SocketCommApp folder in Android Studio.
3. Build and run the project on an Android emulator or a physical device.

### Usage
- **Check Network Connection**: Click the "Проверить подключение к сети" button to check your internet connection.
- **Socket Request**: Click the "Socket Request" button to connect to the time server and retrieve the current time.
- **HTTP GET Request**: Click the "HTTP GET Request" button to fetch data from the URL: `https://jsonplaceholder.typicode.com/posts/1`.

### Permissions
The application requires the following permissions:
- `INTERNET`: To make network requests.
- `ACCESS_NETWORK_STATE`: To check the network connection status.

## Technologies Used
- **Java**: Core programming language for the application.
- **Android Studio**: Development environment used to build the app.
- **Android SDK**: Software development kit for building Android applications.
- **AsyncTask**: Used for performing background operations and updating the UI thread.
- **HTTPURLConnection**: Class used for making HTTP requests.
- **Socket**: Java class used for implementing socket programming to establish network connections.
# TaskWeave
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white) ![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=android&logoColor=white) ![Firebase](https://img.shields.io/badge/firebase-%23039BE5.svg?style=for-the-badge&logo=firebase) ![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)

An offline-first, real-time collaborative project management Android application. TaskWeave is designed to keep teams synchronized without relying on a constant network connection. It is built entirely with **Jetpack Compose**, strictly adheres to **Clean Architecture** principles, and is powered by **Firebase** for secure authentication and real-time data flow.

## 📱 Previews

<img width="108" height="240" alt="Screenshot_20260215_151854" src="https://github.com/user-attachments/assets/95a1e416-ff5f-41e0-8574-543461003fcc" />
<img width="108" height="240" alt="Screenshot_20260215_151849" src="https://github.com/user-attachments/assets/02d12605-af87-449f-81a8-f0f891d32bfa" />
<img width="108" height="240" alt="Screenshot_20260215_151837" src="https://github.com/user-attachments/assets/78e1e27f-3660-4773-a120-ffcdc0c694c2" />
<img width="108" height="240" alt="Screenshot_20260215_151756" src="https://github.com/user-attachments/assets/ac250228-14df-4cd9-8ab7-c4c53b59f501" />
<img width="108" height="240" alt="Screenshot_20260215_151748" src="https://github.com/user-attachments/assets/b1a8dfec-7fe4-40cb-aac4-e8f940f93957" />
<img width="108" height="240" alt="Screenshot_20260215_151734" src="https://github.com/user-attachments/assets/36ba3f1e-d304-4109-87f9-67e907ccdee7" />
<img width="108" height="240" alt="Screenshot_20260215_151728" src="https://github.com/user-attachments/assets/33bce43e-62e7-4bc3-a162-e5deb68af308" />
<img width="108" height="240" alt="Screenshot_20260215_151709" src="https://github.com/user-attachments/assets/14f17ec5-fbdb-40bc-ba29-81369c105417" />
<img width="108" height="240" alt="Screenshot_20260215_151551" src="https://github.com/user-attachments/assets/9d4de358-c8ef-47c4-9443-d94fd65efadd" />
<img width="108" height="240" alt="Screenshot_20260215_151537" src="https://github.com/user-attachments/assets/f7ceed95-1d1a-40d9-abd3-d5476e142062" />
<img width="108" height="240" alt="1" src="https://github.com/user-attachments/assets/580c866b-648a-418a-a27e-7430f69dbd2a" />

## 🚀 Key Features

* **Offline-First Architecture:** Users can create, edit, toggle, and delete tasks without an internet connection. The local UI updates instantly, and Firestore Batched Writes seamlessly sync the state with the server upon reconnection without data collisions.
* **Real-Time Multi-User Collaboration:** Leverages Firebase `SnapshotListeners` converted into Kotlin Coroutine `Flows`, ensuring all project members see task updates and dynamic progress bar changes instantaneously.
* **Secure Workspace Sharing:** Users can generate unique project invite codes and copy them to the clipboard with a single tap to build teams and collaborate in isolated, securely-ruled workspaces.
* **Premium Material 3 UI/UX:** Features a fully edge-to-edge design, dynamic pill-shaped progress indicators, crossfade state transitions, inline task editing with automatic keyboard management, and deeply integrated Light/Dark mode theming.
* **Secure Authentication:** Complete email and password authentication flow powered by Firebase Auth, featuring reactive session management that seamlessly routes users between the Auth and Home graphs.

## 🏗 System Design & Architecture

This application strictly enforces **Clean Architecture** to ensure the separation of concerns, testability, and a highly scalable data flow. Dependencies flow strictly inwards: `Presentation -> Domain <- Data`.

### 1. The Domain Layer (Pure Kotlin)
The core business logic is completely isolated from Android frameworks and Firebase. It contains pure Kotlin models (`User`, `Project`, `Task`) and Repository Interfaces. This guarantees that if the backend is ever migrated (e.g., to a custom REST API or Supabase), the Domain and UI layers remain 100% untouched.

### 2. The Data Layer & Offline Sync Strategy
To achieve true offline-first capabilities, Firebase Transactions were explicitly avoided (as they require an active network connection to guarantee state). Instead, the Data Layer utilizes **Firestore Batched Writes** and `FieldValue.increment()`. 
* **The "Why":** This allows the local Firestore cache to instantly resolve the UI state while completely offline, queuing the mutations to synchronize silently in the background when the device regains connectivity.
* Data Transfer Objects (DTOs) act as the anti-corruption layer, parsing remote Firestore documents and mapping them into the pure Domain models before emitting them up the stream.

### 3. The Presentation Layer (MVVM + Compose)
The UI is built exclusively with Jetpack Compose. ViewModels are heavily optimized to prevent race conditions during real-time updates by combining multiple data streams:

### 4. Tech Stack
* Language: Kotlin
* UI Toolkit: Jetpack Compose (Material 3)
* Architecture: Clean Architecture, MVVM, MVI-style Event Channels
* Dependency Injection: Dagger Hilt
* Asynchrony: Coroutines & Flow (StateFlow, SharedFlow, Channel, combine)
* Backend / Database: Firebase Authentication, Cloud Firestore (with strict Security Rules)
* Lifecycle: ViewModel, collectAsStateWithLifecycle
* Navigation: Jetpack Navigation Compose

### 5. Local Setup
To run this project locally, you will need to connect it to your own Firebase instance:

* Clone the repository
* Go to the Firebase Console and create a new project.
* Enable Authentication (Email/Password) and Firestore Database.
* Apply the required Firestore Security Rules (refer to the documentation or implementation to ensure subcollections are protected).
* Register your Android app in the Firebase console using the project's package name.
* Download the google-services.json file and place it in the app/ directory of the cloned project.
* Build and run the app via Android Studio.

# Planner App

This app is a free to-do list, planner for managing and organizing your daily tasks, to-do lists, notes, reminders

# Architecture
The architecture of the application is based, apply and strictly complies with each of the following 5 points:
  - A single-activity architecture, using the [Navigation component] to manage fragment operations.
  - [Android architecture components], part of Android Jetpack for give to project a robust design, testable and maintainable.
  - Pattern Model-View-Presenter (MPV) & [Moxy] facilitating a separation of development of the graphical user interface.
  - [S.O.L.I.D] design principles intended to make software designs more understandable, flexible and maintainable.
  - [Android-CleanArchitecture](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/) 

# Tech-stack
This project takes advantage of many popular libraries, plugins and tools of the Android ecosystem. Most of the libraries are in the stable version, unless there is a good reason to use non-stable dependency.
-   Minimum SDK level 21
-   Kotlin based + Coroutines for asynchronous.
   
#### Dependencies

-   [Jetpack](https://developer.android.com/jetpack):
    -   [Android KTX](https://developer.android.com/kotlin/ktx.html) - provide concise, idiomatic Kotlin to Jetpack and Android platform APIs.
    -   [AndroidX](https://developer.android.com/jetpack/androidx) - major improvement to the original Android [Support Library](https://developer.android.com/topic/libraries/support-library/index), which is no longer maintained.
    -   [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform actions in response to a change in the lifecycle status of another component, such as activities and fragments.
    -   [Navigation](https://developer.android.com/guide/navigation/) - helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.
    -   [Room](https://developer.android.com/topic/libraries/architecture/room) - persistence library provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
    -   [Work Manager](https://developer.android.com/topic/libraries/architecture/workmanager/basics) - WorkManager is an API that makes it easy to schedule deferrable, asynchronous tasks that are expected to run even if the app exits or the device restarts.
-   [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - managing background threads with simplified code and reducing needs for callbacks.
-   [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - dependency injector for replacement all FactoryFactory classes.
-   [google/gson](https://github.com/google/gson) - gson is a Java library that can be used to convert Java Objects into their JSON representation. 
-   [Timber](https://github.com/JakeWharton/timber) - a logger with a small, extensible API which provides utility on top of Android's normal Log class.

#### Plugins
- [Ktlint](https://github.com/pinterest/ktlint) - an anti-bikeshedding Kotlin linter with built-in formatter.
- [Detekt](https://github.com/arturbosch/detekt) - a static code analysis tool for the Kotlin programming language.
- [SafeArgs](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args) - generates simple object and builder classes for type-safe navigation and access to any associated arguments.
- [Firebase Crashlytics](https://firebase.google.com/products/crashlytics) - Firebase Crashlytics helps you track, prioritize, and fix stability issues that erode app quality, in realtime. Spend less time triaging and troubleshooting crashes and more time building app features that delight users.
- [Firebase Authentication](https://firebase.google.com/docs/auth) - Firebase Authentication provides backend services, easy-to-use SDKs, and ready-made UI libraries to authenticate users to app

# Design
In terms of design has been followed recommendations android [material design](https://developer.android.com/guide/topics/ui/look-and-feel) comprehensive guide for visual design across platforms and devices

   [Navigation component]: <https://developer.android.com/guide/navigation/navigation-getting-started>
   [Android architecture components]: <https://developer.android.com/topic/libraries/architecture/>
   [Moxy]: <https://github.com/moxy-community/Moxy>
   [S.O.L.I.D]: <https://en.wikipedia.org/wiki/SOLID>
 

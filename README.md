# JetpackComposeCanvasCrash
For bug report purposes - https://issuetracker.google.com/issues/232625913

Jetpack Compose version: **1.1.1**

Jetpack Compose component used: **AndroidView**

Android Studio Build: **Android Studio Chipmunk | 2021.2.1**

Build #AI-212.5712.43.2112.8512546, built on April 28, 2022

Kotlin version: **1.6.10**

## Steps to Reproduce or Code Sample to Reproduce:
1. Have an AndroidView in a Compose hierarchy
2. Wrap a custom View in AndroidView, that draws the ComposeView on a software canvas inside of the onDraw call
3. Crash -  `java.lang.IllegalStateException: Recording currently in progress - missing #endRecording() call?`

Even though the example is weird, it's based on a real problem - https://github.com/Dimezis/BlurView/issues/157

I'm trying to obtain a hierarchy snapshot on a software canvas backed by a bitmap, but unlike the regular View system, Compose crashes on it, for some reason.
Judging by the internals of the `AndroidComposeView.dispatchDraw`, it can be fixed.
I haven't tried building a fixed version of Compose, but it seems to me that `updateDisplayList` should not be called when `dispatchDraw` is called with a software canvas as an argument.

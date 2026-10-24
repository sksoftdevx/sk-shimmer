# SK Shimmer

A lightweight Java Android shimmer loading library by **SKSOFTDEV**.

## Status
Initial project. Build and test in Android Studio before publishing a release.

## JitPack distribution
After the repository builds successfully and you create a Git tag such as `1.0.0`, users add JitPack to `settings.gradle`:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Then in app-level `build.gradle`:

```gradle
dependencies {
    implementation 'com.github.sksoftdevx.sk-shimmer:skshimmer:1.0.0'
}
```

This coordinate must be verified on JitPack before telling users it is published.

## XML usage

```xml
<com.sksoftdev.skshimmer.SKShimmerLayout
    android:id="@+id/shimmer"
    android:layout_width="match_parent"
    android:layout_height="120dp"
    app:skShimmerDuration="1200"
    app:skShimmerAutoStart="true">
    <View
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#E5E7EB" />
</com.sksoftdev.skshimmer.SKShimmerLayout>
```

Add `xmlns:app="http://schemas.android.com/apk/res-auto"` to the parent XML element.
Call `startShimmer()` and `stopShimmer()` from Java as needed.

## Requirements
Android Studio, JDK 17 for the Android Gradle Plugin, and internet access for Gradle dependencies.

## License
MIT

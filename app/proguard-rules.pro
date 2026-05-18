# Cardinal Compass — release (R8) rules

# Preserve line numbers for readable release stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Kotlin / metadata
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}

# Kotlin coroutines (Flow/callbackFlow)
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# Hilt / Dagger (supplements consumer rules from hilt-android)
-keep @dagger.hilt.android.HiltAndroidApp class * {
    <init>();
}
-keep @dagger.hilt.android.AndroidEntryPoint class * {
    <init>();
}
-keep @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}
-keepclasseswithmembers class * {
    @dagger.* <methods>;
}
-keepclasseswithmembers class * {
    @javax.inject.* <methods>;
}
-keep class * extends dagger.hilt.internal.GeneratedComponentManagerHolder {
    <init>();
}

# Google Play Services Location (supplements library consumer rules)
-dontwarn com.google.android.gms.**

# Android platform types used via reflection in framework code
-keepclassmembers class * implements android.hardware.SensorEventListener {
    public void onSensorChanged(android.hardware.SensorEvent);
    public void onAccuracyChanged(android.hardware.Sensor, int);
}

# Retrofit stuff
-dontwarn com.squareup.okhttp.*
-dontwarn retrofit.appengine.UrlFetchClient
-dontwarn rx.**

-dontwarn retrofit.**
-dontwarn retrofit.appengine.UrlFetchClient
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
 @retrofit.http.* <methods>;
}

-keepattributes Signature
-keepattributes *Annotation*
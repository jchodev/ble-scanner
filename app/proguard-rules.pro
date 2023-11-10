# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
# Keep Annotation
-keepattributes *Annotation*,InnerClasses

-keepattributes Signature

-keepattributes SourceFile,LineNumberTable

-optimizations !code/simplification/cast,!field/*,!class/merging/*


####################Android #########################

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.app.Application
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View


-keep class **.R$* {*;}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# keep Parcelable
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# keep Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep class com.google.android.material** {*;}

-keep class androidx** {*;}

-keep public class * extends androidx**

-keep interface androidx** {*;}

-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**

-dontwarn androidx.**

####################Android #########################

#################### 3 Party Start#########################
#Retrofit2
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod
# Retain service method parameters when optimizing.
-keep,allowobfuscation,allowoptimization interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**
# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions

-dontwarn retrofit2.**
-keep class retrofit2** { *; }
-keepattributes Signature
-keepattributes Exceptions

#okhttp3
-dontwarn okio.**
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

#-keep class io.alterac.blurkit** { *; }

#-keep class dev.b3nedikt** { *; }

#-keep class dcom.airbnb.android** { *; }

#-keep class com.github.mcxtzhang** { *; }

#-keep class io.apptik.widget** { *; }

#-keep class com.makeramen** { *; }

#-keep class com.contrarywind** { *; }

#youtobe video player
# GoogleApiClient
# Needed to keep generic types and @Key annotations accessed via reflection
-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault
-keepclassmembers class * {
  #@com.google.api.client.util.Key <fields>;
  @com.google.api.client.util.Key* <fields>;
}
# Needed by google-http-client-android when linking against an older platform version
-dontwarn com.google.api.client.extensions.android.**
# Needed by google-api-client-android when linking against an older platform version
-dontwarn com.google.api.client.googleapis.extensions.android.**
# Needed by google-play-services when linking against an older platform version
-dontwarn com.google.android.gms.**
# com.google.client.util.IOUtils references java.nio.file.Files when on Java 7+
-dontnote java.nio.file.Files, java.nio.file.Path
# Suppress notes on LicensingServices
-dontnote **.ILicensingService
# Suppress warnings on sun.misc.Unsafe
-dontnote sun.misc.Unsafe
-dontwarn sun.misc.Unsafe

-dontwarn com.google.common.**
-dontwarn com.google.api.client.json.**



## Android architecture components: Lifecycle
# LifecycleObserver's empty constructor is considered to be unused by proguard
-keepclassmembers class * implements android.arch.lifecycle.LifecycleObserver* {
    <init>(...);
}
# ViewModel's empty constructor is considered to be unused by proguard
-keepclassmembers class * extends android.arch.lifecycle.ViewModel* {
    <init>(...);
}
# keep Lifecycle State and Event enums values
-keepclassmembers class android.arch.lifecycle.Lifecycle$State* { *; }
-keepclassmembers class android.arch.lifecycle.Lifecycle$Event* { *; }
# keep methods annotated with @OnLifecycleEvent even if they seem to be unused
# (Mostly for LiveData.LifecycleBoundObserver.onStateChange(), but who knows)
-keepclassmembers class * {
    @android.arch.lifecycle.OnLifecycleEvent *;
}

-keep class * implements android.arch.lifecycle.LifecycleObserver* {
    <init>(...);
}
-keepclassmembers class android.arch** { *; }
-keep class android.arch** { *; }
-dontwarn android.arch.**


-keep public class com.google.android.gms.* { public *; }
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
-dontwarn com.google.android.gms.**
-dontwarn com.google.common.cache.**
-dontwarn com.google.common.primitives.**

-keep public class com.google.firebase.analytics.FirebaseAnalytics* {
    public *;
}

-keep public class com.google.android.gms.measurement.AppMeasurement* {
    public *;
}

-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe* <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode*{ *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent* {
    <init>(java.lang.Throwable);
}

# Android x
-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }

-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.* { *; }

#Moshi
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}

-keep @com.squareup.moshi.JsonQualifier @interface *

# Enum field names are used by the integrated EnumJsonAdapter.
# values() is synthesized by the Kotlin compiler and is used by EnumJsonAdapter indirectly
# Annotate enums with @JsonClass(generateAdapter = false) to use them with Moshi.
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
    **[] values();
}

# Keep helper method to avoid R8 optimisation that would keep all Kotlin Metadata when unwanted
-keepclassmembers class com.squareup.moshi.internal.Util {
    private static java.lang.String getKotlinMetadataClassName();
}

# Keep ToJson/FromJson-annotated methods
-keepclassmembers class * {
  @com.squareup.moshi.FromJson <methods>;
  @com.squareup.moshi.ToJson <methods>;
}

 # Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
 -keep,allowobfuscation,allowshrinking interface retrofit2.Call
 -keep,allowobfuscation,allowshrinking class retrofit2.Response

 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

 -keep public class kotlin.**
################### 3 Party End #######################

#################### My App #########################
#-keep class com.jerry.assessment.base** {*;}
#-keep class com.jerry.assessment.features.mobilecoverage.data.remote** {*;}


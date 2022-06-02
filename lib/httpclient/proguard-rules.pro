##### https://github.com/square/okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

-dontwarn okio.**
-keep class okio.**{*;}

##### https://github.com/jeasonlzy/okhttp-OkGo
-keep class com.lzy.okgo.cookie.**{*;}

##### https://github.com/google/gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

##### https://github.com/Blankj/AndroidUtilCode
-dontwarn com.blankj.utilcode.**

-keepclassmembers class * {
    @com.blankj.utilcode.util.BusUtils$Bus <methods>;
}

-keep public class * extends com.blankj.utilcode.util.ApiUtils$BaseApi
-keep,allowobfuscation @interface com.blankj.utilcode.util.ApiUtils$Api
-keep @com.blankj.utilcode.util.ApiUtils$Api class *

-keepattributes *Annotation*

##### httpclient
-keep class com.watayouxiang.httpclient.model.** { <fields>; }
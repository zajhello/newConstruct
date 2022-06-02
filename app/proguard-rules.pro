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

################################################基础混淆通用配置####################
-optimizationpasses 5   #指定代码的压缩级别
-dontusemixedcaseclassnames     #包明不混合大小写
-dontskipnonpubliclibraryclasses  #不跳过library中的非public的类
-dontoptimize   #不进行优化，建议使用此选项
-dontpreverify  #不进行预校验,Android不需要,可加快混淆速度。
-verbose    #打印混淆的详细信息
-ignorewarnings   #忽略警告

# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*
#保护范型
-keepattributes Signature
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment
#忽略警告
#-ignorewarning
##记录生成的日志数据,gradle build时在本项目根目录输出##
#apk 包内所有 class 的内部结构
-dump proguard/class_files.txt
#未混淆的类和成员
-printseeds proguard/seeds.txt
#列出从 apk 中删除的代码
-printusage proguard/unused.txt
#混淆前后的映射
-printmapping proguard/mapping.txt
########记录生成的日志数据，gradle build时 在本项目根目录输出-end######
#如果引用了v4或者v7包
-dontwarn android.support.**
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

#保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
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

#保持枚举 enum 类不被混淆
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}


#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------
-keep class com.jiajias.circle.entity.**{*;}

#============================================第三方================================

#==================js================================
-keepattributes Annotation
-keepattributes JavascriptInterface
-keepclassmembers class * {
@android.webkit.JavascriptInterface <methods>;
}
-keepclassmembers class *$JsInterface {
    public *;
}
-keep public class *$JsInterface{
    public void *();
}
-keepclassmembers class *$JsInterface {
    public *;
}
-keep public class *$JsInterface{
    public void *();
}
-keepclassmembers class *$JsInterface {
    public *;
}
-keep public class *$JsInterface{
    public void *();
}

-keepclassmembers class * extends android.webkit.WebChromeClient{
    public void openFileChooser(...);
}

#==================androidx==========================
-keep class com.google.android.material.** {*;}
-keep class androidx.** {*;}
-keep public class * extends androidx.**
-keep interface androidx.** {*;}
-dontwarn com.google.android.material.**
-dontnote com.google.android.material.**
-dontwarn androidx.**

#==================Facebook==========================
# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
-keep,allowobfuscation @interface com.facebook.soloader.DoNotOptimize
# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}
# Do not strip any method/class that is annotated with @DoNotOptimize
-keep @com.facebook.soloader.DoNotOptimize class *
-keepclassmembers class * {
    @com.facebook.soloader.DoNotOptimize *;
}
# Keep native methods
-keepclassmembers class com.facebook.** {
    native <methods>;
}
# Do not strip SoLoader class and init method
-keep public class com.facebook.soloader.SoLoader {
    public static void init(android.content.Context, int);
}
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**

#==================OkHttp3==========================
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**
-keep class okio.**{*;}

#==================BaseRecyclerViewAdapterHelper==========================
-keep class com.chad.library.adapter.** {*;}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers public class * extends com.chad.library.adapter.base.BaseViewHolder {
           <init>(android.view.View);
}

#==================Firebase==========================
-keep class persistence.** {*;}
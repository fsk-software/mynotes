# Configuration for Guava

-keep class com.google.common.base.Preconditions {
    public ** checkNotNull(**);
    public ** checkArgument(**);
}

-keep class com.google.common.base.Strings {
    public ** isNullOrEmpty(**);
    public ** emptyToNull(**);
}

-keep class com.google.common.collect.MapMakerInternalMap$ReferenceEntry
-keep class com.google.common.cache.LocalCache$ReferenceEntry

-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

-keepclassmembers,allowoptimization class com.google.common.* {
    void finalizeReferent();
    void startFinalizer(java.lang.Class,java.lang.Object);
}

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue

-keep,allowoptimization class com.google.inject.** { *; }
-keep,allowoptimization class javax.inject.** { *; }
-keep,allowoptimization class javax.annotation.** { *; }
-keep,allowoptimization class com.google.inject.Binder

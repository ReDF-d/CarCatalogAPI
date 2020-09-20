package com.redf.carcatalog;

import java.lang.reflect.Field;

class IllegalAccessWarningSuppressor {

    static void suppress() {
        try { // Turn off illegal access log messages.
            Class loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field loggerField = loggerClass.getDeclaredField("logger");
            Class unsafeClass = Class.forName("sun.misc.Unsafe");
            Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            Object unsafe = unsafeField.get(null);
            Long offset = (Long)unsafeClass.getMethod("staticFieldOffset", Field.class).invoke(unsafe, loggerField);
            unsafeClass.getMethod("putObjectVolatile", Object.class, long.class, Object.class) //
                    .invoke(unsafe, loggerClass, offset, null);
        } catch (Throwable ex) {
            System.out.println("Failed to disable illegal access warning:");
        }
    }
}

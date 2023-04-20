package org.example.jmm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeFactory {
    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get Unsafe instance", e);
        }
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }
}

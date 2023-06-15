package com.foogui.foo.common.core.utils;

public class NumberBoxUtil {
    private NumberBoxUtil() {
    }
    public static long unboxLong(Long boxing) {
        if (boxing != null) {

            return boxing;
        } else {
            return 0;
        }
    }
    public static boolean unboxBoolean(Boolean boxing) {
        if (boxing != null) {
            return boxing;
        } else {
            return false;
        }
    }
}

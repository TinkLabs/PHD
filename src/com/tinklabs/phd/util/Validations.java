package com.tinklabs.phd.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by root on 4/28/16.
 */
public class Validations {
    public static <T> boolean isEmptyOrNull(T[] args) {
        return args == null || args.length < 1;
    }

    public static boolean isEmptyOrNull(CharSequence cs) {
        return cs == null || cs.length() < 1;
    }

    public static boolean isEmptyOrNull(Collection collection) {
        return collection == null || collection.size() < 1;
    }

    public static boolean isEmptyOrNull(Map map) {
        return map == null || map.size() < 1;
    }

    public static boolean isEmptyOrNull(byte[] mac) {
        return mac != null && mac.length < 1;
    }
}

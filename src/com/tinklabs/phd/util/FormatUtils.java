package com.tinklabs.phd.util;

import java.text.NumberFormat;

/**
 * Created by root on 5/4/16.
 */
public class FormatUtils {
    public static String formatAsPercentage(double num) {
        NumberFormat defaultFormat = NumberFormat.getPercentInstance();
        defaultFormat.setMinimumFractionDigits(0);
        defaultFormat.setMaximumFractionDigits(0);
        return defaultFormat.format(num);
    }
}

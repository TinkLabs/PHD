package com.tinklabs.phd.util;

import java.util.Date;

/**
 * Created by root on 5/4/16.
 */
public class DateHelper {


    private static final int[] LEAP_YEAR_ARRAY = {31, 29, 31, 30, 31, 31, 30, 31, 30, 31, 30, 31};
    private static final int[] NON_LEAP_YEAR_ARRAY = {31, 28, 31, 30, 31, 31, 30, 31, 30, 31, 30, 31};

    public static Date parse(String dateStr) {
        Date date = null;
        if (!Validations.isEmptyOrNull(dateStr)) {
            try {
                String[] dateParts = dateStr.split("-");
                int year = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int day = Integer.parseInt(dateParts[2]);
                if (isValidDate(year, month, day))
                    date = new Date(year, month, day);
            } catch (Throwable t) {
            }
        }
        return date;
    }

    private static boolean isValidDate(int year, int month, int day) {
        if (year > 0 && (month > 0 && month < 13) && (day > 0 && day < 32)) {
            boolean isLeapYear = isLeapYear(year);
            int[] arrayToUse = isLeapYear ? LEAP_YEAR_ARRAY : NON_LEAP_YEAR_ARRAY;
            int maxDays = arrayToUse[month - 1];
            return day <= maxDays;
        }
        return false;
    }

    private static boolean isLeapYear(int year) {
        return year > 0 && (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }
}

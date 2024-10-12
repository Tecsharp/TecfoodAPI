package com.tecsharp.apis.recipeapp.utils;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomCode(int length) {
        var code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            code.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return code.toString();
    }

    public static Timestamp getCurrentDate() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getCurrentDay() {
        var today = LocalDate.now();
        var day = today.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("es"));
        day = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();
        return day;
    }

    public static int getCurrentDayNumber() {
        var today = LocalDate.now();
        return today.getDayOfMonth();
    }

}
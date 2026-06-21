package com.example.Food.Delivery.Platform.Backend.Utils;

import java.time.LocalTime;
import java.util.Random;


public class HelperUtils {

    private static final Random RANDOM = new Random();

    // CUST-8492
    public static String generateCode(String prefix) {
        int number = 1000 + RANDOM.nextInt(9000);
        return prefix + "-" + number;
    }

    // REST-483921
    public static String generateCode(String prefix, int length) {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            code.append(RANDOM.nextInt(10));
        }

        return prefix + "-" + code;
    }

    // Distance in kilometers (Haversine Formula)
    public static double calculateDistance(
            double lat1,
            double lng1,
            double lat2,
            double lng2) {

        final double EARTH_RADIUS = 6371; // km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2)
                * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    public static double calculateTotal(double subtotal, double fee) {
        return subtotal + fee;
    }

    public static double calculateTotal(
            double subtotal,
            double fee,
            double discount) {

        return subtotal + fee - discount;
    }

    public static String formatCurrency(double amount) {
        return String.format("OMR %.2f", amount);
    }

    public static String formatCurrency(
            double amount,
            String currencyCode) {

        return String.format("%s %.2f", currencyCode, amount);
    }

    public static boolean isBusinessOpen(
            String openTime,
            String closeTime) {

        LocalTime now = LocalTime.now();

        LocalTime open = LocalTime.parse(openTime);
        LocalTime close = LocalTime.parse(closeTime);

        return !now.isBefore(open) && !now.isAfter(close);
    }
}

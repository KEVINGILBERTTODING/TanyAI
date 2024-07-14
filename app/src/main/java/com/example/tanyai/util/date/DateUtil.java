package com.example.tanyai.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String getCurrentDateTime() {
        // Mendapatkan tanggal dan waktu saat ini
        Date now = new Date();

        // Format tanggal
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", new Locale("id", "ID"));
        String date = dateFormat.format(now);

        // Format waktu
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm a", Locale.ENGLISH);
        String time = timeFormat.format(now);

        // Gabungkan tanggal dan waktu
        return date + ", " + time;
    }

    public static String getGreeting() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= 6 && hour < 12) {
            return "Selamat Pagi,";
        } else if (hour >= 12 && hour < 15) {
            return "Selamat Siang,";
        } else if (hour >= 15 && hour < 18) {
            return "Selamat Sore,";
        } else {
            return "Selamat Malam,";
        }
    }
}

package com.example.tanyai.util.date;

import java.text.SimpleDateFormat;
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
}

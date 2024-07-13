package com.example.tanyai.util.toast;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    public static void showToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }
}

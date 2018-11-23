package com.sarahehabm.newsapp;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {
    public static String formatDate(Date date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");

        return dateFormat.format(date);
    }
}

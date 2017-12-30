package com.ezz.bakingapp.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by samar ezz on 11/10/2017.
 */

public class Utils {

    public static boolean checkIfConnectedToTheInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetInfo != null && activeNetInfo.isAvailable()
                && activeNetInfo.isConnected();
    }
}

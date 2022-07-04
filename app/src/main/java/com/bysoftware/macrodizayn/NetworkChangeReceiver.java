package com.bysoftware.macrodizayn;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import static com.bysoftware.macrodizayn.MainActivity.dialog;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "Otomatik internet Kontrol¸";
    static boolean isConnected = false;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        //isNetworkAvailable(context);

        try {
            if (isOnline(context)) {
                dialog.cancel();
                Log.e("internet", "Online Connect Intenet ");
            } else {
                dialog.show();
                Log.e("internet", "Conectivity Failure !!! ");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("LongLogTag")
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE); //Sistem ağını dinliyor internet var mı yok mu

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        if (!isConnected) { //internet varsa
                            isConnected = true;
                            Log.v(LOG_TAG, "internete Bağlandınız!");
                            dialog.cancel();

                            //Toast.makeText(context, "internete Bağlandınız!", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                }
            }
        }
        isConnected = false;

        //Toast.makeText(context, "İnternet Yok", Toast.LENGTH_LONG).show();
        dialog.show();
        Log.v(LOG_TAG, "İnternet Yok!");
        return false;
    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
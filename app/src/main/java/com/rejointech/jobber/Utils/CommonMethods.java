package com.rejointech.jobber.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class CommonMethods {
    /*
     * A Common function to display Short toasts
     * */
    public static void DisplayShortTOAST(Context context, String toastMSG) {
        Toast.makeText(context, toastMSG, Toast.LENGTH_SHORT).show();
    }

    /*
     * A Common function to display Long toasts
     * */
    public static void DisplayLongTOAST(Context context, String toastMSG) {
        Toast.makeText(context, toastMSG, Toast.LENGTH_LONG).show();
    }

    /*
     * A Common function to display LOGS
     * */
    public static void LOGthesite(String logtitle, String logtext) {
        Log.d(logtitle, logtext);
    }

    /*
     * A Common function to Check network connection
     * */
    public static Boolean isnetworkConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

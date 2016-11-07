package app.youse.com.reddit.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

/**
 * Created by heitornascimento on 11/2/16.
 */

public class ConnectionUtil {

    /**
     * Must be called for every network communication.
     *
     * @param ctx
     * @return
     */
    public static boolean hasValidaConnection(Context ctx) {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null) {
                return netInfo.isConnectedOrConnecting();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static CustomTabsIntent buildTabIntent() {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = intentBuilder.build();
        return customTabsIntent;
    }
}

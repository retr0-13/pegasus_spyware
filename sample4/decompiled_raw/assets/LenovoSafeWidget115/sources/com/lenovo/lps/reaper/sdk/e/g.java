package com.lenovo.lps.reaper.sdk.e;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
/* loaded from: classes.dex */
public final class g {
    private static int a;
    private static boolean b = false;

    public static int a() {
        return a;
    }

    public static void a(Context context) {
        b = ((TelephonyManager) context.getSystemService("phone")).isNetworkRoaming();
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            a = 1;
            return;
        }
        int type = activeNetworkInfo.getType();
        int subtype = activeNetworkInfo.getSubtype();
        if (type == 1) {
            a = 2;
        } else if (type != 0) {
            a = 0;
        } else if (!activeNetworkInfo.isConnected()) {
        } else {
            if (subtype == 1 || subtype == 2 || subtype == 4) {
                a = 4;
            } else {
                a = 3;
            }
        }
    }

    public static boolean b() {
        return b;
    }
}

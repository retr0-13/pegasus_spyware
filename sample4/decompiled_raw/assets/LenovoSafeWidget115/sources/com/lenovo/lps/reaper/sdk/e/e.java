package com.lenovo.lps.reaper.sdk.e;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
/* loaded from: classes.dex */
public final class e {
    private static String a = "000000000000";
    private static String b = "mac";
    private static Context c;
    private static BroadcastReceiver d;

    public static String a() {
        return a;
    }

    public static void a(Context context) {
        boolean z;
        c = context;
        String deviceId = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
        if (deviceId == null || deviceId.length() <= 0 || "00000000".equals(deviceId)) {
            String lowerCase = Build.MANUFACTURER.toLowerCase();
            if (lowerCase == null || !lowerCase.contains("lenovo")) {
                String lowerCase2 = Build.MODEL.toLowerCase();
                z = lowerCase2 != null && lowerCase2.contains("lenovo");
            } else {
                z = true;
            }
            if (z) {
                String str = Build.VERSION.SDK_INT >= 9 ? Build.SERIAL : null;
                b.a("SN: ", str);
                if (str != null && str.length() > 0 && !str.toLowerCase().equals("unknown")) {
                    a(str.toUpperCase(), "sn");
                    return;
                }
            }
            IntentFilter intentFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
            if (!d() && d == null) {
                d = new f();
                c.registerReceiver(d, intentFilter);
                return;
            }
            return;
        }
        a(deviceId.toUpperCase(), "imei");
    }

    private static void a(String str, String str2) {
        a = str;
        b = str2;
        b.b("PlusUtil", "DeviceType&Id is Set to: " + b + a);
    }

    public static String b() {
        return b;
    }

    public static boolean d() {
        SharedPreferences sharedPreferences = c.getSharedPreferences("PlusUtil", 0);
        if (sharedPreferences.contains("mac")) {
            a(sharedPreferences.getString("mac", a), "mac");
            return true;
        }
        String macAddress = ((WifiManager) c.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            return false;
        }
        b.a("MAC: ", macAddress);
        sharedPreferences.edit().putString("mac", macAddress.replace(":", "").toUpperCase()).commit();
        a(sharedPreferences.getString("mac", a), "mac");
        return true;
    }
}

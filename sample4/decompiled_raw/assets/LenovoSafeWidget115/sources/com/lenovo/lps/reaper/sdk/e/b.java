package com.lenovo.lps.reaper.sdk.e;

import android.util.Log;
/* loaded from: classes.dex */
public class b {
    private static boolean a;

    public static void a(String str, String str2) {
        if (a) {
            Log.v(str, str2);
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (a) {
            Log.e(str, str2, th);
        } else {
            Log.e(str, String.valueOf(str2) + ", " + th.getClass() + ", " + th.getMessage());
        }
    }

    public static void a(boolean z) {
        a = z;
    }

    public static boolean a() {
        return a;
    }

    public static void b(String str, String str2) {
        if (a) {
            Log.i(str, str2);
        }
    }

    public static void c(String str, String str2) {
        if (a) {
            Log.d(str, str2);
        }
    }

    public static void d(String str, String str2) {
        if (a) {
            Log.w(str, str2);
        }
    }
}

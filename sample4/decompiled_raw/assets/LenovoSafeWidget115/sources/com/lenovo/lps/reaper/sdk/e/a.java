package com.lenovo.lps.reaper.sdk.e;

import android.util.Log;
import com.lenovo.lps.reaper.sdk.a.c;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/* loaded from: classes.dex */
public final class a {
    private static final String a = a.class.getName();
    private static ThreadLocal b = new ThreadLocal();

    public static String a(c cVar) {
        StringBuilder sb = new StringBuilder(64);
        com.lenovo.lps.reaper.sdk.a.a[] b2 = cVar.b();
        if (b2 == null) {
            return "";
        }
        for (com.lenovo.lps.reaper.sdk.a.a aVar : b2) {
            sb.append(aVar);
        }
        return sb.toString();
    }

    public static String a(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            Log.e(a, "exception when encode, str is: " + str);
            return str;
        }
    }

    public static void a() {
    }

    public static void b(String str) {
    }
}

package com.lenovo.lps.reaper.sdk.d;

import com.lenovo.lps.reaper.sdk.e.b;
import java.util.HashMap;
/* loaded from: classes.dex */
public final class e {
    private static HashMap a = new HashMap();
    private static long b;

    public static void a() {
        b.a("ServerConfigStorage", "ServerConfigStorage is Clear");
        a.clear();
        i.a = 6;
        g.a = true;
        h.b();
        f.a();
    }

    public static void a(long j) {
        b.a("ServerConfigStorage", "Timestamp is set: " + j);
        b = j;
    }

    public static synchronized void a(String str, boolean z) {
        synchronized (e.class) {
            if (b.a()) {
                b.c("ServerConfigStorage", "put ServerConfigStorage: [KEY]" + str + " [VALUE]" + z);
            }
            if ((i.a(str)) || g.a(str, z) || (h.b(str, z)) || (f.b(str, z))) {
            }
            a.put(str, Boolean.valueOf(z));
        }
    }

    public static long b() {
        return b;
    }

    public static synchronized boolean b(String str, boolean z) {
        boolean booleanValue;
        synchronized (e.class) {
            Boolean bool = (Boolean) a.get(str);
            booleanValue = bool == null ? true : bool.booleanValue();
        }
        return booleanValue;
    }
}

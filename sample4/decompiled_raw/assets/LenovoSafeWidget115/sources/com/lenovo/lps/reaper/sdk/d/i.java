package com.lenovo.lps.reaper.sdk.d;

import com.lenovo.lps.reaper.sdk.e.b;
/* loaded from: classes.dex */
public final class i {
    private static long a = 6;

    public static long a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(String str) {
        if (str.contains("ConfigUpdateTimestamp")) {
            try {
                a = Long.parseLong(str.substring(str.indexOf("Hour-") + 5));
                b.a("ServerConfigStorage", "UpdateConfigInterval is set to " + a);
                return true;
            } catch (Exception e) {
                b.d("ServerConfigStorage", "UpdateConfigStrategy Update Wrong. " + e.getMessage());
            }
        }
        return false;
    }
}

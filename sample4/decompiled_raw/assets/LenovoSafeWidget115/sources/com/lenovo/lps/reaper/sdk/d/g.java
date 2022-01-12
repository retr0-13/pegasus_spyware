package com.lenovo.lps.reaper.sdk.d;

import com.lenovo.lps.reaper.sdk.e.b;
/* loaded from: classes.dex */
public final class g {
    private static boolean a = true;

    public static boolean a() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean a(String str, boolean z) {
        if (!str.contains("SendFlag")) {
            return false;
        }
        a = z;
        b.a("ServerConfigStorage", "SendFlag is set to " + z);
        return true;
    }
}

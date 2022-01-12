package com.lenovo.lps.reaper.sdk.d;

import com.lenovo.lps.reaper.sdk.e.b;
import com.lenovo.lps.reaper.sdk.e.d;
import com.lenovo.lps.reaper.sdk.e.g;
import com.lenovo.safecenterwidget.MemClear4X1;
/* loaded from: classes.dex */
public final class h {
    private static int a = 1;
    private static int b = 1;
    private static boolean c = true;
    private static boolean d = true;

    public static long a() {
        switch (g.a()) {
            case 2:
                return 1;
            case 3:
                return (long) b;
            case MemClear4X1.MSG_AFTER_CLEAR_UI /* 4 */:
                return (long) a;
            default:
                return (long) b;
        }
    }

    public static boolean a(d dVar) {
        if (g.b()) {
            b.c("ServerConfigStorage", "Now is Roaming");
            return false;
        }
        switch (g.a()) {
            case 0:
                b.c("ServerConfigStorage", "Now is NOTCONCERN");
                return true;
            case 1:
            default:
                return false;
            case 2:
                return true;
            case 3:
                if (d.FORCE_DISPATCH.equals(dVar)) {
                    return true;
                }
                return d;
            case MemClear4X1.MSG_AFTER_CLEAR_UI /* 4 */:
                if (d.FORCE_DISPATCH.equals(dVar)) {
                    return true;
                }
                return c;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void b() {
        a = 1;
        b = 1;
        c = true;
        d = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(String str, boolean z) {
        int indexOf = str.indexOf("Threshold-");
        if (indexOf > 0) {
            try {
                int parseInt = Integer.parseInt(str.substring(indexOf + 10));
                if (str.contains("Dispatch3G4G")) {
                    b = parseInt;
                    d = z;
                    b.a("ServerConfigStorage", "DispatchStrategy 3G4G is set to " + parseInt + " [" + z + "]");
                    return true;
                } else if (str.contains("Dispatch2G")) {
                    a = parseInt;
                    c = z;
                    b.a("ServerConfigStorage", "DispatchStrategy 2G is set to " + parseInt + " [" + z + "]");
                    return true;
                }
            } catch (Exception e) {
                b.d("ServerConfigStorage", "DispatchStrategy Update Wrong. " + e.getMessage());
            }
        }
        return false;
    }
}

package com.lenovo.lps.reaper.sdk.d;

import com.lenovo.lps.reaper.sdk.e.b;
import com.lenovo.lps.reaper.sdk.e.g;
import com.lenovo.safecenterwidget.MemClear4X1;
/* loaded from: classes.dex */
public final class f {
    private static int a = 3;
    private static int b = 5;
    private static boolean c = true;
    private static boolean d = true;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void a() {
        a = 3;
        b = 5;
        c = true;
        d = true;
    }

    public static boolean a(int i) {
        switch (g.a()) {
            case 3:
                return d && i >= b;
            case MemClear4X1.MSG_AFTER_CLEAR_UI /* 4 */:
                return c && i >= a;
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(String str, boolean z) {
        int indexOf = str.indexOf("Threshold-");
        if (indexOf > 0) {
            try {
                int parseInt = Integer.parseInt(str.substring(indexOf + 10));
                if (str.contains("Compress3G4G")) {
                    b = parseInt;
                    d = z;
                    b.a("ServerConfigStorage", "CompressStrategy 3G4G is set to " + parseInt + " [" + z + "]");
                    return true;
                } else if (str.contains("Compress2G")) {
                    a = parseInt;
                    c = z;
                    b.a("ServerConfigStorage", "CompressStrategy 2G is set to " + parseInt + " [" + z + "]");
                    return true;
                }
            } catch (Exception e) {
                b.d("ServerConfigStorage", "UpdateCompressStrategy Update Wrong. " + e.getMessage());
            }
        }
        return false;
    }
}

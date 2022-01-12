package com.lenovo.lps.reaper.sdk.c;

import com.lenovo.lps.reaper.sdk.a.c;
import com.lenovo.lps.reaper.sdk.e.b;
/* loaded from: classes.dex */
public final class e {
    private b a;

    public final void a(b bVar) {
        this.a = bVar;
    }

    public final c[] a(c[] cVarArr) {
        if (cVarArr == null || cVarArr.length == 0) {
            return null;
        }
        b.b("ReportManager", "start postEvents");
        return this.a.a(cVarArr);
    }
}

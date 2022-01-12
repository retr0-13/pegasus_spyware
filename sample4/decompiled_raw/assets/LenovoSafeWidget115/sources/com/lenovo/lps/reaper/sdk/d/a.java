package com.lenovo.lps.reaper.sdk.d;

import com.lenovo.lps.reaper.sdk.a.b;
import com.lenovo.lps.reaper.sdk.a.c;
import com.lenovo.lps.reaper.sdk.a.d;
/* loaded from: classes.dex */
public final class a {
    private d a;
    private b b = new b();

    public final void a(int i, String str, String str2) {
        this.b.a(i, str, str2);
    }

    public final void a(c cVar) {
        this.a.a(cVar);
    }

    public final void a(d dVar) {
        this.a = dVar;
    }

    public final void a(c[] cVarArr) {
        if (cVarArr != null && cVarArr.length > 0) {
            this.a.a(cVarArr);
        }
    }

    public final c[] a() {
        return this.a.b();
    }

    public final c[] a(int i) {
        return this.a.a(200);
    }

    public final void b() {
        this.b.b();
    }

    public final void c() {
        this.a.a();
    }

    public final int d() {
        return this.a.c();
    }

    public final b e() {
        return this.b;
    }
}

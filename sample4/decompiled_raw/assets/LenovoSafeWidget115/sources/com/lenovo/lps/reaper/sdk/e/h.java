package com.lenovo.lps.reaper.sdk.e;

import android.app.Activity;
import java.util.ArrayList;
/* loaded from: classes.dex */
public final class h {
    private static h b = new h();
    private Activity c;
    private int d;
    private String e;
    private long f;
    private ArrayList a = new ArrayList();
    private boolean g = false;

    public static h a() {
        return b;
    }

    public final void a(Activity activity) {
        this.c = activity;
    }

    public final void a(String str) {
        this.e = str;
        this.f = System.currentTimeMillis();
    }

    public final void a(boolean z) {
        this.g = true;
    }

    public final void b(Activity activity) {
        this.a.add(activity);
    }

    public final boolean b() {
        return this.g;
    }

    public final Activity c() {
        return this.c;
    }

    public final boolean c(Activity activity) {
        return this.a.remove(activity);
    }

    public final String d() {
        return this.e;
    }

    public final long e() {
        return this.f;
    }

    public final int f() {
        return this.d;
    }

    public final void g() {
        this.d++;
    }

    public final void h() {
        this.d = 0;
    }
}

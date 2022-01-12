package com.lenovo.lps.reaper.sdk.a;
/* loaded from: classes.dex */
public final class a {
    private int a;
    private String b;
    private String c;
    private int d;

    public a() {
        this(3);
    }

    private a(int i) {
        this.d = 3;
    }

    public a(int i, String str, String str2, int i2) {
        this.a = i;
        this.b = str;
        this.c = str2;
        this.d = i2;
    }

    public final void a() {
        this.a = 0;
    }

    public final void a(int i) {
        this.a = i;
    }

    public final void a(String str) {
        this.b = str;
    }

    public final void b(String str) {
        this.c = str;
    }

    public final boolean b() {
        return this.a > 0 && this.a <= 5;
    }

    public final int c() {
        return this.a;
    }

    public final String d() {
        return this.b;
    }

    public final String e() {
        return this.c;
    }

    public final int f() {
        return this.d;
    }

    public final String toString() {
        if (!b()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(200);
        sb.append("(");
        sb.append(this.a);
        sb.append("!");
        sb.append(com.lenovo.lps.reaper.sdk.e.a.a(this.b));
        sb.append("!");
        sb.append(com.lenovo.lps.reaper.sdk.e.a.a(this.c));
        sb.append("!");
        sb.append(this.d);
        sb.append(")");
        return sb.toString();
    }
}

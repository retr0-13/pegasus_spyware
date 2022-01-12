package com.lenovo.lps.reaper.sdk.a;

import java.util.Date;
/* loaded from: classes.dex */
public final class c {
    private long a;
    private int b;
    private String c;
    private int d;
    private long e;
    private long f;
    private long g;
    private int h;
    private String i;
    private String j;
    private String k;
    private int l;
    private int m;
    private long n;
    private a[] o;

    public c(long j, int i, String str, int i2, long j2, long j3, long j4, long j5, int i3, String str2, String str3, String str4, int i4, int i5) {
        this.a = j;
        this.b = i;
        this.c = str;
        this.d = i2;
        this.h = i3;
        this.i = str2;
        this.j = str3;
        this.k = str4;
        this.l = i4;
        this.m = i5;
        this.e = j2;
        this.f = j3;
        this.g = j4;
        this.n = j5;
    }

    public c(String str, String str2, String str3, String str4, int i) {
        this.c = str;
        this.i = str2;
        this.j = str3;
        this.k = str4;
        this.l = i;
        this.n = System.currentTimeMillis();
    }

    public final String a() {
        StringBuilder sb = new StringBuilder(200);
        sb.append("ID:").append(this.a).append(" SessionID:").append(this.b).append(" Visits:").append(this.h).append(" Category:").append(this.i).append(" Action:").append(this.j).append(" Label:").append(this.k).append(" Value:").append(this.l).append(" NetworkStatus:").append(this.m).append(" TimestampFirst:").append(new Date(this.e).toLocaleString()).append(" TimestampPre:").append(new Date(this.f).toLocaleString()).append(" TimestampCur:").append(new Date(this.g).toLocaleString()).append(" TimestampEvent:").append(new Date(this.n).toLocaleString());
        return sb.toString();
    }

    public final void a(a[] aVarArr) {
        this.o = new a[aVarArr.length];
        for (int i = 0; i < aVarArr.length; i++) {
            this.o[i] = new a();
            this.o[i].a(aVarArr[i].c());
            this.o[i].a(aVarArr[i].d());
            this.o[i].b(aVarArr[i].e());
        }
    }

    public final a[] b() {
        return this.o;
    }

    public final int c() {
        return this.b;
    }

    public final String d() {
        return this.c;
    }

    public final int e() {
        return this.d;
    }

    public final long f() {
        return this.e;
    }

    public final long g() {
        return this.f;
    }

    public final long h() {
        return this.g;
    }

    public final int i() {
        return this.h;
    }

    public final String j() {
        return this.i;
    }

    public final String k() {
        return this.j;
    }

    public final String l() {
        return this.k;
    }

    public final int m() {
        return this.l;
    }

    public final int n() {
        return this.m;
    }

    public final long o() {
        return this.n;
    }

    public final String toString() {
        return String.valueOf(this.a);
    }
}

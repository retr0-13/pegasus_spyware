package com.lenovo.lps.reaper.sdk.d;

import com.lenovo.lps.reaper.sdk.e.b;
import java.nio.ByteBuffer;
/* loaded from: classes.dex */
public final class d {
    private static final char[] k = "ReaperSDK".toCharArray();
    private long f;
    private long g;
    private long h;
    private int i;
    private int j;
    private long a = 0;
    private int c = 0;
    private int d = 0;
    private ByteBuffer e = ByteBuffer.allocate(4096);
    private int b = 1000;

    public d(int i) {
    }

    public final int a() {
        return this.d;
    }

    public final int a(int i) {
        int i2 = i + 1;
        if (i2 > this.b) {
            return 0;
        }
        return i2;
    }

    public final void a(long j) {
        this.f = j;
    }

    public final boolean a(ByteBuffer byteBuffer) {
        char[] cArr = new char[k.length];
        for (int i = 0; i < k.length; i++) {
            if (byteBuffer.hasRemaining()) {
                cArr[i] = byteBuffer.getChar();
            }
        }
        if (!"ReaperSDK".equals(new String(cArr))) {
            this.a = 0;
            this.c = 0;
            this.d = 0;
            return false;
        }
        if (byteBuffer.getInt() == 1) {
            this.c = byteBuffer.getInt();
            this.d = byteBuffer.getInt();
            this.b = byteBuffer.getInt();
            this.a = byteBuffer.getLong();
            this.i = byteBuffer.getInt();
            this.f = byteBuffer.getLong();
            this.g = byteBuffer.getLong();
            this.h = byteBuffer.getLong();
            this.j = byteBuffer.getInt();
            b.a("FileStorageMeta", "CurrentTime:" + this.h);
            b.b("FileStorageMeta", "Loading DB...");
            b.a("FileStorageMeta", "Head:" + this.c);
            b.a("FileStorageMeta", "Tail:" + this.d);
            b.a("FileStorageMeta", "Capability:" + this.b);
            b.a("FileStorageMeta", "Sequence:" + this.a);
            b.a("FileStorageMeta", "SessionID:" + this.i);
            b.a("FileStorageMeta", "FirstView:" + this.f);
            b.a("FileStorageMeta", "PreviousView:" + this.g);
            b.a("FileStorageMeta", "visits:" + this.j);
        }
        return true;
    }

    public final int b() {
        return this.c;
    }

    public final void b(int i) {
        this.i = i;
    }

    public final void b(long j) {
        this.g = j;
    }

    public final int c() {
        this.c = a(this.c);
        if (this.c == this.d) {
            b.b("FileStorageMeta", "Drop record at " + this.d);
            this.d = a(this.d);
        }
        return this.c;
    }

    public final void c(int i) {
        this.j = i;
    }

    public final void c(long j) {
        this.h = j;
    }

    public final int d() {
        if (this.c == this.d) {
            return -1;
        }
        int i = this.d;
        this.d = a(this.d);
        return i;
    }

    public final ByteBuffer e() {
        this.e.clear();
        for (char c : k) {
            this.e.putChar(Character.valueOf(c).charValue());
        }
        this.e.putInt(1);
        this.e.putInt(this.c);
        this.e.putInt(this.d);
        this.e.putInt(this.b);
        this.e.putLong(this.a);
        this.e.putInt(this.i);
        this.e.putLong(this.f);
        this.e.putLong(this.g);
        this.e.putLong(this.h);
        this.e.putInt(this.j);
        this.e.flip();
        return this.e;
    }

    public final boolean f() {
        return a(this.c) == this.d;
    }

    public final int g() {
        return this.c >= this.d ? this.c - this.d : ((this.c + this.b) - this.d) + 1;
    }

    public final long h() {
        long j = this.a + 1;
        this.a = j;
        return j;
    }

    public final long i() {
        return this.f;
    }

    public final long j() {
        return this.g;
    }

    public final long k() {
        return this.h;
    }

    public final int l() {
        return this.i;
    }

    public final int m() {
        return this.j;
    }
}

package com.lenovo.lps.sus.a.a.b;

import android.support.v4.view.MotionEventCompat;
import java.io.IOException;
import java.io.InputStream;
/* loaded from: classes.dex */
class a extends InputStream {
    private static final String a = "Bad base64 stream";
    private InputStream b;
    private int[] c;
    private int d = 0;
    private boolean e = false;

    public a(InputStream inputStream) {
        this.b = inputStream;
    }

    private void a() {
        int i = 1;
        char[] cArr = new char[4];
        int i2 = 0;
        do {
            int read = this.b.read();
            if (read != -1) {
                char c = (char) read;
                if (b.a.indexOf(c) != -1 || c == '=') {
                    i2++;
                    cArr[i2] = c;
                    continue;
                } else if (!(c == '\r' || c == '\n')) {
                    throw new IOException(a);
                }
            } else if (i2 != 0) {
                throw new IOException(a);
            } else {
                this.c = new int[0];
                this.e = true;
                return;
            }
        } while (i2 < 4);
        boolean z = false;
        for (int i3 = 0; i3 < 4; i3++) {
            if (cArr[i3] != '=') {
                if (z) {
                    throw new IOException(a);
                }
            } else if (!z) {
                z = true;
            }
        }
        if (cArr[3] != '=') {
            i = 3;
        } else if (this.b.read() != -1) {
            throw new IOException(a);
        } else {
            this.e = true;
            if (cArr[2] != '=') {
                i = 2;
            }
        }
        int i4 = 0;
        for (int i5 = 0; i5 < 4; i5++) {
            if (cArr[i5] != '=') {
                i4 |= b.a.indexOf(cArr[i5]) << ((3 - i5) * 6);
            }
        }
        this.c = new int[i];
        for (int i6 = 0; i6 < i; i6++) {
            this.c[i6] = (i4 >>> ((2 - i6) * 8)) & MotionEventCompat.ACTION_MASK;
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.b.close();
    }

    @Override // java.io.InputStream
    public int read() {
        if (this.c == null || this.d == this.c.length) {
            if (this.e) {
                return -1;
            }
            a();
            if (this.c.length == 0) {
                this.c = null;
                return -1;
            }
            this.d = 0;
        }
        int[] iArr = this.c;
        int i = this.d;
        this.d = i + 1;
        return iArr[i];
    }
}

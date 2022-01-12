package com.lenovo.lps.sus.a.a.b;

import android.support.v4.view.MotionEventCompat;
import java.io.OutputStream;
/* loaded from: classes.dex */
class d extends OutputStream {
    private OutputStream a;
    private int b;
    private int c;
    private int d;
    private int e;

    public d(OutputStream outputStream) {
        this(outputStream, 76);
    }

    public d(OutputStream outputStream, int i) {
        this.a = null;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.a = outputStream;
        this.e = i;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void a() {
        char c = b.b;
        if (this.c > 0) {
            if (this.e > 0 && this.d == this.e) {
                this.a.write("\r\n".getBytes());
                this.d = 0;
            }
            char charAt = b.a.charAt((this.b << 8) >>> 26);
            char charAt2 = b.a.charAt((this.b << 14) >>> 26);
            char charAt3 = this.c < 2 ? '=' : b.a.charAt((this.b << 20) >>> 26);
            if (this.c >= 3) {
                c = b.a.charAt((this.b << 26) >>> 26);
            }
            this.a.write(charAt);
            this.a.write(charAt2);
            this.a.write(charAt3);
            this.a.write(c);
            this.d += 4;
            this.c = 0;
            this.b = 0;
        }
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        a();
        this.a.close();
    }

    @Override // java.io.OutputStream
    public void write(int i) {
        this.b = ((i & MotionEventCompat.ACTION_MASK) << (16 - (this.c * 8))) | this.b;
        this.c++;
        if (this.c == 3) {
            a();
        }
    }
}

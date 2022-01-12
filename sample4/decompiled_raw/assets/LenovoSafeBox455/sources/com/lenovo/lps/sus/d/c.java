package com.lenovo.lps.sus.d;

import android.content.Context;
import android.os.Handler;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
import com.lenovo.lps.sus.control.v;
import java.io.File;
/* loaded from: classes.dex */
public class c {
    private v a = null;
    private Context b = null;
    private f c = null;
    private long d = 0;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private long h = 0;
    private long i = 0;
    private Handler j = new d(this);

    private void a(Context context, int i, String str, File file, long j, String str2) {
        if (this.c != null) {
            this.c.a((a) null);
            this.c.a();
            this.c = null;
        }
        this.h = System.currentTimeMillis();
        this.i = this.h;
        this.e = 0;
        this.f = 0;
        this.g = 0;
        this.c = new f(context, i, "userAgent", str, j, file, str2, new e(this));
        this.c.start();
    }

    public void a() {
        b.a(a.b, "CustomDownloadManager finish");
        if (this.c != null) {
            this.c.a((a) null);
            this.c.a();
            this.c = null;
        }
    }

    public boolean a(int i, int i2, Context context, v vVar, String str, String str2, String str3, long j) {
        this.b = context;
        this.a = vVar;
        if (this.b == null || this.a == null || str == null || str2 == null || str2.length() == 0 || str3 == null || str3.length() == 0 || 0 >= j) {
            return false;
        }
        File file = new File(str);
        if (file.exists()) {
            a(this.b, i, str3, file, j, str2);
            return true;
        }
        b.b(a.b, "The folder isn't exit! path=" + a.g());
        return false;
    }
}

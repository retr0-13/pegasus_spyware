package com.lenovo.lps.reaper.sdk.a;
/* loaded from: classes.dex */
public class b {
    private final a[] a = new a[5];
    private int b;

    public b() {
        for (int i = 0; i < 5; i++) {
            this.a[i] = new a();
        }
    }

    public final void a(int i, String str, String str2) {
        boolean z;
        if (i <= 0 || i > 5) {
            com.lenovo.lps.reaper.sdk.e.b.d("CustomParameter", String.format("index of custom parameter should greater than %s and less than %s.", 0, 6));
            z = false;
        } else if (str == null || str.length() == 0) {
            com.lenovo.lps.reaper.sdk.e.b.d("CustomParameter", "name of custom parameter should not be null or empty.");
            z = false;
        } else if (str2 == null || str2.length() == 0) {
            com.lenovo.lps.reaper.sdk.e.b.d("CustomParameter", "value of custom parameter should not be null or empty.");
            z = false;
        } else {
            z = true;
        }
        if (z) {
            synchronized (this) {
                StringBuilder sb = new StringBuilder(256);
                String str3 = sb.append(str).append(str2).length() > 256 ? sb.substring(str.length(), 256).toString() : str2;
                if (!this.a[i - 1].b()) {
                    this.b++;
                }
                this.a[i - 1].a(i);
                this.a[i - 1].a(str);
                this.a[i - 1].b(str3);
            }
        }
    }

    public final synchronized a[] a() {
        a[] aVarArr;
        synchronized (this) {
            aVarArr = new a[this.b];
            a[] aVarArr2 = this.a;
            int i = 0;
            for (a aVar : aVarArr2) {
                if (aVar.b()) {
                    i++;
                    aVarArr[i] = aVar;
                }
            }
        }
        return aVarArr;
    }

    public final synchronized void b() {
        synchronized (this) {
            for (int i = 0; i < this.a.length; i++) {
                try {
                    this.a[i].a();
                } catch (Exception e) {
                    com.lenovo.lps.reaper.sdk.e.b.d("CustomParameterManager", "ClearCustomParameter. " + e.getMessage());
                }
            }
            this.b = 0;
        }
    }
}

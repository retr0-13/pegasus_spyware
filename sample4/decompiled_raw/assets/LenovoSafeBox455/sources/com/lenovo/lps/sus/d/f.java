package com.lenovo.lps.sus.d;

import android.content.Context;
import com.lenovo.lps.sus.c.a;
import java.io.File;
/* loaded from: classes.dex */
public class f extends Thread {
    private Context a;
    private boolean b = false;
    private a c;
    private String d;
    private String e;
    private long f;
    private File g;
    private String h;
    private String i;
    private int j;

    public f(Context context, int i, String str, String str2, long j, File file, String str3, a aVar) {
        this.d = null;
        this.e = null;
        this.f = 0;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = 0;
        this.a = context;
        this.c = aVar;
        this.j = (i < 0 || i > 2) ? 0 : i;
        this.d = str;
        if (this.d == null) {
            this.d = a.H;
        }
        this.e = str2;
        this.f = j;
        this.g = file;
        this.h = str3;
        this.i = String.valueOf(this.g.toString()) + "/" + this.h;
    }

    private void a(String str) {
        if (this.c != null) {
            this.c.a(str);
        }
    }

    private void a(boolean z) {
        if (this.c != null) {
            this.c.a(z);
        }
    }

    public void a() {
        this.b = true;
    }

    public void a(a aVar) {
        this.c = aVar;
    }

    public void b() {
        this.b = true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(19:9|(3:56|(1:62)|63)(2:13|(2:19|(2:21|271)(2:22|(1:26)(1:55))))|27|(3:257|28|226)|(2:34|(17:36|37|(11:238|39|40|252|41|(1:43)(6:67|(2:259|70)|253|71|72|(1:74)(1:(6:107|(1:218)(3:109|(2:111|(1:113))|114)|234|115|116|(3:255|117|(3:261|119|120)(4:222|139|140|(4:260|(1:157)(1:145)|146|(1:148)(1:217))(6:(2:220|161)|162|163|164|(3:262|166|(4:264|170|171|269))(1:266)|265))))(3:103|(1:105)|106)))|(1:45)|(2:242|47)|(2:248|49)|50|(5:187|224|188|189|273)(2:54|272))|66|252|41|(0)(0)|(0)|(0)|(0)|50|(1:52)|187|224|188|189|273))|219|252|41|(0)(0)|(0)|(0)|(0)|50|(0)|187|224|188|189|273) */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x058c, code lost:
        r2 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x058d, code lost:
        com.lenovo.lps.sus.c.b.c(com.lenovo.lps.sus.c.a.b, "exception when sleeping the thread : " + r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x05d4, code lost:
        r4 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x05d5, code lost:
        r7 = null;
        r5 = null;
        r2 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x05f1, code lost:
        r4 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x05f2, code lost:
        r7 = r9;
        r6 = null;
        r5 = null;
        r2 = r5;
     */
    /* JADX WARN: Removed duplicated region for block: B:132:0x03fa  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0541  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0404 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x054b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:240:0x02a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:242:0x010e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:244:0x0546 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x03ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0113 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:250:0x02a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x016d A[Catch: FileNotFoundException -> 0x05f1, RuntimeException -> 0x05d4, all -> 0x05ad, TRY_ENTER, TRY_LEAVE, TryCatch #6 {all -> 0x05ad, blocks: (B:39:0x00d6, B:41:0x00e0, B:65:0x014f, B:67:0x016d), top: B:226:0x00a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x029b  */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() {
        /*
            Method dump skipped, instructions count: 1549
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.lps.sus.d.f.run():void");
    }
}

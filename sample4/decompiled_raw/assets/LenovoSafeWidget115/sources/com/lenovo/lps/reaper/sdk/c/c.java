package com.lenovo.lps.reaper.sdk.c;

import android.util.Log;
import android.util.Xml;
import com.lenovo.lps.reaper.sdk.b.a;
import com.lenovo.lps.reaper.sdk.e.b;
import java.util.List;
/* loaded from: classes.dex */
public final class c implements Runnable {
    private final a a;

    public c(a aVar) {
        this.a = aVar;
    }

    private boolean a(String str) {
        d dVar = new d(this);
        try {
            Xml.parse(str, dVar);
            b.b("ReaperServerAddressQueryTask", String.valueOf(dVar.b()));
            b.b("ReaperServerAddressQueryTask", dVar.a().toString());
            dVar.b();
            List a = dVar.a();
            if (a != null) {
                if (a.size() == 1) {
                    a aVar = this.a;
                    a.b((String) a.get(0));
                    this.a.c((String) a.get(0));
                    return true;
                } else if (a.size() > 1) {
                    int size = (int) (((double) a.size()) * Math.random());
                    a aVar2 = this.a;
                    a.b((String) a.get(size));
                    this.a.c((String) a.get(size));
                    return true;
                } else {
                    b.d("ReaperServerAddressQueryTask", "don't get reaper server url from lds.");
                }
            }
            return false;
        } catch (Exception e) {
            Log.e("ReaperServerAddressQueryTask", "processResponseResult fail. " + e.getMessage());
            return false;
        }
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v38 */
    /* JADX WARN: Type inference failed for: r0v8 */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            Method dump skipped, instructions count: 321
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.lps.reaper.sdk.c.c.run():void");
    }
}

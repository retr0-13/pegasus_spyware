package com.lenovo.lps.reaper.sdk;

import android.util.Log;
import com.lenovo.lps.reaper.sdk.c.a;
import com.lenovo.lps.reaper.sdk.d.g;
import com.lenovo.lps.reaper.sdk.e.b;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class c implements Runnable {
    private /* synthetic */ AnalyticsTracker a;
    private final /* synthetic */ com.lenovo.lps.reaper.sdk.a.c b;
    private final /* synthetic */ String c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(AnalyticsTracker analyticsTracker, com.lenovo.lps.reaper.sdk.a.c cVar, String str) {
        this.a = analyticsTracker;
        this.b = cVar;
        this.c = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (this.a.c.r()) {
                b.b("AnalyticsTracker", "add configuration update task.");
                new a(this.a.c).run();
            }
            if (g.a()) {
                this.a.e.a(this.b);
                if ("__INITIAL__".equals(this.c)) {
                    this.a.forceDispatch();
                } else {
                    this.a.dispatch();
                }
            }
        } catch (Exception e) {
            Log.e("AnalyticsTracker", "some error occurd in trackEvent.addTask" + e.getMessage());
        }
    }
}

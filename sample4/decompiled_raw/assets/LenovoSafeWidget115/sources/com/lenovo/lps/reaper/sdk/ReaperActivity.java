package com.lenovo.lps.reaper.sdk;

import android.app.Activity;
import android.os.Bundle;
import com.lenovo.lps.reaper.sdk.e.h;
/* loaded from: classes.dex */
public class ReaperActivity extends Activity {
    private h a = h.a();
    private AnalyticsTracker b = AnalyticsTracker.getInstance();

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.a.c() == null) {
            this.b.initialize(this);
            this.a.a(this);
        }
        this.a.b(this);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        this.a.c(this);
        if (equals(this.a.c())) {
            this.a.a((Activity) null);
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        this.b.trackPause(this);
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        this.b.trackResume(this);
    }
}

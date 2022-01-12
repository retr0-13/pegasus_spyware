package com.lenovo.lps.reaper.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.lenovo.lps.reaper.sdk.c.f;
import com.lenovo.lps.reaper.sdk.e.g;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class a extends BroadcastReceiver {
    private /* synthetic */ AnalyticsTracker a;

    public a(AnalyticsTracker analyticsTracker) {
        this.a = analyticsTracker;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        g.a(context);
        f.a().a(new b(this));
    }
}

package com.lenovo.lps.reaper.sdk.e;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class f extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (3 == intent.getIntExtra("wifi_state", -1)) {
            boolean unused = e.d();
            context.unregisterReceiver(this);
        }
    }
}

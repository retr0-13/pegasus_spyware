package com.lenovo.lps.reaper.sdk;

import android.content.pm.PackageInfo;
import android.util.Log;
/* loaded from: classes.dex */
final class d extends Thread {
    private /* synthetic */ AnalyticsTracker a;
    private final /* synthetic */ boolean b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(AnalyticsTracker analyticsTracker, boolean z) {
        this.a = analyticsTracker;
        this.b = z;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        boolean z = true;
        for (PackageInfo packageInfo : this.a.j.getPackageManager().getInstalledPackages(4096)) {
            try {
                String str = this.a.combinePermissions(packageInfo);
                if ((packageInfo.applicationInfo.flags & 128) != 0) {
                    z = false;
                } else if ((packageInfo.applicationInfo.flags & 1) == 0) {
                    z = false;
                }
                if (z && this.b) {
                    this.a.setParam(1, "appVersionCode", String.valueOf(packageInfo.versionCode));
                    this.a.trackEvent("__APPINFO__", packageInfo.packageName, str, 0);
                } else if (!z) {
                    this.a.setParam(1, "appVersionCode", String.valueOf(packageInfo.versionCode));
                    this.a.trackEvent("__APPINFO__", packageInfo.packageName, str, 1);
                }
            } catch (Exception e) {
                Log.e("AnalyticsTracker", "TrackApp is Error. " + e.getMessage());
            }
        }
    }
}

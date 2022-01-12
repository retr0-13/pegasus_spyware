package com.lenovo.lps.reaper.sdk;

import java.lang.Thread;
/* loaded from: classes.dex */
final class e implements Thread.UncaughtExceptionHandler {
    private final /* synthetic */ Thread.UncaughtExceptionHandler a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(AnalyticsTracker analyticsTracker, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.a = uncaughtExceptionHandler;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread thread, Throwable th) {
        AnalyticsTracker.access$5(AnalyticsTracker.a, th, 0);
        this.a.uncaughtException(thread, th);
    }
}

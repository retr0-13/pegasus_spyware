package com.lenovo.lps.reaper.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.util.Log;
import com.lenovo.lps.reaper.sdk.a.c;
import com.lenovo.lps.reaper.sdk.b.a;
import com.lenovo.lps.reaper.sdk.c.e;
import com.lenovo.lps.reaper.sdk.c.f;
import com.lenovo.lps.reaper.sdk.d.h;
import com.lenovo.lps.reaper.sdk.e.b;
import com.lenovo.lps.reaper.sdk.e.d;
import java.net.URL;
/* loaded from: classes.dex */
public final class AnalyticsTracker {
    protected static AnalyticsTracker a;
    private static /* synthetic */ int[] l;
    private a c;
    private e d;
    private com.lenovo.lps.reaper.sdk.d.a e;
    private String i;
    private Context j;
    private boolean f = true;
    private String g = "1.0";
    private int h = 1;
    private BroadcastReceiver k = new a(this);
    private final f b = f.a();

    static /* synthetic */ int[] $SWITCH_TABLE$com$lenovo$lps$reaper$sdk$util$Constants$DispatchMode() {
        int[] iArr = l;
        if (iArr == null) {
            iArr = new int[d.values().length];
            try {
                iArr[d.FORCE_DISPATCH.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[d.NORMAL_DISPATCH.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            l = iArr;
        }
        return iArr;
    }

    private AnalyticsTracker() {
    }

    public static /* synthetic */ void access$5(AnalyticsTracker analyticsTracker, Throwable th, int i) {
        analyticsTracker.trackThrowable(th, i);
    }

    private void clearCustomParameter() {
        if (this.e != null) {
            this.e.b();
        }
    }

    public String combinePermissions(PackageInfo packageInfo) {
        if (packageInfo.requestedPermissions == null) {
            return null;
        }
        String[] strArr = packageInfo.requestedPermissions;
        StringBuilder sb = new StringBuilder(400);
        int length = 20 < strArr.length ? 20 : strArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(strArr[i]).append(",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public synchronized void forceDispatch() {
        try {
            if (h.a(d.FORCE_DISPATCH)) {
                sendEvents(d.FORCE_DISPATCH);
            } else {
                b.b("AnalyticsTracker", "not ready for reporting.");
            }
        } catch (Exception e) {
            Log.e("AnalyticsTracker", "some error occured when dispatch. " + e.getMessage());
        }
    }

    public static synchronized AnalyticsTracker getInstance() {
        AnalyticsTracker analyticsTracker;
        synchronized (AnalyticsTracker.class) {
            if (a == null) {
                a = new AnalyticsTracker();
            }
            analyticsTracker = a;
        }
        return analyticsTracker;
    }

    private boolean isTrackerInitialized() {
        if (com.lenovo.lps.reaper.sdk.e.h.a().b()) {
            return true;
        }
        Log.e("AnalyticsTracker", "please Call initialize() of AnalyticsTracker Once, before use it.");
        return false;
    }

    private void postInitialize() {
        this.d = this.b.c();
        this.c = this.b.d();
        this.e = this.b.e();
    }

    private void sendEvents(d dVar) {
        c[] a2;
        switch ($SWITCH_TABLE$com$lenovo$lps$reaper$sdk$util$Constants$DispatchMode()[dVar.ordinal()]) {
            case 1:
                if (((long) this.e.d()) >= h.a()) {
                    a2 = this.e.a();
                    break;
                } else {
                    b.b("AnalyticsTracker", "current number of events is not enough.");
                    return;
                }
            case 2:
                a2 = this.e.a(200);
                break;
            default:
                return;
        }
        c[] a3 = this.d.a(a2);
        this.e.a(a3);
        if (a3 == null || a3.length == 0) {
            b.b("AnalyticsTracker", "no reported event.");
        }
    }

    private void setUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new e(this, Thread.getDefaultUncaughtExceptionHandler()));
    }

    private void trackAllThrowable(Throwable th, int i) {
        if (th == null || !(i == 0 || i == 1)) {
            Log.e("AnalyticsTracker", "action of throwable event should not be null or empty. OR flag is not appropriate");
            clearCustomParameter();
            return;
        }
        String name = th.getClass().getName();
        String message = th.getMessage();
        if (!(name == null || name.length() == 0)) {
            StringBuilder sb = new StringBuilder(8192);
            sb.append(message).append("\n");
            for (Throwable th2 = th; th2 != null; th2 = th2.getCause()) {
                sb.append("Caused by:");
                StackTraceElement[] stackTrace = th2.getStackTrace();
                for (StackTraceElement stackTraceElement : stackTrace) {
                    sb.append(stackTraceElement.getClassName()).append(".").append(stackTraceElement.getMethodName()).append("() ").append(stackTraceElement.getFileName()).append(":").append(stackTraceElement.getLineNumber()).append("\n");
                }
            }
            trackEvent("__THROWABLE__", name, sb.length() >= 7600 ? sb.toString().substring(0, 7600) : sb.toString(), i);
        }
    }

    private void trackInitial() {
        trackEvent("__INITIAL__", "initial", "", 0);
    }

    public void trackThrowable(Throwable th, int i) {
        if (th == null || !(i == 0 || i == 1)) {
            Log.e("AnalyticsTracker", "action of throwable event should not be null or empty. OR flag is not appropriate");
            clearCustomParameter();
            return;
        }
        Throwable th2 = th;
        while (th2.getCause() != null) {
            th2 = th2.getCause();
        }
        String name = th2.getClass().getName();
        String message = th2.getMessage();
        if (!(name == null || name.length() == 0)) {
            StringBuilder sb = new StringBuilder(4096);
            sb.append(message).append("\n");
            StackTraceElement[] stackTrace = th2.getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(stackTraceElement.getClassName()).append(".").append(stackTraceElement.getMethodName()).append("() ").append(stackTraceElement.getFileName()).append(":").append(stackTraceElement.getLineNumber()).append("\n");
            }
            trackEvent("__THROWABLE__", name, sb.length() >= 3600 ? sb.toString().substring(0, 3600) : sb.toString(), i);
        }
    }

    public final int countEvent() {
        if (com.lenovo.lps.reaper.sdk.e.h.a().b()) {
            return this.e.d();
        }
        Log.e("AnalyticsTracker", "please Call initialize() of AnalyticsTracker Once, before use it.");
        return 0;
    }

    public final void disableReport() {
        if (this.b.a) {
        }
        this.f = false;
    }

    public final synchronized void dispatch() {
        try {
            if (isTrackerInitialized()) {
                if (h.a(d.NORMAL_DISPATCH)) {
                    sendEvents(d.NORMAL_DISPATCH);
                } else {
                    b.b("AnalyticsTracker", "not ready for reporting.");
                }
            }
        } catch (Exception e) {
            Log.e("AnalyticsTracker", "some error occured when dispatch. " + e.getMessage());
        }
    }

    public final String getReaperServerUrl() {
        return this.i;
    }

    public final int getVersionCode() {
        return this.h;
    }

    public final String getVersionName() {
        return this.g;
    }

    public final synchronized void initialize(Context context) {
        try {
            if (this.f) {
                b.a("AnalyticsTracker", "AnalyticsTracker is Initializing.................");
                this.j = context.getApplicationContext();
                this.b.a(this.j);
                this.b.a(this.k);
                this.b.f();
                this.b.b();
                if (!com.lenovo.lps.reaper.sdk.e.h.a().b()) {
                    setUncaughtExceptionHandler();
                }
                postInitialize();
                com.lenovo.lps.reaper.sdk.e.h.a().h();
                com.lenovo.lps.reaper.sdk.e.h.a().a(true);
                trackInitial();
            }
        } catch (Exception e) {
            Log.e("AnalyticsTracker", "some error occured when initital AnalyticsTracker. " + e.getMessage());
            disableReport();
        }
    }

    public final synchronized void initialize(Context context, String str, int i) {
        try {
            if (this.f) {
                if (str == null || str.length() == 0) {
                    Log.e("AnalyticsTracker", "application token should not be null or empty.");
                } else {
                    b.a("AnalyticsTracker", "AnalyticsTracker is Initializing.................");
                    this.j = context.getApplicationContext();
                    this.b.a(this.j);
                    this.b.a(this.k);
                    this.b.f();
                    this.b.a(str, i);
                    if (!com.lenovo.lps.reaper.sdk.e.h.a().b()) {
                        setUncaughtExceptionHandler();
                    }
                    postInitialize();
                    com.lenovo.lps.reaper.sdk.e.h.a().h();
                    com.lenovo.lps.reaper.sdk.e.h.a().a(true);
                    trackInitial();
                }
            }
        } catch (Exception e) {
            Log.e("AnalyticsTracker", "some error occured when initital AnalyticsTracker. " + e.getMessage());
            disableReport();
        }
    }

    public final boolean needReport(String str, String str2) {
        try {
            if (this.f && isTrackerInitialized()) {
                return this.c.a(str, str2);
            }
            return false;
        } catch (Exception e) {
            b.d("AnalyticsTracker", e.getClass() + " " + e.getMessage());
            Log.e("AnalyticsTracker", "some error occured in needReport.");
            return false;
        }
    }

    public final void setParam(int i, String str, String str2) {
        try {
            if (this.f && isTrackerInitialized()) {
                this.e.a(i, str, str2);
            }
        } catch (Exception e) {
            Log.e("AnalyticsTracker", "error in setParam. " + e.getMessage());
        }
    }

    public final void setReaperServerUrl(String str) {
        if (str != null) {
            try {
                if (str.length() != 0) {
                    new URL(str);
                    this.i = str;
                    if (this.c != null) {
                        a aVar = this.c;
                        a.b(str);
                    }
                }
            } catch (Exception e) {
                Log.e("AnalyticsTracker", "some error occured when setReaperServerUrl. " + e.getMessage());
                return;
            }
        }
        Log.e("AnalyticsTracker", "reaper server url should not be null or empty.");
    }

    public final void setVersionCode(int i) {
        this.h = i;
    }

    public final void setVersionName(String str) {
        if (str == null || str.length() == 0) {
            Log.e("AnalyticsTracker", "version name of application should not be null or empty.");
        } else {
            this.g = str;
        }
    }

    public final void shutdown() {
    }

    public final void trackActivity(String str) {
        if (str == null || str.length() == 0) {
            Log.e("AnalyticsTracker", "action of activity event should not be null or empty.");
        } else if (str.startsWith("/")) {
            trackEvent("__PAGEVIEW__", str, null, 1);
        } else {
            trackEvent("__PAGEVIEW__", "/" + str, null, 1);
        }
    }

    public final void trackEvent(String str, String str2, String str3, int i) {
        if (isTrackerInitialized()) {
            if (str == null || str.length() == 0) {
                Log.e("AnalyticsTracker", "categoty of event should not be null or empty.");
                clearCustomParameter();
            } else if (str2 == null || str2.length() == 0) {
                Log.e("AnalyticsTracker", "action of event should not be null or empty.");
                clearCustomParameter();
            } else if (!needReport(str, str2)) {
                b.b("AnalyticsTracker", "the Event is NOT need send.");
                clearCustomParameter();
            } else {
                c cVar = new c(this.c.h(), str, str2, str3, i);
                cVar.a(this.e.e().a());
                clearCustomParameter();
                f.a().a(new c(this, cVar, str));
            }
        }
    }

    public final void trackFeedback(String str) {
        try {
            if (isTrackerInitialized()) {
                if (str == null || str.length() == 0) {
                    clearCustomParameter();
                } else {
                    trackEvent("__FEEDBACK__", str, this.c.f(), 1);
                }
            }
        } catch (Exception e) {
            Log.e("AnalyticsTracker", "some error occured in trackFeedback. " + e.getMessage());
        }
    }

    public final void trackInstalledApps(boolean z) {
        if (this.j == null) {
            b.d("AnalyticsTracker", "Context is Null in trackInstalledApps");
        } else {
            new d(this, z).start();
        }
    }

    public final void trackPause(Context context) {
        try {
            if (isTrackerInitialized()) {
                if (context == null) {
                    Log.e("AnalyticsTracker", "context of trackResume/Pause should not be null.");
                    return;
                }
                long currentTimeMillis = System.currentTimeMillis();
                com.lenovo.lps.reaper.sdk.e.h a2 = com.lenovo.lps.reaper.sdk.e.h.a();
                String str = "/" + context.getClass().getSimpleName();
                if (str.equals(a2.d())) {
                    setParam(1, str, String.valueOf(currentTimeMillis - a2.e()));
                }
                setParam(2, "PAGE_QUEUE_NO", String.valueOf(a2.f()));
                trackEvent("__PAGEVIEW__", str, null, 3);
            }
        } catch (Exception e) {
            Log.e("AnalyticsTracker", "some error occured in trackPause.");
        }
    }

    public final void trackResume(Context context) {
        try {
            if (isTrackerInitialized()) {
                if (context == null) {
                    Log.e("AnalyticsTracker", "context of trackResume/Pause should not be null.");
                } else {
                    com.lenovo.lps.reaper.sdk.e.h a2 = com.lenovo.lps.reaper.sdk.e.h.a();
                    String str = "/" + context.getClass().getSimpleName();
                    a2.a(str);
                    a2.g();
                    setParam(2, "PAGE_QUEUE_NO", String.valueOf(a2.f()));
                    trackEvent("__PAGEVIEW__", str, null, 2);
                }
            }
        } catch (Exception e) {
            Log.e("AnalyticsTracker", "some error occured in trackResume.");
        }
    }

    public final void trackThrowable(Throwable th) {
        try {
            trackThrowable(th, 1);
        } catch (Exception e) {
            Log.e("AnalyticsTracker", "some error occured in trackThrowable. " + e.getMessage());
        }
    }
}

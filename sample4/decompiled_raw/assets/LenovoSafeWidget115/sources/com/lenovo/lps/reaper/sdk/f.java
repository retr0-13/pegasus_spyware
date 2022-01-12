package com.lenovo.lps.reaper.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import com.lenovo.lps.reaper.sdk.b.a;
import com.lenovo.lps.reaper.sdk.c.b;
import com.lenovo.lps.reaper.sdk.c.e;
import com.lenovo.lps.reaper.sdk.d.c;
import com.lenovo.lps.reaper.sdk.d.d;
import com.lenovo.lps.reaper.sdk.e.g;
import java.util.Map;
/* loaded from: classes.dex */
public final class f {
    private static f g = new f();
    protected boolean a;
    protected a b;
    private e c;
    private com.lenovo.lps.reaper.sdk.d.a d;
    private Context e;
    private d f;

    public static f a() {
        return g;
    }

    private void g() {
        this.c = new e();
        this.e.getSystemService("connectivity");
        b bVar = new b();
        bVar.a(this.b);
        this.c.a(bVar);
        this.d = new com.lenovo.lps.reaper.sdk.d.a();
        com.lenovo.lps.reaper.sdk.d.b bVar2 = new com.lenovo.lps.reaper.sdk.d.b();
        c cVar = new c("lenovo_reaper.db5", this.e);
        this.f = new d(1000);
        bVar2.a(this.f);
        bVar2.a(cVar);
        this.d.a(bVar2);
        this.d.c();
        com.lenovo.lps.reaper.sdk.c.f.a().a(new g(this));
    }

    private a h() {
        return new a(this.e);
    }

    public final void a(BroadcastReceiver broadcastReceiver) {
        com.lenovo.lps.reaper.sdk.e.b.a("AnalyticsTrackerBuilder", "Setting On Network Listener...");
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        g.a(this.e);
        this.e.registerReceiver(broadcastReceiver, intentFilter);
    }

    public final void a(Context context) {
        this.e = context;
    }

    public final void a(String str, int i) {
        if (this.a) {
            this.d.c();
        } else {
            this.a = true;
            this.b = h();
            this.b.a(str);
            g();
        }
        com.lenovo.lps.reaper.sdk.e.b.b("AnalyticsTrackerBuilder", "AnalyticsTrackerBuilder initialized");
    }

    public final void b() {
        if (this.a) {
            com.lenovo.lps.reaper.sdk.e.b.a("AnalyticsTrackerBuilder", "reinitialize");
            this.d.c();
        } else {
            com.lenovo.lps.reaper.sdk.e.b.a("AnalyticsTrackerBuilder", "internalInitialize");
            this.a = true;
            this.b = h();
            this.b.a();
            g();
        }
        com.lenovo.lps.reaper.sdk.e.b.b("AnalyticsTrackerBuilder", "AnalyticsTrackerBuilder initialized");
    }

    public final e c() {
        return this.c;
    }

    public final a d() {
        return this.b;
    }

    public final com.lenovo.lps.reaper.sdk.d.a e() {
        return this.d;
    }

    public final void f() {
        try {
            com.lenovo.lps.reaper.sdk.e.b.a("AnalyticsTrackerBuilder", "Reading Configuration From Preferences...");
            for (Map.Entry<String, ?> entry : this.e.getSharedPreferences("reaper", 0).getAll().entrySet()) {
                com.lenovo.lps.reaper.sdk.d.e.a(entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
            }
            com.lenovo.lps.reaper.sdk.d.e.a(this.e.getSharedPreferences("ConfigUpdate", 0).getLong("ConfigUpdateTimestamp", 0));
        } catch (Exception e) {
            Log.e("AnalyticsTrackerBuilder", "read config from preferences error. " + e.getMessage());
        }
    }
}

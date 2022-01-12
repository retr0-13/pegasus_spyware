package com.lenovo.lps.sus.control;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.CompoundButton;
import android.widget.RemoteViews;
import com.lenovo.lps.sus.b.d;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
import com.lenovo.lps.sus.d.c;
import java.io.File;
import java.net.URLEncoder;
/* loaded from: classes.dex */
public class v implements CompoundButton.OnCheckedChangeListener {
    private static c e = null;
    private Context a;
    private Handler b;
    private Notification c = null;
    private NotificationManager d = null;
    private String f;

    public v(Context context, Handler handler) {
        this.a = null;
        this.b = null;
        this.a = context;
        this.b = handler;
        b.a(a.b, "SUSUIHandler create!");
        SUSReceiver.a(this.b);
    }

    private void a(Context context, String str, String str2, String str3, String str4) {
        int a = a.a(this.a, "drawable", "sus_downloadicon");
        if (a == 0) {
            a = 17301633;
        }
        this.c = new Notification(a, str, 1);
        this.c.flags |= 2;
        RemoteViews remoteViews = new RemoteViews(this.a.getPackageName(), a.a(this.a, "layout", "sus_download_notification"));
        remoteViews.setProgressBar(a.c(this.a, "SUS_progress_bar"), 100, 0, false);
        remoteViews.setTextViewText(a.c(this.a, "SUS_progress_text"), "0%");
        remoteViews.setTextViewText(a.c(this.a, "SUS_title"), str2);
        remoteViews.setImageViewResource(a.c(this.a, "SUS_appIcon"), a);
        this.c.contentView = remoteViews;
        this.c.contentIntent = PendingIntent.getActivity(this.a, 0, new Intent(), 134217728);
        this.d = (NotificationManager) this.a.getSystemService("notification");
    }

    public static void a(c cVar) {
        e = cVar;
    }

    private boolean d() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.a.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return false;
        }
        return activeNetworkInfo.isConnectedOrConnecting();
    }

    public String a(v vVar) {
        return vVar.f;
    }

    public String a(String str, String str2, int i) {
        return str + "_" + i + "_" + str2 + ".apk";
    }

    public void a() {
        b.a(a.b, "susuihandler finish entry");
        if (e != null) {
            e.a();
            e = null;
        }
        if (this.d != null) {
            b();
        }
        this.a = null;
        this.b = null;
    }

    public void a(int i) {
        if (this.c != null && this.c.contentView != null && this.d != null) {
            this.c.contentView.setProgressBar(a.c(this.a, "SUS_progress_bar"), 100, i, false);
            this.c.contentView.setTextViewText(a.c(this.a, "SUS_progress_text"), String.valueOf(String.valueOf(i)) + "%");
            this.d.notify(0, this.c);
        }
    }

    public void a(int i, String str, String str2, String str3, long j, u uVar) {
        try {
            if (new File(str2) != null) {
                b.a(a.b, "downloadMode=" + uVar);
                a(this.a, "", a.b(this.a, "SUS_DOWNLOAD_TITLE"), "", str3);
                String a = com.lenovo.lps.sus.a.a.a.c.a(str);
                e = new c();
                if (e != null && !e.a(i, 1, this.a, this, str2, str3, a, j)) {
                    r.b();
                }
            }
        } catch (Exception e2) {
            b.b(a.b, e2.getMessage());
            r.b();
        }
    }

    public void a(com.lenovo.lps.sus.b.a aVar, d dVar, boolean z) {
        String b;
        String encode;
        if (this.a == null || aVar == null) {
            b.b(a.b, "null == myApplicationContext || null == susVerInfo!!!");
        }
        b.a(a.b, "showUpdatePromptActivity entry");
        String g = aVar.g();
        aVar.c();
        String a = aVar.a();
        r0 = aVar.d();
        String b2 = aVar.b();
        if (a == null || a.length() > 0) {
        }
        if (b2 == null || b2.length() <= 0) {
        }
        b.a(a.b, "SUSUIHandler.showUpdatePromptActivity(), start UpdateActivity");
        Intent intent = new Intent();
        intent.setClass(this.a, SUSPromptActivity.class);
        intent.putExtra("actiontype", "UPDATEPROMPT");
        intent.putExtra("versioninfo", String.valueOf(a.b(this.a, "SUS_UPDATEVERPROMPT_VERNAME")) + b2 + "\n" + a.b(this.a, "SUS_UPDATEVERPROMPT_VERSIZE") + a.b(aVar.f().longValue()) + "\n" + a.b(this.a, "SUS_UPDATESIZEPROMPT_WLAN"));
        intent.putExtra("showUserSettingsEnable", z);
        intent.putExtra("usersettings", dVar.ordinal());
        if (g != null && g.length() > 0 && (b = a.b(this.a, "SUS_UPDATEDESC")) != null && b.length() > 0 && (encode = URLEncoder.encode(String.valueOf(b) + "\n" + g)) != null && encode.length() > 0) {
            intent.putExtra("UPDATE_DESC", encode);
        }
        intent.setFlags(268435456);
        SUSPromptActivity.a(this.b);
        this.a.startActivity(intent);
    }

    public void a(boolean z) {
        if (this.b != null) {
            Message message = new Message();
            message.what = com.lenovo.lps.sus.b.b.a(com.lenovo.lps.sus.b.b.SUS_DOWNLOAD_EXCEPTION_EVENT);
            message.obj = Boolean.valueOf(z);
            this.b.sendMessage(message);
        }
    }

    public void b() {
        if (this.d != null) {
            this.d.cancel(0);
        }
        this.c = null;
    }

    public String c() {
        File file = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
            file = Environment.getExternalStorageDirectory();
        }
        return file.toString();
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
    }
}

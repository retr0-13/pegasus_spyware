package com.lenovo.lps.reaper.sdk.b;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.lenovo.lps.reaper.sdk.AnalyticsTracker;
import com.lenovo.lps.reaper.sdk.d.i;
import com.lenovo.lps.reaper.sdk.e.b;
import com.lenovo.lps.reaper.sdk.e.e;
import com.lenovo.lps.reaper.sdk.e.g;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Locale;
/* loaded from: classes.dex */
public final class a {
    private static String l;
    private static String m;
    private static String n;
    private static String o;
    private static final Locale p = Locale.getDefault();
    private final Context a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private int h;
    private String i;
    private String j;
    private String k;
    private boolean q;

    public a(Context context) {
        this.a = context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.a.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        this.b = String.valueOf(displayMetrics.widthPixels) + "x" + String.valueOf(displayMetrics.heightPixels);
        b.b("Configuration", "displayScreen: " + this.b);
    }

    private static String b(String str, String str2) {
        return new StringBuilder(64).append("REAPER.").append(str).append(".").append(str2).toString();
    }

    public static void b(String str) {
        String substring = str.endsWith("/") ? str.substring(0, str.length() - 1) : str;
        try {
            URL url = new URL(substring);
            l = substring + "/reaper/server/post";
            m = substring + "/reaper/server/report";
            n = substring + "/reaper/server/config";
            o = url.getHost();
            if (url.getPort() != -1) {
                url.getPort();
            }
            if (b.a()) {
                b.b("Configuration", "postUrl: " + l.toString());
                b.b("Configuration", "reportUrl: " + m.toString());
                b.b("Configuration", "configUrl: " + n.toString());
            }
        } catch (MalformedURLException e) {
            Log.e("Configuration", "initReportAndConfigurationUrl. " + e.getMessage());
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [boolean] */
    /* JADX WARN: Unknown variable types count: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String d(java.lang.String r8) {
        /*
            r6 = 0
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "/etc/version.conf"
            r0.<init>(r1)
            boolean r0 = r0.exists()
            if (r0 != 0) goto L_0x0017
            java.lang.String r0 = "Configuration"
            java.lang.String r1 = "leos version file not exists!"
            com.lenovo.lps.reaper.sdk.e.b.d(r0, r1)
            r0 = r6
        L_0x0016:
            return r0
        L_0x0017:
            java.io.FileReader r0 = new java.io.FileReader     // Catch: IOException -> 0x0052, all -> 0x0073
            java.lang.String r1 = "/etc/version.conf"
            r0.<init>(r1)     // Catch: IOException -> 0x0052, all -> 0x0073
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch: IOException -> 0x009b, all -> 0x008f
            r2 = 1024(0x400, float:1.435E-42)
            r1.<init>(r0, r2)     // Catch: IOException -> 0x009b, all -> 0x008f
        L_0x0025:
            java.lang.String r2 = r1.readLine()     // Catch: IOException -> 0x009e, all -> 0x0095
            if (r2 != 0) goto L_0x0033
            r0.close()     // Catch: IOException -> 0x008b
        L_0x002e:
            r1.close()     // Catch: Exception -> 0x008d
        L_0x0031:
            r0 = r6
            goto L_0x0016
        L_0x0033:
            int r3 = r2.indexOf(r8)     // Catch: IOException -> 0x009e, all -> 0x0095
            r4 = -1
            if (r3 == r4) goto L_0x0025
            r3 = 44
            int r3 = r2.indexOf(r3)     // Catch: IOException -> 0x009e, all -> 0x0095
            int r3 = r3 + 1
            int r4 = r2.length()     // Catch: IOException -> 0x009e, all -> 0x0095
            java.lang.String r2 = r2.substring(r3, r4)     // Catch: IOException -> 0x009e, all -> 0x0095
            r0.close()     // Catch: IOException -> 0x0081
        L_0x004d:
            r1.close()     // Catch: Exception -> 0x0083
        L_0x0050:
            r0 = r2
            goto L_0x0016
        L_0x0052:
            r0 = move-exception
            r0 = r6
            r1 = r6
        L_0x0055:
            java.lang.String r2 = "Configuration"
            java.lang.String r3 = "IO Exception when getting kernel version of %s"
            r4 = 1
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: all -> 0x0095
            r5 = 0
            r4[r5] = r8     // Catch: all -> 0x0095
            java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch: all -> 0x0095
            android.util.Log.e(r2, r3)     // Catch: all -> 0x0095
            if (r0 == 0) goto L_0x006b
            r0.close()     // Catch: IOException -> 0x0085
        L_0x006b:
            if (r1 == 0) goto L_0x0031
            r1.close()     // Catch: Exception -> 0x0071
            goto L_0x0031
        L_0x0071:
            r0 = move-exception
            goto L_0x0031
        L_0x0073:
            r0 = move-exception
            r1 = r6
            r2 = r6
        L_0x0076:
            if (r1 == 0) goto L_0x007b
            r1.close()     // Catch: IOException -> 0x0087
        L_0x007b:
            if (r2 == 0) goto L_0x0080
            r2.close()     // Catch: Exception -> 0x0089
        L_0x0080:
            throw r0
        L_0x0081:
            r0 = move-exception
            goto L_0x004d
        L_0x0083:
            r0 = move-exception
            goto L_0x0050
        L_0x0085:
            r0 = move-exception
            goto L_0x006b
        L_0x0087:
            r1 = move-exception
            goto L_0x007b
        L_0x0089:
            r1 = move-exception
            goto L_0x0080
        L_0x008b:
            r0 = move-exception
            goto L_0x002e
        L_0x008d:
            r0 = move-exception
            goto L_0x0031
        L_0x008f:
            r1 = move-exception
            r2 = r6
            r7 = r0
            r0 = r1
            r1 = r7
            goto L_0x0076
        L_0x0095:
            r2 = move-exception
            r7 = r2
            r2 = r1
            r1 = r0
            r0 = r7
            goto L_0x0076
        L_0x009b:
            r1 = move-exception
            r1 = r6
            goto L_0x0055
        L_0x009e:
            r2 = move-exception
            goto L_0x0055
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.lps.reaper.sdk.b.a.d(java.lang.String):java.lang.String");
    }

    public static String m() {
        return l;
    }

    public static String n() {
        return m;
    }

    public static String o() {
        return n;
    }

    public static String p() {
        return o;
    }

    private void t() {
        e.a(this.a);
        try {
            TelephonyManager telephonyManager = (TelephonyManager) this.a.getSystemService("phone");
            this.c = telephonyManager.getDeviceId();
            this.g = telephonyManager.getLine1Number();
            if (this.g == null) {
                this.g = "0";
            }
        } catch (Exception e) {
            this.c = "";
            Log.e("Configuration", e.getMessage());
        }
        try {
            this.d = Settings.Secure.getString(this.a.getContentResolver(), "android_id");
            b.b("Configuration", "androidId: " + this.d);
        } catch (Exception e2) {
            this.d = "";
            Log.e("Configuration", "exception when get android id. " + e2.getMessage());
        }
    }

    private void u() {
        if (this.f == null) {
            this.f = AnalyticsTracker.getInstance().getVersionName();
        }
        if (this.h == 0) {
            this.h = AnalyticsTracker.getInstance().getVersionCode();
        }
        String d = d("operating");
        Object[] objArr = new Object[9];
        objArr[0] = this.f;
        objArr[1] = Integer.valueOf(this.h);
        objArr[2] = Build.VERSION.RELEASE;
        objArr[3] = p.getLanguage() == null ? "en" : p.getLanguage().toLowerCase();
        objArr[4] = p.getCountry() == null ? "" : p.getCountry().toLowerCase();
        objArr[5] = Build.MODEL;
        objArr[6] = Build.ID;
        objArr[7] = d == null ? "" : com.lenovo.lps.reaper.sdk.e.a.a(d);
        objArr[8] = Build.MANUFACTURER;
        this.e = String.format("%s/%s (Linux; U; Android %s; %s-%s; %s; Build/%s; %s; %s)", objArr);
        b.b("Configuration", "userAgent = " + this.e);
    }

    public final void a() {
        try {
            PackageManager packageManager = this.a.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.a.getPackageName(), 128);
            PackageInfo packageInfo = packageManager.getPackageInfo(this.a.getPackageName(), 0);
            this.f = packageInfo.versionName;
            this.h = packageInfo.versionCode;
            this.i = applicationInfo.metaData.getString("lenovo:applicationToken");
            this.j = applicationInfo.metaData.getString("lenovo:customReaperServer");
            this.k = applicationInfo.metaData.getString("lenovo:channel");
            this.q = applicationInfo.metaData.getBoolean("lenovo:forceUpdateConfig");
            b.a(applicationInfo.metaData.getBoolean("lenovo:isTestMode"));
            if (!this.i.matches("^[A-Za-z0-9]+$")) {
                Log.e("Configuration", "ApplicationToken should be Number and Character, another char will be Delete.");
                this.i = this.i.replaceAll("[^A-Za-z0-9]", "");
                Log.e("Configuration", "New Token is " + this.i);
            }
            if (this.j != null && this.j.length() > 0) {
                b(this.j);
            }
            if (this.k == null || this.k.length() == 0) {
                this.k = "All";
            } else {
                this.k = com.lenovo.lps.reaper.sdk.e.a.a(this.k);
            }
            if (b.a()) {
                b.b("Configuration", "versionName = " + this.f);
                b.b("Configuration", "versionCode = " + this.h);
                b.b("Configuration", "applicationToken = " + this.i);
                b.b("Configuration", "customReaperServer = " + this.j);
                b.b("Configuration", "channel = " + this.k);
                b.b("Configuration", "isTestMode = " + b.a());
            }
        } catch (Exception e) {
            Log.e("Configuration", "parseApplicationInfo. " + e.getMessage());
        }
        t();
        u();
    }

    public final void a(String str) {
        this.i = str;
        t();
        u();
    }

    public final boolean a(String str, String str2) {
        if (str != null && str2 != null) {
            return com.lenovo.lps.reaper.sdk.d.e.b(b(str, str2), true);
        }
        b.d("Configuration", "wrong parameter.");
        return false;
    }

    public final boolean a(String str, String[] strArr, boolean[] zArr) {
        if (str == null || strArr == null || zArr == null || strArr.length != zArr.length) {
            b.d("Configuration", "wrong parameter.");
            return false;
        }
        SharedPreferences.Editor edit = this.a.getSharedPreferences("reaper", 0).edit();
        int length = zArr.length;
        for (int i = 0; i < length; i++) {
            String b = b(str, strArr[i]);
            edit.putBoolean(b, zArr[i]);
            com.lenovo.lps.reaper.sdk.d.e.a(b, zArr[i]);
        }
        return edit.commit();
    }

    public final void b() {
        this.a.getSharedPreferences("reaper", 0).edit().clear().commit();
        com.lenovo.lps.reaper.sdk.d.e.a();
    }

    public final void c(String str) {
        if (!this.a.getSharedPreferences("ServerUrl", 0).edit().putString("Server", str).commit() || !b.a()) {
            b.d("Configuration", "ServerUrl Save Failed.");
        } else {
            b.c("Configuration", "ServerUrl Has Saved: " + str);
        }
    }

    public final boolean c() {
        if (!"MUNTOAINGVJ8".equals(this.i)) {
            return true;
        }
        String string = Settings.System.getString(this.a.getContentResolver(), "data_collection");
        b.b("Configuration", "dataCollection: " + string);
        return string == null || !"0".equals(string);
    }

    public final String d() {
        return this.b;
    }

    public final String e() {
        return this.c;
    }

    public final String f() {
        return this.g;
    }

    public final String g() {
        return this.d;
    }

    public final String h() {
        return this.i;
    }

    public final String i() {
        return this.j;
    }

    public final String j() {
        return this.k;
    }

    public final String k() {
        return this.e;
    }

    public final String l() {
        return String.format("&sv=%s&ds=%s&aid=%s&dit=%s&di=%s&net=%d", "1.8.6", this.b, this.d, e.b(), e.a(), Integer.valueOf(g.a()));
    }

    public final void q() {
        boolean commit = this.a.getSharedPreferences("ConfigUpdate", 0).edit().putLong("ConfigUpdateTimestamp", System.currentTimeMillis()).commit();
        com.lenovo.lps.reaper.sdk.d.e.a(System.currentTimeMillis());
        if (!commit || !b.a()) {
            b.d("Configuration", "Configuration Timestamp Save Failed.");
        } else {
            b.c("Configuration", "Configuration Timestamp Has Saved: " + new Date(System.currentTimeMillis()).toString());
        }
    }

    public final boolean r() {
        if (this.q) {
            return true;
        }
        long currentTimeMillis = (((System.currentTimeMillis() - com.lenovo.lps.reaper.sdk.d.e.b()) / 1000) / 60) / 60;
        return 0 > currentTimeMillis || currentTimeMillis >= i.a();
    }

    public final String s() {
        return this.a.getSharedPreferences("ServerUrl", 0).getString("Server", null);
    }
}

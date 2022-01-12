package com.lenovo.lps.sus;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.lenovo.lps.sus.b.e;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.control.r;
import com.lenovo.lps.sus.control.y;
import java.io.File;
/* loaded from: classes.dex */
public final class SUS {
    public static void AsyncQueryLatestVersionByPackageName(Context context, String str, int i, String str2) {
        a.b(true);
        r.a(context, y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_CUSTINPUT_QUERYLATESTVER, new e(str, String.valueOf(i), str2, true, true));
    }

    public static void AsyncStartVersionUpdate(Context context) {
        a.b(false);
        r.a(context, y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_USERSETTINGS, (e) null);
    }

    public static void AsyncStartVersionUpdateByAppKey(Context context, String str, int i, String str2) {
        a.b(true);
        r.a(context, y.SUS_UPDATETRANSATION_TYPE_BYAPPKEY_CUSTINPUT_NOUSERSETTINGS, new e(str, String.valueOf(i), str2, true, true));
    }

    public static void AsyncStartVersionUpdateByCustDM2Activity_NoUserSettings(Context context) {
        a.b(true);
        r.a(context, y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_CUSTDM2ACTIVITY__NOUSERSETTINGS, (e) null);
    }

    public static void AsyncStartVersionUpdateByPackageName(Context context, String str, int i, String str2) {
        a.b(true);
        r.a(context, y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_CUSTINPUT_NOUSERSETTINGS, new e(str, String.valueOf(i), str2, true, true));
    }

    public static void AsyncStartVersionUpdate_IgnoreUserSettings(Context context) {
        a.b(true);
        r.a(context, y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_IGNOREUSERSETTINGS, (e) null);
    }

    public static void a(boolean z) {
        r.g(z);
    }

    public static void downloadApp(Context context, String str, String str2, Long l) {
        a.b(true);
        r.a(context, y.SUS_UPDATETRANSATION_TYPE_DOWNLOAD_BYURL, new e(str, str2, String.valueOf(l), true, false));
    }

    public static void finish() {
        r.b();
    }

    public static void installApp(Context context, String str) {
        File file;
        a.b(false);
        if (context != null && str != null) {
            if ((str == null || str.length() > 0) && (file = new File(str)) != null && file.exists()) {
                Intent intent = new Intent("android.intent.action.VIEW");
                String str2 = "file://" + file.getAbsolutePath();
                if (str2 != null) {
                    intent.setDataAndType(Uri.parse(str2), "application/vnd.android.package-archive");
                    context.startActivity(intent);
                }
            }
        }
    }

    public static boolean isVersionUpdateStarted() {
        boolean a = r.a();
        if (a && !a.q()) {
            a.e(false);
        }
        return a;
    }

    public static void setCustomDefNotificationActivityFlag(boolean z) {
        r.f(z);
    }

    public static void setDebugModeFlag(boolean z) {
        r.b(z);
    }

    public static void setDownloadPath(String str, long j, long j2) {
        a.b(true);
        r.a(str, j, j2);
    }

    public static void setInnerDevFlag(boolean z) {
        r.c(z);
    }

    public static void setSDKPromptDisableFlag(boolean z) {
        r.d(z);
    }

    public static void setSUSListener(SUSListener sUSListener) {
        r.a(sUSListener);
    }

    public static void setUpdateDescribeDisableFlag(boolean z) {
        r.e(z);
    }

    public static void setUpdateOnlyWLAN(boolean z) {
        r.a(z);
    }

    public static void testSUSServer(Context context) {
        a.b(false);
        r.a(context, y.SUS_UPDATETRANSATION_TYPE_TESTSUSSERVER, (e) null);
    }
}

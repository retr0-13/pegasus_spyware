package com.lenovo.safecenterwidget;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
/* loaded from: classes.dex */
public class Utils {
    public static final String APPSTORE_URI = "http://safe.lenovo.com/d/";
    public static final long CLICK_INTERVAL = 10000;
    public static final String WIDGET_PKGNAME = "com.lenovo.safecenterwidget";
    private static boolean canClick = true;
    private static boolean canShowFinalUI = true;

    public static boolean nacServerIsExist() {
        File file = new File("/system/bin/nac_server");
        if (!file.exists() || file.length() <= 0) {
            return false;
        }
        return true;
    }

    private static boolean isLenovoSafeCenterInstalled(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.lenovo.safecenter", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isLenovoSafeCenterCanBeUsed(Context context) {
        try {
            int versionCode = context.getPackageManager().getPackageInfo("com.lenovo.safecenter", 0).versionCode;
            Log.i("MemClear", "com.lenovo.safecenter...versionCode == " + versionCode);
            if (versionCode >= 3850757) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void setCanClick(boolean value) {
        canClick = value;
    }

    public static boolean getCanClick() {
        return canClick;
    }

    public static void setCanShowFinalUI(boolean value) {
        canShowFinalUI = value;
    }

    public static boolean getCanShowFinalUI() {
        return canShowFinalUI;
    }

    public static void recordKillEvent(Context context, long killTime, int killPosition) {
        SharedPreferences.Editor editor = context.getSharedPreferences("LeSafeWidget", 0).edit();
        editor.putLong("KillTime", killTime);
        editor.putInt("KillPosition", killPosition);
        editor.commit();
    }

    public static long getLastKillTime(Context context) {
        return context.getSharedPreferences("LeSafeWidget", 0).getLong("KillTime", 0);
    }

    public static void delApp(final Context context, final String pkg, Handler h) {
        try {
            Log.i("ydp", "rm " + context.getPackageManager().getApplicationInfo(pkg, 0).sourceDir.replaceFirst("/", "") + "\n");
            Log.i("ydp", "rm data/dalvik-cache" + context.getPackageManager().getApplicationInfo(pkg, 0).sourceDir.replace("/", "@").replaceFirst("@", "/") + "@classes.dex");
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        new Thread() { // from class: com.lenovo.safecenterwidget.Utils.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Log.e("ydp", "updateSafeCenter");
                boolean result = true;
                try {
                    String comm1 = "rm -r data/data/" + pkg;
                    String comm2 = "rm " + context.getPackageManager().getApplicationInfo(pkg, 0).sourceDir.replaceFirst("/", "");
                    String path2 = context.getPackageManager().getApplicationInfo(pkg, 0).sourceDir.replaceFirst("/", "");
                    String comm3 = "rm data/dalvik-cache" + context.getPackageManager().getApplicationInfo(pkg, 0).sourceDir.replace("/", "@").replaceFirst("@", "/") + "@classes.dex";
                    String path3 = "data/dalvik-cache" + context.getPackageManager().getApplicationInfo(pkg, 0).sourceDir.replace("/", "@").replaceFirst("@", "/") + "@classes.dex";
                    Utils.reMountSystem(("mount -o remount,rw " + Utils.getMountDate(context) + " /system") + "mount -o remount,rw /system \n", context);
                    if (!Utils.execDel(comm1, "data/data/" + pkg, context)) {
                        result = false;
                    }
                    if (!Utils.execDel(comm2, path2, context)) {
                        result = false;
                    }
                    if (!Utils.execDel(comm3, path3, context)) {
                        result = false;
                    }
                } catch (PackageManager.NameNotFoundException e1) {
                    e1.printStackTrace();
                }
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("ydp", "updateSafeCenter over:" + result);
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0025, code lost:
        r4 = r3.substring(0, r3.indexOf(" "));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String getMountDate(android.content.Context r7) {
        /*
            java.lang.String r4 = ""
            r2 = 0
            java.lang.String r5 = "mount"
            java.io.FileInputStream r2 = r7.openFileInput(r5)     // Catch: Exception -> 0x003b, all -> 0x004b
            java.io.BufferedReader r0 = new java.io.BufferedReader     // Catch: Exception -> 0x003b, all -> 0x004b
            java.io.InputStreamReader r5 = new java.io.InputStreamReader     // Catch: Exception -> 0x003b, all -> 0x004b
            r5.<init>(r2)     // Catch: Exception -> 0x003b, all -> 0x004b
            r0.<init>(r5)     // Catch: Exception -> 0x003b, all -> 0x004b
            java.lang.String r3 = r0.readLine()     // Catch: Exception -> 0x003b, all -> 0x004b
        L_0x0017:
            java.lang.String r3 = r0.readLine()     // Catch: Exception -> 0x003b, all -> 0x004b
            if (r3 == 0) goto L_0x0030
            java.lang.String r5 = " /system "
            boolean r5 = r3.contains(r5)     // Catch: Exception -> 0x003b, all -> 0x004b
            if (r5 == 0) goto L_0x0017
            r5 = 0
            java.lang.String r6 = " "
            int r6 = r3.indexOf(r6)     // Catch: Exception -> 0x003b, all -> 0x004b
            java.lang.String r4 = r3.substring(r5, r6)     // Catch: Exception -> 0x003b, all -> 0x004b
        L_0x0030:
            if (r2 == 0) goto L_0x0035
            r2.close()     // Catch: IOException -> 0x0036
        L_0x0035:
            return r4
        L_0x0036:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0035
        L_0x003b:
            r5 = move-exception
            r1 = r5
            r1.printStackTrace()     // Catch: all -> 0x004b
            if (r2 == 0) goto L_0x0035
            r2.close()     // Catch: IOException -> 0x0046
            goto L_0x0035
        L_0x0046:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0035
        L_0x004b:
            r5 = move-exception
            if (r2 == 0) goto L_0x0051
            r2.close()     // Catch: IOException -> 0x0052
        L_0x0051:
            throw r5
        L_0x0052:
            r1 = move-exception
            r1.printStackTrace()
            goto L_0x0051
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.safecenterwidget.Utils.getMountDate(android.content.Context):java.lang.String");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean reMountSystem(String str, Context context) {
        return ScanAppInfo.exeCmd(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean execDel(String str, String path, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("copy.sh", 0);
            fos.write(str.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        Log.e("ydp", "dele " + path + "over:" + ScanAppInfo.exeCmd(context, str));
        if (new File(path).exists()) {
            return false;
        }
        return true;
    }
}

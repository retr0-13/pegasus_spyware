package com.lenovo.safebox.engine;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import com.lenovo.safebox.AppInfo;
import com.lenovo.safebox.PrivateSpaceHelper;
import com.lenovo.safebox.PrivateSpaceTools;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class AppEngine {
    private static ArrayList<String> appWhiteList;
    SQLiteDatabase db;
    public ArrayList<String> lockedApps;
    private Context mContext;
    PrivateSpaceHelper mHelper;
    private final int LOCKED = 0;
    private final int UNLOCKED = 1;
    private String TAG = "AppEngine  ";
    public ArrayList<AppInfo> sysAppList = new ArrayList<>();
    public ArrayList<AppInfo> dataAppList = new ArrayList<>();

    public AppEngine(Context context) {
        this.mContext = context;
        resolveAllApps();
        initWhiteList();
    }

    private void resolveAllApps() {
        PackageManager pm = this.mContext.getPackageManager();
        Intent it = new Intent("android.intent.action.MAIN");
        it.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> resolveList = pm.queryIntentActivities(it, 0);
        this.lockedApps = getLockedApp();
        if (this.lockedApps == null || this.lockedApps.size() <= 0) {
            for (int i = 0; i < resolveList.size(); i++) {
                String pkg = resolveList.get(i).activityInfo.packageName;
                boolean flag = false;
                boolean isDuplicate = false;
                if (!pkg.equals(this.mContext.getPackageName()) && !containedWhiteList(pkg)) {
                    try {
                        flag = isThirdpartApp(pm.getApplicationInfo(pkg, 0));
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    String name = (String) resolveList.get(i).loadLabel(pm);
                    Drawable icon = resolveList.get(i).loadIcon(pm);
                    if (!flag) {
                        Iterator<AppInfo> iterator = this.sysAppList.iterator();
                        while (iterator.hasNext()) {
                            AppInfo tmpAppInfo = iterator.next();
                            if (tmpAppInfo.pkgName.equals(pkg)) {
                                isDuplicate = true;
                                tmpAppInfo.setAppName(tmpAppInfo.getAppName() + " " + name);
                            }
                        }
                    } else {
                        Iterator<AppInfo> iterator1 = this.dataAppList.iterator();
                        while (iterator1.hasNext()) {
                            AppInfo tmpAppInfo2 = iterator1.next();
                            if (tmpAppInfo2.pkgName.equals(pkg)) {
                                isDuplicate = true;
                                tmpAppInfo2.setAppName(tmpAppInfo2.getAppName() + " " + name);
                            }
                        }
                    }
                    if (!isDuplicate) {
                        AppInfo appInfo = new AppInfo();
                        appInfo.setAppName(name);
                        appInfo.setDrawable(icon);
                        appInfo.setPkgName(pkg);
                        appInfo.setStatus(1);
                        if (flag) {
                            this.dataAppList.add(appInfo);
                        } else {
                            this.sysAppList.add(appInfo);
                        }
                    }
                }
            }
            return;
        }
        for (int i2 = 0; i2 < resolveList.size(); i2++) {
            String pkg2 = resolveList.get(i2).activityInfo.packageName;
            boolean flag2 = false;
            boolean isDuplicate2 = false;
            if (!this.lockedApps.contains(pkg2) && !containedWhiteList(pkg2)) {
                try {
                    flag2 = isThirdpartApp(pm.getApplicationInfo(pkg2, 0));
                } catch (PackageManager.NameNotFoundException e2) {
                    e2.printStackTrace();
                }
                String name2 = resolveList.get(i2).loadLabel(pm).toString();
                Drawable icon2 = resolveList.get(i2).loadIcon(pm);
                if (!flag2) {
                    Iterator<AppInfo> iterator2 = this.sysAppList.iterator();
                    while (iterator2.hasNext()) {
                        AppInfo tmpAppInfo3 = iterator2.next();
                        if (tmpAppInfo3.pkgName.equals(pkg2)) {
                            isDuplicate2 = true;
                            tmpAppInfo3.setAppName(tmpAppInfo3.getAppName() + " " + name2);
                        }
                    }
                } else {
                    Iterator<AppInfo> iterator12 = this.dataAppList.iterator();
                    while (iterator12.hasNext()) {
                        AppInfo tmpAppInfo4 = iterator12.next();
                        if (tmpAppInfo4.pkgName.equals(pkg2)) {
                            isDuplicate2 = true;
                            tmpAppInfo4.setAppName(tmpAppInfo4.getAppName() + " " + name2);
                        }
                    }
                }
                if (!isDuplicate2) {
                    AppInfo appInfo2 = new AppInfo();
                    appInfo2.setAppName(name2);
                    appInfo2.setDrawable(icon2);
                    appInfo2.setPkgName(pkg2);
                    appInfo2.setStatus(1);
                    if (flag2) {
                        this.dataAppList.add(appInfo2);
                    } else {
                        this.sysAppList.add(appInfo2);
                    }
                }
            }
        }
    }

    public ArrayList<AppInfo> getSysApps() {
        return this.sysAppList;
    }

    public ArrayList<AppInfo> getDataApps() {
        return this.dataAppList;
    }

    public ArrayList<AppInfo> getLockedApps() {
        if (this.lockedApps != null) {
            return null;
        }
        this.lockedApps = getLockedApp();
        return null;
    }

    private ArrayList<String> getLockedApp() {
        ArrayList<String> lockedApps = new ArrayList<>();
        this.mHelper = new PrivateSpaceHelper(this.mContext, PrivateSpaceTools.DB_NAME, null, 1);
        this.db = this.mHelper.getReadableDatabase();
        Cursor result = this.db.query(true, PrivateSpaceHelper.APP_TB_NAME, new String[]{PrivateSpaceHelper.PKG}, null, null, null, null, null, null);
        if (result != null && result.getCount() > 0) {
            result.moveToFirst();
            while (!result.isAfterLast()) {
                PrivateSpaceHelper privateSpaceHelper = this.mHelper;
                lockedApps.add(result.getString(result.getColumnIndex(PrivateSpaceHelper.PKG)));
                result.moveToNext();
            }
        }
        if (result != null) {
            result.close();
        }
        this.db.close();
        return lockedApps;
    }

    public void initWhiteList() {
        appWhiteList = new ArrayList<>();
        appWhiteList.add("com.lenovo.safecenter");
        appWhiteList.add("com.lenovo.safecenterpad");
        appWhiteList.add("com.lenovo.kidmode");
        appWhiteList.add("com.dianxinos.dxhome");
        appWhiteList.add("com.snda.inote.lenovo");
        appWhiteList.add("com.tencent.qqlauncher");
        appWhiteList.add("com.gau.go.launcherex");
        appWhiteList.add("com.lenovo.safe.powercenter");
        appWhiteList.add(this.mContext.getPackageName());
        appWhiteList.add("com.lenovo.launcher.theme");
        appWhiteList.add("com.huaqin.launcherEx");
        appWhiteList.add("com.lenovo.launcher");
        appWhiteList.add("com.mediatek.bluetooth");
    }

    public boolean containedWhiteList(String pkgName) {
        if (appWhiteList == null) {
            initWhiteList();
        }
        if (appWhiteList.contains(pkgName)) {
            return true;
        }
        return pkgName.length() > 25 && pkgName.substring(0, 25).equals("com.lenovo.launcher.theme");
    }

    public static boolean isThirdpartApp(ApplicationInfo appInfo) {
        if ((appInfo.flags & 128) == 0 && (appInfo.flags & 1) != 0) {
            return false;
        }
        return true;
    }
}

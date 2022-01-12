package com.lenovo.safebox.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import com.lenovo.safebox.PrivateSpaceHelper;
import com.lenovo.safebox.PrivateSpaceTools;
import java.util.ArrayList;
import java.util.HashMap;
/* loaded from: classes.dex */
public class WatchAppService extends Service {
    public static HashMap<String, Boolean> appHm;
    private static Context mContext;
    private String TAG = " WatchAppService   ";
    ActivityManager activityManager;
    private ArrayList<String> checkList;
    SQLiteDatabase db;
    PrivateSpaceHelper mHelper;
    private WatchAppServiceimpl mWatchAppServiceimpl;
    public static String lockedPkg = "";
    public static boolean controler = false;
    public static boolean insafebox = false;
    public static boolean windowShow = false;
    public static Handler handler = new Handler() { // from class: com.lenovo.safebox.service.WatchAppService.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    LockedAppInfo lockedAppInfo = (LockedAppInfo) msg.obj;
                    WatchAppService.addPopWindow(WatchAppService.mContext, lockedAppInfo.getPkgName(), lockedAppInfo.getPid());
                    return;
                case 2:
                default:
                    return;
                case 3:
                    LockedAppInfo lockedAppInfo1 = (LockedAppInfo) msg.obj;
                    WatchAppService.addPopWindowJudge(WatchAppService.mContext, lockedAppInfo1.getPkgName(), lockedAppInfo1.getPid());
                    return;
            }
        }
    };

    @Override // android.app.Service
    public void onCreate() {
        this.activityManager = (ActivityManager) getSystemService("activity");
        appHm = new HashMap<>();
        this.mHelper = new PrivateSpaceHelper(this, PrivateSpaceTools.DB_NAME, null, 1);
        this.db = this.mHelper.getReadableDatabase();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().detectLeakedSqlLiteObjects().build());
        PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        Log.i(this.TAG, "SDK :" + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT > 13) {
            mContext = getApplicationContext();
        }
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int startid) {
        Log.i(this.TAG, "onStart");
        super.onStart(intent, startid);
        Log.i(this.TAG + "    onStart", "Service onStart");
        if (Build.VERSION.SDK_INT > 13) {
            if (appHm == null) {
                appHm = new HashMap<>();
            }
            if (intent != null) {
                if (appHm.containsKey(intent.getStringExtra("pkgName"))) {
                    appHm.put(intent.getStringExtra("pkgName"), Boolean.valueOf(intent.getBooleanExtra("controler", false)));
                }
                if (intent.getStringExtra("pkgName") != null && intent.getStringExtra("pkgName").equals(PrivateSpaceTools.mPkgName)) {
                    insafebox = intent.getBooleanExtra("insafebox", false);
                }
            }
        }
    }

    public static void addPopWindowJudge(Context context, String str, int pid) {
        Log.i("WatchAppService", "windowShow :" + windowShow);
        if (!windowShow) {
            new LockScreen(context, str, pid).showLockScreen();
        }
    }

    public static void addPopWindow(Context context, String str, int pid) {
        new LockScreen(context, str, pid).showLockScreen();
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.i(this.TAG, "onDestroy");
        if (Build.VERSION.SDK_INT > 13) {
            Intent mIntent = new Intent();
            mIntent.setClass(getApplicationContext(), WatchAppService.class);
            getApplicationContext().startService(mIntent);
        }
        super.onDestroy();
    }

    /* loaded from: classes.dex */
    public class TestReceiver extends BroadcastReceiver {
        static final String FALSE_ACTION = "com.lenovo.pspace.FALSE";
        static final String TRUE_ACTION = "com.lenovo.pspace.TRUE";

        public TestReceiver() {
            WatchAppService.this = r1;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.i(WatchAppService.this.TAG, "onReceive");
            if (intent.getAction().equals(TRUE_ACTION)) {
                Log.i(WatchAppService.this.TAG, "set true");
            }
            if (intent.getAction().equals(FALSE_ACTION)) {
                Log.i(WatchAppService.this.TAG, "set false");
            }
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private ArrayList<String> getLockedApp() {
        ArrayList<String> lockedApps = new ArrayList<>();
        this.mHelper = new PrivateSpaceHelper(getApplicationContext(), PrivateSpaceTools.DB_NAME, null, 1);
        this.db = this.mHelper.getReadableDatabase();
        Cursor result = this.db.query(true, PrivateSpaceHelper.APP_TB_NAME, new String[]{PrivateSpaceHelper.PKG}, null, null, null, null, null, null);
        if (result != null && result.getColumnCount() > 0) {
            result.moveToFirst();
            while (!result.isAfterLast()) {
                String str = this.TAG;
                StringBuilder append = new StringBuilder().append("LockedApp From database  ");
                PrivateSpaceHelper privateSpaceHelper = this.mHelper;
                Log.i(str, append.append(result.getString(result.getColumnIndex(PrivateSpaceHelper.PKG))).toString());
                PrivateSpaceHelper privateSpaceHelper2 = this.mHelper;
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
}

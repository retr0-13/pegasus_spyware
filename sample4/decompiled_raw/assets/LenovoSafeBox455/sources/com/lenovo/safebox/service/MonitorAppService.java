package com.lenovo.safebox.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.Uri;
import android.os.IBinder;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import com.lenovo.safebox.LockScreenActivity;
import com.lenovo.safebox.PrivateSpaceHelper;
import com.lenovo.safebox.PrivateSpaceTools;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* loaded from: classes.dex */
public class MonitorAppService extends Service {
    private static final String TAG = "MonitorAppService";
    public static final String executaleFile = "/data/data/com.lenovo.safebox/files/execute.sh";
    private static InputStream mInputStream;
    private static DataOutputStream mOutputStream;
    private static Process mProcess;
    static boolean windowShow;
    private ArrayList<String> checkList;
    SQLiteDatabase db;
    private IntentFilter filter;
    private boolean getBroadcast;
    private String lastPkg;
    private String lastTaskLockedPkg;
    private ActivityManager mActivityManager;
    private Context mContext;
    PrivateSpaceHelper mHelper;
    private Timer mTimer;
    private Matcher matcher;
    private Pattern pattern;
    private Runtime runtime;
    private TimerTask task;
    private ArrayList<String> whiteList;
    private static boolean insafebox = false;
    public static boolean isLocked = false;
    private static boolean DEBUG = false;
    public static boolean needPwd = true;
    private static boolean haveRoot = false;
    private static boolean registered = false;
    private boolean watchAppOpen = false;
    private final BroadcastReceiver receiver = new BroadcastReceiver() { // from class: com.lenovo.safebox.service.MonitorAppService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String thisPkg;
            if (intent != null) {
                if (intent.getAction().equals("android.intent.action.SCREEN_OFF") && MonitorAppService.this.task != null) {
                    MonitorAppService.this.task.cancel();
                }
                if (intent.getAction().equals("android.intent.action.SCREEN_ON") && !MonitorAppService.this.getBroadcast && MonitorAppService.this.checkList.size() > 0) {
                    MonitorAppService.this.mTimer = new Timer(true);
                    MonitorAppService.this.restartTimer();
                }
                try {
                    if (intent.getAction().equals("com.lenovo.safecenter.activityswitch")) {
                        if (MonitorAppService.this.task != null) {
                            MonitorAppService.this.task.cancel();
                        }
                        if (intent.getStringExtra("newPkg") == null || intent.getStringExtra("newPkg").isEmpty()) {
                            thisPkg = MonitorAppService.this.getTopTaskPkg();
                        } else {
                            thisPkg = intent.getStringExtra("newPkg");
                        }
                        if (MonitorAppService.DEBUG) {
                            Log.i(MonitorAppService.TAG, "receive broadcast");
                            Log.i(MonitorAppService.TAG, "isLocked :" + MonitorAppService.isLocked + " lastPkg :" + MonitorAppService.this.lastPkg);
                            Log.i(MonitorAppService.TAG, "insafebox :" + MonitorAppService.insafebox);
                            Log.i(MonitorAppService.TAG, "newPkg :" + intent.getStringExtra("newPkg"));
                        }
                        if (MonitorAppService.isLocked && !thisPkg.equals(PrivateSpaceTools.mPkgName)) {
                            MonitorAppService.isLocked = false;
                        }
                        if (!MonitorAppService.insafebox && MonitorAppService.this.lastPkg != null && !MonitorAppService.this.lastPkg.equals(PrivateSpaceTools.mPkgName) && !MonitorAppService.this.lastPkg.equals(thisPkg) && MonitorAppService.this.checkList.contains(thisPkg) && !MonitorAppService.isLocked) {
                            Intent manIntent = new Intent(MonitorAppService.this.mContext, LockScreenActivity.class);
                            manIntent.putExtra(PrivateSpaceHelper.PKG, thisPkg);
                            manIntent.addFlags(268435456);
                            if (MonitorAppService.DEBUG) {
                                Log.i(MonitorAppService.TAG, "Lock in Broadcast TopTask :" + thisPkg);
                            }
                            MonitorAppService.this.mContext.startActivity(manIntent);
                            MonitorAppService.this.lastTaskLockedPkg = thisPkg;
                            MonitorAppService.isLocked = true;
                            MonitorAppService.this.lastPkg = "invalid";
                            if (MonitorAppService.DEBUG) {
                                Log.i(MonitorAppService.TAG, "lastPkg modified:" + MonitorAppService.this.lastPkg);
                            }
                            if (Settings.Secure.getInt(MonitorAppService.this.mContext.getContentResolver(), "adb_enabled", 0) == 0) {
                                MonitorAppService.this.prepareExecuteFile(MonitorAppService.this.mContext, "start adbd \necho done\n");
                            }
                        }
                        if (MonitorAppService.this.lastPkg != null && !MonitorAppService.this.lastPkg.equals("invalid")) {
                            MonitorAppService.this.lastPkg = thisPkg;
                            if (MonitorAppService.DEBUG) {
                                Log.i(MonitorAppService.TAG, "lastPkg modified:" + MonitorAppService.this.lastPkg);
                            }
                        }
                    }
                } catch (Exception e) {
                }
                if (intent.getAction().equals("com.lenovo.safebox.lockscreen")) {
                    if (MonitorAppService.DEBUG) {
                        Log.i(MonitorAppService.TAG, "receive lockscreen :lastPkg " + intent.getStringExtra("lastPkg"));
                    }
                    MonitorAppService.this.lastPkg = intent.getStringExtra("lastPkg");
                    if (MonitorAppService.DEBUG) {
                        Log.i(MonitorAppService.TAG, "lastPkg modified:" + MonitorAppService.this.lastPkg);
                        Log.i(MonitorAppService.TAG, "after lastPkg :" + MonitorAppService.this.lastPkg);
                    }
                }
            }
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        this.getBroadcast = false;
        this.mContext = this;
        this.pattern = Pattern.compile("(.*)(\\={1})(.*)(//*)");
        this.runtime = Runtime.getRuntime();
        this.filter = new IntentFilter();
        this.filter.addAction("android.intent.action.SCREEN_OFF");
        this.filter.addAction("android.intent.action.SCREEN_ON");
        this.filter.addAction("com.lenovo.safecenter.activityswitch");
        this.filter.addAction("com.lenovo.safebox.lockscreen");
        this.lastPkg = "";
        this.checkList = new ArrayList<>();
        this.checkList = getLockedApp();
        if (this.checkList.size() > 0) {
            registerReceiver(this.receiver, this.filter);
            registered = true;
        }
        if (canGetPassword()) {
            if (!this.watchAppOpen && this.checkList.size() > 0) {
                watchAppStart();
            }
            if (!this.getBroadcast && this.checkList.size() > 0) {
                this.mTimer = new Timer(true);
                restartTimer();
            }
        }
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().detectLeakedSqlLiteObjects().build());
        super.onCreate();
    }

    public void restartTimer() {
        this.task = new TimerTask() { // from class: com.lenovo.safebox.service.MonitorAppService.2
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                String topTask = MonitorAppService.this.getTopTaskPkg();
                if (MonitorAppService.DEBUG) {
                    Log.i(MonitorAppService.TAG, "topTask : " + topTask);
                    Log.i(MonitorAppService.TAG, "lastPkg :" + MonitorAppService.this.lastPkg);
                    Log.i(MonitorAppService.TAG, "isLocked :" + MonitorAppService.isLocked);
                    Log.i(MonitorAppService.TAG, "lastPkg topTask checkList :" + MonitorAppService.this.lastPkg + topTask + MonitorAppService.this.checkList);
                }
                if (MonitorAppService.isLocked && !topTask.equals(PrivateSpaceTools.mPkgName)) {
                    MonitorAppService.isLocked = false;
                }
                if (!MonitorAppService.insafebox && MonitorAppService.this.lastPkg != null && !MonitorAppService.this.lastPkg.equals(PrivateSpaceTools.mPkgName) && !MonitorAppService.this.lastPkg.equals(topTask) && MonitorAppService.this.checkList.contains(topTask) && !MonitorAppService.isLocked) {
                    Intent manIntent = new Intent(MonitorAppService.this.mContext, LockScreenActivity.class);
                    manIntent.putExtra(PrivateSpaceHelper.PKG, topTask);
                    manIntent.addFlags(268435456);
                    if (MonitorAppService.DEBUG) {
                        Log.i(MonitorAppService.TAG, "Lock in TopTask");
                        Log.i(MonitorAppService.TAG, "lastPkg modified:invalid");
                    }
                    MonitorAppService.this.mContext.startActivity(manIntent);
                    MonitorAppService.this.lastPkg = "invalid";
                    MonitorAppService.this.lastTaskLockedPkg = topTask;
                    MonitorAppService.isLocked = true;
                    if (Settings.Secure.getInt(MonitorAppService.this.mContext.getContentResolver(), "adb_enabled", 0) == 0) {
                        MonitorAppService.this.prepareExecuteFile(MonitorAppService.this.mContext, "start adbd \necho done\n");
                    }
                }
                if (MonitorAppService.this.lastPkg != null) {
                    MonitorAppService.this.lastPkg = topTask;
                }
            }
        };
        try {
            this.mTimer.schedule(this.task, 5000, 1000);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int arg0, int arg1) {
        Log.i(TAG, "onStartCommand");
        if (intent != null) {
            if (DEBUG) {
                Log.i(TAG, "will get insafebox");
            }
            insafebox = intent.getBooleanExtra("insafebox", false);
            if (DEBUG) {
                Log.i(TAG, "insafebox :" + insafebox);
            }
            if (!insafebox) {
                prepareExecuteFile(this.mContext, "start adbd & \necho done\n");
            }
        } else {
            prepareExecuteFile(this.mContext, "start adbd & \necho done\n");
        }
        isLocked = false;
        if (this.task != null) {
            this.task.cancel();
        }
        this.checkList = new ArrayList<>();
        if (canGetPassword()) {
            this.checkList = getLockedApp();
            if (!this.watchAppOpen && this.checkList.size() > 0) {
                watchAppStart();
            }
        }
        initWhiteList();
        this.mActivityManager = (ActivityManager) this.mContext.getSystemService("activity");
        if (!this.getBroadcast && this.checkList.size() > 0) {
            this.mTimer = new Timer(true);
            restartTimer();
        }
        if (this.checkList.size() > 0) {
            registerReceiver(this.receiver, this.filter);
            registered = true;
        } else if (registered) {
            unregisterReceiver(this.receiver);
            registered = false;
        }
        return 1;
    }

    private boolean canGetPassword() {
        try {
            Cursor mCursor = this.mContext.getContentResolver().query(Uri.parse("content://com.lenovo.safecenter.password/password"), null, null, null, null);
            if (mCursor == null) {
                if (DEBUG) {
                    Log.i(TAG, "lost password!");
                }
                return checkNewPwdExist("lenovo");
            } else if (mCursor.getCount() <= 0) {
                return false;
            } else {
                this.getBroadcast = isRightVersioncode();
                mCursor.close();
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkNewPwdExist(String inputStr) {
        Cursor mCursor = getContentResolver().query(Uri.parse("content://com.lenovo.safecenter.password/password"), new String[]{"result"}, null, new String[]{inputStr}, null);
        if (mCursor == null) {
            return false;
        }
        if (mCursor.getCount() > 0) {
            mCursor.close();
            return true;
        }
        mCursor.close();
        return false;
    }

    private boolean isRightVersioncode() {
        try {
            if (getPackageManager().getPackageInfo("com.lenovo.safecenter", 0).versionCode >= 3830876) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        Intent mIntent = new Intent();
        mIntent.setClass(this, MonitorAppService.class);
        getApplicationContext().startService(mIntent);
        super.onDestroy();
    }

    private void watchAppStart() {
        Log.i(TAG, "WatchAppStart");
        this.watchAppOpen = true;
        new Thread() { // from class: com.lenovo.safebox.service.MonitorAppService.3
            /* JADX WARN: Removed duplicated region for block: B:218:? A[RETURN, SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:35:0x0105  */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public void run() {
                /*
                    Method dump skipped, instructions count: 1391
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.lenovo.safebox.service.MonitorAppService.AnonymousClass3.run():void");
            }
        }.start();
    }

    public String getTopTaskPkg() {
        if (this.mActivityManager == null) {
            this.mActivityManager = (ActivityManager) this.mContext.getSystemService("activity");
        }
        if (this.checkList == null) {
            this.checkList = new ArrayList<>();
        }
        String taskName = null;
        if (this.mActivityManager != null) {
            taskName = this.mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        return (taskName == null || taskName.isEmpty()) ? "invalid" : taskName;
    }

    private void initWhiteList() {
        if (this.whiteList == null) {
            this.whiteList = new ArrayList<>();
        } else {
            this.whiteList.clear();
        }
        this.whiteList.add("com.android.settings");
    }

    private ArrayList<String> getLockedApp() {
        Cursor mCursor = this.mContext.getContentResolver().query(Uri.parse("content://com.lenovo.safecenter.password/password"), null, null, null, null);
        if (mCursor == null) {
            if (!checkNewPwdExist("lenovo")) {
                return new ArrayList<>();
            }
        } else if (mCursor.getCount() == 0) {
            mCursor.close();
            return new ArrayList<>();
        } else {
            mCursor.close();
        }
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

    public static boolean socketClient(String cmd) {
        boolean success = false;
        try {
            Socket client = new Socket("127.0.0.1", 30001);
            if (DEBUG) {
                Log.d(TAG, "Socket: " + client);
            }
            PrintWriter socketWriter = new PrintWriter(client.getOutputStream(), true);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            socketWriter.write(cmd);
            socketWriter.flush();
            String a = socketReader.readLine();
            if (a != null) {
                success = a.startsWith("success");
            }
            if (DEBUG) {
                Log.d(TAG, "Socket success: " + success);
            }
            socketWriter.close();
            socketReader.close();
            client.close();
            return success;
        } catch (Exception e) {
            try {
                LocalSocketAddress address = new LocalSocketAddress("nac_server");
                LocalSocket localSocket = new LocalSocket();
                localSocket.connect(address);
                if (DEBUG) {
                    Log.d(TAG, "LocalSocket connect: " + localSocket.isConnected());
                }
                PrintWriter socketWriter2 = new PrintWriter(localSocket.getOutputStream(), true);
                BufferedReader socketReader2 = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
                socketWriter2.write(cmd);
                socketWriter2.flush();
                String a2 = socketReader2.readLine();
                if (a2 != null) {
                    success = a2.startsWith("success");
                }
                if (DEBUG) {
                    Log.d(TAG, "LocalSocket success: " + success);
                }
                socketWriter2.close();
                socketReader2.close();
                localSocket.close();
                return success;
            } catch (IOException e2) {
                if (DEBUG) {
                    Log.i(TAG, "This is socketClient: " + cmd);
                    e2.printStackTrace();
                    Log.i(TAG, "This is socketClient: " + cmd);
                }
                if (!haveRoot) {
                    obtainLenovoRoot();
                    if (DEBUG) {
                        Log.i(TAG, "haveRoot :" + haveRoot);
                    }
                    if (haveRoot) {
                        if (cmd.equals("/data/data/com.lenovo.safebox/files/execute.sh")) {
                            rootCmd("sh " + cmd);
                        } else {
                            rootCmd(cmd);
                        }
                    }
                } else if (cmd.equals("/data/data/com.lenovo.safebox/files/execute.sh")) {
                    rootCmd("sh " + cmd);
                } else {
                    rootCmd(cmd);
                }
                return false;
            }
        }
    }

    public boolean prepareExecuteFile(Context context, String command) {
        FileOutputStream fos = null;
        if (!PrivateSpaceTools.isLenovo()) {
            return socketClient(command);
        }
        try {
            try {
                fos = context.openFileOutput("execute.sh", 0);
                fos.write(command.getBytes());
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            return socketClient("/data/data/com.lenovo.safebox/files/execute.sh");
        } catch (Throwable th) {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static void checkRoot() {
        if (!obtainLenovoRoot()) {
            try {
                if (DEBUG) {
                    Log.i(TAG, "checkRoot begin");
                }
                mProcess = Runtime.getRuntime().exec("su");
                mOutputStream = new DataOutputStream(mProcess.getOutputStream());
                mOutputStream.writeBytes("ls data\n");
                mOutputStream.flush();
                mInputStream = mProcess.getInputStream();
                String line = new BufferedReader(new InputStreamReader(mInputStream)).readLine();
                if (line != null) {
                    String readStr = "denied" + line + "\n";
                    if (DEBUG) {
                        Log.i("process", "retrie permission" + readStr);
                    }
                }
                haveRoot = true;
            } catch (Exception e) {
                haveRoot = false;
                if (DEBUG) {
                    e.printStackTrace();
                    Log.i("process", e.getMessage());
                }
            }
        }
    }

    public static boolean obtainLenovoRoot() {
        try {
            mProcess = Runtime.getRuntime().exec("/system/bin/cmcc_ps");
            mOutputStream = new DataOutputStream(mProcess.getOutputStream());
            mOutputStream.writeBytes("id\n");
            mOutputStream.flush();
            mInputStream = mProcess.getInputStream();
            if (new BufferedReader(new InputStreamReader(mInputStream)).readLine() == null) {
                return true;
            }
            haveRoot = true;
            return true;
        } catch (Exception e) {
            haveRoot = false;
            return false;
        }
    }

    public static boolean rootCmd(String cmd) {
        boolean z = false;
        if (DEBUG) {
            Log.i(TAG, "rootCmd()...cmd=" + cmd);
        }
        try {
            DataOutputStream os = mOutputStream;
            if (os != null) {
                os.writeBytes(cmd + "\n");
                os.flush();
                z = true;
            } else if (DEBUG) {
                Log.i(TAG, "rootCmd()...os=null---->cmd=" + cmd);
            }
        } catch (Exception e) {
            if (DEBUG) {
                e.printStackTrace();
            }
        }
        return z;
    }
}

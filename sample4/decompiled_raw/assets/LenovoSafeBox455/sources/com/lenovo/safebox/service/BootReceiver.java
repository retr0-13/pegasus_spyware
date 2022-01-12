package com.lenovo.safebox.service;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import com.lenovo.safebox.PrivateSpaceTools;
import java.io.File;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class BootReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    private static final String EJECT_ACTION = "android.intent.action.MEDIA_EJECT";
    private static final String MOUNTED_ACTION = "android.intent.action.MEDIA_MOUNTED";
    private static final String UMOUNT_STAGING = PrivateSpaceTools.busybox + "  umount  /mnt/secure/staging/.pFolder";
    ActivityManager activityManager;
    ArrayList<Uri> mountPoint;
    private int version = 0;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().detectLeakedSqlLiteObjects().build());
        try {
            this.version = context.getPackageManager().getPackageInfo("com.lenovo.safecenter", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        this.mountPoint = new ArrayList<>();
        this.mountPoint.add(Uri.parse("file:///mnt/sdcard"));
        this.mountPoint.add(Uri.parse("file:///storage/sdcard0"));
        if (intent.getAction().equals(ACTION)) {
            Log.i("MonitorAppService", "Received boot_complete");
            Intent mIntent = new Intent();
            mIntent.setClass(context, MonitorAppService.class);
            context.startService(mIntent);
            File busyboxFile = new File(PrivateSpaceTools.busybox);
            File psFolder = new File("/sdcard/.pFolder");
            File imgFile = new File(PrivateSpaceTools.coverFilePath);
            if (psFolder.exists() && imgFile.exists() && busyboxFile.exists()) {
                PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
            }
        }
        if (intent.getAction().equals(EJECT_ACTION) && this.mountPoint.contains(intent.getData())) {
            Log.i("PrivateSpace BootReceiver", "receive EJECT_ACTION data /mnt/sdcard");
            PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
            Log.i("PrivateSpace BootReceiver", PrivateSpaceTools.showFolder);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PrivateSpaceTools.socketClient(UMOUNT_STAGING);
        }
        if (intent.getAction().equals(MOUNTED_ACTION) && this.mountPoint.contains(intent.getData())) {
            Log.i("PrivateSpace BootReceiver", "receive MOUNTED_ACTION data /mnt/sdcard");
            this.activityManager = (ActivityManager) context.getSystemService("activity");
            if (!safeboxIsTop()) {
                PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
            }
        }
        if (intent.getAction().equals("android.intent.action.USER_PRESENT")) {
            this.activityManager = (ActivityManager) context.getSystemService("activity");
            boolean isAlive = false;
            for (ActivityManager.RunningServiceInfo serviceInfo : this.activityManager.getRunningServices(100)) {
                if (serviceInfo.process.equals("com.lenovo.safebox:watch")) {
                    Log.i("MonitorAppService", "WatchAppService RUNNING");
                    isAlive = true;
                }
            }
            if (!isAlive) {
                Log.i("MonitorAppService", "WatchAppService DEAD");
                Intent mIntent2 = new Intent();
                mIntent2.setClass(context, MonitorAppService.class);
                context.startService(mIntent2);
            }
        }
    }

    private boolean safeboxIsTop() {
        String taskName = null;
        if (this.activityManager != null) {
            try {
                taskName = this.activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
            } catch (Exception e) {
                return false;
            }
        }
        if (taskName == null || taskName.isEmpty()) {
            return false;
        }
        return taskName.equals(PrivateSpaceTools.mPkgName);
    }
}

package com.lenovo.safecenterwidget;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/* loaded from: classes.dex */
public class ScanAppInfo {
    private static final boolean DEBUG = true;
    private static final boolean DEBUG2 = true;
    public static final String TAG = "MemClear";
    private static ScanAppInfo mScanAppInfo = null;
    public static List<String> userWhiteList;
    private Context mContext;
    private ArrayList<String> xmlWhiteList;
    private ArrayList<String> launchers = getInstalledLauncher();
    private ArrayList<String> inputMethodApps = getInputMethodApp();
    private ArrayList<String> myLaunchers = initLauncherList();

    public static synchronized ScanAppInfo getInstance(RefreshWidget widget, Context c) {
        ScanAppInfo scanAppInfo;
        synchronized (ScanAppInfo.class) {
            if (mScanAppInfo == null) {
                mScanAppInfo = new ScanAppInfo(c);
            }
            scanAppInfo = mScanAppInfo;
        }
        return scanAppInfo;
    }

    private ScanAppInfo(Context c) {
        this.mContext = null;
        this.mContext = c;
        this.xmlWhiteList = initXmlWhiteList(c);
        new Timer().schedule(new TimerTask() { // from class: com.lenovo.safecenterwidget.ScanAppInfo.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (((PowerManager) ScanAppInfo.this.mContext.getSystemService("power")).isScreenOn()) {
                    ScanAppInfo.this.refreshWidget();
                }
            }
        }, 0, 1800000);
    }

    public int[] getTotalMemory() {
        Throwable th;
        int[] memoryArr = new int[3];
        BufferedReader buf = null;
        try {
            BufferedReader buf2 = new BufferedReader(new FileReader("/proc/meminfo"));
            int i = 0;
            long totalMem = 0;
            long freeMem = 0;
            long buffermem = 0;
            long buffers = 0;
            while (true) {
                try {
                    String line = buf2.readLine();
                    if (line == null) {
                        break;
                    }
                    if (i == 0) {
                        totalMem = convertData(line);
                    } else if (i == 1) {
                        freeMem = convertData(line);
                    } else if (i == 2) {
                        buffers = convertData(line);
                    } else if (i == 3) {
                        buffermem = convertData(line);
                        break;
                    }
                    i++;
                } catch (FileNotFoundException e) {
                    buf = buf2;
                    if (buf != null) {
                        try {
                            buf.close();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    return memoryArr;
                } catch (IOException e3) {
                    buf = buf2;
                    if (buf != null) {
                        try {
                            buf.close();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    }
                    return memoryArr;
                } catch (Throwable th2) {
                    th = th2;
                    buf = buf2;
                    if (buf != null) {
                        try {
                            buf.close();
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
            if (totalMem - freeMem > 0) {
                long t = ((totalMem - freeMem) - buffermem) - buffers;
                Log.i("MemClear", "used == " + t);
                memoryArr[0] = (int) ((100 * t) / totalMem);
                memoryArr[2] = (int) (t / 1024);
            }
            memoryArr[1] = (int) (totalMem / 1024);
            if (buf2 != null) {
                try {
                    buf2.close();
                } catch (Exception e6) {
                    e6.printStackTrace();
                }
            }
        } catch (FileNotFoundException e7) {
        } catch (IOException e8) {
        } catch (Throwable th3) {
            th = th3;
        }
        return memoryArr;
    }

    private long convertData(String readLine) {
        String[] array = readLine.split("\\s+");
        if (array != null) {
            return Long.parseLong(array[1]);
        }
        return 0;
    }

    public synchronized void killAllProcess(final boolean isNacOk, final Handler handler) {
        final ArrayList<String> runningPkgList = scanAppsPkg();
        Log.i("MemClear", "...killAllProcess()...appCount == " + runningPkgList.size());
        new Thread() { // from class: com.lenovo.safecenterwidget.ScanAppInfo.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                int fakeKillAppCount = 0;
                Iterator i$ = runningPkgList.iterator();
                while (i$.hasNext()) {
                    if (ScanAppInfo.this.myLaunchers.contains((String) i$.next())) {
                        fakeKillAppCount++;
                    }
                }
                Message msg = new Message();
                msg.what = 4;
                msg.arg1 = fakeKillAppCount;
                if (isNacOk) {
                    handler.sendMessageDelayed(msg, 2200);
                    if (runningPkgList.size() > 0) {
                        StringBuffer buffer = new StringBuffer();
                        Iterator i$2 = runningPkgList.iterator();
                        while (i$2.hasNext()) {
                            buffer.append("am force-stop " + ((String) i$2.next()) + "\n");
                        }
                        String command = buffer.toString();
                        Log.i("MemClear", "...killAllProcess()...command == " + command);
                        Log.i("MemClear", "...killAllProcess()...command result == " + ScanAppInfo.exeCmd(ScanAppInfo.this.mContext, command));
                        return;
                    }
                    return;
                }
                ActivityManager am = (ActivityManager) ScanAppInfo.this.mContext.getApplicationContext().getSystemService("activity");
                Iterator i$3 = runningPkgList.iterator();
                while (i$3.hasNext()) {
                    ScanAppInfo.this.killProcessForUserAuthority(am, (String) i$3.next());
                }
                handler.sendMessage(msg);
            }
        }.start();
    }

    public void killSingleProcess(final boolean isNacOk, final String pkgName) {
        new Thread() { // from class: com.lenovo.safecenterwidget.ScanAppInfo.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (pkgName) {
                    ScanAppInfo.this.killProcessForUserAuthority((ActivityManager) ScanAppInfo.this.mContext.getApplicationContext().getSystemService("activity"), pkgName);
                    if (isNacOk) {
                        Log.i("MemClear", "...killSingleProcess()...command result == " + ScanAppInfo.exeCmd(ScanAppInfo.this.mContext, "am force-stop " + pkgName + "\n"));
                    }
                }
            }
        }.start();
    }

    public synchronized ArrayList<ProcessItem> scanApps() {
        ArrayList<ProcessItem> processList;
        Log.i("MemClear", "---> scanApps()");
        PackageManager pm = this.mContext.getPackageManager();
        List<ActivityManager.RunningAppProcessInfo> runningList = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses();
        Log.i("MemClear", "---> scanApps()...xml whitelist...appList count == " + this.xmlWhiteList.size());
        ArrayList<String> pkgs = new ArrayList<>();
        processList = new ArrayList<>();
        for (ActivityManager.RunningAppProcessInfo info : runningList) {
            if (info.importance <= 100) {
                Log.i("MemClear", "---> scanApps()...importance==" + info.importance + " " + info.pkgList[0]);
            } else {
                for (int i = 0; i < info.pkgList.length; i++) {
                    try {
                        String pkgName = info.pkgList[i];
                        if (!pkgs.contains(pkgName)) {
                            pkgs.add(pkgName);
                            if (!this.myLaunchers.contains(pkgName)) {
                                Log.i("MemClear", "---> scanApps()...invisible in launcher...pkgName == " + pkgName);
                            } else if (this.xmlWhiteList.contains(pkgName)) {
                                Log.i("MemClear", "---> scanApps()...xml whitelist...pkgName == " + pkgName);
                            } else if (!this.launchers.contains(pkgName) && !this.inputMethodApps.contains(pkgName) && !checkBindWallpaper(pm, pkgName) && !this.mContext.getPackageName().equals(pkgName)) {
                                processList.add(new ProcessItem(ThumbnailUtils.extractThumbnail(((BitmapDrawable) pm.getApplicationIcon(pkgName)).getBitmap(), 60, 60), pm.getApplicationLabel(pm.getApplicationInfo(pkgName, 0)).toString(), pkgName, info.pid));
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return processList;
    }

    public synchronized ArrayList<String> scanAppsPkg() {
        ArrayList<String> pkgs;
        Log.i("MemClear", "---> scanAppsPkg()");
        PackageManager pm = this.mContext.getPackageManager();
        List<ActivityManager.RunningAppProcessInfo> runningList = ((ActivityManager) this.mContext.getSystemService("activity")).getRunningAppProcesses();
        pkgs = new ArrayList<>();
        for (ActivityManager.RunningAppProcessInfo info : runningList) {
            if (info.importance <= 100) {
                Log.i("MemClear", "---> scanApps()...importance==" + info.importance + " " + info.pkgList[0]);
            } else {
                for (int i = 0; i < info.pkgList.length; i++) {
                    String pkgName = info.pkgList[i];
                    if (!this.xmlWhiteList.contains(pkgName) && !this.launchers.contains(pkgName) && !this.inputMethodApps.contains(pkgName) && !checkBindWallpaper(pm, pkgName) && !this.mContext.getPackageName().equals(pkgName) && !pkgs.contains(pkgName)) {
                        pkgs.add(pkgName);
                    }
                }
            }
        }
        return pkgs;
    }

    public ArrayList<ProcessItem> readRunningAppInfo(Context inContext) {
        Log.i("MemClear", System.currentTimeMillis() + " ...readRunningAppInfo start");
        ArrayList<ProcessItem> zhengYunXingList = new ArrayList<>();
        ArrayList<String> listRunningPkg = new ArrayList<>();
        PackageManager pkgManager = inContext.getPackageManager();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) inContext.getSystemService("activity")).getRunningAppProcesses();
        for (int i = 0; i < runningAppProcesses.size(); i++) {
            ActivityManager.RunningAppProcessInfo rapInfo = runningAppProcesses.get(i);
            if (rapInfo.importance <= 100) {
                Log.i("MemClear", "...readRunningAppInfo...importance==" + rapInfo.importance + " " + rapInfo.pkgList[0]);
            } else {
                String[] pkgList = rapInfo.pkgList;
                for (int j = 0; j < pkgList.length; j++) {
                    try {
                        String pkgName = pkgList[j];
                        ApplicationInfo appInfo = pkgManager.getApplicationInfo(pkgName, 0);
                        if (listRunningPkg.contains(pkgName)) {
                            Log.i("MemClear", "...readRunningAppInfo...duplicate == " + pkgName);
                        } else {
                            listRunningPkg.add(pkgName);
                            if (!this.myLaunchers.contains(pkgName)) {
                                Log.i("MemClear", "...readRunningAppInfo...invisible in launcher == " + pkgName);
                            } else if (this.launchers.contains(pkgName)) {
                                Log.i("MemClear", "...readRunningAppInfo...laucher app == " + pkgName);
                            } else if (this.inputMethodApps.contains(pkgName)) {
                                Log.i("MemClear", "...readRunningAppInfo...input method app == " + pkgName);
                            } else if (checkBindWallpaper(pkgManager, pkgName)) {
                                Log.i("MemClear", "...readRunningAppInfo...wall paper app " + pkgName);
                            } else if (userWhiteList == null || !userWhiteList.contains(pkgName)) {
                                if (userWhiteList == null) {
                                    Log.i("MemClear", "userWhiteList == null");
                                }
                                if (!inContext.getPackageName().equals(pkgName) && !"com.lenovo.safecenter".equals(pkgName)) {
                                    zhengYunXingList.add(new ProcessItem(ThumbnailUtils.extractThumbnail(((BitmapDrawable) appInfo.loadIcon(pkgManager)).getBitmap(), 60, 60), ((Object) appInfo.loadLabel(pkgManager)) + "", pkgList[j], rapInfo.pid));
                                }
                            } else {
                                Log.i("MemClear", "userWhiteList == " + pkgName);
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Log.i("MemClear", System.currentTimeMillis() + " ...readRunningAppInfo end");
        return zhengYunXingList;
    }

    private ArrayList<String> getInstalledLauncher() {
        ArrayList<String> res = new ArrayList<>();
        PackageManager pm = this.mContext.getPackageManager();
        Intent it = new Intent("android.intent.action.MAIN");
        it.addCategory("android.intent.category.HOME");
        for (ResolveInfo ri : pm.queryIntentActivities(it, 0)) {
            if (!ri.activityInfo.packageName.equals("com.lbe.security")) {
                res.add(ri.activityInfo.packageName);
            }
        }
        return res;
    }

    private ArrayList<String> getInputMethodApp() {
        ArrayList<String> list = new ArrayList<>();
        for (InputMethodInfo i : ((InputMethodManager) this.mContext.getSystemService("input_method")).getInputMethodList()) {
            list.add(i.getPackageName());
        }
        return list;
    }

    private ArrayList<String> initLauncherList() {
        ArrayList<String> list = new ArrayList<>();
        Intent it = new Intent("android.intent.action.MAIN");
        it.addCategory("android.intent.category.LAUNCHER");
        for (ResolveInfo f : this.mContext.getPackageManager().queryIntentActivities(it, 0)) {
            list.add(f.activityInfo.packageName);
        }
        return list;
    }

    private boolean checkBindWallpaper(PackageManager pm, String pkgname) {
        PackageInfo pi;
        if ("com.google.android.apps.maps".equals(pkgname)) {
            return false;
        }
        if (pm.checkPermission("android.permission.BIND_WALLPAPER", pkgname) == 0) {
            return true;
        }
        try {
            pi = pm.getPackageInfo(pkgname, 4);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (pi.services == null) {
            return false;
        }
        ServiceInfo[] arr$ = pi.services;
        for (ServiceInfo si : arr$) {
            if (si.permission != null && si.permission.equals("android.permission.BIND_WALLPAPER")) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> initXmlWhiteList(Context context) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(context.getPackageName());
            if (resourcesForApplication == null) {
                return list;
            }
            NodeList process_nodes = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(resourcesForApplication.getAssets().open("application_filter.xml")).getDocumentElement().getElementsByTagName("ProcessNameItem");
            for (int i = 0; i < process_nodes.getLength(); i++) {
                String pro_name = ((Element) process_nodes.item(i)).getAttribute("name");
                Log.i("MemClear", "i=" + i + "\t--pro_name=" + pro_name);
                list.add(pro_name);
            }
            return list;
        } catch (PackageManager.NameNotFoundException e) {
            ArrayList<String> list2 = initAppList();
            e.printStackTrace();
            return list2;
        } catch (IOException e2) {
            ArrayList<String> list3 = initAppList();
            e2.printStackTrace();
            return list3;
        } catch (ParserConfigurationException e3) {
            ArrayList<String> list4 = initAppList();
            e3.printStackTrace();
            return list4;
        } catch (SAXException e4) {
            ArrayList<String> list5 = initAppList();
            e4.printStackTrace();
            return list5;
        }
    }

    private ArrayList<String> initAppList() {
        ArrayList<String> appList = new ArrayList<>();
        appList.add("com.android.mms");
        appList.add("com.android.mms");
        appList.add("com.android.settings");
        appList.add("com.lenovo.app.Calendar");
        appList.add("com.google.android.calendar");
        appList.add("com.lenovomobile.deskclock");
        appList.add("com.android.deskclock");
        appList.add("com.android.contacts");
        appList.add("com.android.stk");
        appList.add("com.tencent.mobileqq");
        appList.add("com.tencent.qq");
        appList.add("com.tencent.mm");
        appList.add("com.duomi.androidarizona");
        appList.add("com.duomi.android");
        appList.add("com.lenovo.safecenter");
        appList.add("com.lenovo.safecenter.plugin");
        appList.add("com.lenovo.safebox");
        return appList;
    }

    private void killProcessForSystemAuthority(ProcessItem item) {
        if (item != null) {
            ActivityManager am = (ActivityManager) this.mContext.getApplicationContext().getSystemService("activity");
            try {
                Method forceStopPackage = am.getClass().getDeclaredMethod("forceStopPackage", String.class);
                forceStopPackage.setAccessible(true);
                forceStopPackage.invoke(am, item.pkgName);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e2) {
                e2.printStackTrace();
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
    }

    private boolean killProcessForSystemAuthority(String pkgName) {
        ActivityManager am = (ActivityManager) this.mContext.getApplicationContext().getSystemService("activity");
        try {
            Method forceStopPackage = am.getClass().getDeclaredMethod("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);
            forceStopPackage.invoke(am, pkgName);
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return false;
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    public void killProcessForUserAuthority(ActivityManager am, String pkgName) {
        if (pkgName != null) {
            am.killBackgroundProcesses(pkgName);
        }
    }

    public static boolean exeCmd(Context context, String fileName) {
        boolean success = false;
        try {
            Socket client = new Socket("127.0.0.1", 30001);
            PrintWriter socketWriter = new PrintWriter(client.getOutputStream(), true);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            socketWriter.write(fileName);
            socketWriter.flush();
            String a = socketReader.readLine();
            Log.i("MemClear", "exeCmd: " + a);
            success = a.startsWith("success");
            socketWriter.close();
            socketReader.close();
            client.close();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                LocalSocketAddress address = new LocalSocketAddress("nac_server");
                LocalSocket localSocket = new LocalSocket();
                localSocket.connect(address);
                PrintWriter socketWriter2 = new PrintWriter(localSocket.getOutputStream(), true);
                BufferedReader socketReader2 = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
                socketWriter2.write(fileName);
                socketWriter2.flush();
                String a2 = socketReader2.readLine();
                Log.i("MemClear", "exeCmd: " + a2);
                success = a2.startsWith("success");
                socketWriter2.close();
                socketReader2.close();
                localSocket.close();
                return success;
            } catch (IOException e2) {
                e2.printStackTrace();
                return success;
            }
        }
    }

    @Deprecated
    public void killProcessByPm(String pkgName) {
        if (pkgName != null) {
            PackageManager pm = this.mContext.getPackageManager();
            try {
                pm.setApplicationEnabledSetting(pkgName, 2, 0);
                pm.setApplicationEnabledSetting(pkgName, 1, 0);
            } catch (Exception e) {
            }
        }
    }

    public void refreshWidget() {
        this.mContext.sendBroadcast(new Intent(MemClear4X1.ACTION_REFRESH));
    }
}

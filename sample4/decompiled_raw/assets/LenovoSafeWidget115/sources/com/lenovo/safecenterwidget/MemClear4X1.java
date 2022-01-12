package com.lenovo.safecenterwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class MemClear4X1 extends AppWidgetProvider implements RefreshWidget {
    private static final String ACTION_CLEAR = "com.lenovo.safewidget.memory.clear";
    public static final String ACTION_ENTRY_SAFEENTER = "com.lenovo.safewidget.memory.entrysafecenter";
    private static final String ACTION_EXE_CLEAR_SHORTCUT = "com.lenovo.safecenter.PERFORMANCE_EXE_SHORTCUT";
    private static final String ACTION_GET_KILL_RESULT = "com.lenovo.safecenter.PERFORMANCE_GET_KILL_RESULT";
    private static final String ACTION_KILL_PROCESS = "com.lenovo.safewidget.memory.killprocess";
    public static final String ACTION_REFRESH = "com.lenovo.safewidget.memory.refresh";
    private static final String EXTRA_END_MEMORY = "com.lenovo.safecenter.PERFORMANCE_SHORTCUT_END_MEMORY";
    private static final String EXTRA_KILL_COUNT = "com.lenovo.safecenter.PERFORMANCE_KILL_COUNT";
    private static final int[] ITEM_IDS = {R.id.image00, R.id.image01, R.id.image02, R.id.image03, R.id.image04, R.id.image05, R.id.image06, R.id.image07, R.id.image08, R.id.image09};
    private static final int MAX_SIZE = ITEM_IDS.length;
    public static final int MSG_AFTER_CLEAR_UI = 4;
    private static final int MSG_AFTER_CLEAR_UI_FINAL = 8;
    private static final int MSG_IS_CLEARING = 3;
    private static final int MSG_IS_REFRESHING = 9;
    private static final int MSG_NO_APP = 6;
    private static final int MSG_REFRESH = 1;
    private static final int MSG_STOP_APP = 7;
    private static final int MSG_UI_APPS = 2;
    private static final int MSG_UI_FINE = 5;
    public static final String TAG = "MemClear";
    private static ArrayList<ProcessItem> processCache;
    private ComponentName mComponentName;
    private Context mContext;
    private Handler mHander = new Handler() { // from class: com.lenovo.safecenterwidget.MemClear4X1.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MemClear4X1.MSG_REFRESH /* 1 */:
                    Log.i("MemClear", "...MSG_REFRESH");
                    MemClear4X1.this.refresh();
                    return;
                case MemClear4X1.MSG_UI_APPS /* 2 */:
                    Log.i("MemClear", System.currentTimeMillis() + " ...MSG_UI_APPS");
                    MemClear4X1.this.mRemoteView.setViewVisibility(R.id.apps_text, MemClear4X1.MSG_AFTER_CLEAR_UI_FINAL);
                    MemClear4X1.this.refreshAppsViewAndScalView();
                    MemClear4X1.this.updateWidget();
                    return;
                case MemClear4X1.MSG_IS_CLEARING /* 3 */:
                    MemClear4X1.this.mRemoteView.setViewVisibility(R.id.apps_text, MemClear4X1.MAX_SIZE);
                    MemClear4X1.this.mRemoteView.setTextViewText(R.id.apps_text, MemClear4X1.this.mContext.getString(R.string.is_in_clear));
                    MemClear4X1.this.buildBind();
                    MemClear4X1.this.updateWidget();
                    return;
                case MemClear4X1.MSG_AFTER_CLEAR_UI /* 4 */:
                    Log.i("MemClear", System.currentTimeMillis() + "...MSG_AFTER_CLEAR_UI");
                    Utils.recordKillEvent(MemClear4X1.this.mContext, System.currentTimeMillis(), MemClear4X1.MSG_NO_APP);
                    MemClear4X1.this.mRemoteView.setViewVisibility(R.id.apps_text, MemClear4X1.MAX_SIZE);
                    if (msg.arg1 != 0) {
                        MemClear4X1.this.mRemoteView.setTextViewText(R.id.apps_text, MemClear4X1.this.mContext.getString(R.string.app_kill) + msg.arg1 + MemClear4X1.this.mContext.getString(R.string.app_number));
                    } else {
                        MemClear4X1.this.mRemoteView.setTextViewText(R.id.apps_text, MemClear4X1.this.mContext.getString(R.string.app_is_fine));
                    }
                    MemClear4X1.this.buildProgressBar();
                    MemClear4X1.this.buildBind();
                    MemClear4X1.this.updateWidget();
                    Utils.setCanClick(true);
                    Log.i("MemClear", "...MSG_AFTER_CLEAR_UI...canClick == true");
                    Utils.setCanShowFinalUI(true);
                    MemClear4X1.this.mHander.sendEmptyMessageDelayed(MemClear4X1.MSG_AFTER_CLEAR_UI_FINAL, 2000);
                    return;
                case MemClear4X1.MSG_UI_FINE /* 5 */:
                    MemClear4X1.this.mRemoteView.setViewVisibility(R.id.apps_text, MemClear4X1.MAX_SIZE);
                    MemClear4X1.this.mRemoteView.setTextViewText(R.id.apps_text, MemClear4X1.this.mContext.getString(R.string.app_no_app_to_clear));
                    if (msg.arg1 != 0) {
                        MemClear4X1.this.buildProgressBar(msg.arg1);
                    } else {
                        MemClear4X1.this.buildProgressBar();
                    }
                    MemClear4X1.this.buildBind();
                    MemClear4X1.this.updateWidget();
                    return;
                case MemClear4X1.MSG_NO_APP /* 6 */:
                    Intent intent = new Intent(MemClear4X1.this.mContext, DownloadLeSafeActivity.class);
                    intent.setFlags(268435456);
                    MemClear4X1.this.mContext.startActivity(intent);
                    return;
                case MemClear4X1.MSG_STOP_APP /* 7 */:
                    Intent i = new Intent(MemClear4X1.this.mContext, SMSNotifyActivity.class);
                    i.setFlags(268435456);
                    MemClear4X1.this.mContext.startActivity(i);
                    return;
                case MemClear4X1.MSG_AFTER_CLEAR_UI_FINAL /* 8 */:
                    if (Utils.getCanShowFinalUI()) {
                        MemClear4X1.this.mRemoteView.setViewVisibility(R.id.apps_text, MemClear4X1.MAX_SIZE);
                        MemClear4X1.this.mRemoteView.setTextViewText(R.id.apps_text, MemClear4X1.this.mContext.getString(R.string.app_no_app_to_clear));
                        MemClear4X1.this.buildBind();
                        MemClear4X1.this.updateWidget();
                        return;
                    }
                    return;
                case MemClear4X1.MSG_IS_REFRESHING /* 9 */:
                    MemClear4X1.this.mRemoteView.setViewVisibility(R.id.apps_text, MemClear4X1.MAX_SIZE);
                    MemClear4X1.this.mRemoteView.setTextViewText(R.id.apps_text, "正在刷新...");
                    MemClear4X1.this.buildBind();
                    MemClear4X1.this.updateWidget();
                    return;
                default:
                    return;
            }
        }
    };
    private RemoteViews mRemoteView;
    private ScanAppInfo mScanAppInfo;

    @Override // android.appwidget.AppWidgetProvider
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i("MemClear", "---> onEnabled");
        if (Utils.nacServerIsExist()) {
            try {
                context.getPackageManager().getPackageInfo("com.lenovo.safewidget", MAX_SIZE);
                Utils.delApp(context, "com.lenovo.safewidget", null);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // android.appwidget.AppWidgetProvider
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.i("MemClear", "---> onUpdate");
        for (int i = MAX_SIZE; i < appWidgetIds.length; i += MSG_REFRESH) {
            Log.i("MemClear", "appWidgetIds[" + i + "] == " + appWidgetIds[i]);
        }
        init(context);
        refreshAppsViewAndScalView();
        updateWidget();
        TrackEvent.initialize(context);
    }

    @Override // android.appwidget.AppWidgetProvider, android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        Log.i("MemClear", System.currentTimeMillis() + " ---> onReceive...action == " + action);
        if (action.equals(ACTION_KILL_PROCESS)) {
            init(context);
            int index = Integer.parseInt(intent.getData().getEncodedSchemeSpecificPart());
            if (index < processCache.size() && index >= 0) {
                ProcessItem item = processCache.get(index);
                if (item.pkgName != null) {
                    if (Utils.isLenovoSafeCenterCanBeUsed(this.mContext)) {
                        Intent intent1 = new Intent("com.lenovo.safecenter.PERFORMANCE_KILL_SINGLE_PROCESS");
                        intent1.putExtra("com.lenovo.safecenter.PERFORMANCE_SINGLE_PROCESS_PKGNAME", item.pkgName);
                        this.mContext.sendBroadcast(intent1);
                    }
                    this.mScanAppInfo.killSingleProcess(Utils.nacServerIsExist(), item.pkgName);
                    processCache.remove(index);
                    this.mHander.sendEmptyMessage(MSG_UI_APPS);
                    TrackEvent.reportWidgetKillOneApp(item.pkgName);
                }
            }
        } else if (action.equals(ACTION_CLEAR)) {
            Log.i("MemClear", "canClick == " + Utils.getCanClick());
            if (Utils.getCanClick()) {
                Utils.setCanShowFinalUI(false);
                init(context);
                Utils.setCanClick(false);
                this.mHander.sendEmptyMessage(MSG_IS_CLEARING);
                long interval = System.currentTimeMillis() - Utils.getLastKillTime(this.mContext);
                if (interval > 0 && interval <= Utils.CLICK_INTERVAL) {
                    Message msg = new Message();
                    msg.what = 4;
                    msg.arg1 = MAX_SIZE;
                    this.mHander.sendMessageDelayed(msg, 2000);
                } else if (Utils.isLenovoSafeCenterCanBeUsed(this.mContext)) {
                    this.mContext.sendBroadcast(new Intent("com.lenovo.safecenter.PERFORMANCE_KILL_ALL_PROCESSES"));
                    Intent intent2 = new Intent("com.lenovo.safecenterwidget.RECORD_KILL_EVENT");
                    intent2.putExtra("com.lenovo.safecenterwidget.KILL_TIME", System.currentTimeMillis());
                    intent2.putExtra("com.lenovo.safecenterwidget.KILL_POSITION", MSG_NO_APP);
                    this.mContext.sendBroadcast(intent2);
                    Log.i("MemClear", "...isLenovoSafeCenterCanBeUsed == true");
                } else {
                    this.mScanAppInfo.killAllProcess(Utils.nacServerIsExist(), this.mHander);
                    Log.i("MemClear", "...isLenovoSafeCenterCanBeUsed == false");
                    Log.i("MemClear", "...nacServerIsExist == " + Utils.nacServerIsExist());
                }
                TrackEvent.reportWidgetKillAllApp();
            }
        } else if (action.equals(ACTION_REFRESH)) {
            Log.i("MemClear", System.currentTimeMillis() + " canClick == " + Utils.getCanClick());
            if (Utils.getCanClick()) {
                Utils.setCanShowFinalUI(false);
                init(context);
                refresh();
                TrackEvent.reportWidgetRefresh();
            }
        } else if (action.equals(ACTION_ENTRY_SAFEENTER)) {
            init(context);
            try {
                ApplicationInfo info = context.getPackageManager().getApplicationInfo("com.lenovo.safecenter", 129);
                Log.i("MemClear", "...info=" + info);
                if (info != null) {
                    Intent i = new Intent();
                    i.setComponent(new ComponentName("com.lenovo.safecenter", "com.lenovo.safecenter.MainTab.SplashActivity"));
                    i.setFlags(268468224);
                    this.mContext.startActivity(i);
                } else {
                    this.mHander.sendEmptyMessage(MSG_NO_APP);
                }
            } catch (PackageManager.NameNotFoundException e) {
                this.mHander.sendEmptyMessage(MSG_NO_APP);
                e.printStackTrace();
            } catch (Exception e2) {
                this.mHander.sendEmptyMessage(MSG_STOP_APP);
                e2.printStackTrace();
            }
            TrackEvent.reportWidgetEntrySafeCenter();
        } else if ("android.intent.action.USER_PRESENT".equals(action)) {
            Utils.setCanShowFinalUI(false);
            init(context);
            refresh();
        } else if (ACTION_GET_KILL_RESULT.equals(action)) {
            init(context);
            int[] killedCount = intent.getIntArrayExtra(EXTRA_KILL_COUNT);
            Message msg2 = new Message();
            msg2.what = 4;
            msg2.arg1 = killedCount[MAX_SIZE];
            this.mHander.sendMessageDelayed(msg2, 1000);
        } else if (ACTION_EXE_CLEAR_SHORTCUT.equals(action)) {
            Utils.setCanShowFinalUI(false);
            init(context);
            int endMemory = intent.getIntExtra(EXTRA_END_MEMORY, MAX_SIZE);
            Message msg3 = new Message();
            msg3.what = MSG_UI_FINE;
            msg3.arg1 = endMemory;
            this.mHander.sendMessage(msg3);
        } else if ("com.lenovo.safecenterwidget.ok".equals(action)) {
            init(context);
            processCache = this.mScanAppInfo.readRunningAppInfo(this.mContext);
            Log.i("MemClear", "isLenovoSafeCenterCanBeUsed == true appSize == " + processCache.size());
            this.mHander.sendEmptyMessage(MSG_UI_APPS);
        } else if ("com.lenovo.safecenter.PERFORMANCE_RECORD_KILL_EVENT".equals(action)) {
            init(context);
            long killTime = intent.getLongExtra("com.lenovo.safecenter.PERFORMANCE_KILL_TIME", 0);
            int killPosition = intent.getIntExtra("com.lenovo.safecenter.PERFORMANCE_KILL_POSITION", MAX_SIZE);
            Log.i("MemClear", "...KILL_EVENT...killTime == " + killTime + " killPosition == " + killPosition);
            if (killTime != 0 && killPosition != 0) {
                Utils.recordKillEvent(this.mContext, killTime, killPosition);
            }
        }
    }

    @Override // com.lenovo.safecenterwidget.RefreshWidget
    public void refresh() {
        Log.i("MemClear", System.currentTimeMillis() + "...refresh()");
        this.mHander.sendEmptyMessage(MSG_IS_REFRESHING);
        new Thread(new Runnable() { // from class: com.lenovo.safecenterwidget.MemClear4X1.2
            @Override // java.lang.Runnable
            public void run() {
                long interval = System.currentTimeMillis() - Utils.getLastKillTime(MemClear4X1.this.mContext);
                if (interval > 0 && interval <= Utils.CLICK_INTERVAL) {
                    Log.i("MemClear", "...refresh()...INTERVAL");
                    ArrayList unused = MemClear4X1.processCache = new ArrayList();
                    MemClear4X1.this.mHander.sendEmptyMessage(MemClear4X1.MSG_UI_APPS);
                } else if (Utils.isLenovoSafeCenterCanBeUsed(MemClear4X1.this.mContext)) {
                    MemClear4X1.this.mContext.startService(new Intent(MemClear4X1.this.mContext, AppGetService.class));
                } else {
                    ArrayList unused2 = MemClear4X1.processCache = MemClear4X1.this.mScanAppInfo.scanApps();
                    Log.i("MemClear", "isLenovoSafeCenterCanBeUsed == false appSize == " + MemClear4X1.processCache.size());
                    MemClear4X1.this.mHander.sendEmptyMessage(MemClear4X1.MSG_UI_APPS);
                }
            }
        }).start();
    }

    private void init(Context context) {
        Log.i("MemClear", System.currentTimeMillis() + "init()");
        if (this.mContext == null) {
            this.mContext = context;
        }
        if (this.mRemoteView == null) {
            this.mRemoteView = new RemoteViews(context.getPackageName(), (int) R.layout.widget_memory);
        }
        if (this.mComponentName == null) {
            this.mComponentName = new ComponentName(context, MemClear4X1.class);
        }
        if (this.mScanAppInfo == null) {
            Log.i("MemClear", System.currentTimeMillis() + "init()...mScanAppInfo == null");
            this.mScanAppInfo = ScanAppInfo.getInstance(this, context);
        }
        if (processCache != null) {
            return;
        }
        if (Utils.isLenovoSafeCenterCanBeUsed(this.mContext)) {
            Log.i("MemClear", "init()...isLenovoSafeCenterCanBeUsed == true");
            this.mContext.startService(new Intent(context, AppGetService.class));
            processCache = this.mScanAppInfo.readRunningAppInfo(this.mContext);
            Log.i("MemClear", "init()...isLenovoSafeCenterCanBeUsed == true appCount == " + processCache.size());
            return;
        }
        Log.i("MemClear", "init()...isLenovoSafeCenterCanBeUsed == false");
        processCache = this.mScanAppInfo.scanApps();
        Log.i("MemClear", "init()...isLenovoSafeCenterCanBeUsed == false appCount == " + processCache.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshAppsViewAndScalView() {
        if (this.mRemoteView != null) {
            int SIZE = processCache.size();
            if (SIZE <= MAX_SIZE) {
                buildAppListView(SIZE);
            } else {
                buildAppListView(MAX_SIZE);
            }
            buildProgressBar();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void buildProgressBar() {
        int[] memInfo = this.mScanAppInfo.getTotalMemory();
        this.mRemoteView.setProgressBar(R.id.progressbar, 100, memInfo[MAX_SIZE], false);
        this.mRemoteView.setTextViewText(R.id.progressbar_text, memInfo[MSG_UI_APPS] + "M/" + memInfo[MSG_REFRESH] + "M");
        this.mRemoteView.setTextViewText(R.id.progressbar_progress, memInfo[MAX_SIZE] + " %");
        Log.i("MemClear", "memScale == " + memInfo[MAX_SIZE] + " memUsed == " + memInfo[MSG_UI_APPS] + " memTotal == " + memInfo[MSG_REFRESH]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void buildProgressBar(int usedMemoryScale) {
        int totalMemory = this.mScanAppInfo.getTotalMemory()[MSG_REFRESH];
        int usedMemory = (usedMemoryScale * totalMemory) / 100;
        this.mRemoteView.setProgressBar(R.id.progressbar, 100, usedMemoryScale, false);
        this.mRemoteView.setTextViewText(R.id.progressbar_text, usedMemory + "M/" + totalMemory + "M");
        this.mRemoteView.setTextViewText(R.id.progressbar_progress, usedMemoryScale + " %");
        Log.i("MemClear", "memScale == " + usedMemoryScale + " memUsed == " + usedMemory + " memTotal == " + totalMemory);
    }

    private synchronized void buildAppListView(int count) {
        if (count == 0) {
            this.mRemoteView.setViewVisibility(R.id.apps_text, MAX_SIZE);
            this.mRemoteView.setTextViewText(R.id.apps_text, this.mContext.getString(R.string.app_no_app_to_clear));
        } else {
            this.mRemoteView.setViewVisibility(R.id.apps_text, MSG_AFTER_CLEAR_UI_FINAL);
        }
        for (int i = MAX_SIZE; i < MAX_SIZE; i += MSG_REFRESH) {
            this.mRemoteView.setViewVisibility(ITEM_IDS[i], MSG_AFTER_CLEAR_UI_FINAL);
        }
        for (int i2 = MAX_SIZE; i2 < processCache.size() && i2 < MAX_SIZE; i2 += MSG_REFRESH) {
            this.mRemoteView.setImageViewBitmap(ITEM_IDS[i2], processCache.get(i2).mIcon);
            this.mRemoteView.setViewVisibility(ITEM_IDS[i2], MAX_SIZE);
            Intent intent = new Intent(ACTION_KILL_PROCESS);
            intent.addCategory("android.intent.category.ALTERNATIVE");
            intent.setClass(this.mContext, MemClear4X1.class);
            intent.putExtra("imageId", ITEM_IDS[i2]);
            intent.setData(Uri.parse("index:" + i2));
            this.mRemoteView.setOnClickPendingIntent(ITEM_IDS[i2], PendingIntent.getBroadcast(this.mContext, MAX_SIZE, intent, MAX_SIZE));
        }
        buildBind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void buildBind() {
        Intent btnIntent = new Intent(ACTION_CLEAR);
        btnIntent.addCategory("android.intent.category.ALTERNATIVE");
        btnIntent.setClass(this.mContext, MemClear4X1.class);
        this.mRemoteView.setOnClickPendingIntent(R.id.clearBtn, PendingIntent.getBroadcast(this.mContext, MAX_SIZE, btnIntent, MAX_SIZE));
        Intent refIntent = new Intent(ACTION_REFRESH);
        refIntent.addCategory("android.intent.category.ALTERNATIVE");
        refIntent.setClass(this.mContext, MemClear4X1.class);
        this.mRemoteView.setOnClickPendingIntent(R.id.refreshBtn, PendingIntent.getBroadcast(this.mContext, MAX_SIZE, refIntent, MAX_SIZE));
        Intent intentSafeCenter = new Intent(ACTION_ENTRY_SAFEENTER);
        intentSafeCenter.addCategory("android.intent.category.ALTERNATIVE");
        intentSafeCenter.setClass(this.mContext, MemClear4X1.class);
        this.mRemoteView.setOnClickPendingIntent(R.id.mem_safecenter, PendingIntent.getBroadcast(this.mContext, MAX_SIZE, intentSafeCenter, MAX_SIZE));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWidget() {
        AppWidgetManager.getInstance(this.mContext).updateAppWidget(this.mComponentName, this.mRemoteView);
        Log.i("MemClear", "AppWidgetManager.updateAppWidget remoteView == " + this.mRemoteView);
    }

    @Override // android.appwidget.AppWidgetProvider
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.i("MemClear", "---> onDelete");
        TrackEvent.shutdown();
    }

    @Override // com.lenovo.safecenterwidget.RefreshWidget
    public Handler getHandler() {
        return this.mHander;
    }
}

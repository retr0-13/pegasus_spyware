package com.lenovo.safecenterwidget;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.lenovo.performancecenter.framework.ICustomerWhiteListService;
/* loaded from: classes.dex */
public class AppGetService extends Service {
    protected static final String TAG = "MemClear";

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.i("MemClear", "AppGetService ---> onCreate");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MemClear", "AppGetService ---> onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override // android.app.Service
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i("MemClear", "AppGetService ---> onStart");
        getApplicationContext().bindService(new Intent("com.lenovo.performancecenter.framework.CustomerWhiteListService"), new ServiceConnection() { // from class: com.lenovo.safecenterwidget.AppGetService.1
            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                Log.i("MemClear", "AppGetService ---> onStart...onServiceDisconnected");
                AppGetService.this.stopService(new Intent(AppGetService.this.getApplicationContext(), AppGetService.class));
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("MemClear", System.currentTimeMillis() + " AppGetService ---> onStart...onServiceConnected");
                try {
                    ScanAppInfo.userWhiteList = ICustomerWhiteListService.Stub.asInterface(service).getCustomerWhiteList();
                    Log.i("MemClear", System.currentTimeMillis() + " AppGetService ---> onStart...userWhiteList.appCount == " + ScanAppInfo.userWhiteList.size());
                    AppGetService.this.sendBroadcast(new Intent("com.lenovo.safecenterwidget.ok"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 1);
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.i("MemClear", "AppGetService ---> onDestroy");
        super.onDestroy();
    }
}

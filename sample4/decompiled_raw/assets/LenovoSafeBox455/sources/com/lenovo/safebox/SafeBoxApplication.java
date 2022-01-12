package com.lenovo.safebox;

import android.app.Application;
import android.content.Context;
/* loaded from: classes.dex */
public class SafeBoxApplication extends Application {
    public static String NAC_DONE;
    public static Context mContext;

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        NAC_DONE = "com.lenovo.safebox.NAC_DONE";
        PrivateSpaceTools.isLenovoProduct = PrivateSpaceTools.isLenovo();
    }
}

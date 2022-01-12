package com.lenovo.lps.sus.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.lenovo.lps.sus.b.b;
import com.lenovo.lps.sus.c.a;
import com.lenovo.safebox.PrivateSpaceHelper;
/* loaded from: classes.dex */
public class SUSReceiver extends BroadcastReceiver {
    private static Handler a = null;

    public static void a(Handler handler) {
        a = handler;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.lenovo.lps.sus.ACTION_UPDATE")) {
            Bundle extras = intent.getExtras();
            String string = extras.getString(PrivateSpaceHelper.FILE);
            String string2 = extras.getString("packagename");
            String f = a.f(context);
            if (string2 != null && f != null && f.equals(string2)) {
                Message message = new Message();
                message.what = b.a(b.SUS_INSTALLAPK_EVENT);
                message.obj = string;
                if (a != null) {
                    a.sendMessage(message);
                }
            }
        }
    }
}

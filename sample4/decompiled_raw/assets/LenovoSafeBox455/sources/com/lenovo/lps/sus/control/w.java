package com.lenovo.lps.sus.control;

import android.os.Handler;
import android.os.Message;
import com.lenovo.lps.sus.b.b;
import com.lenovo.lps.sus.c.a;
/* loaded from: classes.dex */
public class w extends Thread {
    private Handler a;

    public w(Handler handler) {
        this.a = null;
        this.a = handler;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        String a = a.a(a.n, (Boolean) true, 3000);
        String str = (a == null || a.length() <= 0 || !"OK".equals(a)) ? "FAIL" : "SUCCESS";
        Message message = new Message();
        message.what = b.a(b.SUS_TESTSUSSERVER_EVENT);
        message.obj = str;
        if (this.a != null) {
            this.a.sendMessage(message);
        }
    }
}

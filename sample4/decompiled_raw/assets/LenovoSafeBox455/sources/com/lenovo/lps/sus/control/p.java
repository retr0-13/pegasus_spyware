package com.lenovo.lps.sus.control;

import android.os.Handler;
import android.os.Message;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
/* loaded from: classes.dex */
public class p extends Thread {
    private Handler a;
    private String b;

    public p(Handler handler, String str) {
        this.a = null;
        this.b = null;
        this.a = handler;
        this.b = str;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (this.b == null) {
            b.b(a.b, "myReqAppNewVersionURL == null!!");
            return;
        }
        String a = a.a(this.b, (Boolean) true, 5000);
        if (a == null || a.contains("<html><head>") || ((a.contains("<html>") && a.contains("<head>")) || (!a.startsWith("SUCCESS") && !a.startsWith("LATEST") && !a.startsWith("WARNING") && !a.startsWith("EXCEPTION") && !a.startsWith("NOTFIND")))) {
            if (this.a != null) {
                Message message = new Message();
                message.what = com.lenovo.lps.sus.b.b.a(com.lenovo.lps.sus.b.b.SUS_QUERY_EXCEPTION_EVENT);
                message.obj = null;
                this.a.sendMessage(message);
            }
        } else if (this.a != null) {
            Message message2 = new Message();
            message2.what = com.lenovo.lps.sus.b.b.a(com.lenovo.lps.sus.b.b.SUS_REQNEWAPPVERSION_RESPONE_EVENT);
            message2.obj = a;
            this.a.sendMessage(message2);
        }
    }
}

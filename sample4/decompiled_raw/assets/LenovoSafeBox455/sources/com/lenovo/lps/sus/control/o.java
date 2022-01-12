package com.lenovo.lps.sus.control;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import com.lenovo.lps.sus.b.b;
/* loaded from: classes.dex */
class o implements DialogInterface.OnClickListener {
    private final Handler a;

    o(Handler handler) {
        this.a = handler;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        Message message = new Message();
        message.what = b.a(b.SUS_USER_REQUPDATE_EVENT);
        message.obj = 0;
        this.a.sendMessage(message);
    }
}

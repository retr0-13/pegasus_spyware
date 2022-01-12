package com.lenovo.lps.sus.d;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.lenovo.lps.sus.c.a;
import com.lenovo.safebox.PrivateSpaceHelper;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class d extends Handler {
    final /* synthetic */ c a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(c cVar) {
        this.a = cVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        switch (message.what) {
            case -1:
                Toast.makeText(this.a.b, "error", 1).show();
                return;
            case 0:
            default:
                return;
            case 1:
                int i = message.getData().getInt("progress");
                if (this.a.a != null) {
                    this.a.a.a(i);
                    return;
                }
                return;
            case 2:
                Intent intent = new Intent("com.lenovo.lps.sus.ACTION_UPDATE");
                intent.addFlags(268435456);
                Bundle bundle = new Bundle();
                bundle.putString(PrivateSpaceHelper.FILE, (String) message.obj);
                String str = null;
                if (this.a.b != null) {
                    str = a.f(this.a.b);
                }
                bundle.putString("packagename", str);
                intent.putExtras(bundle);
                if (this.a.b != null) {
                    this.a.b.sendBroadcast(intent);
                }
                if (this.a.a != null) {
                    this.a.a.b();
                    return;
                }
                return;
            case 3:
                boolean booleanValue = ((Boolean) message.obj).booleanValue();
                if (this.a.a != null) {
                    this.a.a.a(booleanValue);
                    return;
                }
                return;
        }
    }
}

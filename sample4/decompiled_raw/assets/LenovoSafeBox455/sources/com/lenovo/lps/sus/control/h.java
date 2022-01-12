package com.lenovo.lps.sus.control;

import android.content.DialogInterface;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class h implements DialogInterface.OnClickListener {
    final /* synthetic */ SUSNotificationActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public h(SUSNotificationActivity sUSNotificationActivity) {
        this.a = sUSNotificationActivity;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        if (!(this.a.l == null || this.a.m == null)) {
            this.a.l.removeCallbacks(this.a.m);
        }
        r.b();
        SUSNotificationActivity.j = 3;
        dialogInterface.dismiss();
    }
}

package com.lenovo.lps.sus.control;

import android.content.DialogInterface;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class f implements DialogInterface.OnCancelListener {
    final /* synthetic */ SUSNotificationActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public f(SUSNotificationActivity sUSNotificationActivity) {
        this.a = sUSNotificationActivity;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        b.a(a.b, "SUSNotificationActivity popupPromptionDialog CancelListener");
        if (!(this.a.l == null || this.a.m == null)) {
            this.a.l.removeCallbacks(this.a.m);
        }
        SUSNotificationActivity.j = 3;
        dialogInterface.dismiss();
    }
}

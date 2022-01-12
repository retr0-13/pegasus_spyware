package com.lenovo.lps.sus.control;

import android.app.Activity;
import android.content.DialogInterface;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class g implements DialogInterface.OnDismissListener {
    final /* synthetic */ SUSNotificationActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public g(SUSNotificationActivity sUSNotificationActivity) {
        this.a = sUSNotificationActivity;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        b.a(a.b, "SUSNotificationActivity popupPromptionDialog onDismiss myCustomDialog=" + SUSNotificationActivity.e + "; myContext=" + this.a.f);
        if (!(this.a.l == null || this.a.m == null)) {
            this.a.l.removeCallbacks(this.a.m);
        }
        SUSNotificationActivity.j = 3;
        if (this.a.f != null) {
            ((Activity) this.a.f).finish();
        }
    }
}

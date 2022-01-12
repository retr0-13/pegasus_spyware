package com.lenovo.lps.sus.control;

import android.app.Activity;
import android.content.DialogInterface;
import com.lenovo.lps.sus.c.a;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class b implements DialogInterface.OnDismissListener {
    final /* synthetic */ SUSPromptActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public b(SUSPromptActivity sUSPromptActivity) {
        this.a = sUSPromptActivity;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        com.lenovo.lps.sus.c.b.a(a.b, "SUSPromptActivity popupPromptionDialog onDismiss myCustomDialog=" + SUSPromptActivity.l + "; myContext=" + this.a.m);
        if (this.a.m != null) {
            ((Activity) this.a.m).finish();
        }
    }
}

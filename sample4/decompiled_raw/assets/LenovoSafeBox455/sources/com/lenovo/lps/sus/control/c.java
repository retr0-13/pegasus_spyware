package com.lenovo.lps.sus.control;

import android.content.DialogInterface;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class c implements DialogInterface.OnCancelListener {
    final /* synthetic */ SUSPromptActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public c(SUSPromptActivity sUSPromptActivity) {
        this.a = sUSPromptActivity;
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        b.a(a.b, "SUSPromptActivity popupPromptionDialog CancelListener");
        this.a.n = true;
        r.b();
        dialogInterface.dismiss();
    }
}

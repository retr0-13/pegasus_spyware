package com.lenovo.lps.sus.control;

import android.content.DialogInterface;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class e implements DialogInterface.OnClickListener {
    final /* synthetic */ SUSPromptActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(SUSPromptActivity sUSPromptActivity) {
        this.a = sUSPromptActivity;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        b.a(a.b, "SUSPromptActivity popupPromptionDialog NegativeButton myMSGHandler=null");
        this.a.n = true;
        r.b();
        dialogInterface.dismiss();
    }
}

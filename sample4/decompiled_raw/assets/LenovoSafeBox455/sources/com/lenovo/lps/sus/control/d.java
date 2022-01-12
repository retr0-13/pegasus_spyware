package com.lenovo.lps.sus.control;

import android.content.DialogInterface;
import android.view.KeyEvent;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class d implements DialogInterface.OnKeyListener {
    final /* synthetic */ SUSPromptActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(SUSPromptActivity sUSPromptActivity) {
        this.a = sUSPromptActivity;
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        b.a(a.b, "SUSPromptActivity popupPromptionDialog KeyListener keyCode=" + i + "; event=" + keyEvent);
        return i == 84 || i == 3 || i == 4;
    }
}

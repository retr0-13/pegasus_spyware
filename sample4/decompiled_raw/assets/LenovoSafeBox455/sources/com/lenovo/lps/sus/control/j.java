package com.lenovo.lps.sus.control;

import android.content.DialogInterface;
import android.view.KeyEvent;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class j implements DialogInterface.OnKeyListener {
    final /* synthetic */ SUSNotificationActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public j(SUSNotificationActivity sUSNotificationActivity) {
        this.a = sUSNotificationActivity;
    }

    @Override // android.content.DialogInterface.OnKeyListener
    public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return false;
    }
}

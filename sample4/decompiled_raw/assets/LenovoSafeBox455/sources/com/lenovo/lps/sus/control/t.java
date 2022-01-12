package com.lenovo.lps.sus.control;

import android.content.DialogInterface;
/* loaded from: classes.dex */
class t implements DialogInterface.OnClickListener {
    t() {
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
        r.b();
    }
}

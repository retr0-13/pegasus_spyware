package com.lenovo.lps.sus.control;

import android.content.DialogInterface;
import android.os.Message;
import com.lenovo.lps.sus.c.b;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class a implements DialogInterface.OnClickListener {
    final /* synthetic */ SUSPromptActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(SUSPromptActivity sUSPromptActivity) {
        this.a = sUSPromptActivity;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        this.a.n = false;
        b.a(com.lenovo.lps.sus.c.a.b, "SUSPromptActivity popupPromptionDialog PositiveButton");
        if (SUSPromptActivity.k != null) {
            Message message = new Message();
            message.what = com.lenovo.lps.sus.b.b.a(com.lenovo.lps.sus.b.b.SUS_USER_REQUPDATE_EVENT);
            message.obj = 0;
            SUSPromptActivity.k.sendMessage(message);
        } else {
            b.a(com.lenovo.lps.sus.c.a.b, "SUSPromptActivity popupPromptionDialog PositiveButton myMSGHandler=null");
            this.a.n = true;
            r.b();
        }
        dialogInterface.dismiss();
    }
}

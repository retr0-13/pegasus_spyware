package com.lenovo.lps.sus.control;

import android.os.Message;
import android.widget.CompoundButton;
import com.lenovo.lps.sus.b.b;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class i implements CompoundButton.OnCheckedChangeListener {
    final /* synthetic */ SUSPromptActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public i(SUSPromptActivity sUSPromptActivity) {
        this.a = sUSPromptActivity;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Integer.valueOf(0);
        int i = z ? 1 : 0;
        if (SUSPromptActivity.k != null) {
            Message message = new Message();
            message.what = b.a(b.SUS_USER_CHANGESETTINGS_EVENT);
            message.obj = i;
            SUSPromptActivity.k.sendMessage(message);
        }
    }
}

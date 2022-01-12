package com.lenovo.lps.sus.control;

import android.os.Handler;
import android.os.Message;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class s extends Handler {
    final /* synthetic */ m a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public s(m mVar) {
        this.a = mVar;
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        super.handleMessage(message);
        this.a.a(message);
    }
}

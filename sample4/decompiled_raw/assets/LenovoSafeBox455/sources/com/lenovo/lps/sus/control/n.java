package com.lenovo.lps.sus.control;

import android.view.View;
/* loaded from: classes.dex */
class n implements View.OnClickListener {
    final /* synthetic */ SUSCustdefNotificationActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public n(SUSCustdefNotificationActivity sUSCustdefNotificationActivity) {
        this.a = sUSCustdefNotificationActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.equals(this.a.l)) {
            SUSCustdefNotificationActivity.i = 3;
            this.a.finish();
        }
    }
}

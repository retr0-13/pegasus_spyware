package com.lenovo.lps.sus.control;

import android.widget.TextView;
import com.lenovo.lps.sus.c.a;
/* loaded from: classes.dex */
class q implements Runnable {
    final /* synthetic */ SUSCustdefNotificationActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public q(SUSCustdefNotificationActivity sUSCustdefNotificationActivity) {
        this.a = sUSCustdefNotificationActivity;
    }

    @Override // java.lang.Runnable
    public void run() {
        TextView textView;
        if (SUSCustdefNotificationActivity.i.intValue() > 0) {
            SUSCustdefNotificationActivity.i = Integer.valueOf(SUSCustdefNotificationActivity.i.intValue() - 1);
        }
        if (SUSCustdefNotificationActivity.i.intValue() == 0) {
            if (this.a.k != null) {
                this.a.k.removeCallbacks(this.a.n);
            }
            SUSCustdefNotificationActivity.i = 3;
            this.a.finish();
            return;
        }
        String num = SUSCustdefNotificationActivity.i.toString();
        if (!(num == null || num.length() <= 0 || SUSCustdefNotificationActivity.e == null || (textView = (TextView) this.a.findViewById(a.a(SUSCustdefNotificationActivity.e, SUSCustdefNotificationActivity.d, "SUS_NOTIFICATION_AUTOHIDPROMPTINFOTEXTVIEW_TIME"))) == null)) {
            textView.setText(num);
        }
        if (this.a.k != null && this.a.n != null) {
            this.a.k.removeCallbacks(this.a.n);
            this.a.k.postDelayed(this.a.n, 1500);
        }
    }
}

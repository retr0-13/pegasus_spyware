package com.lenovo.lps.sus.control;

import android.widget.TextView;
import com.lenovo.lps.sus.c.a;
/* loaded from: classes.dex */
class k implements Runnable {
    final /* synthetic */ SUSNotificationActivity a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public k(SUSNotificationActivity sUSNotificationActivity) {
        this.a = sUSNotificationActivity;
    }

    @Override // java.lang.Runnable
    public void run() {
        TextView textView;
        if (SUSNotificationActivity.j.intValue() > 0) {
            SUSNotificationActivity.j = Integer.valueOf(SUSNotificationActivity.j.intValue() - 1);
        }
        if (SUSNotificationActivity.j.intValue() == 0) {
            if (this.a.l != null) {
                this.a.l.removeCallbacks(this.a.m);
            }
            if (SUSNotificationActivity.e != null && this.a.f != null && SUSNotificationActivity.e != null) {
                SUSNotificationActivity.e.dismiss();
                SUSNotificationActivity.j = 3;
                return;
            }
            return;
        }
        String num = SUSNotificationActivity.j.toString();
        if (!(num == null || num.length() <= 0 || SUSNotificationActivity.e == null || this.a.f == null || (textView = (TextView) SUSNotificationActivity.e.findViewById(a.a(this.a.f, SUSNotificationActivity.d, "SUS_NOTIFICATION_AUTOHIDPROMPTINFOTEXTVIEW_TIME"))) == null)) {
            textView.setText(num);
        }
        if (this.a.l != null && this.a.m != null) {
            this.a.l.removeCallbacks(this.a.m);
            this.a.l.postDelayed(this.a.m, 1500);
        }
    }
}

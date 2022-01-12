package com.lenovo.lps.sus.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
/* loaded from: classes.dex */
public class SUSCustdefNotificationActivity extends Activity {
    private static final int h = 3;
    private boolean f = true;
    private int g = 0;
    private final int j = 1500;
    private Handler k = new Handler();
    private Button l = null;
    private Button m = null;
    private Runnable n = new q(this);
    private static String a = "SUS_NOTIFICATION_TITLE";
    private static String b = "layout";
    private static String c = "sus_customdef_notification_dialog";
    private static String d = "id";
    private static Context e = null;
    private static Integer i = 3;

    public static void a(Context context) {
        if (e != null) {
            i = 3;
            ((Activity) e).finish();
            e = null;
        }
    }

    public void a() {
        if (this.k != null) {
            this.k.removeCallbacks(this.n);
            this.k = null;
        }
        if (e != null) {
            e = null;
        }
    }

    @Override // android.app.Activity
    public void finish() {
        b.a(a.b, "SUSNotificationActivity finish isAbortUpdateFlag=" + this.f);
        a();
        a.d(false);
        super.finish();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        super.onCreate(bundle);
        b.a(a.b, "SUSNotificationActivity .onCreate() begin");
        a.d(true);
        setContentView(a.a(this, "layout", c));
        Intent intent = getIntent();
        e = this;
        String b2 = a.b(e, "SUS_NOTIFICATION_TITLE");
        if (b2 != null && b2.length() > 0) {
            setTitle(b2);
        }
        boolean booleanExtra = intent.getBooleanExtra("FailFlag", false);
        this.l = (Button) findViewById(a.a(e, d, "SUS_NOTIFICATION_CONTINUEBUTTON"));
        String b3 = a.b(e, "SUS_NOTIFICATION_HIDE_BUTTONTEXT");
        if (!(this.l == null || b3 == null || b3.length() <= 0)) {
            this.l.setOnClickListener(new n(this));
            this.l.setText(b3);
        }
        this.m = (Button) findViewById(a.a(e, d, "SUS_NOTIFICATION_ABORTUPDATEBUTTON"));
        String b4 = a.b(e, "SUS_NOTIFICATION_ABORTUPDATE_BUTTONTEXT");
        if (this.m != null) {
            this.m.setOnClickListener(new x(this));
            this.m.setText(b4);
        }
        String b5 = booleanExtra ? a.b(e, "SUS_NOTIFICATION_TIMEOUTPROMPT") : a.b(e, "SUS_NOTIFICATION_UPDATINGPROMPT");
        if (!(b5 == null || b5.length() <= 0 || (textView4 = (TextView) findViewById(a.a(e, d, "SUS_NOTIFICATION_PROMPTINFO"))) == null)) {
            textView4.setText(b5);
        }
        String b6 = a.b(e, "SUS_NOTIFICATION_AUTOHIDPROMPTINFO_1");
        String b7 = a.b(e, "SUS_NOTIFICATION_AUTOHIDPROMPTINFO_2");
        String num = i.toString();
        if (!(b6 == null || b6.length() <= 0 || (textView3 = (TextView) findViewById(a.a(e, d, "SUS_NOTIFICATION_AUTOHIDPROMPTINFOTEXTVIEW_1"))) == null)) {
            textView3.setText(b6);
        }
        if (!(b7 == null || b7.length() <= 0 || (textView2 = (TextView) findViewById(a.a(e, d, "SUS_NOTIFICATION_AUTOHIDPROMPTINFOTEXTVIEW_2"))) == null)) {
            textView2.setText(b7);
        }
        if (!(num == null || num.length() <= 0 || (textView = (TextView) findViewById(a.a(e, d, "SUS_NOTIFICATION_AUTOHIDPROMPTINFOTEXTVIEW_TIME"))) == null)) {
            textView.setText(num);
        }
        if (this.k != null && this.n != null) {
            this.k.removeCallbacks(this.n);
            this.k.postDelayed(this.n, 1500);
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        b.a(a.b, "SUSNotificationActivity onDestroy myContext=" + e);
        a();
        a.d(false);
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
    }
}

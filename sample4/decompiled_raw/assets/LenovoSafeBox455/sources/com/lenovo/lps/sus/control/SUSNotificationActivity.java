package com.lenovo.lps.sus.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
/* loaded from: classes.dex */
public class SUSNotificationActivity extends Activity {
    private static final int i = 3;
    private Context f = null;
    private boolean g = true;
    private int h = 0;
    private final int k = 1500;
    private Handler l = new Handler();
    private Runnable m = new k(this);
    private static String a = "SUS_NOTIFICATION_TITLE";
    private static String b = "layout";
    private static String c = "sus_notification_dialog";
    private static String d = "id";
    private static AlertDialog e = null;
    private static Integer j = 3;

    public static void a() {
        if (e != null) {
            j = 3;
            e.dismiss();
        }
    }

    void a(Context context, boolean z) {
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        View inflate = LayoutInflater.from(context).inflate(a.a(context, "layout", c), (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(a.b(context, a));
        builder.setView(inflate);
        if (j.intValue() == 0) {
            j = 3;
        }
        b.a(a.b, "SUSNotificationActivity popupPromptionDialog create");
        builder.setPositiveButton(a.b(context, "SUS_NOTIFICATION_HIDE_BUTTONTEXT"), new l(this));
        builder.setNegativeButton(a.b(context, "SUS_NOTIFICATION_ABORTUPDATE_BUTTONTEXT"), new h(this));
        builder.setOnKeyListener(new j(this));
        e = builder.create();
        e.setCancelable(false);
        e.setOnCancelListener(new f(this));
        e.setOnDismissListener(new g(this));
        if (e == null) {
            b.a(a.b, "null == myCustomDialog!!!");
            r.b();
            return;
        }
        e.show();
        String b2 = z ? a.b(this.f, "SUS_NOTIFICATION_TIMEOUTPROMPT") : a.b(this.f, "SUS_NOTIFICATION_UPDATINGPROMPT");
        if (!(b2 == null || b2.length() <= 0 || (textView4 = (TextView) e.findViewById(a.a(context, d, "SUS_NOTIFICATION_PROMPTINFO"))) == null)) {
            textView4.setText(b2);
        }
        String b3 = a.b(this.f, "SUS_NOTIFICATION_AUTOHIDPROMPTINFO_1");
        String b4 = a.b(this.f, "SUS_NOTIFICATION_AUTOHIDPROMPTINFO_2");
        String num = j.toString();
        if (!(b3 == null || b3.length() <= 0 || (textView3 = (TextView) e.findViewById(a.a(context, d, "SUS_NOTIFICATION_AUTOHIDPROMPTINFOTEXTVIEW_1"))) == null)) {
            textView3.setText(b3);
        }
        if (!(b4 == null || b4.length() <= 0 || (textView2 = (TextView) e.findViewById(a.a(context, d, "SUS_NOTIFICATION_AUTOHIDPROMPTINFOTEXTVIEW_2"))) == null)) {
            textView2.setText(b4);
        }
        if (num != null && num.length() > 0 && (textView = (TextView) e.findViewById(a.a(context, d, "SUS_NOTIFICATION_AUTOHIDPROMPTINFOTEXTVIEW_TIME"))) != null) {
            textView.setText(num);
        }
    }

    @Override // android.app.Activity
    public void finish() {
        b.a(a.b, "SUSNotificationActivity finish isAbortUpdateFlag=" + this.g);
        a.d(false);
        super.finish();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        b.a(a.b, "SUSNotificationActivity .onCreate() begin");
        Intent intent = getIntent();
        requestWindowFeature(1);
        this.f = this;
        boolean booleanExtra = intent.getBooleanExtra("FailFlag", false);
        this.h = getResources().getConfiguration().orientation;
        b.a(a.b, "SUSNotificationActivity onCreate() orientation=" + this.h);
        a.d(true);
        a(this.f, booleanExtra);
        if (this.l != null && this.m != null) {
            this.l.removeCallbacks(this.m);
            this.l.postDelayed(this.m, 1500);
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        b.a(a.b, "SUSNotificationActivity onDestroy myContext=" + this.f);
        if (this.f != null) {
            this.f = null;
        }
        b.a(a.b, "SUSNotificationActivity onDestroy myCustomDialog=" + e);
        if (e != null) {
            e.dismiss();
            e = null;
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onPause() {
        b.a(a.b, "SUSNotificationActivity onPause");
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onRestart() {
        b.a(a.b, "SUSNotificationActivity onRestart");
        super.onRestart();
    }

    @Override // android.app.Activity
    protected void onResume() {
        b.a(a.b, "SUSNotificationActivity onResume");
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onStart() {
        b.a(a.b, "SUSNotificationActivity onStart");
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onStop() {
        b.a(a.b, "SUSNotificationActivity onStop");
        super.onStop();
    }
}

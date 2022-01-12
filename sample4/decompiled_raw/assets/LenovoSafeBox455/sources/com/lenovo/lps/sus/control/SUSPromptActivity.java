package com.lenovo.lps.sus.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.lenovo.lps.sus.b.c;
import com.lenovo.lps.sus.b.d;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
import java.net.URLDecoder;
/* loaded from: classes.dex */
public class SUSPromptActivity extends Activity {
    private static String a = "SUS_VERSIONUPDATE";
    private static String b = "layout";
    private static String c = "sus_updateinfo_dialog";
    private static String d = "id";
    private static String e = "SUS_newversioninfo";
    private static String f = "SUS_versiondescribe";
    private static String g = "versioninfo";
    private static String h = "actiontype";
    private static String i = "UPDATEPROMPT";
    private static String j = "INSTALLAPP";
    private static Handler k = null;
    private static AlertDialog l = null;
    private Context m = null;
    private boolean n = true;
    private int o = 0;

    public static void a() {
        if (l != null) {
            l.dismiss();
        }
    }

    public static void a(Handler handler) {
        k = handler;
    }

    void a(Context context, String str, String str2, d dVar, boolean z) {
        CheckBox checkBox;
        TextView textView;
        TextView textView2;
        View inflate = LayoutInflater.from(context).inflate(a.a(context, b, c), (ViewGroup) null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(a.b(context, a));
        builder.setView(inflate);
        b.a(a.b, "SUSPromptActivity popupPromptionDialog create");
        builder.setPositiveButton(a.b(context, "SUS_UPDATE"), new a(this));
        builder.setNegativeButton(a.b(context, "SUS_CANCEL"), new e(this));
        builder.setOnKeyListener(new d(this));
        l = builder.create();
        l.setCancelable(false);
        l.setOnCancelListener(new c(this));
        l.setOnDismissListener(new b(this));
        if (l == null) {
            b.a(a.b, "null == myCustomDialog!!!");
            r.b();
            return;
        }
        l.show();
        if (!(str == null || str.length() <= 0 || (textView2 = (TextView) l.findViewById(a.a(context, d, e))) == null)) {
            textView2.setText(str);
        }
        if (!r.e() && str2 != null && str2.length() > 0 && (textView = (TextView) l.findViewById(a.a(context, d, f))) != null) {
            textView.setText(str2);
            textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        }
        if (!z) {
            CheckBox checkBox2 = (CheckBox) l.findViewById(a.a(context, "id", "usersettings_neverprompt_checkbox"));
            if (checkBox2 != null) {
                checkBox2.setVisibility(8);
            }
        } else if (a.c() && (checkBox = (CheckBox) l.findViewById(a.a(context, "id", "usersettings_neverprompt_checkbox"))) != null) {
            checkBox.setText("      " + a.b(context, "SUS_SETTINGS_NEVERPROMPT"));
            if (d.UPDATEACTION_NEVERPROMPT == dVar) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
            checkBox.setOnCheckedChangeListener(new i(this));
        }
    }

    @Override // android.app.Activity
    public void finish() {
        b.a(a.b, "SUSPromptActivity finish isAbortUpdateFlag=" + this.n);
        if (this.n) {
            this.n = false;
            r.b();
        }
        super.finish();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        b.a(a.b, "SUSPromptActivity .onCreate() begin");
        Intent intent = getIntent();
        intent.getStringExtra(h);
        requestWindowFeature(1);
        String stringExtra = intent.getStringExtra(g);
        String stringExtra2 = intent.getStringExtra("UPDATE_DESC");
        if (stringExtra2 != null && stringExtra2.length() > 0) {
            stringExtra2 = URLDecoder.decode(stringExtra2);
        }
        boolean booleanExtra = intent.getBooleanExtra("showUserSettingsEnable", false);
        int a2 = c.a();
        this.m = this;
        this.o = getResources().getConfiguration().orientation;
        b.a(a.b, "SUSPromptActivity onCreate() orientation=" + this.o);
        a(this, stringExtra, stringExtra2, c.b(a2), booleanExtra);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        b.a(a.b, "SUSPromptActivity onDestroy myContext=" + this.m);
        Integer valueOf = Integer.valueOf(getResources().getConfiguration().orientation);
        b.a(a.b, "SUSPromptActivity onDestroy() orientation=" + this.o + "; currentOrientation=" + valueOf);
        if (valueOf.equals(Integer.valueOf(this.o)) && this.n) {
            this.n = false;
            r.b();
        }
        b.a(a.b, "SUSPromptActivity onDestroy myCustomDialog=" + l);
        if (l != null) {
            l.dismiss();
            l = null;
        }
        if (this.m != null) {
            this.m = null;
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onPause() {
        b.a(a.b, "SUSPromptActivity onPause");
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onRestart() {
        b.a(a.b, "SUSPromptActivity onRestart");
        super.onRestart();
    }

    @Override // android.app.Activity
    protected void onResume() {
        b.a(a.b, "SUSPromptActivity onResume");
        super.onResume();
    }

    @Override // android.app.Activity
    protected void onStart() {
        b.a(a.b, "SUSPromptActivity onStart");
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onStop() {
        b.a(a.b, "SUSPromptActivity onStop");
        super.onStop();
    }
}

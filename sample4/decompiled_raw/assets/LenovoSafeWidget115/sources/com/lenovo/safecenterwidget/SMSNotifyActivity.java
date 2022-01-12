package com.lenovo.safecenterwidget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
/* loaded from: classes.dex */
public class SMSNotifyActivity extends Activity implements View.OnClickListener {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        findViews();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        TrackEvent.trackResume(this);
    }

    @Override // android.app.Activity
    protected void onPause() {
        TrackEvent.trackPause(this);
        super.onPause();
    }

    private void findViews() {
        findViewById(R.id.positiveButton).setOnClickListener(this);
        findViewById(R.id.negativeButton).setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.negativeButton /* 2131230726 */:
                finish();
                return;
            case R.id.neutralButton /* 2131230727 */:
            default:
                return;
            case R.id.positiveButton /* 2131230728 */:
                showInstalledAppDetails("com.lenovo.safecenter");
                finish();
                return;
        }
    }

    public void showInstalledAppDetails(String pkgName) {
        int apiLevel = Build.VERSION.SDK_INT;
        Intent intent = new Intent();
        if (apiLevel >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", pkgName, null));
        } else {
            intent.setAction("android.intent.action.VIEW");
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra(apiLevel == 8 ? "pkg" : "com.android.settings.ApplicationPkgName", pkgName);
        }
        intent.addFlags(268435456);
        startActivity(intent);
    }
}

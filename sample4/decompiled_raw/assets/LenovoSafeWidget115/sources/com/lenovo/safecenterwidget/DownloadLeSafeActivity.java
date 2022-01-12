package com.lenovo.safecenterwidget;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/* loaded from: classes.dex */
public class DownloadLeSafeActivity extends Activity implements View.OnClickListener {
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
        ((TextView) findViewById(R.id.message)).setText(R.string.if_download_lesafe);
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
                startBrowser(this);
                finish();
                return;
        }
    }

    private void startBrowser(Context context) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Utils.APPSTORE_URI));
            intent.putExtra("com.android.browser.application_id", Utils.WIDGET_PKGNAME);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, (int) R.string.no_browser, 0).show();
            e.printStackTrace();
        }
    }
}

package com.lenovo.safebox;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;
import com.lenovo.lps.sus.SUS;
/* loaded from: classes.dex */
public class UpdateDialogActivity extends Activity {
    public String TAG = "PrivateSpace";
    private int SDK_VERSION = Build.VERSION.SDK_INT;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        if (this.SDK_VERSION >= 11) {
            setFinishOnTouchOutside(false);
        }
        ((TextView) findViewById(R.id.title)).setText(R.string.SUS_VERSIONUPDATE);
        ((TextView) findViewById(R.id.message)).setText(getString(R.string.SUS_UPDATEVERPROMPT_VERNAME) + getIntent().getStringExtra("versionname") + "\n" + getString(R.string.SUS_UPDATEVERPROMPT_VERSIZE) + Formatter.formatFileSize(this, getIntent().getLongExtra("filesize", 0)) + "\n" + getString(R.string.SUS_UPDATESIZEPROMPT_WIFI) + "\n" + getIntent().getStringExtra("appinfo"));
        TextView okbtn = (TextView) findViewById(R.id.positiveButton);
        okbtn.setText(R.string.SUS_UPDATE);
        okbtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.UpdateDialogActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                SUS.setUpdateDescribeDisableFlag(false);
                SUS.setSDKPromptDisableFlag(false);
                SUS.downloadApp(UpdateDialogActivity.this, UpdateDialogActivity.this.getIntent().getStringExtra("url"), UpdateDialogActivity.this.getIntent().getStringExtra("apkname"), Long.valueOf(UpdateDialogActivity.this.getIntent().getLongExtra("filesize", 0)));
                UpdateDialogActivity.this.finish();
            }
        });
        findViewById(R.id.neutralButton).setVisibility(8);
        TextView cancelbtn = (TextView) findViewById(R.id.negativeButton);
        cancelbtn.setText(R.string.cancel);
        cancelbtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.UpdateDialogActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                UpdateDialogActivity.this.finish();
            }
        });
    }
}

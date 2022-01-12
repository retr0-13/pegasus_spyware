package com.lenovo.safebox;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.lenovo.safebox.service.MonitorAppService;
import java.util.Locale;
/* loaded from: classes.dex */
public class LockScreenActivity extends Activity {
    private ImageView appIcon;
    private ApplicationInfo appInfo;
    private String appName;
    private Button cancelBtn;
    private Button cleanBtn;
    private Button delBtn;
    private Drawable icon;
    private Cursor mCursor;
    private Button n0Btn;
    private Button n1Btn;
    private Button n2Btn;
    private Button n3Btn;
    private Button n4Btn;
    private Button n5Btn;
    private Button n6Btn;
    private Button n7Btn;
    private Button n8Btn;
    private Button n9Btn;
    private EditText numShow;
    private String password;
    private String pkgName;
    private PackageManager pm;
    private SharedPreferences settings;
    private Button submitBtn;
    private TextView tvTitle;
    private String TAG = "LockScreen";
    private String PREFS_NAME = "pass";

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.lockedscreen);
        this.pkgName = getIntent().getStringExtra(PrivateSpaceHelper.PKG);
        this.pm = getPackageManager();
        try {
            this.appInfo = this.pm.getApplicationInfo(this.pkgName, 128);
            this.icon = this.pm.getApplicationIcon(this.appInfo);
            this.appName = this.pm.getApplicationLabel(this.appInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        initView();
        initKeyboard();
    }

    private void checkPwd() {
        this.mCursor = getContentResolver().query(Uri.parse("content://com.lenovo.safecenter.password/password"), null, null, null, null);
        if (this.mCursor != null) {
            if (this.mCursor.getCount() > 0) {
                this.mCursor.moveToFirst();
                this.password = this.mCursor.getString(1);
            } else {
                this.password = "empty";
                Intent mIntent = new Intent();
                mIntent.setClass(this, MonitorAppService.class);
                startService(mIntent);
                finish();
            }
            this.mCursor.close();
            return;
        }
        this.password = null;
        checkPwd("lenovo");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkPwd(String inputStr) {
        this.mCursor = getContentResolver().query(Uri.parse("content://com.lenovo.safecenter.password/password"), new String[]{"result"}, null, new String[]{inputStr}, null);
        if (this.mCursor == null) {
            Intent mIntent = new Intent();
            mIntent.setClass(this, MonitorAppService.class);
            startService(mIntent);
            finish();
            return false;
        } else if (this.mCursor.getCount() > 0) {
            this.mCursor.moveToFirst();
            if (this.mCursor.getInt(0) == 1) {
                this.mCursor.close();
                return true;
            }
            this.mCursor.close();
            return false;
        } else {
            Intent mIntent2 = new Intent();
            mIntent2.setClass(this, MonitorAppService.class);
            startService(mIntent2);
            finish();
            this.mCursor.close();
            return false;
        }
    }

    private void initView() {
        this.tvTitle = (TextView) findViewById(R.id.lockedscreen_tv);
        this.numShow = (EditText) findViewById(R.id.lockedscreen_pwd);
        this.submitBtn = (Button) findViewById(R.id.lockedscreen_login);
        this.cancelBtn = (Button) findViewById(R.id.lockedscreen_cancel);
        this.appIcon = (ImageView) findViewById(R.id.app_icon);
        this.cancelBtn.setClickable(true);
        this.numShow.setFocusable(false);
        if (this.icon != null) {
            this.appIcon.setImageDrawable(this.icon);
        } else {
            this.appIcon.setImageResource(R.drawable.icon);
        }
        if (this.appName != null) {
            this.tvTitle.setText(this.appName + getString(R.string.locking));
        } else {
            this.tvTitle.setText(R.string.locking_default);
        }
        checkPwd();
        this.submitBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.LockScreenActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (LockScreenActivity.this.numShow.getText().toString().length() != 6) {
                    LockScreenActivity.this.numShow.setText("");
                    LockScreenActivity.this.numShow.setHint(R.string.input_again);
                } else if (LockScreenActivity.this.password == null || LockScreenActivity.this.password.equals("empty")) {
                    if (LockScreenActivity.this.checkPwd(LockScreenActivity.this.numShow.getText().toString())) {
                        MonitorAppService.isLocked = false;
                        Intent intent = new Intent("com.lenovo.safebox.lockscreen");
                        intent.putExtra("lastPkg", LockScreenActivity.this.pkgName);
                        LockScreenActivity.this.sendBroadcast(intent);
                        LockScreenActivity.this.finish();
                        LockScreenActivity.this.numShow.setHint(R.string.input_le_pwd);
                        return;
                    }
                    LockScreenActivity.this.numShow.setText("");
                    LockScreenActivity.this.numShow.setHint(R.string.input_again);
                } else if (LockScreenActivity.this.numShow.getText().toString().equals(LockScreenActivity.this.password)) {
                    MonitorAppService.isLocked = false;
                    Intent intent2 = new Intent("com.lenovo.safebox.lockscreen");
                    intent2.putExtra("lastPkg", LockScreenActivity.this.pkgName);
                    LockScreenActivity.this.sendBroadcast(intent2);
                    LockScreenActivity.this.finish();
                    LockScreenActivity.this.numShow.setHint(R.string.input_le_pwd);
                } else {
                    LockScreenActivity.this.numShow.setText("");
                    LockScreenActivity.this.numShow.setHint(R.string.input_again);
                }
            }
        });
        this.cancelBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.LockScreenActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent launcher = new Intent("android.intent.action.MAIN");
                launcher.addCategory("android.intent.category.HOME");
                launcher.addFlags(268435456);
                LockScreenActivity.this.startActivity(launcher);
                LockScreenActivity.this.numShow.setHint(R.string.input_le_pwd);
                Intent intent = new Intent("com.lenovo.safebox.lockscreen");
                intent.putExtra("lastPkg", "invalid");
                LockScreenActivity.this.sendBroadcast(intent);
                LockScreenActivity.this.finish();
            }
        });
    }

    private void initKeyboard() {
        this.n1Btn = (Button) findViewById(R.id.num01);
        this.n2Btn = (Button) findViewById(R.id.num02);
        this.n3Btn = (Button) findViewById(R.id.num03);
        this.n4Btn = (Button) findViewById(R.id.num04);
        this.n5Btn = (Button) findViewById(R.id.num05);
        this.n6Btn = (Button) findViewById(R.id.num06);
        this.n7Btn = (Button) findViewById(R.id.num07);
        this.n8Btn = (Button) findViewById(R.id.num08);
        this.n9Btn = (Button) findViewById(R.id.num09);
        this.n0Btn = (Button) findViewById(R.id.num0);
        this.cleanBtn = (Button) findViewById(R.id.num_clean);
        this.delBtn = (Button) findViewById(R.id.num_del);
        if (Locale.getDefault().getLanguage().equals("en")) {
            this.cleanBtn.setBackgroundResource(R.drawable.clean_selector_en);
            this.cancelBtn.setBackgroundResource(R.drawable.exit_selector_en);
        } else {
            this.cleanBtn.setBackgroundResource(R.drawable.clean_selector);
            this.cancelBtn.setBackgroundResource(R.drawable.exit_selector);
        }
        KeyboardListener keyboardListener = new KeyboardListener();
        this.n1Btn.setOnClickListener(keyboardListener);
        this.n2Btn.setOnClickListener(keyboardListener);
        this.n3Btn.setOnClickListener(keyboardListener);
        this.n4Btn.setOnClickListener(keyboardListener);
        this.n5Btn.setOnClickListener(keyboardListener);
        this.n6Btn.setOnClickListener(keyboardListener);
        this.n7Btn.setOnClickListener(keyboardListener);
        this.n8Btn.setOnClickListener(keyboardListener);
        this.n9Btn.setOnClickListener(keyboardListener);
        this.n0Btn.setOnClickListener(keyboardListener);
        this.cleanBtn.setOnClickListener(keyboardListener);
        this.delBtn.setOnClickListener(keyboardListener);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (!(keyCode == 25 || keyCode == 24)) {
            Intent launcher = new Intent("android.intent.action.MAIN");
            launcher.addCategory("android.intent.category.HOME");
            launcher.addFlags(268435456);
            startActivity(launcher);
            Intent intent = new Intent("com.lenovo.safebox.lockscreen");
            intent.putExtra("lastPkg", "invalid");
            sendBroadcast(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class KeyboardListener implements View.OnClickListener {
        KeyboardListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (v.getId() == LockScreenActivity.this.n1Btn.getId()) {
                LockScreenActivity.this.numShow.getText().append('1');
            }
            if (v.getId() == LockScreenActivity.this.n2Btn.getId()) {
                LockScreenActivity.this.numShow.getText().append('2');
            }
            if (v.getId() == LockScreenActivity.this.n3Btn.getId()) {
                LockScreenActivity.this.numShow.getText().append('3');
            }
            if (v.getId() == LockScreenActivity.this.n4Btn.getId()) {
                LockScreenActivity.this.numShow.getText().append('4');
            }
            if (v.getId() == LockScreenActivity.this.n5Btn.getId()) {
                LockScreenActivity.this.numShow.getText().append('5');
            }
            if (v.getId() == LockScreenActivity.this.n6Btn.getId()) {
                LockScreenActivity.this.numShow.getText().append('6');
            }
            if (v.getId() == LockScreenActivity.this.n7Btn.getId()) {
                LockScreenActivity.this.numShow.getText().append('7');
            }
            if (v.getId() == LockScreenActivity.this.n8Btn.getId()) {
                LockScreenActivity.this.numShow.getText().append('8');
            }
            if (v.getId() == LockScreenActivity.this.n9Btn.getId()) {
                LockScreenActivity.this.numShow.getText().append('9');
            }
            if (v.getId() == LockScreenActivity.this.n0Btn.getId()) {
                LockScreenActivity.this.numShow.getText().append('0');
            }
            if (v.getId() == LockScreenActivity.this.cleanBtn.getId()) {
                LockScreenActivity.this.numShow.setText("");
                LockScreenActivity.this.numShow.setHint(R.string.input_le_pwd);
            }
            if (v.getId() == LockScreenActivity.this.delBtn.getId()) {
                String oldText = LockScreenActivity.this.numShow.getText().toString();
                if (oldText.length() > 1) {
                    LockScreenActivity.this.numShow.setText(oldText.subSequence(0, oldText.length() - 1));
                    return;
                }
                LockScreenActivity.this.numShow.setText("");
                LockScreenActivity.this.numShow.setHint(R.string.input_le_pwd);
            }
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        MonitorAppService.isLocked = false;
        ActivityManager mActivityManager = (ActivityManager) getSystemService("activity");
        String taskName = null;
        if (mActivityManager != null) {
            taskName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
        if (!getPackageName().equals(taskName)) {
            finish();
        }
        super.onPause();
    }
}

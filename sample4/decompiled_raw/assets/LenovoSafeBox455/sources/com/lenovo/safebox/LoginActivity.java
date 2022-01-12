package com.lenovo.safebox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.lenovo.safebox.service.WatchAppService;
/* loaded from: classes.dex */
public class LoginActivity extends Activity {
    private ImageView ivBack;
    private Context mContext;
    private Cursor mCursor;
    private Uri mUri;
    private EditText numShow;
    private String password;
    private String pkgName;
    private SharedPreferences settings;
    private Button submitBtn;
    private TextView tvTitle;
    private boolean trigger = false;
    private String TAG = "LoginActivity";
    private String PREFS_NAME = "pass";
    private boolean onkeydown = false;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.gui_lockedscreen);
        this.tvTitle = (TextView) findViewById(R.id.txt_title);
        this.numShow = (EditText) findViewById(R.id.lockedscreen_pwd);
        this.submitBtn = (Button) findViewById(R.id.lockedscreen_login);
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        this.ivBack.setVisibility(4);
        this.pkgName = null;
        this.mContext = this;
        this.tvTitle.setText(R.string.app_lock);
        if (getIntent().getExtras() != null) {
            this.pkgName = getIntent().getExtras().getString(PrivateSpaceHelper.PKG);
        }
        this.mCursor = getContentResolver().query(Uri.parse("content://com.lenovo.safecenter.password/password"), null, null, null, null);
        if (this.mCursor.getCount() > 0) {
            this.mCursor.moveToFirst();
            this.password = this.mCursor.getString(1);
        }
        if (this.password == null || this.password.isEmpty()) {
            this.settings = getSharedPreferences(this.PREFS_NAME, 0);
            this.password = this.settings.getString("pwd", "");
        }
        this.submitBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.LoginActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (LoginActivity.this.numShow.getText().toString().equals(LoginActivity.this.password)) {
                    Intent intent = new Intent();
                    intent.putExtra("controler", true);
                    intent.putExtra("pkgName", LoginActivity.this.getIntent().getStringExtra(PrivateSpaceHelper.PKG));
                    intent.setClass(LoginActivity.this.mContext, WatchAppService.class);
                    LoginActivity.this.startService(intent);
                    LoginActivity.this.onkeydown = true;
                    LoginActivity.this.finish();
                    return;
                }
                Toast.makeText(LoginActivity.this.mContext, LoginActivity.this.getString(R.string.error_3), 0).show();
                LoginActivity.this.numShow.setText("");
                Intent intent2 = new Intent().setAction("com.lenovo.pspace.FALSE");
                LoginActivity.this.sendBroadcast(intent2);
                Intent mIntent = new Intent();
                mIntent.putExtra("controler", false);
                mIntent.putExtra("pkgName", LoginActivity.this.getIntent().getStringExtra(PrivateSpaceHelper.PKG));
                mIntent.setClass(LoginActivity.this.mContext, WatchAppService.class);
                LoginActivity.this.startService(intent2);
                LoginActivity.this.onkeydown = true;
            }
        });
    }

    @Override // android.app.Activity
    public void onStop() {
        if (!this.onkeydown) {
            finish();
            Intent login = new Intent(this.mContext, LoginActivity.class);
            login.putExtra(PrivateSpaceHelper.PKG, getIntent().getStringExtra(PrivateSpaceHelper.PKG));
            login.setFlags(268435456);
            this.mContext.startActivity(login);
        }
        super.onStop();
    }

    @Override // android.app.Activity
    public void onStart() {
        super.onStart();
        Log.i(this.TAG, "onStart ");
    }

    @Override // android.app.Activity
    public void onRestart() {
        super.onRestart();
        Log.i(this.TAG, "onRestart ");
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 67) {
            this.onkeydown = true;
            Intent localIntent = new Intent();
            localIntent.putExtra("controler", false);
            localIntent.putExtra("pkgName", getIntent().getStringExtra(PrivateSpaceHelper.PKG));
            localIntent.setClass(this.mContext, WatchAppService.class);
            startService(localIntent);
            Intent launcher = new Intent("android.intent.action.MAIN");
            launcher.addCategory("android.intent.category.HOME");
            startActivity(launcher);
        }
        return super.onKeyDown(keyCode, event);
    }
}

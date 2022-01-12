package com.lenovo.safebox.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.lenovo.safebox.PrivateSpaceHelper;
import com.lenovo.safebox.R;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class LockScreen extends Thread {
    private static ArrayList<String> appWhiteList;
    private ImageView appIcon;
    private ApplicationInfo appInfo;
    private String appName;
    private Button cancelBtn;
    private Button cleanBtn;
    private Button delBtn;
    private Drawable icon;
    private LayoutInflater inflater;
    private View lockedscreen_view;
    private Context mContext;
    private Cursor mCursor;
    private int mPid;
    private Uri mUri;
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
    private WindowManager.LayoutParams params;
    private String password;
    private String pkgName;
    private PackageManager pm;
    private SharedPreferences settings;
    private Button submitBtn;
    private TextView tvTitle;
    private WindowManager wm;
    private String TAG = "LockScreen";
    private boolean trigger = false;
    private String PREFS_NAME = "pass";
    private final String executaleFile = "/data/data/com.lenovo.safebox/files/execute.sh";
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.service.LockScreen.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Log.i(LockScreen.this.TAG, "receive message addView");
                    LockScreen.this.initView();
                    LockScreen.this.initKeyboard();
                    LockScreen.this.wm.addView(LockScreen.this.lockedscreen_view, LockScreen.this.params);
                    return;
                case 2:
                    try {
                        Log.i(LockScreen.this.TAG, "receive message removeView");
                        LockScreen.this.wm.removeView(LockScreen.this.lockedscreen_view);
                        return;
                    } catch (Exception e) {
                        Log.i(LockScreen.this.TAG, "remove view error :" + e);
                        return;
                    }
                default:
                    return;
            }
        }
    };
    private final BroadcastReceiver receiver = new BroadcastReceiver() { // from class: com.lenovo.safebox.service.LockScreen.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.lenovo.safecenter.RECEIVE_PHONE_CALL")) {
                Log.i(LockScreen.this.TAG, "receive com.lenovo.safecenter.RECEIVE_PHONE_CALL");
                LockScreen.this.doPhoneCallAction();
            }
        }
    };
    private IntentFilter intentFilter = new IntentFilter();

    public LockScreen(Context context, String name, int pid) {
        this.mContext = context;
        this.pkgName = name;
        this.mPid = pid;
        this.pm = this.mContext.getPackageManager();
        this.intentFilter.addAction("com.lenovo.safecenter.RECEIVE_PHONE_CALL");
        this.mContext.registerReceiver(this.receiver, this.intentFilter);
        initAppWhiteList();
        try {
            this.appInfo = this.pm.getApplicationInfo(this.pkgName, 128);
            this.icon = this.pm.getApplicationIcon(this.appInfo);
            this.appName = this.pm.getApplicationLabel(this.appInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showLockScreen() {
        Log.i(this.TAG, "showLockScreen set WindowShow true");
        WatchAppService.windowShow = true;
        this.handler.sendEmptyMessage(1);
    }

    public void hideLockScreen() {
        Log.i(this.TAG, "hideLockScreen ");
        this.handler.sendEmptyMessage(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAppWhiteList() {
        appWhiteList = new ArrayList<>();
        appWhiteList.add("com.android.stk");
        appWhiteList.add("com.tencent.mobileqq");
        appWhiteList.add("com.tencent.mm");
        appWhiteList.add("com.android.deskclock");
        appWhiteList.add("com.android.music");
        appWhiteList.add("com.duomi.androidarizona");
    }

    private void checkPwd() {
        this.mCursor = this.mContext.getContentResolver().query(Uri.parse("content://com.lenovo.safecenter.password/password"), null, null, null, null);
        if (this.mCursor != null) {
            if (this.mCursor.getCount() > 0) {
                this.mCursor.moveToFirst();
                this.password = this.mCursor.getString(1);
            }
            if (this.password == null || this.password.isEmpty()) {
                this.settings = this.mContext.getSharedPreferences(this.PREFS_NAME, 0);
                this.password = this.settings.getString("pwd", "");
            }
            this.mCursor.close();
        } else if (this.password == null || this.password.isEmpty()) {
            this.settings = this.mContext.getSharedPreferences(this.PREFS_NAME, 0);
            this.password = this.settings.getString("pwd", "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initView() {
        this.wm = (WindowManager) this.mContext.getSystemService("window");
        this.params = new WindowManager.LayoutParams(2010, 256);
        this.params.width = -1;
        this.params.height = -1;
        this.params.screenOrientation = 1;
        this.inflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        this.lockedscreen_view = this.inflater.inflate(R.layout.lockedscreen, (ViewGroup) null);
        this.tvTitle = (TextView) this.lockedscreen_view.findViewById(R.id.lockedscreen_tv);
        this.numShow = (EditText) this.lockedscreen_view.findViewById(R.id.lockedscreen_pwd);
        this.submitBtn = (Button) this.lockedscreen_view.findViewById(R.id.lockedscreen_login);
        this.cancelBtn = (Button) this.lockedscreen_view.findViewById(R.id.lockedscreen_cancel);
        this.appIcon = (ImageView) this.lockedscreen_view.findViewById(R.id.app_icon);
        this.cancelBtn.setClickable(true);
        this.numShow.setFocusable(false);
        if (this.icon != null) {
            this.appIcon.setImageDrawable(this.icon);
        } else {
            this.appIcon.setImageResource(R.drawable.icon);
        }
        if (this.appName != null) {
            this.tvTitle.setText(this.appName + this.mContext.getString(R.string.locking));
        } else {
            this.tvTitle.setText(R.string.locking_default);
        }
        checkPwd();
        this.submitBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.service.LockScreen.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (LockScreen.this.numShow.getText().toString().equals(LockScreen.this.password)) {
                    Log.i(LockScreen.this.TAG, "will remove view ");
                    ((InputMethodManager) LockScreen.this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(LockScreen.this.numShow.getWindowToken(), 0);
                    LockScreen.this.wm.removeView(LockScreen.this.lockedscreen_view);
                    WatchAppService.windowShow = false;
                    LockScreen.this.mContext.unregisterReceiver(LockScreen.this.receiver);
                    return;
                }
                LockScreen.this.numShow.setText("");
                LockScreen.this.numShow.setHint(R.string.input_again);
                Log.i(LockScreen.this.TAG, "getItent pkg : " + LockScreen.this.pkgName);
            }
        });
        this.cancelBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.service.LockScreen.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (LockScreen.appWhiteList == null) {
                    LockScreen.this.initAppWhiteList();
                }
                if (!LockScreen.appWhiteList.contains(LockScreen.this.pkgName)) {
                    Intent intent = new Intent("com.lenovo.safebox.KILL_APP");
                    intent.putExtra(PrivateSpaceHelper.PKG, LockScreen.this.pkgName);
                    LockScreen.this.mContext.sendBroadcast(intent);
                    LockScreen.this.prepareExecuteFile(LockScreen.this.mContext, "kill " + LockScreen.this.mPid + "  \n");
                }
                Intent launcher = new Intent("android.intent.action.MAIN");
                launcher.addCategory("android.intent.category.HOME");
                launcher.addFlags(268435456);
                Log.i("WatchAppService--LockScreen", "will remove view ");
                LockScreen.this.mContext.startActivity(launcher);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ((InputMethodManager) LockScreen.this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(LockScreen.this.numShow.getWindowToken(), 0);
                LockScreen.this.wm.removeView(LockScreen.this.lockedscreen_view);
                WatchAppService.windowShow = false;
                LockScreen.this.mContext.unregisterReceiver(LockScreen.this.receiver);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initKeyboard() {
        this.n1Btn = (Button) this.lockedscreen_view.findViewById(R.id.num01);
        this.n2Btn = (Button) this.lockedscreen_view.findViewById(R.id.num02);
        this.n3Btn = (Button) this.lockedscreen_view.findViewById(R.id.num03);
        this.n4Btn = (Button) this.lockedscreen_view.findViewById(R.id.num04);
        this.n5Btn = (Button) this.lockedscreen_view.findViewById(R.id.num05);
        this.n6Btn = (Button) this.lockedscreen_view.findViewById(R.id.num06);
        this.n7Btn = (Button) this.lockedscreen_view.findViewById(R.id.num07);
        this.n8Btn = (Button) this.lockedscreen_view.findViewById(R.id.num08);
        this.n9Btn = (Button) this.lockedscreen_view.findViewById(R.id.num09);
        this.n0Btn = (Button) this.lockedscreen_view.findViewById(R.id.num0);
        this.cleanBtn = (Button) this.lockedscreen_view.findViewById(R.id.num_clean);
        this.delBtn = (Button) this.lockedscreen_view.findViewById(R.id.num_del);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void doPhoneCallAction() {
        if (appWhiteList == null) {
            initAppWhiteList();
        }
        if (!appWhiteList.contains(this.pkgName)) {
            Intent intent = new Intent("com.lenovo.safebox.KILL_APP");
            intent.putExtra(PrivateSpaceHelper.PKG, this.pkgName);
            this.mContext.sendBroadcast(intent);
            prepareExecuteFile(this.mContext, "kill " + this.mPid + "  \n");
        } else {
            Intent launcher = new Intent("android.intent.action.MAIN");
            launcher.addCategory("android.intent.category.HOME");
            launcher.addFlags(268435456);
            Log.i("WatchAppService--LockScreen", "will remove view ");
            this.mContext.startActivity(launcher);
        }
        ((InputMethodManager) this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(this.numShow.getWindowToken(), 0);
        this.wm.removeView(this.lockedscreen_view);
        WatchAppService.windowShow = false;
        this.mContext.unregisterReceiver(this.receiver);
    }

    public boolean socketClient(String cmd) {
        boolean success;
        Socket client;
        PrintWriter socketWriter;
        BufferedReader socketReader;
        String a;
        Log.i(this.TAG, "This is socketClient: " + cmd);
        try {
            client = new Socket("127.0.0.1", 30001);
            socketWriter = new PrintWriter(client.getOutputStream(), true);
            socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            socketWriter.write(cmd);
            socketWriter.flush();
            a = socketReader.readLine();
        } catch (Exception e) {
            Log.i("EXCEPTION", "This is socketClient: " + cmd);
            e.printStackTrace();
            Log.i("EXCEPTION", "This is socketClient: " + cmd);
            success = false;
        }
        if (a == null) {
            socketWriter.close();
            socketReader.close();
            client.close();
            return false;
        }
        if (a.startsWith("success")) {
            Log.i(this.TAG, "nac success cmd: " + cmd);
            success = true;
        } else {
            success = false;
            Log.i(this.TAG, "nac failed cmd: " + cmd);
        }
        socketWriter.close();
        socketReader.close();
        client.close();
        return success;
    }

    public boolean prepareExecuteFile(Context context, String command) {
        FileOutputStream fos = null;
        try {
            try {
                fos = context.openFileOutput("execute.sh", 0);
                fos.write(command.getBytes());
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            return socketClient("/data/data/com.lenovo.safebox/files/execute.sh");
        } catch (Throwable th) {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public final class KeyboardListener implements View.OnClickListener {
        KeyboardListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (v.getId() == LockScreen.this.n1Btn.getId()) {
                LockScreen.this.numShow.getText().append('1');
            }
            if (v.getId() == LockScreen.this.n2Btn.getId()) {
                LockScreen.this.numShow.getText().append('2');
            }
            if (v.getId() == LockScreen.this.n3Btn.getId()) {
                LockScreen.this.numShow.getText().append('3');
            }
            if (v.getId() == LockScreen.this.n4Btn.getId()) {
                LockScreen.this.numShow.getText().append('4');
            }
            if (v.getId() == LockScreen.this.n5Btn.getId()) {
                LockScreen.this.numShow.getText().append('5');
            }
            if (v.getId() == LockScreen.this.n6Btn.getId()) {
                LockScreen.this.numShow.getText().append('6');
            }
            if (v.getId() == LockScreen.this.n7Btn.getId()) {
                LockScreen.this.numShow.getText().append('7');
            }
            if (v.getId() == LockScreen.this.n8Btn.getId()) {
                LockScreen.this.numShow.getText().append('8');
            }
            if (v.getId() == LockScreen.this.n9Btn.getId()) {
                LockScreen.this.numShow.getText().append('9');
            }
            if (v.getId() == LockScreen.this.n0Btn.getId()) {
                LockScreen.this.numShow.getText().append('0');
            }
            if (v.getId() == LockScreen.this.cleanBtn.getId()) {
                LockScreen.this.numShow.setText("");
                LockScreen.this.numShow.setHint(R.string.input_le_pwd);
            }
            if (v.getId() == LockScreen.this.delBtn.getId()) {
                String oldText = LockScreen.this.numShow.getText().toString();
                if (oldText.length() > 1) {
                    LockScreen.this.numShow.setText(oldText.subSequence(0, oldText.length() - 1));
                    return;
                }
                LockScreen.this.numShow.setText("");
                LockScreen.this.numShow.setHint(R.string.input_le_pwd);
            }
        }
    }
}

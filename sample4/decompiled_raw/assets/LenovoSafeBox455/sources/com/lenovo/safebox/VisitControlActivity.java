package com.lenovo.safebox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.lenovo.lps.sus.c.a;
import com.lenovo.safebox.ShortcutHelper;
import com.lenovo.safebox.dialog.CustomDialog;
import com.lenovo.safebox.engine.AppEngine;
import com.lenovo.safebox.service.MonitorAppService;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class VisitControlActivity extends Activity {
    private int ListPos;
    private int allAppCount;
    private AllAppAdapter appAdapter;
    private AppEngine appEngine;
    private ArrayList<AppInfo> dataApps;
    SQLiteDatabase db;
    private SharedPreferences.Editor editor;
    private boolean fromLeSafe;
    private ImageView ivBack;
    private ImageView ivHidePriInfo;
    private int lockedCount;
    private ListView lv;
    private Context mContext;
    private Cursor mCursor;
    PrivateSpaceHelper mHelper;
    private ArrayList<AppInfo> mLockedApps;
    private Uri mUri;
    private String password;
    private ProgressBar pgBar;
    private ProgressDialog progressDialog;
    private SharedPreferences settings;
    private ArrayList<AppInfo> sysApps;
    private TextView tvInfoBar;
    private TextView tvTitle;
    private String TAG = "VisitControlActivity";
    private String PREFS_NAME = "pass";
    private boolean DEBUG = false;
    private Handler mHandler = new Handler() { // from class: com.lenovo.safebox.VisitControlActivity.5
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (VisitControlActivity.this.progressDialog != null) {
                VisitControlActivity.this.progressDialog.dismiss();
            }
            if (Settings.System.getInt(VisitControlActivity.this.mContext.getContentResolver(), "guest_mode_on", 0) == 0) {
                VisitControlActivity.this.ivHidePriInfo.setImageDrawable(VisitControlActivity.this.getResources().getDrawable(R.drawable.btn_off));
            } else {
                VisitControlActivity.this.ivHidePriInfo.setImageDrawable(VisitControlActivity.this.getResources().getDrawable(R.drawable.btn_on));
            }
        }
    };
    private AbsListView.OnScrollListener ScrollLis = new AbsListView.OnScrollListener() { // from class: com.lenovo.safebox.VisitControlActivity.6
        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0) {
                VisitControlActivity.this.ListPos = VisitControlActivity.this.lv.getFirstVisiblePosition();
            }
        }
    };
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.VisitControlActivity.8
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    VisitControlActivity.this.pgBar.setVisibility(8);
                    VisitControlActivity.this.appAdapter = new AllAppAdapter(VisitControlActivity.this.mContext, VisitControlActivity.this.sysApps, VisitControlActivity.this.dataApps, VisitControlActivity.this.mLockedApps);
                    VisitControlActivity.this.allAppCount = VisitControlActivity.this.appAdapter.getCount();
                    VisitControlActivity.this.lockedCount = VisitControlActivity.this.mLockedApps.size();
                    VisitControlActivity.this.lv.setAdapter((ListAdapter) VisitControlActivity.this.appAdapter);
                    return;
                default:
                    return;
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.visit_activity);
        this.pgBar = (ProgressBar) findViewById(R.id.widget196);
        this.mContext = this;
        prework();
        initView();
        initData();
        this.pgBar.setVisibility(0);
        this.lv = (ListView) findViewById(R.id.selectapp_listview);
        this.lv.setOnItemClickListener(new ListItemListener(this));
        this.lv.setOnScrollListener(this.ScrollLis);
    }

    private void prework() {
        this.fromLeSafe = getIntent().getBooleanExtra("entryFromLeSafe", false);
        this.settings = getSharedPreferences(this.PREFS_NAME, 0);
        this.editor = this.settings.edit();
        if (this.DEBUG) {
            Log.i(this.TAG, "fromLeSafe  :" + this.fromLeSafe);
            Log.i(this.TAG, "isfirst  :" + this.settings.getBoolean("isfirst", true));
        }
        if (!this.fromLeSafe) {
            this.mCursor = getContentResolver().query(Uri.parse("content://com.lenovo.safecenter.password/password"), null, null, null, null);
            if (this.mCursor == null) {
                System.exit(0);
            }
            if (this.mCursor.getCount() > 0) {
                this.mCursor.moveToFirst();
                this.password = this.mCursor.getString(1);
                this.mCursor.close();
            }
            CustomDialog mShowPwdDialog = showPwdDialog(this.mContext);
            mShowPwdDialog.setCanceledOnTouchOutside(false);
            mShowPwdDialog.setCancelable(false);
            mShowPwdDialog.show();
        } else if (this.settings.getBoolean("isfirst", true)) {
            installKidDesktopShortcutForFirst();
            this.editor.putBoolean("isfirst", false);
            this.editor.commit();
        }
    }

    private void installKidDesktopShortcutForFirst() {
        ShortcutHelper sc = new ShortcutHelper();
        if (!sc.isExistShortcut(this)) {
            Parcelable shortIcon = Intent.ShortcutIconResource.fromContext(this, R.drawable.visit_control_icon);
            Intent launchIntent = getIntent();
            launchIntent.putExtra("entryFromLeSafe", false);
            try {
                sc.setShortcutName(getString(R.string.visit_title)).setShortcutIcon(shortIcon).setLaunchIntent(launchIntent).installTheShortcut(this);
            } catch (ShortcutHelper.ShortcutCreatorInstallException e) {
                e.printStackTrace();
            }
        }
    }

    public CustomDialog showPwdDialog(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.input_password_content, (ViewGroup) null);
        final TextView message = (TextView) view.findViewById(R.id.txt_message);
        message.setText(R.string.input_privacy_pwd);
        final EditText edit = (EditText) view.findViewById(R.id.edt_input_pwd);
        return new CustomDialog.Builder(context).setTitle(R.string.input_pwd).setContentView(view).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.VisitControlActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                String newPwd = edit.getText().toString();
                if (newPwd.equals(VisitControlActivity.this.password)) {
                    dialog.dismiss();
                } else if (newPwd.length() == 0) {
                    message.setText(R.string.error_tips_null);
                    edit.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                } else {
                    edit.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));
                    edit.setText("");
                    message.setText(R.string.pwd_error_tips);
                }
            }
        }, false).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.VisitControlActivity.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);
            }
        }).create(true);
    }

    void initView() {
        this.tvTitle = (TextView) findViewById(R.id.txt_title);
        this.tvTitle.setText(R.string.visit_title);
        this.ivHidePriInfo = (ImageView) findViewById(R.id.hide_info_btn);
        this.tvInfoBar = (TextView) findViewById(R.id.locked_info_bar);
        this.mLockedApps = getLockedApp();
        this.lockedCount = this.mLockedApps.size();
        this.tvInfoBar.setText(getString(R.string.lock_app_info) + this.mLockedApps.size() + getString(R.string.protected_end));
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        if (Settings.System.getInt(getContentResolver(), "guest_mode_on", 0) == 0) {
            this.ivHidePriInfo.setImageDrawable(getResources().getDrawable(R.drawable.btn_off));
        } else {
            this.ivHidePriInfo.setImageDrawable(getResources().getDrawable(R.drawable.btn_on));
        }
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.VisitControlActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                System.gc();
                Intent mIntent = new Intent();
                mIntent.setClass(VisitControlActivity.this.mContext, MonitorAppService.class);
                VisitControlActivity.this.mContext.startService(mIntent);
                VisitControlActivity.this.finish();
                System.exit(0);
            }
        });
        this.ivHidePriInfo.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.VisitControlActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (Settings.System.getInt(VisitControlActivity.this.mContext.getContentResolver(), "guest_mode_on", 0) == 0) {
                    Settings.System.putInt(VisitControlActivity.this.mContext.getContentResolver(), "guest_mode_on", 1);
                    Intent it = new Intent("com.safecenter.broadcast.openGuestMode");
                    it.putExtra("state", true);
                    VisitControlActivity.this.mContext.sendBroadcast(it);
                    VisitControlActivity.this.showProgress(1);
                } else {
                    Settings.System.putInt(VisitControlActivity.this.mContext.getContentResolver(), "guest_mode_on", 0);
                    Intent it2 = new Intent("com.safecenter.broadcast.openGuestMode");
                    it2.putExtra("state", false);
                    VisitControlActivity.this.mContext.sendBroadcast(it2);
                    VisitControlActivity.this.showProgress(0);
                }
                VisitControlActivity.this.mHandler.sendMessageDelayed(VisitControlActivity.this.mHandler.obtainMessage(6), a.E);
            }
        });
    }

    /* loaded from: classes.dex */
    class ListItemListener implements AdapterView.OnItemClickListener {
        private Context mContext;

        ListItemListener(Context context) {
            this.mContext = context;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppInfo clickedItem = VisitControlActivity.this.appAdapter.getItem(position);
            if (clickedItem.getStatus() == 1) {
                if (!(VisitControlActivity.this.appAdapter.getItem(position) == null || VisitControlActivity.this.appAdapter.getItem(position).getPkgName() == null || !VisitControlActivity.this.insertAppInfo(VisitControlActivity.this.appAdapter.getItem(position).getPkgName(), VisitControlActivity.this.appAdapter.getItem(position).getAppName()))) {
                    if (position >= VisitControlActivity.this.dataApps.size()) {
                        VisitControlActivity.this.sysApps.remove(VisitControlActivity.this.appAdapter.getItem(position));
                    } else {
                        VisitControlActivity.this.dataApps.remove(VisitControlActivity.this.appAdapter.getItem(position));
                    }
                    VisitControlActivity.this.mLockedApps.add(VisitControlActivity.this.appAdapter.getItem(position));
                    clickedItem.setStatus(0);
                    VisitControlActivity.this.appAdapter.notifyDataSetChanged();
                }
            } else if (VisitControlActivity.this.appAdapter.getItem(position) != null && VisitControlActivity.this.deleteFromDatabase(VisitControlActivity.this.appAdapter.getItem(position).pkgName)) {
                try {
                    if (AppEngine.isThirdpartApp(this.mContext.getPackageManager().getApplicationInfo(VisitControlActivity.this.appAdapter.getItem(position).pkgName, 0))) {
                        VisitControlActivity.this.dataApps.add(VisitControlActivity.this.appAdapter.getItem(position));
                    } else {
                        VisitControlActivity.this.sysApps.add(VisitControlActivity.this.appAdapter.getItem(position));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                VisitControlActivity.this.mLockedApps.remove(VisitControlActivity.this.appAdapter.getItem(position));
                clickedItem.setStatus(1);
                VisitControlActivity.this.appAdapter.notifyDataSetChanged();
            }
            VisitControlActivity.this.tvInfoBar.setText(VisitControlActivity.this.getString(R.string.lock_app_info) + VisitControlActivity.this.mLockedApps.size() + VisitControlActivity.this.getString(R.string.protected_end));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showProgress(int status) {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.lenovo.safebox.VisitControlActivity.7
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                VisitControlActivity.this.progressDialog = null;
            }
        });
        this.progressDialog.setProgressStyle(0);
        if (status == 0) {
            this.progressDialog.setMessage(getString(R.string.exec_close));
        } else {
            this.progressDialog.setMessage(getString(R.string.exec_open));
        }
        this.progressDialog.show();
    }

    public void initData() {
        new Thread() { // from class: com.lenovo.safebox.VisitControlActivity.9
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                VisitControlActivity.this.appEngine = new AppEngine(VisitControlActivity.this.mContext);
                VisitControlActivity.this.sysApps = VisitControlActivity.this.appEngine.getSysApps();
                VisitControlActivity.this.dataApps = VisitControlActivity.this.appEngine.getDataApps();
                VisitControlActivity.this.mLockedApps = VisitControlActivity.this.getLockedApp();
                Message msg = new Message();
                msg.what = 0;
                VisitControlActivity.this.handler.sendMessage(msg);
            }
        }.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList<AppInfo> getLockedApp() {
        ArrayList<String> uninstalledApps = new ArrayList<>();
        ArrayList<AppInfo> lockedApps = new ArrayList<>();
        PackageManager pm = this.mContext.getPackageManager();
        this.mHelper = new PrivateSpaceHelper(getApplicationContext(), PrivateSpaceTools.DB_NAME, null, 1);
        this.db = this.mHelper.getReadableDatabase();
        Cursor result = this.db.query(true, PrivateSpaceHelper.APP_TB_NAME, new String[]{PrivateSpaceHelper.PKG, PrivateSpaceHelper.APP_NAME}, null, null, null, null, null, null);
        if (result != null && result.getCount() > 0) {
            result.moveToFirst();
            while (!result.isAfterLast()) {
                PackageInfo packageInfo = null;
                try {
                    PackageManager packageManager = getPackageManager();
                    PrivateSpaceHelper privateSpaceHelper = this.mHelper;
                    packageInfo = packageManager.getPackageInfo(result.getString(result.getColumnIndex(PrivateSpaceHelper.PKG)), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (packageInfo == null) {
                    PrivateSpaceHelper privateSpaceHelper2 = this.mHelper;
                    uninstalledApps.add(result.getString(result.getColumnIndex(PrivateSpaceHelper.PKG)));
                } else {
                    AppInfo appInfo = new AppInfo();
                    PrivateSpaceHelper privateSpaceHelper3 = this.mHelper;
                    String appName = result.getString(result.getColumnIndex(PrivateSpaceHelper.APP_NAME));
                    if (appName == null || appName.isEmpty()) {
                        appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
                    } else {
                        appInfo.setAppName(appName);
                    }
                    appInfo.setDrawable(packageInfo.applicationInfo.loadIcon(pm));
                    PrivateSpaceHelper privateSpaceHelper4 = this.mHelper;
                    appInfo.setPkgName(result.getString(result.getColumnIndex(PrivateSpaceHelper.PKG)));
                    appInfo.setStatus(0);
                    lockedApps.add(appInfo);
                }
                result.moveToNext();
            }
        }
        if (result != null) {
            result.close();
        }
        this.db.close();
        if (uninstalledApps.size() > 0) {
            for (int i = 0; i < uninstalledApps.size(); i++) {
                deleteFromDatabase(uninstalledApps.get(i));
            }
        }
        return lockedApps;
    }

    public boolean deleteFromDatabase(String pkg) {
        boolean result = true;
        this.mHelper = new PrivateSpaceHelper(this, PrivateSpaceTools.DB_NAME, null, 1);
        this.db = this.mHelper.getReadableDatabase();
        if (this.db.delete(PrivateSpaceHelper.APP_TB_NAME, "pkg= \"" + pkg + "\"", null) <= 0) {
            result = false;
        }
        this.db.close();
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean insertAppInfo(String pkgName, String appName) {
        this.mHelper = new PrivateSpaceHelper(this, PrivateSpaceTools.DB_NAME, null, 1);
        this.db = this.mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(PrivateSpaceHelper.PKG, pkgName);
        values.put(PrivateSpaceHelper.APP_NAME, appName);
        values.put(PrivateSpaceHelper.ISLOCK, "1");
        long result = this.db.insert(PrivateSpaceHelper.APP_TB_NAME, "_id", values);
        this.db.close();
        if (result == -1) {
            return false;
        }
        return true;
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (Settings.System.getInt(getContentResolver(), "guest_mode_on", 0) == 0) {
            this.ivHidePriInfo.setImageDrawable(getResources().getDrawable(R.drawable.btn_off));
        } else {
            this.ivHidePriInfo.setImageDrawable(getResources().getDrawable(R.drawable.btn_on));
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        Intent mIntent = new Intent();
        mIntent.setClass(this, MonitorAppService.class);
        startService(mIntent);
        finish();
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        Intent mIntent = new Intent();
        mIntent.setClass(this, MonitorAppService.class);
        startService(mIntent);
        System.gc();
        finish();
        System.exit(0);
        return super.onKeyDown(keyCode, event);
    }

    /* loaded from: classes.dex */
    public class AllAppAdapter extends BaseAdapter {
        private ArrayList<AppInfo> allList;
        private ArrayList<AppInfo> dataAppList;
        private ArrayList<AppInfo> lockedAppList;
        private Context mContext;
        private ArrayList<AppInfo> sysAppList;

        public AllAppAdapter(Context context, ArrayList<AppInfo> sys, ArrayList<AppInfo> data, ArrayList<AppInfo> locked) {
            this.mContext = context;
            this.sysAppList = sys;
            this.dataAppList = data;
            this.lockedAppList = locked;
            resolveList(this.sysAppList, this.dataAppList, this.lockedAppList);
        }

        private void resolveList(ArrayList<AppInfo> sys, ArrayList<AppInfo> data, ArrayList<AppInfo> locked) {
            this.allList = new ArrayList<>();
            for (int i = 0; i < locked.size(); i++) {
                this.allList.add(locked.get(i));
            }
            for (int i2 = 0; i2 < data.size(); i2++) {
                this.allList.add(data.get(i2));
            }
            for (int i3 = 0; i3 < sys.size(); i3++) {
                this.allList.add(sys.get(i3));
            }
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.allList.size();
        }

        @Override // android.widget.Adapter
        public AppInfo getItem(int position) {
            return this.allList.get(position);
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return (long) position;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            AppViewHolder appViewHolder = new AppViewHolder();
            if (convertView == null) {
                convertView = View.inflate(this.mContext, R.layout.gui_selectapp_item, null);
                appViewHolder.iconView = (ImageView) convertView.findViewById(R.id.appitem_icon);
                appViewHolder.appName = (TextView) convertView.findViewById(R.id.appitem_name);
                appViewHolder.isLocked = (ImageView) convertView.findViewById(R.id.appitem_islock);
                convertView.setTag(appViewHolder);
            } else {
                appViewHolder = (AppViewHolder) convertView.getTag();
            }
            if (this.allList.get(position).getDrawable() != null) {
                appViewHolder.iconView.setImageDrawable(this.allList.get(position).getDrawable());
            }
            if (this.allList.get(position).getAppName() != null) {
                appViewHolder.appName.setText(this.allList.get(position).getAppName());
            }
            if (this.allList.get(position).getAppName() != null) {
                if (this.allList.get(position).getStatus() == 0) {
                    appViewHolder.isLocked.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.gui_app_locked));
                } else {
                    appViewHolder.isLocked.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.gui_app_unlock));
                }
            }
            return convertView;
        }

        /* loaded from: classes.dex */
        class AppViewHolder {
            TextView appName;
            ImageView iconView;
            ImageView isLocked;

            AppViewHolder() {
            }
        }
    }
}

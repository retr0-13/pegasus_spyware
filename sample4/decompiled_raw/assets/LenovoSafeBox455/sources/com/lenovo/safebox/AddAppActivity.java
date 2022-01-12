package com.lenovo.safebox;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.lenovo.safebox.adapter.AppAdapter;
import com.lenovo.safebox.adapter.LockedAppAdapter;
import com.lenovo.safebox.dialog.CustomDialog;
import com.lenovo.safebox.engine.AppEngine;
import com.lenovo.safebox.service.MonitorAppService;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class AddAppActivity extends Activity {
    private int ListPos;
    private AppAdapter appAdapter;
    private AppEngine appEngine;
    private ArrayList<AppInfo> dataApps;
    SQLiteDatabase db;
    private SharedPreferences.Editor editor;
    private ImageView ivBack;
    private ImageView ivLocked;
    private ImageView ivUnlock;
    private LinearLayout locked;
    private LockedAppAdapter lockedAppAdapter;
    private int lockedCount;
    private ListView lv;
    private ListView lv2;
    private Context mContext;
    private Cursor mCursor;
    PrivateSpaceHelper mHelper;
    private ArrayList<AppInfo> mLockedApps;
    private Uri mUri;
    private String passedPWD;
    private String password;
    private ProgressBar pgBar;
    private SharedPreferences settings;
    private ArrayList<AppInfo> sysApps;
    private TextView tvLocked;
    private TextView tvTitle;
    private TextView tvUnlock;
    private LinearLayout unlock;
    private int unlockCount;
    private ViewPager viewPager;
    private String TAG = "AddAppActivity    ";
    private int flag = 0;
    private String PREFS_NAME = "pass";
    private List<View> mListViews = new ArrayList();
    private AbsListView.OnScrollListener ScrollLis = new AbsListView.OnScrollListener() { // from class: com.lenovo.safebox.AddAppActivity.5
        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0) {
                AddAppActivity.this.ListPos = AddAppActivity.this.lv.getFirstVisiblePosition();
            }
        }
    };
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.AddAppActivity.6
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AddAppActivity.this.pgBar.setVisibility(8);
                    AddAppActivity.this.locked.setClickable(true);
                    AddAppActivity.this.appAdapter = new AppAdapter(AddAppActivity.this.mContext, AddAppActivity.this.sysApps, AddAppActivity.this.dataApps);
                    AddAppActivity.this.unlockCount = AddAppActivity.this.appAdapter.getCount();
                    AddAppActivity.this.lockedCount = AddAppActivity.this.mLockedApps.size();
                    AddAppActivity.this.tvLocked.setText(AddAppActivity.this.getString(R.string.locked_apps) + AddAppActivity.this.getString(R.string.protected_begin) + AddAppActivity.this.lockedCount + AddAppActivity.this.getString(R.string.protected_end));
                    AddAppActivity.this.tvUnlock.setText(AddAppActivity.this.getString(R.string.unlock_apps) + AddAppActivity.this.getString(R.string.protected_begin) + AddAppActivity.this.unlockCount + AddAppActivity.this.getString(R.string.protected_end));
                    AddAppActivity.this.lv.setAdapter((ListAdapter) AddAppActivity.this.appAdapter);
                    return;
                case 1:
                    AddAppActivity.this.appAdapter = new AppAdapter(AddAppActivity.this.mContext, AddAppActivity.this.sysApps, AddAppActivity.this.dataApps);
                    AddAppActivity.this.appAdapter.notifyDataSetChanged();
                    AddAppActivity.this.lv.setAdapter((ListAdapter) AddAppActivity.this.appAdapter);
                    return;
                case 2:
                    AddAppActivity.this.mLockedApps = AddAppActivity.this.getLockedApp();
                    AddAppActivity.this.lockedAppAdapter = new LockedAppAdapter(AddAppActivity.this.mContext, AddAppActivity.this.mLockedApps);
                    AddAppActivity.this.lockedAppAdapter.notifyDataSetChanged();
                    AddAppActivity.this.lv2.setAdapter((ListAdapter) AddAppActivity.this.lockedAppAdapter);
                    return;
                default:
                    return;
            }
        }
    };

    static /* synthetic */ int access$1008(AddAppActivity x0) {
        int i = x0.unlockCount;
        x0.unlockCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$1010(AddAppActivity x0) {
        int i = x0.unlockCount;
        x0.unlockCount = i - 1;
        return i;
    }

    static /* synthetic */ int access$1108(AddAppActivity x0) {
        int i = x0.lockedCount;
        x0.lockedCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$1110(AddAppActivity x0) {
        int i = x0.lockedCount;
        x0.lockedCount = i - 1;
        return i;
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.gui_selectapp_activity);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        this.mContext = this;
        prework();
        initView();
        initData();
    }

    private void prework() {
        this.passedPWD = getIntent().getStringExtra("pwd");
        if (this.passedPWD == null) {
            System.exit(0);
        }
    }

    void initView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService("layout_inflater");
        View view1 = inflater.inflate(R.layout.page_view_item, (ViewGroup) null);
        View view2 = inflater.inflate(R.layout.page_view_item, (ViewGroup) null);
        this.mListViews.add(view1);
        this.mListViews.add(view2);
        this.viewPager.setAdapter(new ViewPagerAdapter());
        this.viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() { // from class: com.lenovo.safebox.AddAppActivity.1
            @Override // android.support.v4.view.ViewPager.SimpleOnPageChangeListener, android.support.v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                if (position == 0) {
                    AddAppActivity.this.flag = 0;
                } else {
                    AddAppActivity.this.flag = 1;
                }
                AddAppActivity.this.switcher(AddAppActivity.this.flag);
                Log.i("yincc", " onPageSelected pos = " + position);
            }
        });
        this.pgBar = (ProgressBar) view1.findViewById(R.id.widget196);
        this.pgBar.setVisibility(0);
        this.lv = (ListView) view1.findViewById(R.id.selectapp_listview);
        this.lv.setOnItemClickListener(new ListItemListener(this));
        this.lv.setOnScrollListener(this.ScrollLis);
        this.lv2 = (ListView) view2.findViewById(R.id.selectapp_listview);
        this.lv2.setOnItemClickListener(new ListItemListener(this));
        this.lv2.setOnScrollListener(this.ScrollLis);
        this.unlock = (LinearLayout) findViewById(R.id.unlock_tab);
        this.locked = (LinearLayout) findViewById(R.id.locked_tab);
        this.ivUnlock = (ImageView) findViewById(R.id.unlock_icon);
        this.ivLocked = (ImageView) findViewById(R.id.locked_icon);
        this.tvUnlock = (TextView) findViewById(R.id.unlock_txt);
        this.tvLocked = (TextView) findViewById(R.id.locked_txt);
        this.tvTitle = (TextView) findViewById(R.id.txt_title);
        this.tvTitle.setText(R.string.app_lock);
        this.mLockedApps = getLockedApp();
        this.lockedCount = this.mLockedApps.size();
        this.tvLocked.setText(getString(R.string.locked_apps) + getString(R.string.protected_begin) + this.lockedCount + getString(R.string.protected_end));
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddAppActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                System.gc();
                Intent mIntent = new Intent();
                mIntent.setClass(AddAppActivity.this.mContext, MonitorAppService.class);
                AddAppActivity.this.mContext.startService(mIntent);
                AddAppActivity.this.finish();
                System.exit(0);
            }
        });
        this.unlock.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddAppActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (AddAppActivity.this.flag != 0) {
                    AddAppActivity.this.flag = 0;
                    AddAppActivity.this.switcher(AddAppActivity.this.flag);
                    AddAppActivity.this.viewPager.setCurrentItem(0);
                }
            }
        });
        this.locked.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddAppActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (AddAppActivity.this.flag != 1) {
                    AddAppActivity.this.flag = 1;
                    AddAppActivity.this.switcher(AddAppActivity.this.flag);
                    AddAppActivity.this.viewPager.setCurrentItem(1);
                }
            }
        });
        this.locked.setClickable(false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ViewPagerAdapter extends PagerAdapter {
        ViewPagerAdapter() {
        }

        @Override // android.support.v4.view.PagerAdapter
        public void destroyItem(View collection, int position, Object view) {
            ((ViewPager) collection).removeView((View) AddAppActivity.this.mListViews.get(position));
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return AddAppActivity.this.mListViews.size();
        }

        @Override // android.support.v4.view.PagerAdapter
        public Object instantiateItem(View collection, int position) {
            ((ViewPager) collection).addView((View) AddAppActivity.this.mListViews.get(position), 0);
            return AddAppActivity.this.mListViews.get(position);
        }

        @Override // android.support.v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    void switcher(int flag) {
        if (flag == 0) {
            this.unlock.setBackgroundResource(R.drawable.gui_tab_select);
            this.ivUnlock.setImageResource(R.drawable.gui_unlock_pressed);
            this.tvUnlock.setTextColor(Color.parseColor("#1188c6"));
            this.locked.setBackgroundResource(R.drawable.gui_tab_normal);
            this.ivLocked.setImageResource(R.drawable.gui_locked_normal);
            this.tvLocked.setTextColor(Color.parseColor("#4a4a4a"));
            this.tvLocked.setText(getString(R.string.locked_apps) + getString(R.string.protected_begin) + this.lockedCount + getString(R.string.protected_end));
            this.tvUnlock.setText(getString(R.string.unlock_apps) + getString(R.string.protected_begin) + this.unlockCount + getString(R.string.protected_end));
        }
        if (flag == 1) {
            this.locked.setBackgroundResource(R.drawable.gui_tab_select);
            this.ivLocked.setImageResource(R.drawable.gui_locked_pressed);
            this.tvLocked.setTextColor(Color.parseColor("#1188c6"));
            this.unlock.setBackgroundResource(R.drawable.gui_tab_normal);
            this.ivUnlock.setImageResource(R.drawable.gui_unlock_normal);
            this.tvUnlock.setTextColor(Color.parseColor("#4a4a4a"));
            this.tvLocked.setText(getString(R.string.locked_apps) + getString(R.string.protected_begin) + this.lockedCount + getString(R.string.protected_end));
            this.tvUnlock.setText(getString(R.string.unlock_apps) + getString(R.string.protected_begin) + this.unlockCount + getString(R.string.protected_end));
        }
        updateUI();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ListItemListener implements AdapterView.OnItemClickListener {
        private Context mContext;

        ListItemListener(Context context) {
            this.mContext = context;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (AddAppActivity.this.flag == 0 && AddAppActivity.this.appAdapter.getItem(position) != null && AddAppActivity.this.appAdapter.getItem(position).getPkgName() != null && AddAppActivity.this.insertAppInfo(AddAppActivity.this.appAdapter.getItem(position).getPkgName(), AddAppActivity.this.appAdapter.getItem(position).getAppName())) {
                if (position >= AddAppActivity.this.dataApps.size()) {
                    AddAppActivity.this.sysApps.remove(AddAppActivity.this.appAdapter.getItem(position));
                } else {
                    AddAppActivity.this.dataApps.remove(AddAppActivity.this.appAdapter.getItem(position));
                }
                String appName = AddAppActivity.this.appAdapter.getItem(position).getAppName();
                AddAppActivity.this.appAdapter = new AppAdapter(this.mContext, AddAppActivity.this.sysApps, AddAppActivity.this.dataApps);
                AddAppActivity.this.appAdapter.notifyDataSetChanged();
                AddAppActivity.this.lv.setAdapter((ListAdapter) AddAppActivity.this.appAdapter);
                AddAppActivity.this.lv.setSelection(AddAppActivity.this.ListPos);
                Toast.makeText(this.mContext, appName + AddAppActivity.this.getString(R.string.locked), 0).show();
                AddAppActivity.access$1010(AddAppActivity.this);
                AddAppActivity.access$1108(AddAppActivity.this);
                AddAppActivity.this.tvLocked.setText(AddAppActivity.this.getString(R.string.locked_apps) + AddAppActivity.this.getString(R.string.protected_begin) + AddAppActivity.this.lockedCount + AddAppActivity.this.getString(R.string.protected_end));
                AddAppActivity.this.tvUnlock.setText(AddAppActivity.this.getString(R.string.unlock_apps) + AddAppActivity.this.getString(R.string.protected_begin) + AddAppActivity.this.unlockCount + AddAppActivity.this.getString(R.string.protected_end));
            }
            if (AddAppActivity.this.flag == 1 && AddAppActivity.this.lockedAppAdapter.getItem(position) != null && AddAppActivity.this.deleteFromDatabase(AddAppActivity.this.lockedAppAdapter.getItem(position).pkgName)) {
                try {
                    if (AppEngine.isThirdpartApp(this.mContext.getPackageManager().getApplicationInfo(AddAppActivity.this.lockedAppAdapter.getItem(position).pkgName, 0))) {
                        AddAppActivity.this.dataApps.add(AddAppActivity.this.mLockedApps.get(position));
                    } else {
                        AddAppActivity.this.sysApps.add(AddAppActivity.this.mLockedApps.get(position));
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                String lockedAppName = AddAppActivity.this.lockedAppAdapter.getItem(position).getAppName();
                AddAppActivity.this.mLockedApps.remove(position);
                AddAppActivity.this.lockedAppAdapter.notifyDataSetChanged();
                AddAppActivity.this.lv.setAdapter((ListAdapter) AddAppActivity.this.lockedAppAdapter);
                AddAppActivity.this.lv.setSelection(AddAppActivity.this.ListPos);
                Toast.makeText(this.mContext, lockedAppName + AddAppActivity.this.getString(R.string.unlock), 0).show();
                AddAppActivity.access$1008(AddAppActivity.this);
                AddAppActivity.access$1110(AddAppActivity.this);
                AddAppActivity.this.tvLocked.setText(AddAppActivity.this.getString(R.string.locked_apps) + AddAppActivity.this.getString(R.string.protected_begin) + AddAppActivity.this.lockedCount + AddAppActivity.this.getString(R.string.protected_end));
                AddAppActivity.this.tvUnlock.setText(AddAppActivity.this.getString(R.string.unlock_apps) + AddAppActivity.this.getString(R.string.protected_begin) + AddAppActivity.this.unlockCount + AddAppActivity.this.getString(R.string.protected_end));
            }
        }
    }

    private void updateUI() {
        if (this.flag == 0) {
            Message msg = new Message();
            msg.what = 0;
            this.handler.sendMessage(msg);
        }
        if (this.flag == 1) {
            this.mLockedApps = getLockedApp();
            this.lockedAppAdapter = new LockedAppAdapter(this.mContext, this.mLockedApps);
            this.lv2.setAdapter((ListAdapter) this.lockedAppAdapter);
            this.unlockCount = this.appAdapter.getCount();
            this.lockedCount = this.mLockedApps.size();
            this.tvLocked.setText(getString(R.string.locked_apps) + getString(R.string.protected_begin) + this.lockedCount + getString(R.string.protected_end));
            this.tvUnlock.setText(getString(R.string.unlock_apps) + getString(R.string.protected_begin) + this.unlockCount + getString(R.string.protected_end));
        }
    }

    public void initData() {
        new Thread() { // from class: com.lenovo.safebox.AddAppActivity.7
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                AddAppActivity.this.appEngine = new AppEngine(AddAppActivity.this.mContext);
                AddAppActivity.this.sysApps = AddAppActivity.this.appEngine.getSysApps();
                AddAppActivity.this.dataApps = AddAppActivity.this.appEngine.getDataApps();
                Message msg = new Message();
                msg.what = 0;
                AddAppActivity.this.handler.sendMessage(msg);
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
                    result.getString(result.getColumnIndex(PrivateSpaceHelper.APP_NAME));
                    appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
                    appInfo.setDrawable(packageInfo.applicationInfo.loadIcon(pm));
                    PrivateSpaceHelper privateSpaceHelper4 = this.mHelper;
                    appInfo.setPkgName(result.getString(result.getColumnIndex(PrivateSpaceHelper.PKG)));
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
    protected void onPause() {
        Intent mIntent = new Intent();
        mIntent.setClass(this, MonitorAppService.class);
        startService(mIntent);
        finish();
        super.onPause();
    }

    public CustomDialog createActionDialog() {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(this);
        mediaBuilder.setTitle(getString(R.string.exit_title));
        mediaBuilder.setMessage(getString(R.string.app_exit_msg));
        mediaBuilder.setNegativeButton(getString(R.string.cancel), (DialogInterface.OnClickListener) null);
        mediaBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.AddAppActivity.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                System.gc();
                AddAppActivity.this.finish();
                System.exit(0);
            }
        });
        return mediaBuilder.create();
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
}

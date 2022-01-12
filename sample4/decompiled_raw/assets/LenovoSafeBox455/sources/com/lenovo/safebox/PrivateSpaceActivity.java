package com.lenovo.safebox;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.lenovo.lps.sus.EventType;
import com.lenovo.lps.sus.SUS;
import com.lenovo.lps.sus.SUSListener;
import com.lenovo.lps.sus.c.a;
import com.lenovo.safebox.dialog.CustomDialog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Calendar;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public class PrivateSpaceActivity extends Activity {
    public static boolean isPrivateMedia;
    public static SUS mSUS;
    public static SUSListener mSUSListener;
    private TextView appTitle;
    private Calendar beginTime;
    SQLiteDatabase db;
    private SharedPreferences.Editor editor;
    private ImageView ivBack;
    private int lastDay;
    private int lastMonth;
    private int[] list;
    private ListView lv;
    private ListViewAdapter lvAdapter;
    private Context mContext;
    private Cursor mCursor;
    PrivateSpaceHelper mHelper;
    private Uri mUri;
    private String passedPWD;
    private String password;
    private File priFile;
    private File priImage;
    private File priVideo;
    private SharedPreferences settings;
    private String TAG = "PrivateSpaceActivity ";
    private String PREFS_NAME = "pass";
    private int imgNum = 0;
    private int videoNum = 0;
    private int fileNum = 0;
    private String FileName = null;

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.main_list_activity);
        this.mContext = this;
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().detectLeakedSqlLiteObjects().build());
        prework();
        initView();
        setResult(10);
    }

    private boolean isForceCmccVersioncode() {
        try {
            if (getPackageManager().getPackageInfo("com.lenovo.safecenter", 0).versionCode >= 3831623) {
                return true;
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private void prework() {
        if (isForceCmccVersioncode()) {
            PrivateSpaceTools.forceCmcc = true;
        } else {
            PrivateSpaceTools.forceCmcc = false;
        }
        String info = Settings.System.getString(this.mContext.getContentResolver(), "cpuinfo");
        if (info != null && info.equals("intel")) {
            PrivateSpaceTools.busybox = "/data/data/com.lenovo.safebox/toolbox";
            PrivateSpaceTools.hideFolder = PrivateSpaceTools.busybox + " mount -o loop -t ext2 " + PrivateSpaceTools.sdDir + PrivateSpaceTools.coverFile + " " + PrivateSpaceTools.sdDir + PrivateSpaceTools.mountPoint;
            PrivateSpaceTools.showFolder = PrivateSpaceTools.busybox + " umount " + PrivateSpaceTools.sdDir + PrivateSpaceTools.mountPoint;
        }
        if (Build.MODEL.contains("K900")) {
            PrivateSpaceTools.forceCmcc = false;
            if (!new File("/dev/loop100").exists()) {
                PrivateSpaceTools.socketClient(PrivateSpaceTools.createLoopNod);
            }
            PrivateSpaceTools.hideFolder = "losetup /dev/loop100 /sdcard/.cover.img \n " + PrivateSpaceTools.busybox + " mount -t ext2 /dev/loop100 " + PrivateSpaceTools.sdDir + PrivateSpaceTools.mountPoint;
            PrivateSpaceTools.showFolder = PrivateSpaceTools.busybox + " umount " + PrivateSpaceTools.sdDir + PrivateSpaceTools.mountPoint;
        }
        isPrivateMedia = false;
        this.settings = getSharedPreferences(this.PREFS_NAME, 0);
        this.editor = this.settings.edit();
        this.beginTime = Calendar.getInstance();
        clearCache();
        needUpdate();
        this.passedPWD = getIntent().getStringExtra("pwd");
        if (this.passedPWD == null) {
            System.exit(0);
        }
        if (!PrivateSpaceTools.checkSdSpace()) {
            CustomDialog noSpaceDia = createNospaceDialog();
            noSpaceDia.setCanceledOnTouchOutside(false);
            noSpaceDia.setCancelable(false);
            noSpaceDia.show();
        }
        this.mHelper = new PrivateSpaceHelper(this, PrivateSpaceTools.DB_NAME, null, 1);
        PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
        File imgFile = new File(PrivateSpaceTools.coverFilePath);
        File busyboxFile = new File(PrivateSpaceTools.busybox);
        File psFolder = new File("/sdcard/.pFolder");
        this.priImage = new File(PrivateSpaceTools.priImgFolder);
        this.priVideo = new File(PrivateSpaceTools.priVideoFolder);
        this.priFile = new File(PrivateSpaceTools.priFilesFolder);
        if (!imgFile.exists()) {
            CopyAssets();
        }
        if (!busyboxFile.exists()) {
            CopyAssets();
        }
        if (!PrivateSpaceTools.isLenovo() || PrivateSpaceTools.forceCmcc) {
            PrivateSpaceTools.socketClient("chmod 755 " + PrivateSpaceTools.busybox);
        } else {
            PrivateSpaceTools.prepareExecuteFile(this, "chmod 755 " + PrivateSpaceTools.busybox);
        }
        if (!psFolder.exists()) {
            psFolder.mkdir();
            this.priImage.mkdir();
            this.priVideo.mkdir();
            this.priFile.mkdir();
        }
        if (!this.priImage.exists()) {
            this.priImage.mkdir();
        }
        if (!this.priVideo.exists()) {
            this.priVideo.mkdir();
        }
        if (!this.priFile.exists()) {
            this.priFile.mkdir();
        }
        PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
    }

    private void initView() {
        this.appTitle = (TextView) findViewById(R.id.txt_title);
        this.appTitle.setText(R.string.app_name);
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateSpaceActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
                PrivateSpaceActivity.this.finish();
                System.gc();
                System.exit(0);
            }
        });
        this.lv = (ListView) findViewById(R.id.main_list);
    }

    private void clearCache() {
        File cacheFile = new File("/data/data/com.lenovo.safebox/cache/LenovoSafeBox.apk");
        if (cacheFile.exists()) {
            cacheFile.delete();
        }
    }

    private boolean needUpdate() {
        this.lastDay = this.settings.getInt("lastDay", 0);
        this.lastMonth = this.settings.getInt("lastMonth", 0);
        if (this.beginTime == null) {
            this.beginTime = Calendar.getInstance();
        }
        if (this.lastDay != this.beginTime.get(5)) {
            this.editor.putInt("lastDay", this.beginTime.get(5));
            this.editor.putInt("lastMonth", this.beginTime.get(2));
            this.editor.commit();
            updateApp();
            return true;
        } else if (this.lastMonth == this.beginTime.get(2)) {
            return false;
        } else {
            this.editor.putInt("lastDay", this.beginTime.get(5));
            this.editor.putInt("lastMonth", this.beginTime.get(2));
            this.editor.commit();
            updateApp();
            return true;
        }
    }

    private void CopyAssets() {
        Exception e;
        OutputStream out;
        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e2) {
            Log.e("tag", e2.getMessage());
        }
        if (files != null) {
            for (String filename : files) {
                try {
                    if (filename.equals(PrivateSpaceTools.coverFileOri)) {
                        InputStream in = assetManager.open(filename);
                        out = new FileOutputStream(PrivateSpaceTools.coverFilePath);
                        try {
                            copyFile(in, out);
                            in.close();
                            out.flush();
                            out.close();
                            out = null;
                        } catch (Exception e3) {
                            e = e3;
                            Log.e(this.TAG, e.getMessage());
                        }
                    } else {
                        out = null;
                    }
                    if (PrivateSpaceTools.busybox.equals("/data/data/com.lenovo.safebox/toolbox")) {
                        if (filename.equals("toolbox")) {
                            InputStream in2 = assetManager.open(filename);
                            OutputStream out2 = new FileOutputStream(PrivateSpaceTools.busybox);
                            copyFile(in2, out2);
                            in2.close();
                            out2.flush();
                            out2.close();
                        }
                    } else if (filename.equals("busybox")) {
                        InputStream in3 = assetManager.open(filename);
                        OutputStream out3 = new FileOutputStream(PrivateSpaceTools.busybox);
                        copyFile(in3, out3);
                        in3.close();
                        out3.flush();
                        out3.close();
                    }
                } catch (Exception e4) {
                    e = e4;
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int read = in.read(buffer);
            if (read != -1) {
                out.write(buffer, 0, read);
            } else {
                return;
            }
        }
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        System.gc();
        System.exit(0);
        return super.onKeyDown(keyCode, event);
    }

    public CustomDialog createActionDialog() {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(this);
        mediaBuilder.setTitle(getString(R.string.exit_title));
        mediaBuilder.setMessage(getString(R.string.exit_msg));
        mediaBuilder.setNegativeButton(getString(R.string.cancel), (DialogInterface.OnClickListener) null);
        mediaBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.PrivateSpaceActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                System.gc();
                PrivateSpaceActivity.this.finish();
                System.exit(0);
            }
        });
        return mediaBuilder.create();
    }

    public CustomDialog createNospaceDialog() {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(this);
        mediaBuilder.setTitle(getString(R.string.exit_title));
        mediaBuilder.setMessage(getString(R.string.no_space_prompt));
        mediaBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.PrivateSpaceActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                System.gc();
                PrivateSpaceActivity.this.finish();
                System.exit(0);
            }
        });
        return mediaBuilder.create();
    }

    private void waitOpen() {
        int countTmp = 0;
        File[] tmpFilelist = this.priImage.listFiles();
        while (tmpFilelist == null) {
            countTmp++;
            if (countTmp != 1000 && PrivateSpaceTools.forceCmcc) {
                PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
                tmpFilelist = this.priImage.listFiles();
                if (tmpFilelist != null) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void updateUI() {
        this.list = new int[3];
        PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
        try {
            this.priImage = new File(PrivateSpaceTools.priImgFolder);
            this.priVideo = new File(PrivateSpaceTools.priVideoFolder);
            this.priFile = new File(PrivateSpaceTools.priFilesFolder);
            if (PrivateSpaceTools.forceCmcc || !PrivateSpaceTools.isLenovoProduct) {
                waitOpen();
            }
            this.imgNum = this.priImage.listFiles().length;
            this.videoNum = this.priVideo.listFiles().length;
            this.fileNum = this.priFile.listFiles().length;
            PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        } catch (Exception e) {
            e.printStackTrace();
            if (!PrivateSpaceTools.checkSdSpace()) {
                CustomDialog noSpaceDia = createNospaceDialog();
                noSpaceDia.setCanceledOnTouchOutside(false);
                noSpaceDia.setCancelable(false);
                noSpaceDia.show();
            }
        }
        this.list[0] = this.imgNum;
        this.list[1] = this.videoNum;
        this.list[2] = this.fileNum;
        this.lvAdapter = new ListViewAdapter(this.list);
        this.lv.setAdapter((ListAdapter) this.lvAdapter);
        this.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lenovo.safebox.PrivateSpaceActivity.4
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                switch (position) {
                    case 0:
                        Intent manIntent = new Intent(PrivateSpaceActivity.this.getApplicationContext(), PrivateMediaActivity.class);
                        manIntent.setFlags(67141632);
                        manIntent.addFlags(8388608);
                        manIntent.setData(Uri.parse("image"));
                        PrivateSpaceActivity.this.startActivity(manIntent);
                        PrivateSpaceActivity.isPrivateMedia = true;
                        return;
                    case 1:
                        Intent vedioIntent = new Intent(PrivateSpaceActivity.this.getApplicationContext(), PrivateMediaActivity.class);
                        vedioIntent.setFlags(67141632);
                        vedioIntent.addFlags(8388608);
                        vedioIntent.setData(Uri.parse("video"));
                        PrivateSpaceActivity.this.startActivity(vedioIntent);
                        PrivateSpaceActivity.isPrivateMedia = true;
                        return;
                    case 2:
                        Intent fileIntent = new Intent(PrivateSpaceActivity.this.getApplicationContext(), PrivateFileActivity.class);
                        fileIntent.setFlags(67108864);
                        fileIntent.addFlags(8388608);
                        PrivateSpaceActivity.this.startActivity(fileIntent);
                        PrivateSpaceActivity.isPrivateMedia = true;
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void updateApp() {
        int versionCode;
        mSUSListener = new SUSListener() { // from class: com.lenovo.safebox.PrivateSpaceActivity.5
            @Override // com.lenovo.lps.sus.SUSListener
            public void onUpdateNotification(EventType eventType, String param) {
                switch (AnonymousClass6.$SwitchMap$com$lenovo$lps$sus$EventType[eventType.ordinal()]) {
                    case 1:
                        Log.e(PrivateSpaceActivity.this.TAG, "SUS_FAIL_NETWORKUNAVAILABLE");
                        PrivateSpaceActivity.this.editor.putInt("lastDay", 0);
                        PrivateSpaceActivity.this.editor.commit();
                        return;
                    case 2:
                        Log.e(PrivateSpaceActivity.this.TAG, "SUS_FAIL_INSUFFICIENTSTORAGESPACE");
                        return;
                    case 3:
                        PrivateSpaceActivity.this.editor.putInt("lastDay", 0);
                        PrivateSpaceActivity.this.editor.commit();
                        Log.e(PrivateSpaceActivity.this.TAG, "SUS_FAIL_DOWNLOAD_EXCEPTION");
                        return;
                    case 4:
                        Log.e(PrivateSpaceActivity.this.TAG, "SUS_WARNING_PENDING");
                        return;
                    case 5:
                        Log.e(PrivateSpaceActivity.this.TAG, "SUS_DOWNLOADSTART");
                        return;
                    case 6:
                        Log.e(PrivateSpaceActivity.this.TAG, "SUS_DOWNLOADCOMPLETE");
                        PrivateSpaceActivity.mSUSListener = null;
                        Log.i(PrivateSpaceActivity.this.TAG, "DOWNLOADCOMPLETE:" + param);
                        String command = "chmod 777 /data/data/com.lenovo.safebox/cache/" + PrivateSpaceActivity.this.FileName;
                        Runtime runtime = Runtime.getRuntime();
                        try {
                            runtime.exec(command);
                            runtime.exec("chmod 777 /data/data/com.lenovo.safebox/cache");
                            Intent intent = new Intent("android.intent.action.VIEW");
                            intent.setDataAndType(Uri.parse("file:///data/data/com.lenovo.safebox/cache/" + PrivateSpaceActivity.this.FileName), "application/vnd.android.package-archive");
                            PrivateSpaceActivity.this.startActivity(intent);
                            Toast.makeText(PrivateSpaceActivity.this.mContext, (int) R.string.updateversion_ok, 1).show();
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    case MotionEventCompat.ACTION_HOVER_MOVE /* 7 */:
                        Log.e(PrivateSpaceActivity.this.TAG, "SUS_QUERY_RESP");
                        Log.e(PrivateSpaceActivity.this.TAG, param);
                        if (param != null && param.length() > 0) {
                            try {
                                JSONObject jsonObject = new JSONObject(param);
                                try {
                                    String resultion = jsonObject.getString("RES");
                                    if ("SUCCESS".equals(resultion)) {
                                        String jsonObjectStr = jsonObject.getString("ChannelKey");
                                        if (jsonObjectStr != null && jsonObjectStr.length() > 0) {
                                            URLDecoder.decode(jsonObjectStr);
                                        }
                                        String jsonObjectStr2 = jsonObject.getString("VerCode");
                                        if (jsonObjectStr2 != null && jsonObjectStr2.length() > 0) {
                                            URLDecoder.decode(jsonObjectStr2);
                                        }
                                        String jsonObjectStr3 = jsonObject.getString("VerName");
                                        String VerName = (jsonObjectStr3 == null || jsonObjectStr3.length() <= 0) ? null : URLDecoder.decode(jsonObjectStr3);
                                        String jsonObjectStr4 = jsonObject.getString("DownloadURL");
                                        String DownloadURL = (jsonObjectStr4 == null || jsonObjectStr4.length() <= 0) ? null : URLDecoder.decode(jsonObjectStr4);
                                        String jsonObjectStr5 = jsonObject.getString("Size");
                                        String Size = (jsonObjectStr5 == null || jsonObjectStr5.length() <= 0) ? null : URLDecoder.decode(jsonObjectStr5);
                                        String jsonObjectStr6 = jsonObject.getString("UpdateDesc");
                                        String UpdateDesc = (jsonObjectStr6 == null || jsonObjectStr6.length() <= 0) ? null : URLDecoder.decode(jsonObjectStr6);
                                        String jsonObjectStr7 = jsonObject.getString("FileName");
                                        PrivateSpaceActivity.this.FileName = (jsonObjectStr7 == null || jsonObjectStr7.length() <= 0) ? null : URLDecoder.decode(jsonObjectStr7);
                                        Intent i = new Intent(PrivateSpaceActivity.this, UpdateDialogActivity.class);
                                        i.putExtra("versionname", VerName);
                                        i.putExtra("appinfo", UpdateDesc);
                                        i.putExtra("url", DownloadURL);
                                        i.putExtra("apkname", PrivateSpaceActivity.this.FileName);
                                        i.putExtra("filesize", Long.valueOf(Size));
                                        i.addFlags(67108864);
                                        PrivateSpaceActivity.this.startActivity(i);
                                        return;
                                    }
                                    if ("LATESTVERSION".equals(resultion) || "NOTFOUND".equals(resultion) || !"EXCEPTION".equals(resultion)) {
                                    }
                                    return;
                                } catch (JSONException e2) {
                                    e = e2;
                                    Log.i(PrivateSpaceActivity.this.TAG, "updateversion error:" + e);
                                    return;
                                }
                            } catch (JSONException e3) {
                                e = e3;
                            }
                        } else {
                            return;
                        }
                        break;
                    default:
                        return;
                }
            }
        };
        SUS.setUpdateDescribeDisableFlag(false);
        SUS.setSDKPromptDisableFlag(false);
        SUS.setSUSListener(mSUSListener);
        if (!SUS.isVersionUpdateStarted()) {
            SUS.setDownloadPath("data/data/com.lenovo.safebox/cache", 10000000, 1000);
            PackageInfo packageinfo = null;
            ApplicationInfo applicationinfo = null;
            String pkgName = getPackageName();
            try {
                packageinfo = getPackageManager().getPackageInfo(pkgName, 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            try {
                versionCode = packageinfo.versionCode;
            } catch (Exception e2) {
                versionCode = 0;
            }
            try {
                applicationinfo = getPackageManager().getApplicationInfo(pkgName, 128);
            } catch (PackageManager.NameNotFoundException e1) {
                e1.printStackTrace();
            }
            String channelKey = null;
            if (!(applicationinfo == null || applicationinfo.metaData == null)) {
                channelKey = applicationinfo.metaData.getString(a.J);
            }
            SUS.AsyncQueryLatestVersionByPackageName(this, pkgName, versionCode, channelKey);
        }
    }

    /* renamed from: com.lenovo.safebox.PrivateSpaceActivity$6 */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass6 {
        static final /* synthetic */ int[] $SwitchMap$com$lenovo$lps$sus$EventType = new int[EventType.values().length];

        static {
            try {
                $SwitchMap$com$lenovo$lps$sus$EventType[EventType.SUS_FAIL_NETWORKUNAVAILABLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$lenovo$lps$sus$EventType[EventType.SUS_FAIL_INSUFFICIENTSTORAGESPACE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$lenovo$lps$sus$EventType[EventType.SUS_FAIL_DOWNLOAD_EXCEPTION.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$lenovo$lps$sus$EventType[EventType.SUS_WARNING_PENDING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$lenovo$lps$sus$EventType[EventType.SUS_DOWNLOADSTART.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$lenovo$lps$sus$EventType[EventType.SUS_DOWNLOADCOMPLETE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$lenovo$lps$sus$EventType[EventType.SUS_QUERY_RESP.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override // android.app.Activity
    protected void onStop() {
        System.gc();
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (!isPrivateMedia) {
            PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        }
        isPrivateMedia = false;
        super.onDestroy();
    }

    /* loaded from: classes.dex */
    private class ViewHolder {
        ImageView icon;
        TextView num;
        TextView title;

        private ViewHolder() {
            PrivateSpaceActivity.this = r1;
        }
    }

    /* loaded from: classes.dex */
    public class ListViewAdapter extends BaseAdapter {
        int[] numList;

        public ListViewAdapter(int[] list) {
            PrivateSpaceActivity.this = r1;
            this.numList = list;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return 3;
        }

        @Override // android.widget.Adapter
        public Object getItem(int position) {
            return null;
        }

        @Override // android.widget.Adapter
        public long getItemId(int position) {
            return 0;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View convertView2 = View.inflate(PrivateSpaceActivity.this.mContext, R.layout.main_list_item, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (ImageView) convertView2.findViewById(R.id.grid_icon);
            holder.title = (TextView) convertView2.findViewById(R.id.grid_title);
            holder.num = (TextView) convertView2.findViewById(R.id.grid_num);
            switch (position) {
                case 0:
                    holder.icon.setImageResource(R.drawable.icon_img);
                    holder.title.setText(PrivateSpaceActivity.this.mContext.getString(R.string.image));
                    holder.num.setText(PrivateSpaceActivity.this.mContext.getString(R.string.protected_begin) + this.numList[position] + PrivateSpaceActivity.this.mContext.getString(R.string.protected_end));
                    break;
                case 1:
                    holder.icon.setImageResource(R.drawable.icon_video);
                    holder.title.setText(PrivateSpaceActivity.this.mContext.getString(R.string.video));
                    holder.num.setText(PrivateSpaceActivity.this.mContext.getString(R.string.protected_begin) + this.numList[position] + PrivateSpaceActivity.this.mContext.getString(R.string.protected_end));
                    break;
                case 2:
                    holder.icon.setImageResource(R.drawable.icon_file);
                    holder.title.setText(PrivateSpaceActivity.this.mContext.getString(R.string.file));
                    holder.num.setText(PrivateSpaceActivity.this.mContext.getString(R.string.protected_begin) + this.numList[position] + PrivateSpaceActivity.this.mContext.getString(R.string.protected_end));
                    break;
            }
            return convertView2;
        }
    }
}

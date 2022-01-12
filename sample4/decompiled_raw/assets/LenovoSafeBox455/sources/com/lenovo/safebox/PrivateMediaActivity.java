package com.lenovo.safebox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.lenovo.safebox.adapter.PriMediaAdapter;
import com.lenovo.safebox.dialog.CustomDialog;
import com.lenovo.safebox.dialog.ImageDialogListener;
import com.lenovo.safebox.dialog.VideoDialogListener;
import com.lenovo.safebox.engine.ImageEngine;
import com.lenovo.safebox.service.MonitorAppService;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
/* loaded from: classes.dex */
public class PrivateMediaActivity extends Activity {
    private static final int DELETE_SUCCESS = 2;
    private static final int RESTORE_COVER_MSG = 3;
    private static final int RESTORE_SUCCESS = 1;
    private static final int RESTORE_TO_RECOVERY = 4;
    private int ListPos;
    private int actionControl;
    private View addAction;
    private Button addButton;
    private File[] childList;
    private ArrayList<String> chosenFiles;
    private boolean closePrivateSpace;
    SQLiteDatabase db;
    private Button delBtn;
    private IntentFilter filter;
    private GridView girdView;
    private CustomDialog imgDialog;
    private TextView imgEditor;
    private ImageEngine imgEngine;
    private TextView infoBar;
    private ImageView ivBack;
    public PriMediaAdapter mAdpater;
    PrivateSpaceHelper mHelper;
    private BroadcastReceiver mReceiver;
    private ArrayList<MediaInfo> miLst;
    private View moveAction;
    private ImageView nullBgImage;
    private File pFolder;
    private String password;
    private ProgressBar progress;
    private ProgressDialog progressDialog;
    private Button restoreBtn;
    private Button selectBtn;
    private SharedPreferences settings;
    private Context thisContext;
    private TextView titleTv;
    public int trigger;
    private CustomDialog videoDialog;
    private String TAG = "PrivateMediaActivity  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private final int moveActionShow = 1;
    private final int moveActionHide = 0;
    private boolean fromOpenFile = false;
    private String PREFS_NAME = "pass";
    private AbsListView.OnScrollListener ScrollLis = new AbsListView.OnScrollListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.5
        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == 0) {
                PrivateMediaActivity.this.ListPos = PrivateMediaActivity.this.girdView.getFirstVisiblePosition();
            }
        }
    };
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.PrivateMediaActivity.6
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        if (PrivateMediaActivity.this.progressDialog != null) {
                            PrivateMediaActivity.this.progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                    }
                    PrivateMediaActivity.this.updateUI();
                    PrivateMediaActivity.this.mAdpater.notifyDataSetChanged();
                    PrivateMediaActivity.this.moveAction.setVisibility(8);
                    PrivateMediaActivity.this.addButton.setVisibility(0);
                    PrivateSpaceTools.scanSD(PrivateMediaActivity.this.thisContext);
                    PrivateMediaActivity.this.closePrivateSpace = true;
                    return;
                case 2:
                    try {
                        if (PrivateMediaActivity.this.progressDialog != null) {
                            PrivateMediaActivity.this.progressDialog.dismiss();
                        }
                    } catch (Exception e2) {
                    }
                    PrivateMediaActivity.this.updateUI();
                    PrivateMediaActivity.this.mAdpater.notifyDataSetChanged();
                    PrivateMediaActivity.this.closePrivateSpace = true;
                    return;
                case 3:
                    Toast.makeText(PrivateMediaActivity.this.thisContext, msg.obj.toString(), 0).show();
                    PrivateMediaActivity.this.closePrivateSpace = true;
                    return;
                case 4:
                    Toast.makeText(PrivateMediaActivity.this.thisContext, msg.obj.toString(), 0).show();
                    PrivateMediaActivity.this.closePrivateSpace = true;
                    return;
                default:
                    return;
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preWork();
        this.thisContext = this;
        requestWindowFeature(1);
        setContentView(R.layout.gui_privatemedia_activity);
        this.closePrivateSpace = true;
        if (getIntent().getDataString() == null || getIntent().getDataString().isEmpty()) {
            this.trigger = getIntent().getFlags();
        } else if (getIntent().getDataString().equals("image")) {
            this.trigger = 0;
        } else {
            this.trigger = 1;
        }
        this.miLst = new ArrayList<>();
        initView();
        this.chosenFiles = new ArrayList<>();
        this.imgEditor.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (PrivateMediaActivity.this.actionControl == 1) {
                    PrivateMediaActivity.this.addButton.setVisibility(0);
                    PrivateMediaActivity.this.moveAction.setVisibility(8);
                    PrivateMediaActivity.this.actionControl = 0;
                    PrivateMediaActivity.this.mAdpater.setCheckboxStatus(8);
                    PrivateMediaActivity.this.chosenFiles.clear();
                    PrivateMediaActivity.this.mAdpater.setDisSelectAll();
                    PrivateMediaActivity.this.mAdpater.notifyDataSetChanged();
                    PrivateMediaActivity.this.imgEditor.setText(R.string.edit);
                    PrivateMediaActivity.this.thisContext.getResources().getDrawable(R.drawable.edit_icon).setBounds(0, 0, 25, 25);
                    return;
                }
                PrivateMediaActivity.this.actionControl = 1;
                PrivateMediaActivity.this.moveAction.setVisibility(0);
                PrivateMediaActivity.this.addButton.setVisibility(8);
                PrivateMediaActivity.this.selectBtn.setText(R.string.select_all);
                PrivateMediaActivity.this.mAdpater.setCheckboxStatus(0);
                PrivateMediaActivity.this.chosenFiles.clear();
                PrivateMediaActivity.this.mAdpater.setDisSelectAll();
                PrivateMediaActivity.this.mAdpater.notifyDataSetChanged();
                PrivateMediaActivity.this.girdView.setAdapter((ListAdapter) PrivateMediaActivity.this.mAdpater);
                PrivateMediaActivity.this.imgEditor.setText(R.string.complete);
                PrivateMediaActivity.this.thisContext.getResources().getDrawable(R.drawable.complete_icon).setBounds(0, 0, 25, 25);
            }
        });
        this.girdView.setAdapter((ListAdapter) this.mAdpater);
        this.girdView.setOnScrollListener(this.ScrollLis);
        this.addButton.setOnClickListener(new AddButtonListener(this));
        this.girdView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (PrivateMediaActivity.this.actionControl == 1) {
                    PrivateMediaActivity.this.mAdpater.getItem(position).setCheckHolder(PrivateMediaActivity.this.mAdpater.getItem(position).getCheckHolder());
                    MediaInfo tmpMI = PrivateMediaActivity.this.mAdpater.getItem(position);
                    if (PrivateMediaActivity.this.mAdpater.getItem(position).getCheckHolder()) {
                        if (!PrivateMediaActivity.this.chosenFiles.contains(PrivateMediaActivity.this.mAdpater.getItem(position).getFilePath())) {
                            PrivateMediaActivity.this.chosenFiles.add(tmpMI.getFilePath());
                        }
                        if (PrivateMediaActivity.this.chosenFiles.size() == PrivateMediaActivity.this.mAdpater.getCount()) {
                            PrivateMediaActivity.this.selectBtn.setText(R.string.cancel);
                        } else {
                            PrivateMediaActivity.this.selectBtn.setText(R.string.select_all);
                        }
                    } else {
                        PrivateMediaActivity.this.selectBtn.setText(R.string.select_all);
                        PrivateMediaActivity.this.chosenFiles.remove(tmpMI.getFilePath());
                    }
                    PrivateMediaActivity.this.mAdpater.notifyDataSetChanged();
                    return;
                }
                PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
                PrivateMediaActivity.this.openFile(PrivateMediaActivity.this.thisContext, PrivateMediaActivity.this.childList[position]);
            }
        });
        this.girdView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.3
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PrivateMediaActivity.this.createActionDialog(PrivateMediaActivity.this.thisContext, new PriMediaListener(PrivateMediaActivity.this.thisContext, PrivateSpaceTools.getFileName(PrivateMediaActivity.this.childList[position].getName()), PrivateMediaActivity.this.childList[position].getAbsolutePath())).show();
                return true;
            }
        });
    }

    public void showProgress() {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.4
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                PrivateMediaActivity.this.progressDialog = null;
            }
        });
        this.progressDialog.setProgressStyle(0);
        this.progressDialog.setMessage(getString(R.string.exec_state));
        this.progressDialog.show();
    }

    private void waitOpen() {
        int countTmp = 0;
        File priImage = new File(PrivateSpaceTools.priImgFolder);
        File[] tmpFilelist = priImage.listFiles();
        while (tmpFilelist == null) {
            countTmp++;
            if (countTmp != 500) {
                PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
                tmpFilelist = priImage.listFiles();
                if (tmpFilelist != null) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    void updateUI() {
        PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
        this.miLst.clear();
        this.actionControl = 0;
        this.selectBtn.setText(R.string.select_all);
        this.moveAction.setVisibility(8);
        this.addButton.setVisibility(0);
        try {
            if (PrivateSpaceTools.forceCmcc || !PrivateSpaceTools.isLenovoProduct) {
                waitOpen();
            }
            if (this.trigger == 0) {
                this.pFolder = new File(PrivateSpaceTools.priImgFolder);
                this.childList = this.pFolder.listFiles();
                if (this.childList.length > 0) {
                    this.nullBgImage.setVisibility(8);
                    this.infoBar.setVisibility(0);
                    this.imgEditor.setVisibility(0);
                    this.infoBar.setText(getString(R.string.protected_img) + this.childList.length + getString(R.string.protected_end));
                } else {
                    this.nullBgImage.setVisibility(0);
                    if (Locale.getDefault().getLanguage().equals("en")) {
                        this.nullBgImage.setBackgroundResource(R.drawable.gui_img_null_en);
                    } else {
                        this.nullBgImage.setBackgroundResource(R.drawable.gui_img_null);
                    }
                    this.infoBar.setVisibility(8);
                    this.imgEditor.setVisibility(4);
                }
                for (int i = 0; i < this.childList.length; i++) {
                    this.miLst.add(new MediaInfo(this.childList[i].getAbsolutePath(), this.childList[i].getAbsolutePath()));
                }
                this.mAdpater = new PriMediaAdapter(this, this.miLst, this.trigger, this.girdView);
            } else {
                this.pFolder = new File(PrivateSpaceTools.priVideoFolder);
                this.childList = this.pFolder.listFiles();
                if (this.childList.length > 0) {
                    this.nullBgImage.setVisibility(8);
                    this.imgEditor.setVisibility(0);
                    this.infoBar.setVisibility(0);
                    this.infoBar.setText(getString(R.string.protected_video) + this.childList.length + getString(R.string.protected_end));
                } else {
                    this.nullBgImage.setVisibility(0);
                    if (Locale.getDefault().getLanguage().equals("en")) {
                        this.nullBgImage.setBackgroundResource(R.drawable.gui_video_null_en);
                    } else {
                        this.nullBgImage.setBackgroundResource(R.drawable.gui_video_null);
                    }
                    this.infoBar.setVisibility(8);
                    this.imgEditor.setVisibility(4);
                }
                for (int i2 = 0; i2 < this.childList.length; i2++) {
                    this.miLst.add(new MediaInfo(this.childList[i2].getAbsolutePath(), this.childList[i2].getAbsolutePath()));
                }
                this.mAdpater = new PriMediaAdapter(this, this.miLst, this.trigger, this.girdView);
            }
        } catch (NullPointerException e) {
            finish();
        }
        this.chosenFiles.clear();
        this.mAdpater = new PriMediaAdapter(this, this.miLst, this.trigger, this.girdView);
        this.mAdpater.notifyDataSetChanged();
        this.girdView.setAdapter((ListAdapter) this.mAdpater);
        this.girdView.setSelection(this.ListPos);
        this.imgEditor.setText(R.string.edit);
        this.thisContext.getResources().getDrawable(R.drawable.edit_icon).setBounds(0, 0, 25, 25);
        this.imgEditor.setPadding(10, 0, 0, 0);
    }

    public CustomDialog createActionDialog(Context context, PriMediaListener mPriMediaListener) {
        return new CustomDialog.Builder(context).setTitle(context.getString(R.string.dia_showfiles_title)).setItems(new String[]{context.getString(R.string.restore), context.getString(R.string.delete), context.getString(R.string.ori_path)}, mPriMediaListener).create();
    }

    public CustomDialog createDeleteDialog() {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(this);
        mediaBuilder.setTitle(getString(R.string.delete_title));
        mediaBuilder.setMessage(getString(R.string.delete_msg));
        mediaBuilder.setNegativeButton(getString(R.string.cancel), (DialogInterface.OnClickListener) null);
        mediaBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.7
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                if (PrivateMediaActivity.this.chosenFiles.size() > 0) {
                    PrivateMediaActivity.this.showProgress();
                    PrivateMediaActivity.this.closePrivateSpace = false;
                    new Thread() { // from class: com.lenovo.safebox.PrivateMediaActivity.7.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            boolean sendFlag = true;
                            StringBuffer buffer = new StringBuffer();
                            try {
                                PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
                                ArrayList<String> delList = new ArrayList<>();
                                for (int i = 0; i < PrivateMediaActivity.this.chosenFiles.size(); i++) {
                                    buffer.append(PrivateSpaceTools.busybox + " rm " + PrivateSpaceTools.resolveFilename(((String) PrivateMediaActivity.this.chosenFiles.get(i)).toString()) + "\n");
                                    delList.add(PrivateSpaceTools.getFileName(((String) PrivateMediaActivity.this.chosenFiles.get(i)).toString()));
                                }
                                PrivateMediaActivity.this.deleteData(delList);
                                buffer.append("echo done\n");
                                PrivateSpaceTools.prepareExecuteFile(PrivateMediaActivity.this.thisContext, buffer.toString());
                                delList.clear();
                            } catch (IndexOutOfBoundsException e) {
                                e.printStackTrace();
                                sendFlag = false;
                            } catch (NullPointerException e2) {
                                e2.printStackTrace();
                                sendFlag = false;
                            }
                            if (sendFlag) {
                                PrivateMediaActivity.this.handler.sendMessage(PrivateMediaActivity.this.handler.obtainMessage(2));
                            }
                        }
                    }.start();
                }
            }
        });
        return mediaBuilder.create();
    }

    public CustomDialog createSingleDeleteDialog(final String fileLabel, final String cmd) {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(this);
        mediaBuilder.setTitle(getString(R.string.delete_title));
        mediaBuilder.setMessage(getString(R.string.delete_msg));
        mediaBuilder.setNegativeButton(getString(R.string.cancel), (DialogInterface.OnClickListener) null);
        mediaBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PrivateSpaceTools.prepareExecuteFile(PrivateMediaActivity.this.thisContext, cmd);
                Log.i(PrivateMediaActivity.this.TAG, "DELETE RESULT : " + PrivateMediaActivity.this.deleteData(fileLabel));
                PrivateMediaActivity.this.updateUI();
            }
        });
        return mediaBuilder.create();
    }

    public void openFile(Context context, File aFile) {
        this.fromOpenFile = true;
        PrivateSpaceActivity.isPrivateMedia = true;
        Intent intent = new Intent();
        Uri data = getUri(aFile);
        String type = PrivateSpaceTools.getMimeTypeOfFile(aFile.getName());
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(data, type);
        if (isVideoFile(context, aFile)) {
            intent.putExtra("video_title", aFile.getName());
            intent.addFlags(8388608);
            intent.setFlags(268435456);
            try {
                Intent mIntent = new Intent();
                mIntent.putExtra("insafebox", true);
                mIntent.setClass(this, MonitorAppService.class);
                startService(mIntent);
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Log.i(this.TAG, "can not open this file");
                Toast.makeText(getApplicationContext(), context.getString(R.string.unknown_file), 0).show();
            }
        } else {
            Intent imgIntent = new Intent();
            imgIntent.setClass(context, ImageBrowserActivity.class);
            imgIntent.putExtra("ImagePath", aFile.getAbsolutePath());
            imgIntent.addFlags(8388608);
            imgIntent.setFlags(268435456);
            context.startActivity(imgIntent);
        }
    }

    public Uri getUri(File file) {
        return Uri.parse("file://" + file.getAbsolutePath());
    }

    public boolean isVideoFile(Context context, File file) {
        String mimetype;
        if (file == null || !file.isFile() || (mimetype = PrivateSpaceTools.getMimeTypeOfFile(file.getName())) == null || !mimetype.startsWith("video")) {
            return false;
        }
        return true;
    }

    /* loaded from: classes.dex */
    class AddButtonListener implements View.OnClickListener {
        Context mContext;

        public AddButtonListener(Context context) {
            PrivateMediaActivity.this = r1;
            this.mContext = context;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (PrivateMediaActivity.this.trigger == 0) {
                PrivateMediaActivity.this.imgDialog = PrivateMediaActivity.this.createImageDialog(this.mContext);
                PrivateMediaActivity.this.imgDialog.show();
                return;
            }
            PrivateMediaActivity.this.videoDialog = PrivateMediaActivity.this.createVideoDialog(this.mContext);
            PrivateMediaActivity.this.videoDialog.show();
        }
    }

    public CustomDialog createImageDialog(Context context) {
        return new CustomDialog.Builder(context).setTitle(context.getString(R.string.select_image_location)).setItems(new String[]{context.getString(R.string.scan_image), context.getString(R.string.manul_image)}, new ImageDialogListener(context)).create();
    }

    public CustomDialog createVideoDialog(Context context) {
        return new CustomDialog.Builder(context).setTitle(context.getString(R.string.select_video_location)).setItems(new String[]{context.getString(R.string.scan_video), context.getString(R.string.manul_video)}, new VideoDialogListener(context)).create();
    }

    void initView() {
        this.actionControl = 0;
        this.titleTv = (TextView) findViewById(R.id.txt_title);
        this.nullBgImage = (ImageView) findViewById(R.id.iv_Null);
        this.addAction = findViewById(R.id.btn_AddNew);
        this.moveAction = findViewById(R.id.layout_second);
        this.moveAction.setVisibility(8);
        this.addButton = (Button) findViewById(R.id.btn_AddMedia);
        this.addButton.setVisibility(0);
        this.delBtn = (Button) findViewById(R.id.btn_delete);
        this.selectBtn = (Button) findViewById(R.id.btn_selectAll);
        this.restoreBtn = (Button) findViewById(R.id.btn_restore);
        this.girdView = (GridView) findViewById(R.id.gridview);
        this.imgEditor = (TextView) findViewById(R.id.btn_edit);
        this.infoBar = (TextView) findViewById(R.id.info_bar);
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.9
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PrivateMediaActivity.this.closePrivateSpace = true;
                Intent manIntent = new Intent().setClass(PrivateMediaActivity.this, PrivateSpaceActivity.class);
                manIntent.putExtra("pwd", PrivateMediaActivity.this.password);
                manIntent.setFlags(67141632);
                manIntent.addFlags(8388608);
                System.gc();
                PrivateMediaActivity.this.startActivity(manIntent);
                PrivateMediaActivity.this.finish();
            }
        });
        if (this.trigger == 0) {
            this.titleTv.setText(R.string.pri_image);
        } else {
            this.titleTv.setText(R.string.pri_video);
            this.addButton.setText(R.string.addVideo);
        }
        this.selectBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.10
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (PrivateMediaActivity.this.selectBtn.getText().equals(PrivateMediaActivity.this.thisContext.getString(R.string.select_all))) {
                    PrivateMediaActivity.this.chosenFiles.clear();
                    ArrayList<MediaInfo> tmpArray = PrivateMediaActivity.this.mAdpater.setSelectAll();
                    for (int i = 0; i < tmpArray.size(); i++) {
                        PrivateMediaActivity.this.chosenFiles.add(tmpArray.get(i).getFilePath());
                    }
                    PrivateMediaActivity.this.selectBtn.setText(R.string.cancel);
                } else {
                    PrivateMediaActivity.this.chosenFiles.clear();
                    PrivateMediaActivity.this.mAdpater.setDisSelectAll();
                    PrivateMediaActivity.this.selectBtn.setText(R.string.select_all);
                }
                PrivateMediaActivity.this.mAdpater.notifyDataSetChanged();
            }
        });
        this.delBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.11
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (PrivateMediaActivity.this.chosenFiles.size() > 0) {
                    PrivateMediaActivity.this.createDeleteDialog().show();
                }
            }
        });
        this.restoreBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateMediaActivity.12
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (PrivateMediaActivity.this.chosenFiles.size() > 0) {
                    PrivateMediaActivity.this.showProgress();
                    PrivateMediaActivity.this.closePrivateSpace = false;
                    new Thread() { // from class: com.lenovo.safebox.PrivateMediaActivity.12.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            boolean sendFlag = true;
                            StringBuffer buffer = new StringBuffer();
                            String recoveryFileName = "";
                            int recoveryCount = 0;
                            try {
                                PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
                                ArrayList<String> dbList = new ArrayList<>();
                                for (int i = 0; i < PrivateMediaActivity.this.chosenFiles.size(); i++) {
                                    String fileName = PrivateSpaceTools.getFileName((String) PrivateMediaActivity.this.chosenFiles.get(i));
                                    String oriPath = PrivateMediaActivity.this.getOriPath(PrivateSpaceTools.getFileName((String) PrivateMediaActivity.this.chosenFiles.get(i)));
                                    if (oriPath == null || oriPath.isEmpty()) {
                                        File recovery = new File("/sdcard/leSafeRecovery");
                                        if (!recovery.exists()) {
                                            recovery.mkdir();
                                        }
                                        buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename((String) PrivateMediaActivity.this.chosenFiles.get(i)) + " " + recovery.getAbsolutePath() + "\n");
                                        recoveryCount++;
                                        recoveryFileName = fileName;
                                        dbList.add(PrivateSpaceTools.getFileName(((String) PrivateMediaActivity.this.chosenFiles.get(i)).toString()));
                                    } else {
                                        File desD = new File(oriPath);
                                        if (!desD.exists() || !desD.isDirectory()) {
                                            desD.mkdir();
                                            buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename((String) PrivateMediaActivity.this.chosenFiles.get(i)) + " " + desD.getAbsolutePath() + "\n");
                                            dbList.add(PrivateSpaceTools.getFileName(((String) PrivateMediaActivity.this.chosenFiles.get(i)).toString()));
                                        } else if (new File(oriPath + "/" + fileName).exists()) {
                                            PrivateMediaActivity.this.handler.sendMessage(PrivateMediaActivity.this.handler.obtainMessage(4, fileName + PrivateMediaActivity.this.getString(R.string.restore_cover_msg)));
                                        } else {
                                            buffer.append(PrivateSpaceTools.busybox + " mv  " + PrivateSpaceTools.resolveFilename((String) PrivateMediaActivity.this.chosenFiles.get(i)) + " " + PrivateSpaceTools.resolveFilename(oriPath) + "\n");
                                            dbList.add(PrivateSpaceTools.getFileName(((String) PrivateMediaActivity.this.chosenFiles.get(i)).toString()));
                                        }
                                    }
                                }
                                PrivateMediaActivity.this.deleteData(dbList);
                                if (recoveryCount == 1) {
                                    PrivateMediaActivity.this.handler.sendMessage(PrivateMediaActivity.this.handler.obtainMessage(4, recoveryFileName + PrivateMediaActivity.this.getString(R.string.restore_recovery)));
                                } else if (recoveryCount > 1) {
                                    PrivateMediaActivity.this.handler.sendMessage(PrivateMediaActivity.this.handler.obtainMessage(4, recoveryCount + PrivateMediaActivity.this.getString(R.string.restore_file) + PrivateMediaActivity.this.getString(R.string.restore_recovery)));
                                }
                                buffer.append("echo done\n");
                                PrivateSpaceTools.prepareExecuteFile(PrivateMediaActivity.this.thisContext, buffer.toString());
                                dbList.clear();
                            } catch (IndexOutOfBoundsException e) {
                                e.printStackTrace();
                                sendFlag = false;
                            } catch (NullPointerException e2) {
                                e2.printStackTrace();
                                sendFlag = false;
                            }
                            if (sendFlag) {
                                PrivateMediaActivity.this.handler.sendMessage(PrivateMediaActivity.this.handler.obtainMessage(1));
                            }
                            PrivateMediaActivity.this.closePrivateSpace = true;
                        }
                    }.start();
                }
            }
        });
    }

    public String getOriPath(String fileName) {
        String returnStr = null;
        Cursor mCursor = fetchData(fileName);
        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            returnStr = mCursor.getString(mCursor.getColumnIndex(PrivateSpaceHelper.FROM));
        }
        if (mCursor != null) {
            mCursor.close();
        }
        this.db.close();
        Log.i(this.TAG, "returnStr  " + returnStr);
        return returnStr;
    }

    void preWork() {
        this.mHelper = new PrivateSpaceHelper(this, PrivateSpaceTools.DB_NAME, null, 1);
        PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
        this.settings = getSharedPreferences(this.PREFS_NAME, 0);
        this.password = this.settings.getString("password", "");
        this.mReceiver = new RequestReceiver();
        this.filter = new IntentFilter();
    }

    /* loaded from: classes.dex */
    public class RequestReceiver extends BroadcastReceiver {
        public RequestReceiver() {
            PrivateMediaActivity.this = r1;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SafeBoxApplication.NAC_DONE) && PrivateMediaActivity.this.progressDialog != null && PrivateMediaActivity.this.progressDialog.isShowing()) {
                PrivateMediaActivity.this.progressDialog.dismiss();
                PrivateMediaActivity.this.closePrivateSpace = true;
                PrivateMediaActivity.this.finish();
            }
        }
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        registerReceiver(this.mReceiver, this.filter);
        if (this.closePrivateSpace) {
            PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
            updateUI();
        }
        this.closePrivateSpace = true;
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
        if (getIntent().getDataString() == null || getIntent().getDataString().isEmpty()) {
            this.trigger = getIntent().getFlags();
        } else if (getIntent().getDataString().equals("image")) {
            this.trigger = 0;
        } else {
            this.trigger = 1;
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        unregisterReceiver(this.mReceiver);
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.filter.addAction(SafeBoxApplication.NAC_DONE);
        }
        if (!this.fromOpenFile && this.closePrivateSpace) {
            PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        }
        this.fromOpenFile = false;
        if (this.videoDialog != null) {
            this.videoDialog.dismiss();
        }
        if (this.imgDialog != null) {
            this.imgDialog.dismiss();
        }
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        registerReceiver(this.mReceiver, this.filter);
    }

    protected void onDestory() {
        super.onDestroy();
        PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        this.mAdpater.recycleBitmap();
        System.gc();
    }

    public boolean deleteData(String fileLabel) {
        boolean result = true;
        this.mHelper = new PrivateSpaceHelper(this.thisContext, PrivateSpaceTools.DB_NAME, null, 1);
        this.db = this.mHelper.getReadableDatabase();
        if (this.db.delete(PrivateSpaceHelper.TB_NAME, "filename= \"" + fileLabel + "\"", null) <= 0) {
            result = false;
        }
        this.db.close();
        return result;
    }

    public void deleteData(ArrayList<String> list) {
        this.mHelper = new PrivateSpaceHelper(this.thisContext, PrivateSpaceTools.DB_NAME, null, 1);
        this.db = this.mHelper.getReadableDatabase();
        this.db.beginTransaction();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                this.db.delete(PrivateSpaceHelper.TB_NAME, "filename= \"" + list.get(i) + "\"", null);
            }
        }
        this.db.setTransactionSuccessful();
        this.db.endTransaction();
        this.db.close();
    }

    private Cursor fetchData(String fileLabel) {
        this.mHelper = new PrivateSpaceHelper(this.thisContext, PrivateSpaceTools.DB_NAME, null, 1);
        this.db = this.mHelper.getReadableDatabase();
        return this.db.query(true, PrivateSpaceHelper.TB_NAME, new String[]{"_id", PrivateSpaceHelper.FILE, PrivateSpaceHelper.FROM}, "filename = \"" + fileLabel + "\"", null, null, null, null, null);
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        this.closePrivateSpace = true;
        Intent manIntent = new Intent().setClass(this, PrivateSpaceActivity.class);
        manIntent.putExtra("pwd", this.password);
        manIntent.setFlags(67141632);
        manIntent.addFlags(8388608);
        System.gc();
        startActivity(manIntent);
        finish();
        return super.onKeyDown(keyCode, event);
    }

    /* loaded from: classes.dex */
    class PriMediaListener implements DialogInterface.OnClickListener {
        Context mContext;
        private String mFileLabel;
        private String mTAG = "PriMediaListener  aaaaaaaaaaaaaaaaaaaaa";
        private String oriPath;
        private String priPath;

        public PriMediaListener(Context context, String label, String priPath) {
            PrivateMediaActivity.this = r2;
            this.mContext = context;
            this.mFileLabel = label;
            this.priPath = priPath;
            this.oriPath = r2.getOriPath(this.mFileLabel);
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
                    String command = null;
                    String fileName = PrivateSpaceTools.getFileName(this.priPath);
                    if (this.oriPath == null || this.oriPath.isEmpty()) {
                        File recovery = new File("/sdcard/leSafeRecovery");
                        if (!recovery.exists()) {
                            recovery.mkdir();
                        }
                        command = PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(this.priPath) + " " + recovery.getAbsolutePath();
                        PrivateMediaActivity.this.handler.sendMessage(PrivateMediaActivity.this.handler.obtainMessage(4, PrivateSpaceTools.resolveFilename(this.priPath) + PrivateMediaActivity.this.getString(R.string.restore_recovery)));
                        Log.i(PrivateMediaActivity.this.TAG, "DELETE RESULT : " + PrivateMediaActivity.this.deleteData(PrivateSpaceTools.getFileName(this.priPath)));
                    } else {
                        File desD = new File(this.oriPath);
                        if (!desD.exists() || !desD.isDirectory()) {
                            desD.mkdir();
                            command = PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(this.priPath) + " " + desD.getAbsolutePath() + "\n";
                            Log.i(PrivateMediaActivity.this.TAG, "DELETE RESULT : " + PrivateMediaActivity.this.deleteData(PrivateSpaceTools.getFileName(this.priPath)));
                        } else if (new File(this.oriPath + "/" + fileName).exists()) {
                            PrivateMediaActivity.this.handler.sendMessage(PrivateMediaActivity.this.handler.obtainMessage(4, fileName + PrivateMediaActivity.this.getString(R.string.restore_cover_msg)));
                        } else {
                            command = PrivateSpaceTools.busybox + " mv  " + PrivateSpaceTools.resolveFilename(this.priPath) + " " + PrivateSpaceTools.resolveFilename(this.oriPath) + "\n";
                            Log.i(PrivateMediaActivity.this.TAG, "DELETE RESULT : " + PrivateMediaActivity.this.deleteData(PrivateSpaceTools.getFileName(this.priPath)));
                        }
                    }
                    PrivateSpaceTools.prepareExecuteFile(PrivateMediaActivity.this.thisContext, command + "\n echo done \n");
                    PrivateMediaActivity.this.updateUI();
                    return;
                case 1:
                    PrivateMediaActivity.this.createSingleDeleteDialog(this.mFileLabel, (PrivateSpaceTools.busybox + " rm " + PrivateSpaceTools.resolveFilename(this.priPath)) + "\n echo done \n").show();
                    return;
                case 2:
                    if (this.oriPath == null) {
                        Toast.makeText(this.mContext, "/sdcard/leSafeRecovery", 0).show();
                        return;
                    } else {
                        Toast.makeText(this.mContext, this.oriPath + "/" + this.mFileLabel, 0).show();
                        return;
                    }
                default:
                    return;
            }
        }
    }
}

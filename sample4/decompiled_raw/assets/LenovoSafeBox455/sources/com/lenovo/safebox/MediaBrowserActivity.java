package com.lenovo.safebox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.lenovo.safebox.adapter.SingleMediaAdapter;
import com.lenovo.safebox.dialog.CustomDialog;
import java.io.File;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class MediaBrowserActivity extends Activity {
    private static final int ENCRYPT_COVER_FILE = 2;
    private static final int ENCRYPT_NO_SPACE = 5;
    private static final int ENCRYPT_SUCCESS = 1;
    private View actionBar;
    private Button btnSelect;
    private ArrayList<MediaInfo> chosenFiles;
    SQLiteDatabase db;
    private Button encryptAction;
    private GridView gridView;
    private TextView infoBar;
    private ImageView ivBack;
    private boolean jumpFinish;
    private CheckBox mCheckbox;
    private FolderInfo mFolderInfo;
    PrivateSpaceHelper mHelper;
    private SingleMediaAdapter mSingleMediaAdapter;
    private Thread mThread;
    public ArrayList<MediaInfo> mediaList;
    private ProgressBar progress;
    private ProgressDialog progressDialog;
    private Context thisContext;
    private TextView tvTitle;
    private String TAG = "MediaBrowserActivity  ";
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.MediaBrowserActivity.5
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        if (MediaBrowserActivity.this.progressDialog != null) {
                            MediaBrowserActivity.this.progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                    }
                    PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
                    for (int i = 0; i < MediaBrowserActivity.this.chosenFiles.size(); i++) {
                        if (MediaBrowserActivity.this.mediaList.contains(MediaBrowserActivity.this.chosenFiles.get(i))) {
                            MediaBrowserActivity.this.mediaList.remove(MediaBrowserActivity.this.chosenFiles.get(i));
                        }
                    }
                    try {
                        MediaBrowserActivity.this.mSingleMediaAdapter = new SingleMediaAdapter(MediaBrowserActivity.this.thisContext, MediaBrowserActivity.this.mediaList, MediaBrowserActivity.this.getIntent().getBooleanExtra("image", true), MediaBrowserActivity.this.gridView);
                        MediaBrowserActivity.this.gridView.setAdapter((ListAdapter) MediaBrowserActivity.this.mSingleMediaAdapter);
                        MediaBrowserActivity.this.chosenFiles.size();
                        MediaBrowserActivity.this.chosenFiles.clear();
                        MediaBrowserActivity.this.mSingleMediaAdapter.notifyDataSetChanged();
                        MediaBrowserActivity.this.actionBar.setVisibility(8);
                        Intent jumpIntent = new Intent(MediaBrowserActivity.this.thisContext, PrivateMediaActivity.class);
                        if (MediaBrowserActivity.this.getIntent().getBooleanExtra("image", true)) {
                            jumpIntent.setFlags(0);
                            jumpIntent.setData(Uri.parse("image"));
                        } else {
                            jumpIntent.setFlags(0);
                            jumpIntent.setData(Uri.parse("image"));
                        }
                        MediaBrowserActivity.this.thisContext.startActivity(jumpIntent);
                        jumpIntent.addFlags(8388608);
                        MediaBrowserActivity.this.jumpFinish = true;
                        MediaBrowserActivity.this.finish();
                        return;
                    } catch (NullPointerException e2) {
                        e2.printStackTrace();
                        MediaBrowserActivity.this.finish();
                        return;
                    }
                case 2:
                    Toast.makeText(MediaBrowserActivity.this.thisContext, msg.obj.toString() + MediaBrowserActivity.this.thisContext.getString(R.string.encrypt_cover_msg) + "encrypt_" + msg.obj.toString(), 0).show();
                    return;
                case 3:
                case 4:
                default:
                    return;
                case 5:
                    try {
                        if (MediaBrowserActivity.this.progressDialog != null) {
                            MediaBrowserActivity.this.progressDialog.dismiss();
                        }
                    } catch (Exception e3) {
                    }
                    MediaBrowserActivity.this.createNospaceDialog().show();
                    return;
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preWork();
        requestWindowFeature(1);
        setContentView(R.layout.gui_selectmedia_activity);
        this.gridView = (GridView) findViewById(R.id.gridview);
        this.actionBar = findViewById(R.id.layout_btninsert);
        this.actionBar.setVisibility(8);
        this.btnSelect = (Button) findViewById(R.id.btn_selectAll);
        this.btnSelect.setOnClickListener(new SelectButtonListener(this));
        this.encryptAction = (Button) findViewById(R.id.btn_import);
        this.tvTitle = (TextView) findViewById(R.id.txt_title);
        this.infoBar = (TextView) findViewById(R.id.info_bar);
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        this.jumpFinish = false;
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.MediaBrowserActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                MediaBrowserActivity.this.finish();
            }
        });
        this.thisContext = this;
        this.chosenFiles = new ArrayList<>();
        Toast.makeText(getApplicationContext(), getString(R.string.select_prompt), 0).show();
        this.encryptAction.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.MediaBrowserActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
                if (MediaBrowserActivity.this.chosenFiles.size() > 0) {
                    MediaBrowserActivity.this.showProgress();
                }
                MediaBrowserActivity.this.mThread = new Thread() { // from class: com.lenovo.safebox.MediaBrowserActivity.2.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        File desF;
                        File desFileP = null;
                        boolean sendFlag = true;
                        StringBuffer buffer = new StringBuffer();
                        if (MediaBrowserActivity.this.chosenFiles.size() > 0) {
                            desFileP = new File(((MediaInfo) MediaBrowserActivity.this.chosenFiles.get(0)).getFilePath().toString());
                        }
                        long fileSize = 0;
                        try {
                            StatFs sdStat = new StatFs(new File(PrivateSpaceTools.sdDir).getPath());
                            boolean isSameBlock = false;
                            int i = 0;
                            while (true) {
                                if (i >= MediaBrowserActivity.this.chosenFiles.size()) {
                                    break;
                                }
                                File thisFile = new File(((MediaInfo) MediaBrowserActivity.this.chosenFiles.get(i)).getFilePath());
                                fileSize += thisFile.length();
                                if (new StatFs(thisFile.getPath()).getAvailableBlocks() == sdStat.getAvailableBlocks()) {
                                    isSameBlock = true;
                                    break;
                                }
                                i++;
                            }
                            if (fileSize > PrivateSpaceTools.getSdSpace() && !isSameBlock) {
                                MediaBrowserActivity.this.chosenFiles.clear();
                                MediaBrowserActivity.this.handler.sendMessage(MediaBrowserActivity.this.handler.obtainMessage(5));
                            }
                            for (int i2 = 0; i2 < MediaBrowserActivity.this.chosenFiles.size(); i2++) {
                                String[] resolveString = ((MediaInfo) MediaBrowserActivity.this.chosenFiles.get(i2)).getFilePath().split("/");
                                String fileName = resolveString[resolveString.length - 1];
                                if (MediaBrowserActivity.this.getIntent().getBooleanExtra("image", true)) {
                                    desF = new File("/mnt/sdcard/.pFolder/pictures/" + fileName);
                                } else {
                                    desF = new File("/mnt/sdcard/.pFolder/videos/" + fileName);
                                }
                                if (resolveString.length == 0) {
                                    Log.i(MediaBrowserActivity.this.TAG, "0000000000");
                                }
                                if (MediaBrowserActivity.this.getIntent().getBooleanExtra("image", true)) {
                                    if (desF.exists()) {
                                        MediaBrowserActivity.this.insert("encrypt_" + resolveString[resolveString.length - 1], desFileP.getParent(), 0);
                                        MediaBrowserActivity.this.handler.sendMessage(MediaBrowserActivity.this.handler.obtainMessage(2, fileName));
                                    } else {
                                        MediaBrowserActivity.this.insert(resolveString[resolveString.length - 1], desFileP.getParent(), 0);
                                    }
                                } else if (desF.exists()) {
                                    MediaBrowserActivity.this.insert("encrypt_" + resolveString[resolveString.length - 1], desFileP.getParent(), 0);
                                    MediaBrowserActivity.this.handler.sendMessage(MediaBrowserActivity.this.handler.obtainMessage(2, fileName));
                                } else {
                                    MediaBrowserActivity.this.insert(resolveString[resolveString.length - 1], desFileP.getParent(), 1);
                                }
                                if (((Activity) MediaBrowserActivity.this.thisContext).getIntent().getBooleanExtra("image", true)) {
                                    if (desF.exists()) {
                                        buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(((MediaInfo) MediaBrowserActivity.this.chosenFiles.get(i2)).getFilePath()) + " " + PrivateSpaceTools.priImgFolder + "/" + PrivateSpaceTools.resolveFilename("encrypt_" + fileName) + "\n");
                                    } else {
                                        buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(((MediaInfo) MediaBrowserActivity.this.chosenFiles.get(i2)).getFilePath()) + " " + PrivateSpaceTools.priImgFolder + "\n");
                                    }
                                } else if (desF.exists()) {
                                    buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(((MediaInfo) MediaBrowserActivity.this.chosenFiles.get(i2)).getFilePath()) + " " + PrivateSpaceTools.priVideoFolder + "/" + PrivateSpaceTools.resolveFilename("encrypt_" + fileName) + "\n");
                                } else {
                                    buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(((MediaInfo) MediaBrowserActivity.this.chosenFiles.get(i2)).getFilePath()) + " " + PrivateSpaceTools.priVideoFolder + "\n");
                                }
                            }
                            buffer.append("echo done\n");
                            PrivateSpaceTools.prepareExecuteFile(MediaBrowserActivity.this.thisContext, buffer.toString());
                        } catch (IllegalArgumentException e) {
                            sendFlag = false;
                            MediaBrowserActivity.this.finish();
                        } catch (IndexOutOfBoundsException e2) {
                            sendFlag = false;
                        } catch (NullPointerException e3) {
                            sendFlag = false;
                        }
                        if (sendFlag && MediaBrowserActivity.this.chosenFiles.size() > 0) {
                            MediaBrowserActivity.this.handler.sendMessage(MediaBrowserActivity.this.handler.obtainMessage(1));
                        }
                    }
                };
                MediaBrowserActivity.this.mThread.start();
            }
        });
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lenovo.safebox.MediaBrowserActivity.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MediaInfo actionOnFilePath = MediaBrowserActivity.this.mSingleMediaAdapter.getItem(position);
                MediaBrowserActivity.this.mSingleMediaAdapter.getItem(position).setCheckHolder(MediaBrowserActivity.this.mSingleMediaAdapter.getItem(position).getCheckHolder());
                if (MediaBrowserActivity.this.mSingleMediaAdapter.getItem(position).getCheckHolder()) {
                    if (!MediaBrowserActivity.this.chosenFiles.contains(actionOnFilePath)) {
                        MediaBrowserActivity.this.chosenFiles.add(actionOnFilePath);
                    }
                    if (MediaBrowserActivity.this.chosenFiles.size() == MediaBrowserActivity.this.mSingleMediaAdapter.getCount()) {
                        MediaBrowserActivity.this.actionBar.setVisibility(0);
                        MediaBrowserActivity.this.btnSelect.setText(R.string.dis_select_all);
                        MediaBrowserActivity.this.btnSelect.setPadding(10, 0, 0, 0);
                    } else {
                        MediaBrowserActivity.this.actionBar.setVisibility(0);
                        MediaBrowserActivity.this.btnSelect.setText(R.string.select_all);
                        MediaBrowserActivity.this.btnSelect.setPadding(20, 0, 20, 0);
                    }
                } else {
                    MediaBrowserActivity.this.chosenFiles.remove(actionOnFilePath);
                    if (MediaBrowserActivity.this.chosenFiles.size() == 0) {
                        MediaBrowserActivity.this.actionBar.setVisibility(8);
                    } else {
                        MediaBrowserActivity.this.btnSelect.setText(R.string.select_all);
                        MediaBrowserActivity.this.btnSelect.setPadding(20, 0, 20, 0);
                    }
                }
                MediaBrowserActivity.this.mSingleMediaAdapter.notifyDataSetChanged();
            }
        });
    }

    public void showProgress() {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.lenovo.safebox.MediaBrowserActivity.4
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                MediaBrowserActivity.this.progressDialog = null;
            }
        });
        this.progressDialog.setProgressStyle(0);
        this.progressDialog.setMessage(getString(R.string.exec_state));
        this.progressDialog.show();
    }

    private void updateUI() {
        this.mediaList = null;
        if (getIntent().getBooleanExtra("image", true)) {
            this.mediaList = AddAutoImageActivity.passFolderInfo.getChildList();
            this.tvTitle.setText(R.string.addPrivateImage);
            this.infoBar.setText(R.string.select_img);
        } else {
            this.mediaList = AddAutoVideoActivity.passFolderInfo.getChildList();
            this.tvTitle.setText(R.string.addPrivateVideo);
            this.infoBar.setText(R.string.select_video);
        }
        this.mSingleMediaAdapter = new SingleMediaAdapter(this.thisContext, this.mediaList, getIntent().getBooleanExtra("image", true), this.gridView);
        this.gridView.setAdapter((ListAdapter) this.mSingleMediaAdapter);
    }

    public boolean insert(String filename, String path, int type) {
        this.db = this.mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(PrivateSpaceHelper.FILE, filename);
        values.put(PrivateSpaceHelper.FROM, path);
        values.put(PrivateSpaceHelper.TYPE, Integer.valueOf(type));
        long result = this.db.insert(PrivateSpaceHelper.TB_NAME, "_id", values);
        this.db.close();
        if (result != -1) {
            return true;
        }
        Log.i("aaaaaaaaaaaaaaaaaaaaaaaaaaaPrivateSpaceHelper", "insert failed");
        return false;
    }

    public CustomDialog createActionDialog(Context context, int count) {
        CustomDialog.Builder mediaBuilder;
        CustomDialog.Builder mediaBuilder2 = new CustomDialog.Builder(context).setTitle(context.getString(R.string.encrypt_title));
        if (getIntent().getBooleanExtra("image", true)) {
            mediaBuilder = mediaBuilder2.setMessage(context.getString(R.string.encrypt_message1) + count + context.getString(R.string.encrypt_message_image) + context.getString(R.string.encrypt_message2));
        } else {
            mediaBuilder = mediaBuilder2.setMessage(context.getString(R.string.encrypt_message1) + count + context.getString(R.string.encrypt_message_video) + context.getString(R.string.encrypt_message2));
        }
        EncryptDialogListener1 mListener = new EncryptDialogListener1(context, getIntent().getBooleanExtra("image", true), PrivateMediaActivity.class);
        if (this.mediaList.size() > 0) {
            mediaBuilder.setPositiveButton(context.getString(R.string.continue_add), (DialogInterface.OnClickListener) null);
        }
        mediaBuilder.setNegativeButton(context.getString(R.string.complete), mListener);
        return mediaBuilder.create();
    }

    public CustomDialog createNospaceDialog() {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(this);
        mediaBuilder.setTitle(getString(R.string.exit_title));
        mediaBuilder.setMessage(getString(R.string.no_space_sdcard));
        mediaBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.MediaBrowserActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                Log.i("onClick", "onClick");
                if (MediaBrowserActivity.this.chosenFiles.size() == 0) {
                    MediaBrowserActivity.this.actionBar.setVisibility(8);
                    MediaBrowserActivity.this.mSingleMediaAdapter.setDisSelectAll();
                }
            }
        });
        return mediaBuilder.create();
    }

    /* loaded from: classes.dex */
    class EncryptDialogListener1 implements DialogInterface.OnClickListener {
        Class mClass;
        Context mContext;
        boolean mFlag;

        public EncryptDialogListener1(Context context, boolean flag, Class startClass) {
            MediaBrowserActivity.this = r1;
            this.mContext = context;
            this.mClass = startClass;
            this.mFlag = flag;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            Intent manIntent = new Intent(this.mContext, this.mClass);
            if (this.mFlag) {
                manIntent.setFlags(0);
                manIntent.setData(Uri.parse("image"));
            } else {
                manIntent.setFlags(1);
                manIntent.setData(Uri.parse("video"));
            }
            this.mContext.startActivity(manIntent);
            manIntent.addFlags(8388608);
            MediaBrowserActivity.this.jumpFinish = true;
            MediaBrowserActivity.this.finish();
        }
    }

    void preWork() {
        this.mHelper = new PrivateSpaceHelper(this, PrivateSpaceTools.DB_NAME, null, 1);
    }

    /* loaded from: classes.dex */
    private class SelectButtonListener implements View.OnClickListener {
        Context mContext;

        SelectButtonListener(Context context) {
            MediaBrowserActivity.this = r1;
            this.mContext = context;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (MediaBrowserActivity.this.btnSelect.getText().equals(this.mContext.getString(R.string.select_all))) {
                ArrayList<MediaInfo> tmpArray = MediaBrowserActivity.this.mSingleMediaAdapter.setSelectAll();
                MediaBrowserActivity.this.chosenFiles.clear();
                for (int i = 0; i < tmpArray.size(); i++) {
                    MediaBrowserActivity.this.chosenFiles.add(tmpArray.get(i));
                }
                MediaBrowserActivity.this.mSingleMediaAdapter.notifyDataSetChanged();
                MediaBrowserActivity.this.btnSelect.setText(this.mContext.getString(R.string.dis_select_all));
                MediaBrowserActivity.this.btnSelect.setPadding(10, 0, 0, 0);
                return;
            }
            Log.i(MediaBrowserActivity.this.TAG, "this is disSelect all");
            MediaBrowserActivity.this.mSingleMediaAdapter.setDisSelectAll();
            MediaBrowserActivity.this.chosenFiles.clear();
            MediaBrowserActivity.this.btnSelect.setText(this.mContext.getString(R.string.select_all));
            MediaBrowserActivity.this.btnSelect.setPadding(20, 0, 20, 0);
            MediaBrowserActivity.this.actionBar.setVisibility(8);
            MediaBrowserActivity.this.mSingleMediaAdapter.notifyDataSetChanged();
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        try {
            updateUI();
        } catch (NullPointerException e) {
            finish();
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        this.chosenFiles.clear();
        try {
            if (this.progressDialog != null && this.progressDialog.isShowing()) {
                this.progressDialog.dismiss();
                finish();
            }
        } catch (Exception e) {
        }
        this.mediaList = null;
        if (this.mSingleMediaAdapter != null) {
            this.mSingleMediaAdapter.recycleBitmap();
            this.mSingleMediaAdapter = null;
        }
        if (!this.jumpFinish) {
            PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        }
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }
}

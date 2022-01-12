package com.lenovo.safebox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.lenovo.safebox.adapter.PriFileAdapter;
import com.lenovo.safebox.dialog.CustomDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
/* loaded from: classes.dex */
public class PrivateFileActivity extends Activity {
    private static final int DELETE_SUCCESS = 2;
    private static final int RESTORE_COVER_MSG = 3;
    private static final int RESTORE_SUCCESS = 1;
    private static final int RESTORE_TO_RECOVERY = 4;
    private View addAction;
    private Button addButton;
    public ArrayList<String> chosenFiles;
    private boolean closePrivateSpace;
    SQLiteDatabase db;
    private Button delBtn;
    private ArrayList<FileInfo> fiLst;
    private IntentFilter filter;
    private Button imgEditor;
    private TextView infoBar;
    private ImageView ivBack;
    private ListView lv;
    private PriFileAdapter mAdapter;
    PrivateSpaceHelper mHelper;
    private BroadcastReceiver mReceiver;
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
    private TextView tvTitle;
    private String TAG = "PrivateFileActivity    aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private String PREFS_NAME = "pass";
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.PrivateFileActivity.11
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        if (PrivateFileActivity.this.progressDialog != null) {
                            PrivateFileActivity.this.progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                    }
                    PrivateFileActivity.this.closePrivateSpace = true;
                    PrivateFileActivity.this.updateUI();
                    PrivateFileActivity.this.mAdapter.notifyDataSetChanged();
                    PrivateFileActivity.this.selectBtn.setText(R.string.select_all);
                    PrivateFileActivity.this.moveAction.setVisibility(8);
                    PrivateFileActivity.this.addButton.setVisibility(0);
                    return;
                case 2:
                    try {
                        if (PrivateFileActivity.this.progressDialog != null) {
                            PrivateFileActivity.this.progressDialog.dismiss();
                        }
                    } catch (Exception e2) {
                    }
                    PrivateFileActivity.this.closePrivateSpace = true;
                    PrivateFileActivity.this.updateUI();
                    PrivateFileActivity.this.mAdapter.notifyDataSetChanged();
                    return;
                case 3:
                    Toast.makeText(PrivateFileActivity.this.thisContext, msg.obj.toString(), 0).show();
                    PrivateFileActivity.this.closePrivateSpace = true;
                    return;
                case 4:
                    Toast.makeText(PrivateFileActivity.this.thisContext, msg.obj.toString(), 0).show();
                    PrivateFileActivity.this.closePrivateSpace = true;
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
        setContentView(R.layout.gui_privatefile_activity);
        this.closePrivateSpace = true;
        this.mReceiver = new RequestReceiver();
        this.filter = new IntentFilter();
        preWork();
        initView();
        this.fiLst = new ArrayList<>();
        this.chosenFiles = new ArrayList<>();
        this.pFolder = new File(PrivateSpaceTools.priFilesFolder);
    }

    private void waitOpen() {
        int countTmp = 0;
        File priImage = new File(PrivateSpaceTools.priFilesFolder);
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
        this.fiLst.clear();
        if (this.pFolder == null) {
            this.pFolder = new File(PrivateSpaceTools.priFilesFolder);
        }
        if (!this.pFolder.isDirectory()) {
            PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
            this.pFolder.mkdir();
        }
        if (PrivateSpaceTools.forceCmcc || !PrivateSpaceTools.isLenovoProduct) {
            waitOpen();
        }
        File[] childList = this.pFolder.listFiles();
        try {
            if (childList.length > 0) {
                this.nullBgImage.setVisibility(8);
                this.infoBar.setVisibility(0);
                this.infoBar.setText(getString(R.string.protected_file) + childList.length + getString(R.string.protected_end));
            } else {
                this.nullBgImage.setVisibility(0);
                this.infoBar.setVisibility(8);
            }
            for (File file : childList) {
                this.fiLst.add(new FileInfo(file.getAbsolutePath(), null));
            }
        } catch (NullPointerException e) {
            finish();
        }
        this.selectBtn.setText(R.string.select_all);
        this.addButton.setVisibility(0);
        this.moveAction.setVisibility(8);
        this.chosenFiles.clear();
        this.mAdapter = new PriFileAdapter(this, this.fiLst);
        this.lv.setAdapter((ListAdapter) this.mAdapter);
        this.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lenovo.safebox.PrivateFileActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PrivateFileActivity.this.mAdapter.getItem(position).setCheckHolder(PrivateFileActivity.this.mAdapter.getItem(position).getCheckHolder());
                if (PrivateFileActivity.this.mAdapter.getItem(position).getCheckHolder()) {
                    if (!PrivateFileActivity.this.chosenFiles.contains(PrivateFileActivity.this.mAdapter.getItem(position).getFilePath())) {
                        PrivateFileActivity.this.chosenFiles.add(PrivateFileActivity.this.mAdapter.getItem(position).getFilePath());
                    }
                    if (PrivateFileActivity.this.chosenFiles.size() == PrivateFileActivity.this.mAdapter.getCount()) {
                        PrivateFileActivity.this.moveAction.setVisibility(0);
                        PrivateFileActivity.this.selectBtn.setText(R.string.cancel);
                        PrivateFileActivity.this.addButton.setVisibility(8);
                    } else {
                        PrivateFileActivity.this.addButton.setVisibility(8);
                        PrivateFileActivity.this.moveAction.setVisibility(0);
                    }
                } else {
                    PrivateFileActivity.this.chosenFiles.remove(PrivateFileActivity.this.mAdapter.getItem(position).getFilePath());
                    if (PrivateFileActivity.this.chosenFiles.size() == 0) {
                        PrivateFileActivity.this.addButton.setVisibility(0);
                        PrivateFileActivity.this.moveAction.setVisibility(8);
                        PrivateFileActivity.this.selectBtn.setText(R.string.select_all);
                    } else {
                        PrivateFileActivity.this.addButton.setVisibility(8);
                        PrivateFileActivity.this.moveAction.setVisibility(0);
                    }
                    PrivateFileActivity.this.selectBtn.setText(R.string.select_all);
                }
                PrivateFileActivity.this.mAdapter.notifyDataSetChanged();
            }
        });
        this.lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.lenovo.safebox.PrivateFileActivity.2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PrivateFileActivity.this.createListActionDialog(PrivateFileActivity.this.thisContext, new PriFileListener(PrivateFileActivity.this.thisContext, PrivateSpaceTools.getFileName(((FileInfo) PrivateFileActivity.this.fiLst.get(position)).getFilePath()), ((FileInfo) PrivateFileActivity.this.fiLst.get(position)).getFilePath())).show();
                return true;
            }
        });
    }

    public CustomDialog createListActionDialog(Context context, PriFileListener mPriFileListener) {
        return new CustomDialog.Builder(context).setTitle(context.getString(R.string.dia_showfiles_title)).setItems(new String[]{context.getString(R.string.restore), context.getString(R.string.delete), context.getString(R.string.ori_path)}, mPriFileListener).create();
    }

    private void initView() {
        this.moveAction = findViewById(R.id.layout_temp_button);
        this.moveAction.setVisibility(8);
        this.addButton = (Button) findViewById(R.id.btn_AddNew);
        this.delBtn = (Button) findViewById(R.id.btn_GroupDelete);
        this.selectBtn = (Button) findViewById(R.id.btn_SelectAll);
        this.restoreBtn = (Button) findViewById(R.id.btn_DeFile);
        this.infoBar = (TextView) findViewById(R.id.info_bar);
        this.tvTitle = (TextView) findViewById(R.id.txt_title);
        this.tvTitle.setText(R.string.pri_file);
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateFileActivity.3
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PrivateFileActivity.this.closePrivateSpace = true;
                Intent manIntent = new Intent().setClass(PrivateFileActivity.this, PrivateSpaceActivity.class);
                manIntent.putExtra("pwd", PrivateFileActivity.this.password);
                manIntent.setFlags(67141632);
                manIntent.addFlags(8388608);
                System.gc();
                PrivateFileActivity.this.startActivity(manIntent);
                PrivateFileActivity.this.finish();
            }
        });
        this.lv = (ListView) findViewById(R.id.lv_temp);
        this.nullBgImage = (ImageView) findViewById(R.id.iv_Null);
        if (Locale.getDefault().getLanguage().equals("en")) {
            this.nullBgImage.setBackgroundResource(R.drawable.gui_file_null_en);
        } else {
            this.nullBgImage.setBackgroundResource(R.drawable.gui_file_null);
        }
        this.addButton.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateFileActivity.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Intent intent = new Intent(PrivateFileActivity.this.thisContext, AddNormalFileActivity.class);
                intent.setFlags(67141632);
                intent.addFlags(8388608);
                PrivateFileActivity.this.thisContext.startActivity(intent);
            }
        });
        this.delBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateFileActivity.5
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                PrivateFileActivity.this.createDeleteDialog().show();
            }
        });
        this.selectBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateFileActivity.6
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (PrivateFileActivity.this.selectBtn.getText().equals(PrivateFileActivity.this.thisContext.getString(R.string.select_all))) {
                    PrivateFileActivity.this.chosenFiles.clear();
                    ArrayList<FileInfo> tmpArray = PrivateFileActivity.this.mAdapter.setSelectAll();
                    for (int i = 0; i < tmpArray.size(); i++) {
                        PrivateFileActivity.this.chosenFiles.add(tmpArray.get(i).getFilePath());
                    }
                    PrivateFileActivity.this.selectBtn.setText(R.string.cancel);
                } else {
                    PrivateFileActivity.this.chosenFiles.clear();
                    PrivateFileActivity.this.mAdapter.setDisSelectAll();
                    PrivateFileActivity.this.selectBtn.setText(R.string.select_all);
                    PrivateFileActivity.this.moveAction.setVisibility(8);
                    PrivateFileActivity.this.addButton.setVisibility(0);
                }
                PrivateFileActivity.this.mAdapter.notifyDataSetChanged();
            }
        });
        this.restoreBtn.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.PrivateFileActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (PrivateFileActivity.this.chosenFiles.size() > 0) {
                    PrivateFileActivity.this.showProgress();
                    PrivateFileActivity.this.closePrivateSpace = false;
                    new Thread() { // from class: com.lenovo.safebox.PrivateFileActivity.7.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            boolean sendFlag = true;
                            StringBuffer buffer = new StringBuffer();
                            String recoveryFileName = "";
                            int recoveryCount = 0;
                            try {
                                ArrayList<String> dbList = new ArrayList<>();
                                for (int i = 0; i < PrivateFileActivity.this.chosenFiles.size(); i++) {
                                    String fileName = PrivateSpaceTools.getFileName(PrivateFileActivity.this.chosenFiles.get(i));
                                    String oriPath = PrivateFileActivity.this.getOriPath(fileName);
                                    if (oriPath == null || oriPath.isEmpty()) {
                                        File recovery = new File("/sdcard/leSafeRecovery");
                                        if (!recovery.exists()) {
                                            recovery.mkdir();
                                        }
                                        if (Build.MODEL.contains("K900")) {
                                            buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(PrivateFileActivity.this.chosenFiles.get(i)) + " " + recovery.getAbsolutePath() + "\n");
                                        } else {
                                            buffer.append(PrivateSpaceTools.busybox + " mv -f " + PrivateSpaceTools.resolveFilename(PrivateFileActivity.this.chosenFiles.get(i)) + " " + recovery.getAbsolutePath() + "\n");
                                        }
                                        recoveryCount++;
                                        recoveryFileName = fileName;
                                        dbList.add(PrivateSpaceTools.getFileName(PrivateFileActivity.this.chosenFiles.get(i).toString()));
                                    } else {
                                        File desD = new File(oriPath);
                                        if (!desD.exists() || !desD.isDirectory()) {
                                            desD.mkdir();
                                            buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(PrivateFileActivity.this.chosenFiles.get(i)) + " -f " + desD.getAbsolutePath() + "\n");
                                            dbList.add(PrivateSpaceTools.getFileName(PrivateFileActivity.this.chosenFiles.get(i).toString()));
                                        } else if (new File(oriPath + "/" + fileName).exists()) {
                                            PrivateFileActivity.this.handler.sendMessage(PrivateFileActivity.this.handler.obtainMessage(3, fileName + PrivateFileActivity.this.getString(R.string.restore_cover_msg)));
                                        } else {
                                            buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(PrivateFileActivity.this.chosenFiles.get(i)) + " " + PrivateSpaceTools.resolveFilename(oriPath) + "\n");
                                            dbList.add(PrivateSpaceTools.getFileName(PrivateFileActivity.this.chosenFiles.get(i).toString()));
                                        }
                                    }
                                }
                                PrivateFileActivity.this.deleteData(dbList);
                                if (recoveryCount == 1) {
                                    PrivateFileActivity.this.handler.sendMessage(PrivateFileActivity.this.handler.obtainMessage(4, recoveryFileName + PrivateFileActivity.this.getString(R.string.restore_recovery)));
                                } else if (recoveryCount > 1) {
                                    PrivateFileActivity.this.handler.sendMessage(PrivateFileActivity.this.handler.obtainMessage(4, recoveryCount + PrivateFileActivity.this.getString(R.string.restore_file) + PrivateFileActivity.this.getString(R.string.restore_recovery)));
                                }
                                buffer.append("echo done\n");
                                PrivateSpaceTools.prepareExecuteFile(PrivateFileActivity.this.thisContext, buffer.toString());
                                dbList.clear();
                            } catch (IndexOutOfBoundsException e) {
                                e.printStackTrace();
                                sendFlag = false;
                            } catch (NullPointerException e2) {
                                e2.printStackTrace();
                                sendFlag = false;
                            }
                            if (sendFlag) {
                                PrivateFileActivity.this.handler.sendMessage(PrivateFileActivity.this.handler.obtainMessage(1));
                            }
                        }
                    }.start();
                }
            }
        });
    }

    public CustomDialog createDeleteDialog() {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(this);
        mediaBuilder.setTitle(getString(R.string.delete_title));
        mediaBuilder.setMessage(getString(R.string.delete_msg));
        mediaBuilder.setNegativeButton(getString(R.string.cancel), (DialogInterface.OnClickListener) null);
        mediaBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.PrivateFileActivity.8
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                if (PrivateFileActivity.this.chosenFiles.size() > 0) {
                    PrivateFileActivity.this.showProgress();
                    PrivateFileActivity.this.closePrivateSpace = false;
                    new Thread() { // from class: com.lenovo.safebox.PrivateFileActivity.8.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            boolean sendFlag = true;
                            StringBuffer buffer = new StringBuffer();
                            try {
                                ArrayList<String> dbList = new ArrayList<>();
                                for (int i = 0; i < PrivateFileActivity.this.chosenFiles.size(); i++) {
                                    if (Build.MODEL.contains("K900")) {
                                        buffer.append(PrivateSpaceTools.busybox + " rm -r " + PrivateSpaceTools.resolveFilename(PrivateFileActivity.this.chosenFiles.get(i).toString()) + "\n");
                                    } else {
                                        buffer.append(PrivateSpaceTools.busybox + " rm -rf " + PrivateSpaceTools.resolveFilename(PrivateFileActivity.this.chosenFiles.get(i).toString()) + "\n");
                                    }
                                    dbList.add(PrivateSpaceTools.getFileName(PrivateFileActivity.this.chosenFiles.get(i).toString()));
                                }
                                PrivateFileActivity.this.deleteData(dbList);
                                buffer.append("echo done\n");
                                PrivateSpaceTools.prepareExecuteFile(PrivateFileActivity.this.thisContext, buffer.toString());
                                dbList.clear();
                            } catch (IndexOutOfBoundsException e) {
                                e.printStackTrace();
                                sendFlag = false;
                            } catch (NullPointerException e2) {
                                e2.printStackTrace();
                                sendFlag = false;
                            }
                            if (sendFlag) {
                                PrivateFileActivity.this.handler.sendMessage(PrivateFileActivity.this.handler.obtainMessage(2));
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
        mediaBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.PrivateFileActivity.9
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                PrivateSpaceTools.prepareExecuteFile(PrivateFileActivity.this.thisContext, cmd);
                Log.i(PrivateFileActivity.this.TAG, "DELETE RESULT : " + PrivateFileActivity.this.deleteData(fileLabel));
                PrivateFileActivity.this.updateUI();
            }
        });
        return mediaBuilder.create();
    }

    public void showProgress() {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.lenovo.safebox.PrivateFileActivity.10
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                PrivateFileActivity.this.progressDialog = null;
            }
        });
        this.progressDialog.setProgressStyle(0);
        this.progressDialog.setMessage(getString(R.string.exec_state));
        this.progressDialog.show();
    }

    /* loaded from: classes.dex */
    class PriFileListener implements DialogInterface.OnClickListener {
        Context mContext;
        private String mFileLabel;
        private String mTAG = "PriMediaListener  aaaaaaaaaaaaaaaaaaaaa";
        private String oriPath;
        private String priPath;

        public PriFileListener(Context context, String label, String priPath) {
            PrivateFileActivity.this = r2;
            this.mContext = context;
            this.mFileLabel = label;
            this.priPath = priPath;
            this.oriPath = r2.getOriPath(this.mFileLabel);
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    String command = null;
                    String fileName = PrivateSpaceTools.getFileName(this.priPath);
                    if (this.oriPath == null || this.oriPath.isEmpty()) {
                        File recovery = new File("/sdcard/leSafeRecovery");
                        if (!recovery.exists()) {
                            recovery.mkdir();
                        }
                        command = PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(this.priPath) + " " + recovery.getAbsolutePath();
                        PrivateFileActivity.this.handler.sendMessage(PrivateFileActivity.this.handler.obtainMessage(4, PrivateSpaceTools.resolveFilename(this.priPath) + PrivateFileActivity.this.getString(R.string.restore_recovery)));
                        Log.i(PrivateFileActivity.this.TAG, "DELETE RESULT : " + PrivateFileActivity.this.deleteData(PrivateSpaceTools.getFileName(this.priPath)));
                    } else {
                        File desD = new File(this.oriPath);
                        if (!desD.exists() || !desD.isDirectory()) {
                            desD.mkdir();
                            command = PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(this.priPath) + " " + desD.getAbsolutePath() + "\n";
                            Log.i(PrivateFileActivity.this.TAG, "DELETE RESULT : " + PrivateFileActivity.this.deleteData(PrivateSpaceTools.getFileName(this.priPath)));
                        } else if (new File(this.oriPath + "/" + fileName).exists()) {
                            PrivateFileActivity.this.handler.sendMessage(PrivateFileActivity.this.handler.obtainMessage(4, fileName + PrivateFileActivity.this.getString(R.string.restore_cover_msg)));
                        } else {
                            if (Build.MODEL.contains("K900")) {
                                command = PrivateSpaceTools.busybox + " mv  " + PrivateSpaceTools.resolveFilename(this.priPath) + " " + PrivateSpaceTools.resolveFilename(this.oriPath) + "\n";
                            } else {
                                command = PrivateSpaceTools.busybox + " mv -f " + PrivateSpaceTools.resolveFilename(this.priPath) + " " + PrivateSpaceTools.resolveFilename(this.oriPath) + "\n";
                            }
                            Log.i(PrivateFileActivity.this.TAG, "DELETE RESULT : " + PrivateFileActivity.this.deleteData(PrivateSpaceTools.getFileName(this.priPath)));
                        }
                    }
                    PrivateSpaceTools.prepareExecuteFile(PrivateFileActivity.this.thisContext, command + "\n echo done \n");
                    PrivateFileActivity.this.updateUI();
                    return;
                case 1:
                    StringBuffer buffer1 = new StringBuffer();
                    if (Build.MODEL.contains("K900")) {
                        buffer1.append(PrivateSpaceTools.busybox + " rm  -r " + PrivateSpaceTools.resolveFilename(this.priPath));
                    } else {
                        buffer1.append(PrivateSpaceTools.busybox + " rm  -rf " + PrivateSpaceTools.resolveFilename(this.priPath));
                    }
                    buffer1.append("\necho done\n");
                    PrivateFileActivity.this.createSingleDeleteDialog(this.mFileLabel, buffer1.toString()).show();
                    return;
                case 2:
                    if (this.oriPath == null) {
                        Toast.makeText(this.mContext, "/sdcard/leSafeRecovery", 0).show();
                        return;
                    } else if (this.oriPath.substring(this.oriPath.length() - 1, this.oriPath.length()).equals("/")) {
                        Toast.makeText(this.mContext, this.oriPath + this.mFileLabel, 0).show();
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

    void preWork() {
        this.mHelper = new PrivateSpaceHelper(this, PrivateSpaceTools.DB_NAME, null, 1);
        PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
        this.thisContext = this;
        this.settings = getSharedPreferences(this.PREFS_NAME, 0);
        this.password = this.settings.getString("password", "");
    }

    public String getOriPath(String fileName) {
        String returnStr = null;
        this.db = this.mHelper.getReadableDatabase();
        this.db.beginTransaction();
        Cursor mCursor = fetchData(fileName);
        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            returnStr = mCursor.getString(mCursor.getColumnIndex(PrivateSpaceHelper.FROM));
        }
        if (mCursor != null) {
            mCursor.close();
        }
        this.db.setTransactionSuccessful();
        this.db.endTransaction();
        this.db.close();
        return returnStr;
    }

    private Cursor fetchData(String fileLabel) {
        this.db = this.mHelper.getReadableDatabase();
        return this.db.query(true, PrivateSpaceHelper.TB_NAME, new String[]{"_id", PrivateSpaceHelper.FILE, PrivateSpaceHelper.FROM}, "filename = \"" + fileLabel + "\"", null, null, null, null, null);
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

    /* loaded from: classes.dex */
    public class RequestReceiver extends BroadcastReceiver {
        public RequestReceiver() {
            PrivateFileActivity.this = r1;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SafeBoxApplication.NAC_DONE) && PrivateFileActivity.this.progressDialog != null && PrivateFileActivity.this.progressDialog.isShowing()) {
                PrivateFileActivity.this.progressDialog.dismiss();
                PrivateFileActivity.this.closePrivateSpace = true;
                PrivateFileActivity.this.finish();
            }
        }
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        Log.i(this.TAG, "closePrivateSpace :" + this.closePrivateSpace);
        registerReceiver(this.mReceiver, this.filter);
        if (this.closePrivateSpace) {
            PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
            updateUI();
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        unregisterReceiver(this.mReceiver);
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.filter.addAction(SafeBoxApplication.NAC_DONE);
        }
        if (this.closePrivateSpace) {
            PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        }
        Log.i(this.TAG, "onDestory---Stop");
        super.onStop();
    }

    protected void onDestory() {
        super.onDestroy();
        PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        Log.i(this.TAG, "onDestory");
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
        startActivityForResult(manIntent, 1);
        finish();
        Log.i(this.TAG, "onDest---onKeyDown");
        return super.onKeyDown(keyCode, event);
    }
}

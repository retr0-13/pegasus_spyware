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
import com.lenovo.safebox.adapter.ManualMediaAdapter;
import com.lenovo.safebox.dialog.CustomDialog;
import com.lenovo.safebox.dialog.EncryptDialogListener;
import java.io.File;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class AddManualMediaActivity extends Activity {
    private static final int ENCRYPT_COVER_FILE = 2;
    private static final int ENCRYPT_NO_SPACE = 5;
    private static final int ENCRYPT_SUCCESS = 1;
    public static String mimeType;
    private int MediaCount;
    private View actionBar;
    private ArrayList<String> chosenFiles;
    SQLiteDatabase db;
    private Button encryptAction;
    private String extraSd;
    private ImageView ivBack;
    private ListView lstView;
    private ManualMediaAdapter mAdapter;
    PrivateSpaceHelper mHelper;
    private ProgressBar progress;
    private ProgressDialog progressDialog;
    private Button selectAll;
    private String[] storageList;
    private Context thisContext;
    private Button tvPath;
    private ImageView tvPathImage;
    private TextView tvTitle;
    private String TAG = "AddManualMediaActivity  aaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.AddManualMediaActivity.3
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        if (AddManualMediaActivity.this.progressDialog != null) {
                            AddManualMediaActivity.this.progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                    }
                    PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
                    AddManualMediaActivity.this.chosenFiles.size();
                    AddManualMediaActivity.this.updateChildUI(AddManualMediaActivity.this.tvPath.getText().toString());
                    AddManualMediaActivity.this.chosenFiles.clear();
                    AddManualMediaActivity.this.mAdapter.notifyDataSetChanged();
                    Intent jumpIntent = new Intent(AddManualMediaActivity.this.thisContext, PrivateMediaActivity.class);
                    if (AddManualMediaActivity.this.getIntent().getBooleanExtra("image", true)) {
                        jumpIntent.setFlags(0);
                        jumpIntent.setData(Uri.parse("image"));
                    } else {
                        jumpIntent.setFlags(0);
                        jumpIntent.setData(Uri.parse("image"));
                    }
                    AddManualMediaActivity.this.thisContext.startActivity(jumpIntent);
                    jumpIntent.addFlags(8388608);
                    return;
                case 2:
                    Toast.makeText(AddManualMediaActivity.this.thisContext, msg.obj.toString() + AddManualMediaActivity.this.thisContext.getString(R.string.encrypt_cover_msg) + "encrypt_" + msg.obj.toString(), 0).show();
                    return;
                case 3:
                case 4:
                default:
                    return;
                case 5:
                    try {
                        if (AddManualMediaActivity.this.progressDialog != null) {
                            AddManualMediaActivity.this.progressDialog.dismiss();
                        }
                    } catch (Exception e2) {
                    }
                    AddManualMediaActivity.this.createNospaceDialog().show();
                    return;
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.gui_normalfile_activity);
        preWork();
        initView();
        this.thisContext = this;
        this.selectAll.setOnClickListener(new SelectButtonListener(this));
        PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        this.chosenFiles = new ArrayList<>();
        this.mAdapter = new ManualMediaAdapter(this, getMediaChildList(PrivateSpaceTools.sdDir));
        this.lstView.setAdapter((ListAdapter) this.mAdapter);
        this.lstView.setOnItemClickListener(new ListItemListener(this));
        this.encryptAction.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddManualMediaActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (AddManualMediaActivity.this.chosenFiles.size() > 0) {
                    AddManualMediaActivity.this.showProgress();
                }
                PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
                new Thread() { // from class: com.lenovo.safebox.AddManualMediaActivity.1.1
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        boolean sendFlag = true;
                        StringBuffer buffer = new StringBuffer();
                        long fileSize = 0;
                        try {
                            StatFs sdStat = new StatFs(new File(PrivateSpaceTools.sdDir).getPath());
                            boolean isSameBlock = false;
                            int i = 0;
                            while (true) {
                                if (i >= AddManualMediaActivity.this.chosenFiles.size()) {
                                    break;
                                }
                                File thisFile = new File((String) AddManualMediaActivity.this.chosenFiles.get(i));
                                fileSize += thisFile.length();
                                if (!thisFile.getPath().equals("...") && new StatFs(thisFile.getPath()).getAvailableBlocks() == sdStat.getAvailableBlocks()) {
                                    isSameBlock = true;
                                    break;
                                }
                                i++;
                            }
                            if (fileSize > PrivateSpaceTools.getSdSpace() && !isSameBlock) {
                                AddManualMediaActivity.this.chosenFiles.clear();
                                AddManualMediaActivity.this.handler.sendMessage(AddManualMediaActivity.this.handler.obtainMessage(5));
                            }
                            for (int i2 = 0; i2 < AddManualMediaActivity.this.chosenFiles.size(); i2++) {
                                String[] resolveString = ((String) AddManualMediaActivity.this.chosenFiles.get(i2)).toString().split("/");
                                String fileName = resolveString[resolveString.length - 1];
                                if (PrivateSpaceTools.isImage((String) AddManualMediaActivity.this.chosenFiles.get(i2))) {
                                    if (new File("/mnt/sdcard/.pFolder/pictures/" + fileName).exists()) {
                                        String fileName2 = "encrypt_" + fileName;
                                        buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(((String) AddManualMediaActivity.this.chosenFiles.get(i2)).toString()) + " " + PrivateSpaceTools.priImgFolder + "/" + PrivateSpaceTools.resolveFilename(fileName2) + "\n");
                                        AddManualMediaActivity.this.insert(fileName2, AddManualMediaActivity.this.tvPath.getText().toString(), AddManualMediaActivity.this.getIntent().getBooleanExtra("image", true));
                                        AddManualMediaActivity.this.handler.sendMessage(AddManualMediaActivity.this.handler.obtainMessage(2, fileName2));
                                    } else {
                                        buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(((String) AddManualMediaActivity.this.chosenFiles.get(i2)).toString()) + " " + PrivateSpaceTools.priImgFolder + "\n");
                                        AddManualMediaActivity.this.insert(resolveString[resolveString.length - 1], AddManualMediaActivity.this.tvPath.getText().toString(), AddManualMediaActivity.this.getIntent().getBooleanExtra("image", true));
                                    }
                                } else if (!PrivateSpaceTools.isVideo((String) AddManualMediaActivity.this.chosenFiles.get(i2))) {
                                    buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(((String) AddManualMediaActivity.this.chosenFiles.get(i2)).toString()) + " " + PrivateSpaceTools.priFilesFolder + "\n");
                                } else if (new File("/mnt/sdcard/.pFolder/videos/" + fileName).exists()) {
                                    String fileName3 = "encrypt_" + fileName;
                                    buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(((String) AddManualMediaActivity.this.chosenFiles.get(i2)).toString()) + " " + PrivateSpaceTools.priVideoFolder + "/" + PrivateSpaceTools.resolveFilename(fileName3) + "\n");
                                    AddManualMediaActivity.this.insert(fileName3, AddManualMediaActivity.this.tvPath.getText().toString(), AddManualMediaActivity.this.getIntent().getBooleanExtra("image", true));
                                    AddManualMediaActivity.this.handler.sendMessage(AddManualMediaActivity.this.handler.obtainMessage(2, fileName3));
                                } else {
                                    buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(((String) AddManualMediaActivity.this.chosenFiles.get(i2)).toString()) + " " + PrivateSpaceTools.priVideoFolder + "\n");
                                    AddManualMediaActivity.this.insert(fileName, AddManualMediaActivity.this.tvPath.getText().toString(), AddManualMediaActivity.this.getIntent().getBooleanExtra("image", true));
                                }
                                String[] resolveString2 = ((String) AddManualMediaActivity.this.chosenFiles.get(i2)).toString().split("/");
                                if (resolveString2.length == 0) {
                                }
                                AddManualMediaActivity.this.insert(resolveString2[resolveString2.length - 1], AddManualMediaActivity.this.tvPath.getText().toString(), AddManualMediaActivity.this.getIntent().getBooleanExtra("image", true));
                            }
                            buffer.append("echo done\n");
                            PrivateSpaceTools.prepareExecuteFile(AddManualMediaActivity.this.thisContext, buffer.toString());
                        } catch (IllegalArgumentException e) {
                            sendFlag = false;
                            AddManualMediaActivity.this.finish();
                        } catch (IndexOutOfBoundsException e2) {
                            e2.printStackTrace();
                            sendFlag = false;
                        } catch (NullPointerException e3) {
                            e3.printStackTrace();
                            sendFlag = false;
                        }
                        if (sendFlag && AddManualMediaActivity.this.chosenFiles.size() > 0) {
                            AddManualMediaActivity.this.handler.sendMessage(AddManualMediaActivity.this.handler.obtainMessage(1));
                        }
                    }
                }.start();
            }
        });
    }

    public void showProgress() {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.lenovo.safebox.AddManualMediaActivity.2
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                AddManualMediaActivity.this.progressDialog = null;
            }
        });
        this.progressDialog.setProgressStyle(0);
        this.progressDialog.setMessage(getString(R.string.exec_state));
        this.progressDialog.show();
    }

    public CustomDialog createActionDialog(Context context, int count) {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(context).setTitle(context.getString(R.string.encrypt_title));
        if (getIntent().getBooleanExtra("image", true)) {
            mediaBuilder.setMessage(context.getString(R.string.encrypt_message1) + count + context.getString(R.string.encrypt_message_image) + context.getString(R.string.encrypt_message2));
        } else {
            mediaBuilder.setMessage(context.getString(R.string.encrypt_message1) + count + context.getString(R.string.encrypt_message_video) + context.getString(R.string.encrypt_message2));
        }
        EncryptDialogListener mListener = new EncryptDialogListener(context, getIntent().getBooleanExtra("image", true), PrivateMediaActivity.class);
        mediaBuilder.setPositiveButton(context.getString(R.string.continue_add), (DialogInterface.OnClickListener) null);
        mediaBuilder.setNegativeButton(context.getString(R.string.complete), mListener);
        return mediaBuilder.create();
    }

    public CustomDialog createNospaceDialog() {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(this);
        mediaBuilder.setTitle(getString(R.string.exit_title));
        mediaBuilder.setMessage(getString(R.string.no_space_sdcard));
        mediaBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.AddManualMediaActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                Log.i("onClick", "onClick");
                if (AddManualMediaActivity.this.chosenFiles.size() == 0) {
                    AddManualMediaActivity.this.actionBar.setVisibility(8);
                    AddManualMediaActivity.this.mAdapter.setDisSelectAll();
                }
            }
        });
        return mediaBuilder.create();
    }

    public CustomDialog createStorageSelectDialog(Context context, final String[] storageList) {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(context);
        mediaBuilder.setTitle(R.string.choose_path);
        mediaBuilder.setSingleChoiceItems(storageList, 2, new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.AddManualMediaActivity.5
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    AddManualMediaActivity.this.tvPath.setText(storageList[0]);
                    AddManualMediaActivity.this.updateChildUI(storageList[0]);
                } else if (which == 1) {
                    AddManualMediaActivity.this.tvPath.setText(storageList[1]);
                    AddManualMediaActivity.this.updateChildUI(storageList[1]);
                }
                dialog.dismiss();
            }
        });
        return mediaBuilder.create();
    }

    private String checkExtraSd() {
        ArrayList<String> extraSd = new ArrayList<>();
        extraSd.add("/mnt/sdcard1");
        extraSd.add("/mnt/sdcard2");
        extraSd.add("/mnt/sdcard/extra_sd");
        extraSd.add("/mnt/sdcard-ext");
        for (int i = 0; i < extraSd.size(); i++) {
            File sd1 = new File(extraSd.get(i));
            if (sd1.exists() && new StatFs(sd1.getPath()).getBlockCount() > 0) {
                return extraSd.get(i);
            }
        }
        return null;
    }

    /* loaded from: classes.dex */
    private class SelectButtonListener implements View.OnClickListener {
        Context mContext;

        SelectButtonListener(Context context) {
            AddManualMediaActivity.this = r1;
            this.mContext = context;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (AddManualMediaActivity.this.selectAll.getText().equals(this.mContext.getString(R.string.select_all))) {
                AddManualMediaActivity.this.MediaCount = AddManualMediaActivity.this.mAdapter.setSelectAll();
                AddManualMediaActivity.this.mAdapter.notifyDataSetChanged();
                ArrayList<FileInfo> selectAllList = AddManualMediaActivity.this.mAdapter.getSelectAllList();
                AddManualMediaActivity.this.chosenFiles.clear();
                for (int i = 0; i < selectAllList.size(); i++) {
                    AddManualMediaActivity.this.chosenFiles.add(selectAllList.get(i).getFilePath());
                }
                AddManualMediaActivity.this.selectAll.setText(this.mContext.getString(R.string.dis_select_all));
                AddManualMediaActivity.this.selectAll.setPadding(10, 0, 0, 0);
                return;
            }
            AddManualMediaActivity.this.mAdapter.setDisSelectAll();
            AddManualMediaActivity.this.MediaCount = 0;
            AddManualMediaActivity.this.chosenFiles.clear();
            AddManualMediaActivity.this.selectAll.setText(this.mContext.getString(R.string.select_all));
            AddManualMediaActivity.this.selectAll.setPadding(20, 0, 20, 0);
            AddManualMediaActivity.this.actionBar.setVisibility(8);
            AddManualMediaActivity.this.mAdapter.notifyDataSetChanged();
        }
    }

    /* loaded from: classes.dex */
    private class ListItemListener implements AdapterView.OnItemClickListener {
        private Context mContext;

        ListItemListener(Context context) {
            AddManualMediaActivity.this = r1;
            this.mContext = context;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (AddManualMediaActivity.this.mAdapter.getItem(position).getFilePath().equals("...")) {
                AddManualMediaActivity.this.updateParentUI();
            } else if (AddManualMediaActivity.this.mAdapter.isItemFolder(position)) {
                AddManualMediaActivity.this.updateChildUI(AddManualMediaActivity.this.mAdapter.getItem(position).getFilePath());
            } else {
                AddManualMediaActivity.this.mAdapter.getItem(position).setCheckHolder(AddManualMediaActivity.this.mAdapter.getItem(position).getCheckHolder());
                AddManualMediaActivity.this.mAdapter.notifyDataSetChanged();
                if (AddManualMediaActivity.this.mAdapter.getItem(position).getCheckHolder()) {
                    Log.i(AddManualMediaActivity.this.TAG, "Checked");
                    AddManualMediaActivity.this.chosenFiles.add(AddManualMediaActivity.this.mAdapter.getItem(position).getFilePath());
                    if (AddManualMediaActivity.this.chosenFiles.size() == AddManualMediaActivity.this.MediaCount) {
                        AddManualMediaActivity.this.actionBar.setVisibility(0);
                        AddManualMediaActivity.this.selectAll.setText(R.string.dis_select_all);
                        AddManualMediaActivity.this.selectAll.setPadding(10, 0, 0, 0);
                        return;
                    }
                    AddManualMediaActivity.this.actionBar.setVisibility(0);
                    AddManualMediaActivity.this.selectAll.setText(R.string.select_all);
                    AddManualMediaActivity.this.selectAll.setPadding(20, 0, 20, 0);
                    return;
                }
                AddManualMediaActivity.this.chosenFiles.remove(AddManualMediaActivity.this.mAdapter.getItem(position).getFilePath());
                if (AddManualMediaActivity.this.chosenFiles.size() == 0) {
                    AddManualMediaActivity.this.actionBar.setVisibility(8);
                    return;
                }
                AddManualMediaActivity.this.selectAll.setText(R.string.select_all);
                AddManualMediaActivity.this.selectAll.setPadding(20, 0, 20, 0);
            }
        }
    }

    void preWork() {
        PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        this.mHelper = new PrivateSpaceHelper(this, PrivateSpaceTools.DB_NAME, null, 1);
    }

    private void initView() {
        this.tvTitle = (TextView) findViewById(R.id.txt_title);
        this.tvPath = (Button) findViewById(R.id.tv_Path);
        this.tvPath.setText(PrivateSpaceTools.sdDir);
        this.tvPathImage = (ImageView) findViewById(R.id.tv_Path_image);
        this.extraSd = checkExtraSd();
        if (this.extraSd != null) {
            this.storageList = new String[2];
            this.storageList[0] = PrivateSpaceTools.sdDir;
            this.storageList[1] = this.extraSd;
            this.tvPathImage.setVisibility(0);
            this.tvPath.setClickable(true);
            this.tvPath.setFocusable(true);
            this.tvPath.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddManualMediaActivity.6
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    AddManualMediaActivity.this.createStorageSelectDialog(AddManualMediaActivity.this.thisContext, AddManualMediaActivity.this.storageList).show();
                }
            });
        } else {
            this.tvPath.setClickable(false);
            this.tvPath.setFocusable(false);
            this.tvPathImage.setVisibility(8);
        }
        this.actionBar = findViewById(R.id.layout_temp);
        this.selectAll = (Button) findViewById(R.id.btn_selectAll);
        this.encryptAction = (Button) findViewById(R.id.btn_import);
        this.actionBar.setVisibility(8);
        this.lstView = (ListView) findViewById(R.id.lv_temp);
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddManualMediaActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddManualMediaActivity.this.finish();
            }
        });
        if (getIntent().getBooleanExtra("image", true)) {
            this.tvTitle.setText(R.string.addPrivateImage);
            mimeType = "image";
            return;
        }
        this.tvTitle.setText(R.string.addPrivateVideo);
        mimeType = "video";
    }

    public void updateChildUI(String folderPath) {
        this.tvPath.setText(folderPath);
        this.mAdapter = new ManualMediaAdapter(this, getMediaChildList(folderPath));
        this.chosenFiles.clear();
        this.actionBar.setVisibility(8);
        this.lstView.setAdapter((ListAdapter) this.mAdapter);
    }

    public void updateParentUI() {
        this.chosenFiles.clear();
        this.actionBar.setVisibility(8);
        String pwd = (String) this.tvPath.getText();
        Log.i(this.TAG, "tvPath.getText " + pwd);
        String parent = new File(pwd).getParent();
        if (!(pwd + "/").equals(PrivateSpaceTools.sdDir) && !pwd.equals(this.extraSd)) {
            this.tvPath.setText(parent);
            this.mAdapter = new ManualMediaAdapter(this, getMediaChildList(parent + "/"));
            this.lstView.setAdapter((ListAdapter) this.mAdapter);
        }
    }

    public ArrayList<FileInfo> getMediaChildList(String path) {
        ArrayList<String> excludeFolders;
        File[] files = new File(path).listFiles();
        ArrayList<FileInfo> returnList = new ArrayList<>();
        this.MediaCount = 0;
        if (PrivateSpaceTools.exculdeFolders != null) {
            excludeFolders = PrivateSpaceTools.exculdeFolders;
        } else {
            excludeFolders = PrivateSpaceTools.resolveExcludeFolders();
        }
        if (this.extraSd == null) {
            this.extraSd = checkExtraSd();
        }
        if (!path.equals(PrivateSpaceTools.sdDir)) {
            if (this.extraSd == null) {
                returnList.add(new FileInfo("...", null));
            } else if (!path.equals(this.extraSd) && !path.equals(this.extraSd + "/")) {
                returnList.add(new FileInfo("...", null));
            }
        }
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (!files[i].toString().equals(PrivateSpaceTools.coverFilePath) && !files[i].toString().equals("/sdcard/.pFolder") && !excludeFolders.contains(files[i].toString())) {
                    if (PrivateSpaceTools.isFolder(files[i].getAbsolutePath())) {
                        returnList.add(new FileInfo(files[i].toString(), null));
                    } else if (PrivateSpaceTools.isImage(files[i].getAbsolutePath()) && mimeType.equals("image")) {
                        FileInfo f1Info = new FileInfo(files[i].toString(), mimeType);
                        this.MediaCount++;
                        returnList.add(f1Info);
                    } else if (PrivateSpaceTools.isVideo(files[i].getAbsolutePath()) && mimeType.equals("video")) {
                        FileInfo f1Info2 = new FileInfo(files[i].toString(), mimeType);
                        this.MediaCount++;
                        returnList.add(f1Info2);
                    }
                }
            }
        } else {
            returnList.add(new FileInfo(path, null));
        }
        return returnList;
    }

    public boolean insert(String filename, String path, boolean type) {
        this.db = this.mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(PrivateSpaceHelper.FILE, filename);
        values.put(PrivateSpaceHelper.FROM, path);
        if (type) {
            values.put(PrivateSpaceHelper.TYPE, (Integer) 0);
        } else {
            values.put(PrivateSpaceHelper.TYPE, (Integer) 1);
        }
        long result = this.db.insert(PrivateSpaceHelper.TB_NAME, "_id", values);
        this.db.close();
        if (result == -1) {
            Log.i(this.TAG, "insert failed");
            return false;
        }
        Log.i(this.TAG, "insert success");
        return true;
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if ((((String) this.tvPath.getText()) + "/").equals(PrivateSpaceTools.sdDir) || ((String) this.tvPath.getText()).equals(PrivateSpaceTools.sdDir)) {
            return super.onKeyDown(keyCode, event);
        }
        if (this.extraSd != null && !this.extraSd.isEmpty() && ((String) this.tvPath.getText()).equals(this.extraSd)) {
            return super.onKeyDown(keyCode, event);
        }
        updateParentUI();
        return true;
    }

    /* loaded from: classes.dex */
    class EncryptCompleted implements DialogInterface.OnClickListener {
        Context mContext;

        EncryptCompleted(Context context) {
            AddManualMediaActivity.this = r1;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
        }
    }

    @Override // android.app.Activity
    protected void onStop() {
        this.mAdapter.setDisSelectAll();
        this.chosenFiles.clear();
        System.gc();
        super.onStop();
    }
}

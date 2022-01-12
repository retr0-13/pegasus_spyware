package com.lenovo.safebox;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
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
import com.lenovo.safebox.adapter.NormalFileAdapter;
import com.lenovo.safebox.dialog.CustomDialog;
import com.lenovo.safebox.dialog.EncryptDialogListener;
import java.io.File;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class AddNormalFileActivity extends Activity {
    private static final int ENCRYPT_COVER_FILE = 2;
    private static final int ENCRYPT_NO_SPACE = 5;
    private static final int ENCRYPT_SUCCESS = 1;
    private View actionBar;
    public ArrayList chosenFiles;
    SQLiteDatabase db;
    private Button encryptAction;
    private String extraSd;
    private IntentFilter filter;
    private ImageView ivBack;
    private ListView lstView;
    private NormalFileAdapter mAdapter;
    private int mAdapterRealCount;
    PrivateSpaceHelper mHelper;
    private BroadcastReceiver mReceiver;
    private ProgressBar progress;
    private ProgressDialog progressDialog;
    private Button selectAll;
    private String[] storageList;
    private Context thisContext;
    private Button tvPath;
    private ImageView tvPathImage;
    private TextView tvTitle;
    private String TAG = "AddNormalFileActivity aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.AddNormalFileActivity.5
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        if (AddNormalFileActivity.this.progressDialog != null) {
                            AddNormalFileActivity.this.progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                    }
                    int tmpCount = AddNormalFileActivity.this.chosenFiles.size();
                    if (AddNormalFileActivity.this.chosenFiles.contains("...")) {
                        int tmpCount2 = tmpCount - 1;
                    }
                    PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
                    AddNormalFileActivity.this.updateChildUI(AddNormalFileActivity.this.tvPath.getText().toString());
                    AddNormalFileActivity.this.chosenFiles.clear();
                    AddNormalFileActivity.this.mAdapter.notifyDataSetChanged();
                    AddNormalFileActivity.this.actionBar.setVisibility(8);
                    Intent manIntent = new Intent(AddNormalFileActivity.this.thisContext, PrivateFileActivity.class);
                    manIntent.addFlags(8388608);
                    AddNormalFileActivity.this.thisContext.startActivity(manIntent);
                    return;
                case 2:
                    Toast.makeText(AddNormalFileActivity.this.thisContext, msg.obj.toString() + AddNormalFileActivity.this.thisContext.getString(R.string.encrypt_cover_msg) + "encrypt_" + msg.obj.toString(), 0).show();
                    return;
                case 3:
                case 4:
                default:
                    return;
                case 5:
                    try {
                        if (AddNormalFileActivity.this.progressDialog != null) {
                            AddNormalFileActivity.this.progressDialog.dismiss();
                        }
                    } catch (Exception e2) {
                    }
                    AddNormalFileActivity.this.createNospaceDialog().show();
                    return;
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.thisContext = this;
        this.mReceiver = new RequestReceiver();
        this.filter = new IntentFilter();
        this.filter.addAction(NormalFileAdapter.REQUEST_UPDATE_ADAPTER);
        requestWindowFeature(1);
        setContentView(R.layout.gui_normalfile_activity);
        preWork();
        initView();
        this.selectAll.setOnClickListener(new SelectButtonListener(this));
        this.chosenFiles = new ArrayList();
        this.mAdapter = new NormalFileAdapter(this, getMediaChildList(PrivateSpaceTools.sdDir));
        this.lstView.setAdapter((ListAdapter) this.mAdapter);
        this.lstView.setOnItemClickListener(new ListItemListener(this));
        this.encryptAction.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddNormalFileActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddNormalFileActivity.this.mAdapterRealCount = AddNormalFileActivity.this.mAdapter.getRealCount();
                PrivateSpaceTools.socketClient(PrivateSpaceTools.showFolder);
                if (AddNormalFileActivity.this.chosenFiles.size() > 0) {
                    AddNormalFileActivity.this.showProgress();
                }
                new Thread() { // from class: com.lenovo.safebox.AddNormalFileActivity.1.1
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
                                if (i >= AddNormalFileActivity.this.chosenFiles.size()) {
                                    break;
                                }
                                File thisFile = new File(AddNormalFileActivity.this.chosenFiles.get(i).toString());
                                fileSize += thisFile.length();
                                if (!thisFile.getPath().equals("...") && new StatFs(thisFile.getPath()).getAvailableBlocks() == sdStat.getAvailableBlocks()) {
                                    isSameBlock = true;
                                    break;
                                }
                                i++;
                            }
                            if (fileSize > PrivateSpaceTools.getSdSpace() && !isSameBlock) {
                                AddNormalFileActivity.this.chosenFiles.clear();
                                AddNormalFileActivity.this.handler.sendMessage(AddNormalFileActivity.this.handler.obtainMessage(5));
                            }
                            for (int i2 = 0; i2 < AddNormalFileActivity.this.chosenFiles.size(); i2++) {
                                String[] resolveString = AddNormalFileActivity.this.chosenFiles.get(i2).toString().split("/");
                                if (resolveString.length == 0) {
                                }
                                String fileName = resolveString[resolveString.length - 1];
                                if (new File("/mnt/sdcard/.pFolder/files/" + fileName).exists()) {
                                    String fileName2 = "encrypt_" + fileName;
                                    buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(AddNormalFileActivity.this.chosenFiles.get(i2).toString()) + " " + PrivateSpaceTools.priFilesFolder + "/" + PrivateSpaceTools.resolveFilename(fileName2) + "\n");
                                    AddNormalFileActivity.this.insert(fileName2, AddNormalFileActivity.this.tvPath.getText().toString());
                                    AddNormalFileActivity.this.handler.sendMessage(AddNormalFileActivity.this.handler.obtainMessage(2, fileName2));
                                } else {
                                    buffer.append(PrivateSpaceTools.busybox + " mv " + PrivateSpaceTools.resolveFilename(AddNormalFileActivity.this.chosenFiles.get(i2).toString()) + " " + PrivateSpaceTools.priFilesFolder + "\n");
                                    AddNormalFileActivity.this.insert(fileName, AddNormalFileActivity.this.tvPath.getText().toString());
                                }
                            }
                            buffer.append("echo done\n");
                            PrivateSpaceTools.prepareExecuteFile(AddNormalFileActivity.this.thisContext, buffer.toString());
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                            sendFlag = false;
                        } catch (IndexOutOfBoundsException e2) {
                            e2.printStackTrace();
                            sendFlag = false;
                        } catch (NullPointerException e3) {
                            e3.printStackTrace();
                            sendFlag = false;
                        }
                        if (sendFlag && AddNormalFileActivity.this.chosenFiles.size() > 0) {
                            AddNormalFileActivity.this.handler.sendMessage(AddNormalFileActivity.this.handler.obtainMessage(1));
                        }
                    }
                }.start();
            }
        });
    }

    public CustomDialog createActionDialog(Context context, int count) {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(context).setTitle(context.getString(R.string.encrypt_title));
        mediaBuilder.setMessage(context.getString(R.string.encrypt_message1) + count + context.getString(R.string.encrypt_message_file) + context.getString(R.string.encrypt_message2));
        EncryptDialogListener mListener = new EncryptDialogListener(context, true, PrivateFileActivity.class);
        if (count < this.mAdapterRealCount) {
            mediaBuilder.setPositiveButton(context.getString(R.string.continue_add), (DialogInterface.OnClickListener) null);
        }
        mediaBuilder.setNegativeButton(context.getString(R.string.complete), mListener);
        return mediaBuilder.create();
    }

    public CustomDialog createStorageSelectDialog(Context context, final String[] storageList) {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(context);
        mediaBuilder.setTitle(R.string.choose_path);
        mediaBuilder.setSingleChoiceItems(storageList, 2, new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.AddNormalFileActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    AddNormalFileActivity.this.tvPath.setText(storageList[0]);
                    AddNormalFileActivity.this.updateChildUI(storageList[0]);
                } else if (which == 1) {
                    AddNormalFileActivity.this.tvPath.setText(storageList[1]);
                    AddNormalFileActivity.this.updateChildUI(storageList[1]);
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

    void preWork() {
        this.mHelper = new PrivateSpaceHelper(this, PrivateSpaceTools.DB_NAME, null, 1);
    }

    public boolean insert(String filename, String path) {
        this.db = this.mHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(PrivateSpaceHelper.FILE, filename);
        values.put(PrivateSpaceHelper.FROM, path);
        long result = this.db.insert(PrivateSpaceHelper.TB_NAME, "_id", values);
        this.db.close();
        if (result == -1) {
            Log.i("aaaaaaaaaaaaaaaaaaaaaaaaaaaPrivateSpaceHelper", "insert failed");
            return false;
        }
        Log.i("aaaaaaaaaaaaaaaaaaaaaaaaaaaPrivateSpaceHelper", "insert success");
        return true;
    }

    /* loaded from: classes.dex */
    private class SelectButtonListener implements View.OnClickListener {
        Context mContext;

        SelectButtonListener(Context context) {
            this.mContext = context;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v) {
            if (AddNormalFileActivity.this.selectAll.getText().equals(this.mContext.getString(R.string.select_all))) {
                AddNormalFileActivity.this.chosenFiles.clear();
                ArrayList<FileInfo> tmpArray = AddNormalFileActivity.this.mAdapter.setSelectAll();
                for (int i = 0; i < tmpArray.size(); i++) {
                    AddNormalFileActivity.this.chosenFiles.add(tmpArray.get(i).getFilePath());
                }
                AddNormalFileActivity.this.selectAll.setText(this.mContext.getString(R.string.dis_select_all));
                AddNormalFileActivity.this.selectAll.setPadding(10, 0, 0, 0);
                AddNormalFileActivity.this.mAdapter.notifyDataSetChanged();
                return;
            }
            AddNormalFileActivity.this.mAdapter.setDisSelectAll();
            AddNormalFileActivity.this.chosenFiles.clear();
            AddNormalFileActivity.this.selectAll.setText(this.mContext.getString(R.string.select_all));
            AddNormalFileActivity.this.selectAll.setPadding(20, 0, 20, 0);
            AddNormalFileActivity.this.actionBar.setVisibility(8);
            AddNormalFileActivity.this.mAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showProgress() {
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setCanceledOnTouchOutside(false);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.lenovo.safebox.AddNormalFileActivity.3
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialog) {
                AddNormalFileActivity.this.progressDialog = null;
            }
        });
        this.progressDialog.setProgressStyle(0);
        this.progressDialog.setMessage(getString(R.string.exec_state));
        if (!this.tvPath.getText().subSequence(1, 7).equals("sdcard")) {
            Toast.makeText(getApplicationContext(), getString(R.string.pls_wait), 0).show();
        }
        this.progressDialog.show();
    }

    public CustomDialog createNospaceDialog() {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(this);
        mediaBuilder.setTitle(getString(R.string.exit_title));
        mediaBuilder.setMessage(getString(R.string.no_space_sdcard));
        mediaBuilder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.AddNormalFileActivity.4
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int which) {
                Log.i("onClick", "onClick");
                if (AddNormalFileActivity.this.chosenFiles.size() == 0) {
                    AddNormalFileActivity.this.actionBar.setVisibility(8);
                    AddNormalFileActivity.this.mAdapter.setDisSelectAll();
                }
            }
        });
        return mediaBuilder.create();
    }

    /* loaded from: classes.dex */
    private class ListItemListener implements AdapterView.OnItemClickListener {
        private Context mContext;

        ListItemListener(Context context) {
            this.mContext = context;
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (AddNormalFileActivity.this.mAdapter.getItem(position).getFilePath().equals("...")) {
                AddNormalFileActivity.this.updateParentUI();
            } else if (AddNormalFileActivity.this.mAdapter.isItemFolder(position)) {
                AddNormalFileActivity.this.updateChildUI(AddNormalFileActivity.this.mAdapter.getItem(position).getFilePath());
            } else {
                AddNormalFileActivity.this.mAdapter.getItem(position).setCheckHolder(AddNormalFileActivity.this.mAdapter.getItem(position).getCheckHolder());
                if (AddNormalFileActivity.this.mAdapter.getItem(position).getCheckHolder()) {
                    if (!AddNormalFileActivity.this.chosenFiles.contains(AddNormalFileActivity.this.mAdapter.getItem(position).getFilePath())) {
                        AddNormalFileActivity.this.chosenFiles.add(AddNormalFileActivity.this.mAdapter.getItem(position).getFilePath());
                    }
                    if (AddNormalFileActivity.this.chosenFiles.size() == AddNormalFileActivity.this.mAdapter.getRealCount()) {
                        AddNormalFileActivity.this.actionBar.setVisibility(0);
                        AddNormalFileActivity.this.selectAll.setText(R.string.dis_select_all);
                        AddNormalFileActivity.this.selectAll.setPadding(10, 0, 0, 0);
                    } else {
                        AddNormalFileActivity.this.actionBar.setVisibility(0);
                        AddNormalFileActivity.this.selectAll.setText(R.string.select_all);
                        AddNormalFileActivity.this.selectAll.setPadding(20, 0, 20, 0);
                    }
                } else {
                    AddNormalFileActivity.this.chosenFiles.remove(AddNormalFileActivity.this.mAdapter.getItem(position).getFilePath());
                    if (AddNormalFileActivity.this.chosenFiles.size() == 0) {
                        AddNormalFileActivity.this.actionBar.setVisibility(8);
                    } else {
                        AddNormalFileActivity.this.selectAll.setText(R.string.select_all);
                        AddNormalFileActivity.this.selectAll.setPadding(20, 0, 20, 0);
                    }
                }
            }
            AddNormalFileActivity.this.mAdapter.notifyDataSetChanged();
        }
    }

    public void resolveChosenFilesSelectButton(int position) {
        if (this.mAdapter.getItem(position).getCheckHolder()) {
            if (!this.chosenFiles.contains(this.mAdapter.getItem(position).getFilePath())) {
                this.chosenFiles.add(this.mAdapter.getItem(position).getFilePath());
            }
            if (this.chosenFiles.size() == this.mAdapter.getCount()) {
                this.actionBar.setVisibility(0);
                this.selectAll.setText(R.string.dis_select_all);
                this.selectAll.setPadding(10, 0, 0, 0);
                return;
            }
            this.actionBar.setVisibility(0);
            this.selectAll.setText(R.string.select_all);
            this.selectAll.setPadding(20, 0, 20, 0);
            return;
        }
        this.chosenFiles.remove(this.mAdapter.getItem(position).getFilePath());
        if (this.chosenFiles.size() == 0) {
            this.actionBar.setVisibility(8);
            return;
        }
        this.selectAll.setText(R.string.select_all);
        this.selectAll.setPadding(20, 0, 20, 0);
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
            this.tvPath.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddNormalFileActivity.6
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    AddNormalFileActivity.this.createStorageSelectDialog(AddNormalFileActivity.this.thisContext, AddNormalFileActivity.this.storageList).show();
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
        this.tvTitle.setText(R.string.normal_file);
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddNormalFileActivity.7
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddNormalFileActivity.this.finish();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateChildUI(String folderPath) {
        this.tvPath.setText(folderPath);
        this.mAdapter = new NormalFileAdapter(this, getMediaChildList(folderPath));
        this.chosenFiles.clear();
        this.actionBar.setVisibility(8);
        this.lstView.setAdapter((ListAdapter) this.mAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateParentUI() {
        this.chosenFiles.clear();
        this.actionBar.setVisibility(8);
        String pwd = (String) this.tvPath.getText();
        String parent = new File(pwd).getParent();
        if ((pwd + "/").equals(PrivateSpaceTools.sdDir) || pwd.equals(this.extraSd)) {
            Log.i("updateParentUI", "There is no file under /sdcard !!!");
            return;
        }
        this.tvPath.setText(parent);
        this.mAdapter = new NormalFileAdapter(this, getMediaChildList(parent + "/"));
        this.lstView.setAdapter((ListAdapter) this.mAdapter);
    }

    public ArrayList<FileInfo> getMediaChildList(String path) {
        ArrayList<String> excludeFolders;
        File[] files = new File(path).listFiles();
        ArrayList<FileInfo> returnList = new ArrayList<>();
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
                    if (!PrivateSpaceTools.isFolder(files[i].getAbsolutePath())) {
                        String tmpMime = PrivateSpaceTools.getMimeTypeOfFile(files[i].getAbsolutePath());
                        if (tmpMime != null) {
                            returnList.add(new FileInfo(files[i].toString(), tmpMime.split("/")[0]));
                        } else {
                            returnList.add(new FileInfo(files[i].toString(), "unknown"));
                        }
                    } else {
                        returnList.add(new FileInfo(files[i].toString(), null));
                    }
                }
            }
        } else {
            returnList.add(new FileInfo(path, null));
        }
        return returnList;
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
    public class RequestReceiver extends BroadcastReceiver {
        public RequestReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(NormalFileAdapter.REQUEST_UPDATE_ADAPTER)) {
                NormalFileAdapter unused = AddNormalFileActivity.this.mAdapter;
                AddNormalFileActivity.this.resolveChosenFilesSelectButton(intent.getIntExtra(NormalFileAdapter.POSITION, 0));
                AddNormalFileActivity.this.mAdapter.notifyDataSetChanged();
                if (AddNormalFileActivity.this.chosenFiles.size() == AddNormalFileActivity.this.mAdapter.getRealCount()) {
                    AddNormalFileActivity.this.selectAll.setText(R.string.dis_select_all);
                    AddNormalFileActivity.this.selectAll.setPadding(10, 0, 0, 0);
                }
            }
            if (intent.getAction().equals(SafeBoxApplication.NAC_DONE) && AddNormalFileActivity.this.progressDialog != null && AddNormalFileActivity.this.progressDialog.isShowing()) {
                AddNormalFileActivity.this.progressDialog.dismiss();
                AddNormalFileActivity.this.finish();
            }
        }
    }

    @Override // android.app.Activity
    protected void onStart() {
        super.onStart();
        registerReceiver(this.mReceiver, this.filter);
    }

    @Override // android.app.Activity
    protected void onRestart() {
        super.onRestart();
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        if (this.progressDialog == null) {
            PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        } else if (!this.progressDialog.isShowing()) {
            PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        }
    }

    @Override // android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // android.app.Activity
    protected void onStop() {
        this.mAdapter.setDisSelectAll();
        this.chosenFiles.clear();
        unregisterReceiver(this.mReceiver);
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.filter.addAction(SafeBoxApplication.NAC_DONE);
        }
        System.gc();
        super.onStop();
    }
}

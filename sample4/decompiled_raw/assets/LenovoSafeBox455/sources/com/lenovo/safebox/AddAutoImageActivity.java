package com.lenovo.safebox;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.lenovo.safebox.adapter.FolderAdapter;
import com.lenovo.safebox.engine.ImageEngine;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class AddAutoImageActivity extends Activity {
    public static FolderInfo passFolderInfo;
    ArrayList<FolderInfo> allFoldersInfo;
    private Uri allImagesUri;
    private Cursor cursor;
    private IntentFilter filter;
    private GridView folderGrid;
    private ImageEngine imageEngine;
    private ImageView ivBack;
    private Context mContext;
    public FolderAdapter mFolderAdapter;
    private boolean receiverInitData;
    private TextView tvInfoBar;
    private TextView tvTitle;
    private String TAG = "AddAutoImageActivity  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.AddAutoImageActivity.4
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AddAutoImageActivity.this.updateUI(false);
                    return;
                case 1:
                    AddAutoImageActivity.this.updateUI(true);
                    return;
                default:
                    return;
            }
        }
    };
    private final BroadcastReceiver receiver = new BroadcastReceiver() { // from class: com.lenovo.safebox.AddAutoImageActivity.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.MEDIA_SCANNER_FINISHED") && AddAutoImageActivity.this.receiverInitData) {
                AddAutoImageActivity.this.initData(true);
                AddAutoImageActivity.this.receiverInitData = false;
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.gui_imgfolder_activity);
        this.tvTitle = (TextView) findViewById(R.id.txt_title);
        this.tvTitle.setText(R.string.addPrivateImage);
        this.tvInfoBar = (TextView) findViewById(R.id.info_bar);
        this.folderGrid = (GridView) findViewById(R.id.gvImgFolder);
        this.allFoldersInfo = new ArrayList<>();
        this.mContext = this;
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddAutoImageActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddAutoImageActivity.this.finish();
            }
        });
        this.allImagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", this.allImagesUri));
        this.imageEngine = new ImageEngine(this.allImagesUri, this);
        this.folderGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lenovo.safebox.AddAutoImageActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddAutoImageActivity.passFolderInfo = AddAutoImageActivity.this.mFolderAdapter.getFolderInfo(position);
                Intent intent = new Intent(AddAutoImageActivity.this.getApplicationContext(), MediaBrowserActivity.class);
                intent.putExtra("image", true);
                intent.addFlags(8388608);
                AddAutoImageActivity.this.startActivity(intent);
            }
        });
    }

    public void updateUI(boolean showToast) {
        if (this.allFoldersInfo.size() == 0) {
            this.tvTitle.setText(R.string.add_pri_image_empty);
            if (showToast) {
                Toast.makeText(getApplicationContext(), getString(R.string.img_empty), 0).show();
            }
            this.tvInfoBar.setText(R.string.ImageFolderNull);
            this.mFolderAdapter = new FolderAdapter(this, this.allFoldersInfo);
            this.folderGrid.setAdapter((ListAdapter) this.mFolderAdapter);
            return;
        }
        this.tvTitle.setText(R.string.addPrivateImage);
        this.mFolderAdapter = new FolderAdapter(this, this.allFoldersInfo);
        this.folderGrid.setAdapter((ListAdapter) this.mFolderAdapter);
        this.tvInfoBar.setText(R.string.ImageFolderPick);
    }

    public void initData(final boolean showToast) {
        new Thread() { // from class: com.lenovo.safebox.AddAutoImageActivity.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                AddAutoImageActivity.this.imageEngine = new ImageEngine(AddAutoImageActivity.this.allImagesUri, AddAutoImageActivity.this.mContext);
                try {
                    AddAutoImageActivity.this.allFoldersInfo = AddAutoImageActivity.this.imageEngine.getAllFolders();
                } catch (Exception e) {
                    System.exit(0);
                }
                if (AddAutoImageActivity.this.allFoldersInfo.isEmpty() || AddAutoImageActivity.this.allFoldersInfo != null) {
                }
                if (showToast) {
                    Message msg = new Message();
                    msg.what = 1;
                    AddAutoImageActivity.this.handler.sendMessage(msg);
                    return;
                }
                Message msg2 = new Message();
                msg2.what = 0;
                AddAutoImageActivity.this.handler.sendMessage(msg2);
            }
        }.start();
    }

    private void scanSD() {
        sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath())));
    }

    @Override // android.app.Activity
    protected void onResume() {
        super.onResume();
        PrivateSpaceTools.socketClient(PrivateSpaceTools.hideFolder);
        this.receiverInitData = true;
        this.filter = new IntentFilter();
        this.filter.addAction("android.intent.action.MEDIA_SCANNER_FINISHED");
        this.filter.addDataScheme("file");
        registerReceiver(this.receiver, this.filter);
        scanSD();
        initData(false);
        Toast.makeText(this, getString(R.string.wait_img_folder), 0).show();
    }

    @Override // android.app.Activity
    protected void onStop() {
        unregisterReceiver(this.receiver);
        System.gc();
        super.onStop();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.mFolderAdapter != null) {
            this.mFolderAdapter.recycleBitmap();
        }
        super.onDestroy();
    }
}

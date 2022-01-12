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
public class AddAutoVideoActivity extends Activity {
    public static FolderInfo passFolderInfo;
    private ArrayList<FolderInfo> allFoldersInfo;
    private Uri allVideosUri;
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
    private String TAG = "AddAutoVideoActivity  vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv";
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.AddAutoVideoActivity.4
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AddAutoVideoActivity.this.updateUI(false);
                    return;
                case 1:
                    AddAutoVideoActivity.this.updateUI(true);
                    return;
                default:
                    return;
            }
        }
    };
    private final BroadcastReceiver receiver = new BroadcastReceiver() { // from class: com.lenovo.safebox.AddAutoVideoActivity.5
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.MEDIA_SCANNER_FINISHED") && AddAutoVideoActivity.this.receiverInitData) {
                AddAutoVideoActivity.this.initData(true);
                AddAutoVideoActivity.this.receiverInitData = false;
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.gui_imgfolder_activity);
        this.tvTitle = (TextView) findViewById(R.id.txt_title);
        this.tvTitle.setText(R.string.addPrivateVideo);
        this.tvInfoBar = (TextView) findViewById(R.id.info_bar);
        this.folderGrid = (GridView) findViewById(R.id.gvImgFolder);
        this.allFoldersInfo = new ArrayList<>();
        this.ivBack = (ImageView) findViewById(R.id.title_back);
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.AddAutoVideoActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                AddAutoVideoActivity.this.finish();
            }
        });
        this.mContext = this;
        this.allVideosUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", this.allVideosUri));
        this.imageEngine = new ImageEngine(this.allVideosUri, this);
        this.folderGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lenovo.safebox.AddAutoVideoActivity.2
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddAutoVideoActivity.passFolderInfo = AddAutoVideoActivity.this.mFolderAdapter.getFolderInfo(position);
                Intent intent = new Intent(AddAutoVideoActivity.this.getApplicationContext(), MediaBrowserActivity.class);
                intent.putExtra("image", false);
                intent.addFlags(8388608);
                AddAutoVideoActivity.this.startActivity(intent);
            }
        });
    }

    public void initData(final boolean showToast) {
        new Thread() { // from class: com.lenovo.safebox.AddAutoVideoActivity.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                AddAutoVideoActivity.this.imageEngine = new ImageEngine(AddAutoVideoActivity.this.allVideosUri, AddAutoVideoActivity.this.mContext);
                try {
                    AddAutoVideoActivity.this.allFoldersInfo = AddAutoVideoActivity.this.imageEngine.getAllFolders();
                } catch (NullPointerException e) {
                    System.exit(0);
                }
                if (AddAutoVideoActivity.this.allFoldersInfo.isEmpty() || AddAutoVideoActivity.this.allFoldersInfo != null) {
                }
                if (showToast) {
                    Message msg = new Message();
                    msg.what = 1;
                    AddAutoVideoActivity.this.handler.sendMessage(msg);
                    return;
                }
                Message msg2 = new Message();
                msg2.what = 0;
                AddAutoVideoActivity.this.handler.sendMessage(msg2);
            }
        }.start();
    }

    public void updateUI(boolean showToast) {
        if (this.allFoldersInfo.size() == 0) {
            this.tvTitle.setText(R.string.add_pri_video_empty);
            if (showToast) {
                Toast.makeText(getApplicationContext(), getString(R.string.video_empty), 0).show();
            }
            this.tvInfoBar.setText(R.string.VideoFolderNull);
            this.mFolderAdapter = new FolderAdapter(this, this.allFoldersInfo);
            this.folderGrid.setAdapter((ListAdapter) this.mFolderAdapter);
            return;
        }
        this.tvTitle.setText(R.string.addPrivateVideo);
        this.mFolderAdapter = new FolderAdapter(this, this.allFoldersInfo);
        this.folderGrid.setAdapter((ListAdapter) this.mFolderAdapter);
        this.tvInfoBar.setText(R.string.VideoFolderPick);
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
        Toast.makeText(this, getString(R.string.wait_video_folder), 0).show();
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (this.mFolderAdapter != null) {
            this.mFolderAdapter.recycleBitmap();
        }
        this.allFoldersInfo = null;
        this.mFolderAdapter = null;
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onStop() {
        unregisterReceiver(this.receiver);
        System.gc();
        super.onStop();
    }
}

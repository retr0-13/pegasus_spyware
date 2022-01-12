package com.lenovo.safebox.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.lenovo.safebox.FileInfo;
import com.lenovo.safebox.PrivateSpaceTools;
import com.lenovo.safebox.R;
import java.io.File;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class NormalFileAdapter extends BaseAdapter {
    public static String POSITION = "thisposition";
    public static final String REQUEST_UPDATE_ADAPTER = "com.android.broadcast.REQUEST_UPDATE_ADAPTER";
    private String TAG = "NormalFileAdapter  aaaaaaaaaaaaaaaaaaaa";
    private ArrayList<String> extraSdFilter = initFilter();
    private Context mContext;
    private ArrayList<FileInfo> mFileList;

    public NormalFileAdapter(Context context, ArrayList<FileInfo> lst) {
        this.mFileList = lst;
        this.mContext = context;
    }

    private ArrayList<String> initFilter() {
        ArrayList<String> extraSdList = new ArrayList<>();
        extraSdList.add("/sdcard/extra_sd");
        extraSdList.add("/sdcard/external_sd");
        extraSdList.add("/sdcard/usbStorage");
        return extraSdList;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mFileList.size();
    }

    public int getRealCount() {
        int result = 0;
        for (int i = 0; i < this.mFileList.size(); i++) {
            if (!this.extraSdFilter.contains(this.mFileList.get(i).getFilePath()) && !this.mFileList.get(i).getFilePath().equals("...")) {
                result++;
            }
        }
        return result;
    }

    @Override // android.widget.Adapter
    public FileInfo getItem(int position) {
        return this.mFileList.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return (long) position;
    }

    public ArrayList<FileInfo> setSelectAll() {
        ArrayList<FileInfo> resultList = new ArrayList<>();
        for (int i = 0; i < this.mFileList.size(); i++) {
            if (!this.extraSdFilter.contains(this.mFileList.get(i).getFilePath()) && !this.mFileList.get(i).equals("...")) {
                this.mFileList.get(i).setCheckHolder(false);
                resultList.add(this.mFileList.get(i));
            }
        }
        return resultList;
    }

    public void setDisSelectAll() {
        for (int i = 0; i < this.mFileList.size(); i++) {
            this.mFileList.get(i).setCheckHolder(true);
        }
    }

    public boolean isItemFolder(int position) {
        if (new File(this.mFileList.get(position).getFilePath()).isDirectory()) {
            return true;
        }
        return false;
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        final NormalFileViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.mContext, R.layout.gui_listview_temp_list, null);
            convertView.setClickable(false);
            convertView.setFocusable(false);
            holder = new NormalFileViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_Icon);
            holder.fileName = (TextView) convertView.findViewById(R.id.tv_FileName);
            holder.property = (TextView) convertView.findViewById(R.id.tv_FileSize);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_file);
            convertView.setTag(holder);
        } else {
            holder = (NormalFileViewHolder) convertView.getTag();
        }
        holder.checkBox.setFocusable(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.adapter.NormalFileAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                Log.i(NormalFileAdapter.this.TAG, "Checkbox Listener heard!!");
                if (((FileInfo) NormalFileAdapter.this.mFileList.get(position)).getMime() == null) {
                    ((FileInfo) NormalFileAdapter.this.mFileList.get(position)).setCheckHolder(((FileInfo) NormalFileAdapter.this.mFileList.get(position)).getCheckHolder());
                    Intent intent = new Intent(NormalFileAdapter.REQUEST_UPDATE_ADAPTER);
                    intent.putExtra(NormalFileAdapter.POSITION, position);
                    NormalFileAdapter.this.mContext.sendBroadcast(intent);
                    return;
                }
                holder.checkBox.setChecked(false);
            }
        });
        if (this.mFileList.get(position).getFilePath().equals("...")) {
            holder.imageView.setImageResource(R.drawable.gui_return);
            holder.fileName.setText(this.mFileList.get(position).getFilePath());
            holder.property.setText(R.string.back_last_level);
            holder.checkBox.setVisibility(8);
        } else {
            holder.fileName.setText(PrivateSpaceTools.getFileName(this.mFileList.get(position).getFilePath().toString()));
            if (PrivateSpaceTools.isFolder(this.mFileList.get(position).getFilePath())) {
                holder.checkBox.setClickable(true);
                holder.imageView.setImageResource(R.drawable.gui_folder);
                holder.property.setText(R.string.isfolder);
                if (this.extraSdFilter.contains(this.mFileList.get(position).getFilePath())) {
                    holder.checkBox.setVisibility(4);
                } else {
                    holder.checkBox.setVisibility(0);
                }
                holder.checkBox.setChecked(this.mFileList.get(position).getCheckHolder());
            } else {
                holder.checkBox.setClickable(false);
                if (this.mFileList.get(position).getMime().equals("image")) {
                    holder.imageView.setImageResource(R.drawable.gui_img);
                    holder.property.setText(this.mContext.getString(R.string.size) + PrivateSpaceTools.getFileSize(this.mFileList.get(position).getFilePath()));
                    holder.checkBox.setVisibility(0);
                    holder.checkBox.setChecked(this.mFileList.get(position).getCheckHolder());
                } else if (this.mFileList.get(position).getMime().equals("video")) {
                    holder.imageView.setImageResource(R.drawable.gui_video);
                    holder.property.setText(this.mContext.getString(R.string.size) + PrivateSpaceTools.getFileSize(this.mFileList.get(position).getFilePath()));
                    holder.checkBox.setVisibility(0);
                    holder.checkBox.setChecked(this.mFileList.get(position).getCheckHolder());
                } else {
                    holder.imageView.setImageResource(R.drawable.gui_file);
                }
                holder.property.setText(this.mContext.getString(R.string.size) + PrivateSpaceTools.getFileSize(this.mFileList.get(position).getFilePath()));
                holder.checkBox.setVisibility(0);
                holder.checkBox.setChecked(this.mFileList.get(position).getCheckHolder());
            }
        }
        convertView.setTag(holder);
        return convertView;
    }

    /* loaded from: classes.dex */
    public class NormalFileViewHolder {
        public CheckBox checkBox;
        public TextView fileName;
        public ImageView imageView;
        public TextView property;

        public NormalFileViewHolder() {
        }
    }
}

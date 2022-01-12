package com.lenovo.safebox.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.lenovo.safebox.FileInfo;
import com.lenovo.safebox.PrivateSpaceTools;
import com.lenovo.safebox.R;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class PriFileAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<FileInfo> mFileList;

    public PriFileAdapter(Context context, ArrayList<FileInfo> lst) {
        this.mContext = context;
        this.mFileList = lst;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mFileList.size();
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
        for (int i = 0; i < this.mFileList.size(); i++) {
            this.mFileList.get(i).setCheckHolder(false);
        }
        return this.mFileList;
    }

    public void setDisSelectAll() {
        for (int i = 0; i < this.mFileList.size(); i++) {
            this.mFileList.get(i).setCheckHolder(true);
        }
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2 = View.inflate(this.mContext, R.layout.gui_listview_temp_list, null);
        convertView2.setClickable(false);
        convertView2.setFocusable(false);
        FileViewHolder holder = new FileViewHolder();
        holder.imageView = (ImageView) convertView2.findViewById(R.id.iv_Icon);
        holder.fileName = (TextView) convertView2.findViewById(R.id.tv_FileName);
        holder.property = (TextView) convertView2.findViewById(R.id.tv_FileSize);
        holder.checkBox = (CheckBox) convertView2.findViewById(R.id.cb_file);
        holder.checkBox.setFocusable(false);
        holder.checkBox.setClickable(false);
        if (PrivateSpaceTools.isFolder(this.mFileList.get(position).getFilePath())) {
            holder.imageView.setImageResource(R.drawable.gui_folder_locked);
            holder.property.setText(R.string.isfolder);
        } else {
            holder.imageView.setImageResource(R.drawable.gui_file_locked);
            holder.property.setText(this.mContext.getString(R.string.size) + PrivateSpaceTools.getFileSize(this.mFileList.get(position).getFilePath()));
        }
        holder.fileName.setText(PrivateSpaceTools.getFileName(this.mFileList.get(position).getFilePath().toString()));
        holder.checkBox.setVisibility(0);
        holder.checkBox.setChecked(this.mFileList.get(position).getCheckHolder());
        return convertView2;
    }

    /* loaded from: classes.dex */
    public class FileViewHolder {
        public CheckBox checkBox;
        public TextView fileName;
        public ImageView imageView;
        public TextView property;

        public FileViewHolder() {
        }
    }
}

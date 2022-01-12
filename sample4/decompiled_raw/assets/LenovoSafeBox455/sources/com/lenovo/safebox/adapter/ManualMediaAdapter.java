package com.lenovo.safebox.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.lenovo.safebox.AddManualMediaActivity;
import com.lenovo.safebox.FileInfo;
import com.lenovo.safebox.PrivateSpaceTools;
import com.lenovo.safebox.R;
import java.io.File;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class ManualMediaAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FileInfo> mFileList;
    private String TAG = "ManualMediaAdapter  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    public int checkboxCount = 0;
    private ArrayList<FileInfo> mSelectAllList = new ArrayList<>();

    public ManualMediaAdapter(Context context, ArrayList<FileInfo> fileList) {
        this.mContext = context;
        this.mFileList = fileList;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mFileList.size();
    }

    public ArrayList<FileInfo> getAllData() {
        return this.mFileList;
    }

    public int setSelectAll() {
        this.checkboxCount = 0;
        for (int i = 0; i < this.mFileList.size(); i++) {
            if (this.mFileList.get(i).getMime() != null) {
                this.mFileList.get(i).setCheckHolder(false);
                this.mSelectAllList.add(this.mFileList.get(i));
                this.checkboxCount++;
            }
        }
        return this.checkboxCount;
    }

    public void setDisSelectAll() {
        this.checkboxCount = 0;
        this.mSelectAllList.clear();
        for (int i = 0; i < this.mFileList.size(); i++) {
            if (this.mFileList.get(i).getMime() != null) {
                this.mFileList.get(i).setCheckHolder(true);
            }
        }
    }

    @Override // android.widget.Adapter
    public FileInfo getItem(int position) {
        return this.mFileList.get(position);
    }

    public ArrayList<FileInfo> getSelectAllList() {
        return this.mSelectAllList;
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return (long) position;
    }

    public boolean isItemFolder(int position) {
        if (new File(this.mFileList.get(position).getFilePath()).isDirectory()) {
            return true;
        }
        return false;
    }

    public int getCheckboxCount() {
        return this.checkboxCount;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        MediaFileViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.mContext, R.layout.gui_listview_temp_list, null);
            convertView.setClickable(false);
            convertView.setFocusable(false);
            holder = new MediaFileViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_Icon);
            holder.fileName = (TextView) convertView.findViewById(R.id.tv_FileName);
            holder.property = (TextView) convertView.findViewById(R.id.tv_FileSize);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_file);
            convertView.setTag(holder);
        } else {
            holder = (MediaFileViewHolder) convertView.getTag();
        }
        holder.checkBox.setFocusable(false);
        holder.checkBox.setClickable(false);
        if (this.mFileList.get(position).getFilePath().equals("...")) {
            holder.imageView.setImageResource(R.drawable.back_icon);
            holder.fileName.setText(this.mFileList.get(position).getFilePath());
            holder.property.setText(R.string.back_last_level);
            holder.checkBox.setVisibility(8);
        } else {
            holder.fileName.setText(PrivateSpaceTools.getFileName(this.mFileList.get(position).getFilePath().toString()));
            if (PrivateSpaceTools.isFolder(this.mFileList.get(position).getFilePath())) {
                holder.imageView.setImageResource(R.drawable.gui_folder);
                holder.property.setText(R.string.isfolder);
                holder.checkBox.setVisibility(8);
            } else if (AddManualMediaActivity.mimeType == "image") {
                holder.imageView.setImageResource(R.drawable.gui_img);
                holder.property.setText(this.mContext.getString(R.string.size) + PrivateSpaceTools.getFileSize(this.mFileList.get(position).getFilePath()));
                holder.checkBox.setVisibility(0);
                holder.checkBox.setChecked(this.mFileList.get(position).getCheckHolder());
            } else if (AddManualMediaActivity.mimeType == "video") {
                holder.imageView.setImageResource(R.drawable.gui_video);
                holder.property.setText(this.mContext.getString(R.string.size) + PrivateSpaceTools.getFileSize(this.mFileList.get(position).getFilePath()));
                holder.checkBox.setVisibility(0);
                holder.checkBox.setChecked(this.mFileList.get(position).getCheckHolder());
            } else {
                holder.imageView.setImageResource(R.drawable.gui_folder);
                holder.property.setText(R.string.isfolder);
                holder.checkBox.setVisibility(8);
            }
        }
        convertView.setTag(holder);
        return convertView;
    }

    /* loaded from: classes.dex */
    public class MediaFileViewHolder {
        public CheckBox checkBox;
        public TextView fileName;
        public ImageView imageView;
        public TextView property;

        public MediaFileViewHolder() {
        }
    }
}

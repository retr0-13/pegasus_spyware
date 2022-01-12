package com.lenovo.safebox.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.lenovo.safebox.AsyncImageLoader;
import com.lenovo.safebox.MediaInfo;
import com.lenovo.safebox.PrivateSpaceHelper;
import com.lenovo.safebox.R;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class PriMediaAdapter extends BaseAdapter {
    private AsyncImageLoader asyncImageLoader;
    SQLiteDatabase db;
    private Context mContext;
    private int mFlag;
    private GridView mGridView;
    PrivateSpaceHelper mHelper;
    private ArrayList<MediaInfo> mMediaList;
    private String TAG = "PriMediaAdapter  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private int mCheckboxStatus = 8;
    private ArrayList<Bitmap> dataCache = new ArrayList<>();

    public PriMediaAdapter(Context context, ArrayList<MediaInfo> lst, int flag, GridView listView) {
        this.mContext = context;
        this.mMediaList = lst;
        this.mFlag = flag;
        this.asyncImageLoader = new AsyncImageLoader(this.mFlag, this.mContext);
        this.mGridView = listView;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mMediaList.size();
    }

    @Override // android.widget.Adapter
    public MediaInfo getItem(int position) {
        return this.mMediaList.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return (long) position;
    }

    public void setCheckboxStatus(int status) {
        this.mCheckboxStatus = status;
    }

    public ArrayList<MediaInfo> setSelectAll() {
        for (int i = 0; i < this.mMediaList.size(); i++) {
            this.mMediaList.get(i).setCheckHolder(false);
        }
        return this.mMediaList;
    }

    public void setDisSelectAll() {
        for (int i = 0; i < this.mMediaList.size(); i++) {
            this.mMediaList.get(i).setCheckHolder(true);
        }
    }

    private void resolveBitmap() {
        for (int i = 0; i < this.mMediaList.size(); i++) {
            this.dataCache.add(i, null);
        }
    }

    public void recycleBitmap() {
        for (int i = 0; i < this.dataCache.size(); i++) {
            if (this.dataCache.get(i) != null && !this.dataCache.get(i).isRecycled()) {
                this.dataCache.get(i).recycle();
            }
        }
        this.dataCache.clear();
        resolveBitmap();
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        MediaViewHolder holder = new MediaViewHolder();
        if (convertView == null) {
            convertView = View.inflate(this.mContext, R.layout.gui_priimage_item, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_image_item);
            holder.textView = (TextView) convertView.findViewById(R.id.priimage_item_text);
            convertView.setTag(holder);
        } else {
            holder = (MediaViewHolder) convertView.getTag();
        }
        ImageView imageView = holder.imageView;
        holder.imageView.setTag(this.mMediaList.get(position).getFilePath());
        Bitmap cachedImage = this.asyncImageLoader.loadBitmap(this.mMediaList.get(position).getFilePath(), this.mMediaList.get(position).getFileName(), new AsyncImageLoader.ImageCallback() { // from class: com.lenovo.safebox.adapter.PriMediaAdapter.1
            @Override // com.lenovo.safebox.AsyncImageLoader.ImageCallback
            public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) PriMediaAdapter.this.mGridView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageBitmap(imageDrawable);
                }
            }
        });
        if (cachedImage != null) {
            holder.imageView.setImageBitmap(cachedImage);
        } else if (this.mFlag == 0) {
            holder.imageView.setImageResource(R.drawable.gui_icon_img);
        } else {
            holder.imageView.setImageResource(R.drawable.gui_icon_video);
        }
        holder.checkBox.setClickable(false);
        holder.checkBox.setFocusable(false);
        holder.checkBox.setVisibility(this.mCheckboxStatus);
        holder.checkBox.setChecked(this.mMediaList.get(position).getCheckHolder());
        holder.textView.setVisibility(0);
        holder.textView.setText(this.mMediaList.get(position).getFileName());
        return convertView;
    }

    /* loaded from: classes.dex */
    public class MediaViewHolder {
        public CheckBox checkBox;
        public ImageView imageView;
        public TextView textView;

        public MediaViewHolder() {
        }
    }
}

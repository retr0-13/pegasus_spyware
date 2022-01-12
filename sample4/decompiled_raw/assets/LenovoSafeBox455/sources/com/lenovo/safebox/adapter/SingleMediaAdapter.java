package com.lenovo.safebox.adapter;

import android.content.Context;
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
import com.lenovo.safebox.R;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class SingleMediaAdapter extends BaseAdapter {
    private ArrayList<MediaInfo> imgList;
    private boolean isImage;
    private Context mContext;
    private GridView mGridView;
    public MediaViewHolder mMediaViewHolder;
    private String TAG = "SingleMediaAdapter  aaaaaaaaaaaaaaaaaa";
    private ArrayList checkedList = new ArrayList();
    private ArrayList<Bitmap> dataCache = new ArrayList<>();
    private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

    public SingleMediaAdapter(Context context, ArrayList<MediaInfo> imgList, boolean judgeImage, GridView listView) {
        this.mContext = context;
        this.imgList = imgList;
        this.isImage = judgeImage;
        this.mGridView = listView;
        resolveBitmap();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.imgList.size();
    }

    @Override // android.widget.Adapter
    public MediaInfo getItem(int position) {
        return this.imgList.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return (long) position;
    }

    public Bitmap getPositionBitmap(String filePath) {
        int tmpPosition = 0;
        int i = 0;
        while (true) {
            if (i >= this.imgList.size()) {
                break;
            } else if (this.imgList.get(i).getFilePath() == filePath) {
                tmpPosition = i;
                break;
            } else {
                i++;
            }
        }
        return this.dataCache.get(tmpPosition);
    }

    public ArrayList<MediaInfo> setSelectAll() {
        for (int i = 0; i < this.imgList.size(); i++) {
            this.imgList.get(i).setCheckHolder(false);
        }
        return this.imgList;
    }

    public void setDisSelectAll() {
        for (int i = 0; i < this.imgList.size(); i++) {
            this.imgList.get(i).setCheckHolder(true);
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

    void resolveBitmap() {
        for (int i = 0; i < this.imgList.size(); i++) {
            this.dataCache.add(i, null);
        }
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        MediaViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(this.mContext, R.layout.gui_priimage_item, null);
            holder = new MediaViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_image_item);
            convertView.setTag(holder);
        } else {
            holder = (MediaViewHolder) convertView.getTag();
        }
        ImageView imageView = holder.imageView;
        holder.imageView.setTag(this.imgList.get(position).getThumbImage());
        Bitmap cachedImage = this.asyncImageLoader.loadBitmap(this.imgList.get(position).getThumbImage(), this.imgList.get(position).getFileName(), new AsyncImageLoader.ImageCallback() { // from class: com.lenovo.safebox.adapter.SingleMediaAdapter.1
            @Override // com.lenovo.safebox.AsyncImageLoader.ImageCallback
            public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
                ImageView imageViewByTag = (ImageView) SingleMediaAdapter.this.mGridView.findViewWithTag(imageUrl);
                if (imageViewByTag != null) {
                    imageViewByTag.setImageBitmap(imageDrawable);
                }
            }
        });
        if (cachedImage != null) {
            holder.imageView.setImageBitmap(cachedImage);
            this.dataCache.add(position, cachedImage);
        } else if (this.isImage) {
            holder.imageView.setImageResource(R.drawable.gui_icon_img);
        } else {
            holder.imageView.setImageResource(R.drawable.gui_icon_video);
        }
        holder.textView = (TextView) convertView.findViewById(R.id.priimage_item_text);
        holder.checkBox.setClickable(false);
        holder.checkBox.setFocusable(false);
        holder.checkBox.setChecked(this.imgList.get(position).getCheckHolder());
        holder.textView.setVisibility(0);
        holder.textView.setText(this.imgList.get(position).getFileName());
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

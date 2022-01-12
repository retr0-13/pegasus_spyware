package com.lenovo.safebox.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lenovo.safebox.AsyncImageLoader;
import com.lenovo.safebox.FolderInfo;
import com.lenovo.safebox.R;
import java.util.ArrayList;
import java.util.Locale;
/* loaded from: classes.dex */
public class FolderAdapter extends BaseAdapter {
    private ArrayList<FolderInfo> imgFolders;
    private Context mContext;
    private FolderViewHolder mFolderViewHolder;
    private String TAG = "FolderAdapter  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private ArrayList<Bitmap> dataCache = new ArrayList<>();
    private AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

    public FolderAdapter(Context context, ArrayList dirList) {
        this.mContext = context;
        this.imgFolders = dirList;
        resolveBitmap();
    }

    public void resolveBitmap() {
        for (int i = 0; i < this.imgFolders.size(); i++) {
            this.dataCache.add(i, null);
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.imgFolders.size();
    }

    @Override // android.widget.Adapter
    public FolderInfo getItem(int position) {
        return this.imgFolders.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return 0;
    }

    public FolderInfo getFolderInfo(int position) {
        return this.imgFolders.get(position);
    }

    public String getFolderPath(int position) {
        return this.imgFolders.get(position).getFolderPath();
    }

    public void recycleBitmap() {
        if (this.dataCache != null && this.dataCache.size() > 0) {
            for (int i = 0; i < this.dataCache.size(); i++) {
                if (this.dataCache.get(i) == null || this.dataCache.get(i).isRecycled()) {
                    Log.i(this.TAG, i + " IS NULL");
                } else {
                    this.dataCache.get(i).recycle();
                    Log.i(this.TAG, i + "");
                    Log.i(this.TAG, i + " NOT NULL");
                }
            }
            this.dataCache.clear();
            resolveBitmap();
        }
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View convertView2 = View.inflate(this.mContext, R.layout.gui_priimage_folder, null);
        FolderInfo mFolderInfo = this.imgFolders.get(position);
        this.mFolderViewHolder = new FolderViewHolder();
        this.mFolderViewHolder.imageView = (ImageView) convertView2.findViewById(R.id.ivPrv);
        this.mFolderViewHolder.folderName = (TextView) convertView2.findViewById(R.id.txtFolder);
        this.mFolderViewHolder.folderName.setText(mFolderInfo.getFolderName() + "(" + mFolderInfo.getFilesNum() + ")");
        final ImageView image = this.mFolderViewHolder.imageView;
        image.setTag(this.imgFolders.get(position).getSnapShot());
        if (this.dataCache.get(position) != null) {
            image.setImageBitmap(this.dataCache.get(position));
        } else {
            Bitmap cachedImage = this.asyncImageLoader.loadBitmap(this.imgFolders.get(position).getSnapShot(), this.imgFolders.get(position).getFolderName(), new AsyncImageLoader.ImageCallback() { // from class: com.lenovo.safebox.adapter.FolderAdapter.1
                @Override // com.lenovo.safebox.AsyncImageLoader.ImageCallback
                public void imageLoaded(Bitmap imageDrawable, String imageUrl) {
                    image.setImageBitmap(imageDrawable);
                }
            });
            this.dataCache.set(position, cachedImage);
            if (cachedImage != null) {
                image.setImageBitmap(cachedImage);
            } else if (Locale.getDefault().getLanguage().equals("en")) {
                image.setImageResource(R.drawable.gui_load_folder_en);
            } else {
                image.setImageResource(R.drawable.gui_load_folder);
            }
        }
        return convertView2;
    }

    /* loaded from: classes.dex */
    class FolderViewHolder {
        TextView folderName;
        ImageView imageView;

        FolderViewHolder() {
        }
    }
}

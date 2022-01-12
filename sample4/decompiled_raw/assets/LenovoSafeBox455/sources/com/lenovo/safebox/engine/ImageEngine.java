package com.lenovo.safebox.engine;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import com.lenovo.safebox.FolderInfo;
import com.lenovo.safebox.MediaInfo;
import com.lenovo.safebox.PrivateSpaceTools;
import java.io.File;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class ImageEngine {
    private final String DATA;
    private final String IMAGE_ID;
    private String TAG;
    public ArrayList<FolderInfo> allFolders;
    public ArrayList<MediaInfo> allImages;
    private Cursor c;
    private Cursor cursor;
    private Uri imageUri;
    private boolean k900Resolve;
    private Context mContext;
    public String mFolder;
    private ArrayList<String> mPriMediaList;
    private Uri mUri;
    private int mediaType;
    private Uri videoUri;

    public ImageEngine(Uri uri, Context context) {
        this.TAG = "ImageEngine aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        this.DATA = "_data";
        this.IMAGE_ID = "_id";
        this.imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        this.videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        this.k900Resolve = false;
        this.mUri = uri;
        this.mContext = context;
    }

    public ImageEngine(Context context, String parent, int mediaType) {
        this.TAG = "ImageEngine aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        this.DATA = "_data";
        this.IMAGE_ID = "_id";
        this.imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        this.videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        this.k900Resolve = false;
        this.mContext = context;
        this.mFolder = parent;
        this.mediaType = mediaType;
    }

    public ImageEngine(Context context, File[] files, Uri uri) {
        this.TAG = "ImageEngine aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        this.DATA = "_data";
        this.IMAGE_ID = "_id";
        this.imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        this.videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        this.k900Resolve = false;
        this.mUri = uri;
        this.mContext = context;
        this.mPriMediaList = new ArrayList<>();
        for (File file : files) {
            this.mPriMediaList.add(file.getAbsolutePath());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList getAllImages() {
        this.k900Resolve = Build.MODEL.contains("K900");
        this.allImages = new ArrayList<>();
        this.cursor = this.mContext.getContentResolver().query(this.mUri, null, null, null, null);
        if (this.cursor.getColumnCount() > 0) {
            this.cursor.moveToFirst();
            this.cursor.moveToFirst();
            while (!this.cursor.isAfterLast()) {
                if (!this.mUri.equals(this.imageUri)) {
                    String filePath = this.cursor.getString(this.cursor.getColumnIndex("_data"));
                    if (PrivateSpaceTools.isVideo(filePath)) {
                        Log.i(this.TAG, " filePath  :" + filePath);
                        if (filePath != null) {
                            this.allImages.add(new MediaInfo(filePath, filePath));
                        }
                    }
                } else if (PrivateSpaceTools.isImage(this.cursor.getString(this.cursor.getColumnIndex("_data")))) {
                    String filePath2 = this.cursor.getString(this.cursor.getColumnIndex("_data"));
                    Log.i(this.TAG, " filePath  :" + filePath2);
                    if (filePath2 != null) {
                        this.allImages.add(new MediaInfo(filePath2, filePath2));
                    }
                }
                this.cursor.moveToNext();
            }
            this.cursor.close();
            return this.allImages;
        }
        this.cursor.close();
        return null;
    }

    public void resolveThumbnail() {
        new Thread() { // from class: com.lenovo.safebox.engine.ImageEngine.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                ImageEngine.this.cursor = ImageEngine.this.mContext.getContentResolver().query(ImageEngine.this.mUri, null, null, null, null);
                if (ImageEngine.this.cursor.getColumnCount() > 0) {
                    ImageEngine.this.cursor.moveToFirst();
                    if (ImageEngine.this.allImages.size() == 0) {
                        ImageEngine.this.getAllImages();
                    }
                    ImageEngine.this.cursor.moveToFirst();
                    while (!ImageEngine.this.cursor.isAfterLast()) {
                        for (int i = 0; i < ImageEngine.this.allImages.size(); i++) {
                            if (ImageEngine.this.allImages.get(i).getFilePath().equals(ImageEngine.this.cursor.getString(ImageEngine.this.cursor.getColumnIndex("_data")))) {
                                String thumbPath = null;
                                if (ImageEngine.this.mUri.equals(ImageEngine.this.imageUri)) {
                                    thumbPath = ImageEngine.this.getImageThumbnail(ImageEngine.this.cursor.getLong(ImageEngine.this.cursor.getColumnIndex("_id")));
                                } else if (ImageEngine.this.mUri.equals(ImageEngine.this.videoUri)) {
                                    thumbPath = ImageEngine.this.getVideoThumbnail(ImageEngine.this.cursor.getLong(ImageEngine.this.cursor.getColumnIndex("_id")));
                                }
                                ImageEngine.this.allImages.get(i).setThumbImage(thumbPath);
                            }
                        }
                        ImageEngine.this.cursor.moveToNext();
                    }
                }
                ImageEngine.this.cursor.close();
            }
        }.start();
    }

    public ArrayList<MediaInfo> getAllPriMedia() {
        MediaInfo mediaInfo;
        ArrayList<MediaInfo> thisResult = new ArrayList<>();
        this.cursor = this.mContext.getContentResolver().query(Uri.parse("file://" + this.mFolder), null, null, null, null);
        if (this.cursor.getColumnCount() > 0) {
            this.cursor.moveToFirst();
            this.cursor.moveToFirst();
            while (!this.cursor.isAfterLast()) {
                String thumbPath = null;
                if (this.mediaType == 0) {
                    thumbPath = getImageThumbnail(this.cursor.getLong(this.cursor.getColumnIndex("_id")));
                } else if (this.mediaType == 1) {
                    thumbPath = getVideoThumbnail(this.cursor.getLong(this.cursor.getColumnIndex("_id")));
                }
                if (thumbPath == null || thumbPath.isEmpty()) {
                    mediaInfo = new MediaInfo(this.cursor.getString(this.cursor.getColumnIndex("_data")), this.cursor.getString(this.cursor.getColumnIndex("_data")));
                } else {
                    mediaInfo = new MediaInfo(this.cursor.getString(this.cursor.getColumnIndex("_data")), thumbPath);
                }
                thisResult.add(mediaInfo);
                this.cursor.moveToNext();
            }
            this.cursor.close();
            return thisResult;
        }
        this.cursor.close();
        return null;
    }

    public ArrayList<MediaInfo> getAllPriMedia1() {
        MediaInfo mediaInfo;
        MediaInfo mediaInfo2;
        this.mContext.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + this.mFolder)));
        ArrayList<MediaInfo> returnLst = new ArrayList<>();
        this.cursor = this.mContext.getContentResolver().query(this.mUri, null, null, null, null);
        if (this.cursor.getColumnCount() > 0) {
            this.cursor.moveToFirst();
            this.cursor.moveToFirst();
            while (!this.cursor.isAfterLast()) {
                if (this.mUri.equals(this.imageUri)) {
                    for (int i = 0; i < this.mPriMediaList.size(); i++) {
                        if (this.cursor.getString(this.cursor.getColumnIndex("_data")).equals(this.mPriMediaList.get(i))) {
                            String thumbPath = getImageThumbnail(this.cursor.getLong(this.cursor.getColumnIndex("_id")));
                            if (thumbPath == null || thumbPath.isEmpty()) {
                                mediaInfo2 = new MediaInfo(this.cursor.getString(this.cursor.getColumnIndex("_data")), this.cursor.getString(this.cursor.getColumnIndex("_data")));
                            } else {
                                mediaInfo2 = new MediaInfo(this.cursor.getString(this.cursor.getColumnIndex("_data")), thumbPath);
                            }
                            returnLst.add(mediaInfo2);
                        }
                    }
                } else if (this.mUri.equals(this.videoUri)) {
                    for (int i2 = 0; i2 < this.mPriMediaList.size(); i2++) {
                        if (this.cursor.getString(this.cursor.getColumnIndex("_data")).equals(this.mPriMediaList.get(i2))) {
                            String thumbPath2 = getVideoThumbnail(this.cursor.getLong(this.cursor.getColumnIndex("_id")));
                            if (thumbPath2 == null || thumbPath2.isEmpty()) {
                                mediaInfo = new MediaInfo(this.cursor.getString(this.cursor.getColumnIndex("_data")), this.cursor.getString(this.cursor.getColumnIndex("_data")));
                            } else {
                                mediaInfo = new MediaInfo(this.cursor.getString(this.cursor.getColumnIndex("_data")), thumbPath2);
                            }
                            returnLst.add(mediaInfo);
                        }
                    }
                }
                this.cursor.moveToNext();
            }
            this.cursor.close();
            return returnLst;
        }
        this.cursor.close();
        return null;
    }

    public ArrayList<FolderInfo> getAllFolders() {
        ArrayList<MediaInfo> resolveAllImages = getAllImages();
        if (resolveAllImages == null) {
            return null;
        }
        this.allFolders = new ArrayList<>();
        ArrayList uniqueFoldersPaths = new ArrayList();
        for (int i = 0; i < resolveAllImages.size(); i++) {
            String parentPath = new File(resolveAllImages.get(i).getFilePath()).getParent();
            if (!uniqueFoldersPaths.contains(parentPath)) {
                uniqueFoldersPaths.add(parentPath);
                FolderInfo dirInfo = new FolderInfo(parentPath, 1);
                dirInfo.setChildList(resolveAllImages.get(i));
                this.allFolders.add(dirInfo);
            } else {
                this.allFolders.get(uniqueFoldersPaths.indexOf(parentPath)).setFileNum(this.allFolders.get(uniqueFoldersPaths.indexOf(parentPath)).getFilesNum() + 1);
                this.allFolders.get(uniqueFoldersPaths.indexOf(parentPath)).setChildList(resolveAllImages.get(i));
            }
        }
        return this.allFolders;
    }

    public String getImageThumbnail(long image_id) {
        String thumbPath = "";
        this.c = MediaStore.Images.Thumbnails.queryMiniThumbnail(this.mContext.getContentResolver(), image_id, 1, new String[]{"_data"});
        if (this.c.getColumnCount() > 0 && this.c != null && this.c.moveToFirst()) {
            thumbPath = this.c.getString(this.c.getColumnIndex("_data"));
        }
        this.c.close();
        return thumbPath;
    }

    public String getVideoThumbnail(long video_id) {
        String thumbPath = "";
        Cursor c = this.mContext.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, new String[]{"_data"}, "video_id=?", new String[]{video_id + ""}, null);
        if (c.getColumnCount() > 0 && c != null && c.moveToFirst()) {
            thumbPath = c.getString(c.getColumnIndex("_data"));
        }
        c.close();
        return thumbPath;
    }

    public String[] childList() {
        return null;
    }
}

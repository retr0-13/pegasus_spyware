package com.lenovo.safebox;

import android.graphics.Bitmap;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class FolderInfo {
    private String TAG = "FolderInfo  aaaaaaaaaaaaaaaaaaaaaaaaaaa";
    ArrayList<MediaInfo> childList = new ArrayList<>();
    int mFilesNum;
    String mFolderName;
    String mFolderPath;
    Bitmap mSnapshot;

    public FolderInfo(String dirPath, int count) {
        this.mFolderPath = dirPath;
        this.mFilesNum = count;
    }

    public String getFolderName() {
        if (this.mFolderPath == null || this.mFolderPath.isEmpty()) {
            return "";
        }
        String[] tmpString = this.mFolderPath.split("/");
        if (tmpString.length == 0) {
            return "";
        }
        this.mFolderName = tmpString[tmpString.length - 1];
        return this.mFolderName;
    }

    public String getFolderPath() {
        return this.mFolderPath;
    }

    public int getFilesNum() {
        return this.mFilesNum;
    }

    public String getSnapShot() {
        return this.childList.get(0).getThumbImage();
    }

    public void setChildList(MediaInfo oneMedia) {
        this.childList.add(oneMedia);
    }

    public ArrayList<MediaInfo> getChildList() {
        return this.childList;
    }

    public void setFileNum(int count) {
        this.mFilesNum = count;
    }
}

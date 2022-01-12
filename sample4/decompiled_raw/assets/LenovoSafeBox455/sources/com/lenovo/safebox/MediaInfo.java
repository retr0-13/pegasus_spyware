package com.lenovo.safebox;
/* loaded from: classes.dex */
public class MediaInfo {
    private String TAG = "MediaInfo aaaaaaaaaaaaaaaaaaaaaa";
    public boolean checkHolder = false;
    public String fileName;
    public String filePath;
    public String thumbPath;

    public MediaInfo(String path, String thumbPath) {
        this.filePath = path;
        this.thumbPath = thumbPath;
    }

    public String getThumbImage() {
        return this.thumbPath;
    }

    public void setThumbImage(String path) {
        if (path == null || path.isEmpty()) {
            this.thumbPath = this.filePath;
        } else {
            this.thumbPath = path;
        }
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getFileName() {
        String[] tmpString = this.filePath.split("/");
        this.fileName = tmpString[tmpString.length - 1];
        return this.fileName;
    }

    public void setCheckHolder(boolean isCheck) {
        this.checkHolder = !isCheck;
    }

    public boolean getCheckHolder() {
        return this.checkHolder;
    }
}

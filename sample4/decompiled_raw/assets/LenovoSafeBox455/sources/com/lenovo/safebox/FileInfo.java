package com.lenovo.safebox;
/* loaded from: classes.dex */
public class FileInfo {
    private String TAG = "FileInfo  aaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private boolean checkHolder = false;
    private String mPath;
    private String mime;

    /* JADX INFO: Access modifiers changed from: package-private */
    public FileInfo(String path, String mimeType) {
        this.mPath = path;
        this.mime = mimeType;
    }

    public boolean getCheckHolder() {
        return this.checkHolder;
    }

    public void setCheckHolder(boolean isCheck) {
        this.checkHolder = !isCheck;
    }

    public String getFilePath() {
        return this.mPath;
    }

    public String getMime() {
        return this.mime;
    }
}

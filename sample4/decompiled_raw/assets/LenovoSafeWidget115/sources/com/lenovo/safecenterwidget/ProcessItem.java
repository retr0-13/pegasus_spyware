package com.lenovo.safecenterwidget;

import android.graphics.Bitmap;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ScanAppInfo.java */
/* loaded from: classes.dex */
public class ProcessItem {
    public Bitmap mIcon;
    public String mLabel;
    public int pid;
    public String pkgName;

    public ProcessItem(Bitmap mIcon, String mLabel, String pkgName, int pid) {
        this.mIcon = mIcon;
        this.mLabel = mLabel;
        this.pkgName = pkgName;
        this.pid = pid;
    }
}

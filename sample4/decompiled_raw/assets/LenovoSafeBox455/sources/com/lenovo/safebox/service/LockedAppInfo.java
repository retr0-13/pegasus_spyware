package com.lenovo.safebox.service;
/* loaded from: classes.dex */
public class LockedAppInfo {
    int mPid;
    String mPkgName;

    LockedAppInfo(int pid, String pkgName) {
        this.mPid = pid;
        this.mPkgName = pkgName;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPid() {
        return this.mPid;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getPkgName() {
        return this.mPkgName;
    }
}

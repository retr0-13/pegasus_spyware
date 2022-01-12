package com.lenovo.safebox;

import android.graphics.drawable.Drawable;
/* loaded from: classes.dex */
public class AppInfo {
    public String appName;
    public Drawable drawable;
    public int drawableId;
    public String pkgName;

    public void setAppName(String name) {
        this.appName = name;
    }

    public void setPkgName(String name) {
        this.pkgName = name;
    }

    public void setDrawable(Drawable tmpDrw) {
        this.drawable = tmpDrw;
    }

    public void setStatus(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getAppName() {
        return this.appName;
    }

    public String getPkgName() {
        return this.pkgName;
    }

    public Drawable getDrawable() {
        return this.drawable;
    }

    public int getStatus() {
        return this.drawableId;
    }
}

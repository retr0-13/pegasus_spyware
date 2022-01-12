package com.lenovo.safebox;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
/* loaded from: classes.dex */
public final class ShortcutHelper {
    private Intent mShortcutIntent;

    public ShortcutHelper() {
        this.mShortcutIntent = new Intent();
        setDuplicate(false);
    }

    public ShortcutHelper(String shortcutName) {
        this();
        setShortcutName(shortcutName);
    }

    public ShortcutHelper(String shortcutName, Intent launchIntent) {
        this(shortcutName);
        setLaunchIntent(launchIntent);
    }

    public ShortcutHelper(String shortcutName, Parcelable shortIcon) {
        this(shortcutName);
        setShortcutIcon(shortIcon);
    }

    public ShortcutHelper(String shortcutName, Parcelable shortIcon, Intent launchIntent) {
        this(shortcutName, shortIcon);
        setLaunchIntent(launchIntent);
    }

    public ShortcutHelper setDuplicate(boolean allowed) {
        this.mShortcutIntent.putExtra("duplicate", allowed);
        return this;
    }

    public ShortcutHelper setShortcutName(String name) {
        this.mShortcutIntent.putExtra("android.intent.extra.shortcut.NAME", name);
        return this;
    }

    public ShortcutHelper setShortcutIcon(Parcelable shortIcon) {
        this.mShortcutIntent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", shortIcon);
        return this;
    }

    public ShortcutHelper setLaunchIntent(Intent launchIntent) {
        this.mShortcutIntent.putExtra("android.intent.extra.shortcut.INTENT", launchIntent);
        return this;
    }

    public ShortcutHelper installTheShortcut(Context ctx) throws ShortcutCreatorInstallException {
        if (ctx == null) {
            throw new ShortcutCreatorInstallException("Shortcut installation need a Context, but it is null!");
        }
        checkKeysForInstall();
        this.mShortcutIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        ctx.sendBroadcast(this.mShortcutIntent);
        return this;
    }

    private void checkKeysForInstall() throws ShortcutCreatorInstallException {
        if (!this.mShortcutIntent.getExtras().containsKey("android.intent.extra.shortcut.INTENT")) {
            throw new ShortcutCreatorInstallException("Didnot set Intent.EXTRA_SHORTCUT_INTENT yet!");
        }
    }

    public ShortcutHelper uninstallTheShortcut(Context ctx) throws ShortcutCreatorInstallException {
        if (ctx == null) {
            throw new ShortcutCreatorInstallException("Shortcut installation need a Context, but it is null!");
        }
        checkKeysForInstall();
        this.mShortcutIntent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");
        ctx.sendBroadcast(this.mShortcutIntent);
        return this;
    }

    public boolean isExistShortcut(Context ctx) {
        boolean isInstallShortcut = false;
        String[] AUTHORITY = {"com.android.launcher.settings", "com.android.launcher2.settings", "com.lenovo.launcher2.settings"};
        Cursor c = null;
        for (Uri uri : new Uri[]{Uri.parse("content://" + AUTHORITY[0] + "/favorites?notify=true"), Uri.parse("content://" + AUTHORITY[1] + "/favorites?notify=true"), Uri.parse("content://" + AUTHORITY[2] + "/favorites?notify=true")}) {
            try {
                c = ctx.getContentResolver().query(uri, new String[]{"iconPackage"}, "iconPackage=?", new String[]{ctx.getPackageName()}, null);
                if (c != null && c.moveToFirst()) {
                    isInstallShortcut = true;
                }
            } finally {
                if (c != null) {
                    c.close();
                }
            }
        }
        return isInstallShortcut;
    }

    /* loaded from: classes.dex */
    public static class ShortcutCreatorInstallException extends Exception {
        private static final long serialVersionUID = -1459308340272695930L;

        public ShortcutCreatorInstallException(String msg) {
            super(msg);
        }
    }
}

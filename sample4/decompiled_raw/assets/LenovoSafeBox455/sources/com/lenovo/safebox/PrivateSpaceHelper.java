package com.lenovo.safebox;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/* loaded from: classes.dex */
public class PrivateSpaceHelper extends SQLiteOpenHelper {
    public static final String APP_ID = "_id";
    public static final String APP_NAME = "name";
    public static final String APP_TB_NAME = "app_lock";
    public static final String FILE = "filename";
    public static final String FROM = "origin";
    public static final String ID = "_id";
    public static final String ISLOCK = "islock";
    public static final String PKG = "pkg";
    public static final String TB_NAME = "file_from";
    public static final String THUMB = "thumb";
    public static final String TYPE = "type";
    public static final String UNDER = "under";

    public PrivateSpaceHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS file_from (_id INTEGER PRIMARY KEY,filename VARCHAR,origin VARCHAR,type INTEGER,thumb BLOB,under VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS app_lock (_id INTEGER PRIMARY KEY,pkg VARCHAR,name VARCHAR,islock BIT )");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS file_from");
        db.execSQL("DROP TABLE IF EXISTS app_lock");
        onCreate(db);
    }
}

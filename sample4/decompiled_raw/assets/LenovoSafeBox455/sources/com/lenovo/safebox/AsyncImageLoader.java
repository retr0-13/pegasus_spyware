package com.lenovo.safebox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* loaded from: classes.dex */
public class AsyncImageLoader {
    private String TAG;
    SQLiteDatabase db;
    private ExecutorService executorService;
    private HashMap<String, WeakReference<Bitmap>> imageCache;
    private int mFlag;
    PrivateSpaceHelper mHelper;

    /* loaded from: classes.dex */
    public interface ImageCallback {
        void imageLoaded(Bitmap bitmap, String str);
    }

    public AsyncImageLoader() {
        this.TAG = "PrivateSpace  AsyncImageLoader ";
        this.imageCache = null;
        this.mFlag = 999;
        this.executorService = Executors.newFixedThreadPool(15);
        this.imageCache = new HashMap<>();
    }

    public AsyncImageLoader(int flag, Context context) {
        this.TAG = "PrivateSpace  AsyncImageLoader ";
        this.imageCache = null;
        this.mFlag = 999;
        this.executorService = Executors.newFixedThreadPool(15);
        this.imageCache = new HashMap<>();
        this.mHelper = new PrivateSpaceHelper(context, PrivateSpaceTools.DB_NAME, null, 1);
        this.mFlag = flag;
    }

    public Bitmap loadBitmap(final String imageUrl, final String filename, final ImageCallback imageCallback) {
        Bitmap Bitmap;
        if (this.imageCache.containsKey(imageUrl) && (Bitmap = this.imageCache.get(imageUrl).get()) != null) {
            return Bitmap;
        }
        final Handler handler = new Handler() { // from class: com.lenovo.safebox.AsyncImageLoader.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                imageCallback.imageLoaded((Bitmap) message.obj, imageUrl);
            }
        };
        try {
            this.executorService.execute(new Thread() { // from class: com.lenovo.safebox.AsyncImageLoader.2
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    Bitmap bitmap;
                    if (AsyncImageLoader.this.mFlag == 999) {
                        bitmap = AsyncImageLoader.this.loadImageFromUrl(imageUrl);
                    } else {
                        try {
                            bitmap = AsyncImageLoader.this.getThumbnail(filename, imageUrl);
                        } catch (Exception e) {
                            bitmap = null;
                        }
                        if (bitmap == null) {
                            bitmap = AsyncImageLoader.this.loadImageFromUrl(imageUrl);
                        }
                    }
                    if (bitmap != null && !bitmap.isRecycled()) {
                        AsyncImageLoader.this.imageCache.put(imageUrl, new WeakReference(bitmap));
                        handler.sendMessage(handler.obtainMessage(0, bitmap));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized Bitmap loadImageFromUrl(String path) {
        Bitmap bm;
        if (path == null) {
            bm = null;
        } else if (PrivateSpaceTools.getMimeTypeOfFile(PrivateSpaceTools.getFileName(path)) != null) {
            String[] mimeType = PrivateSpaceTools.getMimeTypeOfFile(PrivateSpaceTools.getFileName(path)).split("/");
            if (mimeType[0] == null || !mimeType[0].equals("video")) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);
                options.inJustDecodeBounds = false;
                int be = (int) (((float) options.outHeight) / 76.0f);
                if (be <= 0) {
                    be = 1;
                }
                options.inSampleSize = be;
                bm = BitmapFactory.decodeFile(path, options);
            } else {
                bm = ThumbnailUtils.extractThumbnail(ThumbnailUtils.createVideoThumbnail(path, 3), 83, 83, 2);
            }
        } else {
            BitmapFactory.Options options2 = new BitmapFactory.Options();
            options2.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options2);
            options2.inJustDecodeBounds = false;
            int be2 = (int) (((float) options2.outHeight) / 76.0f);
            if (be2 <= 0) {
                be2 = 1;
            }
            options2.inSampleSize = be2;
            bm = BitmapFactory.decodeFile(path, options2);
        }
        return bm;
    }

    public synchronized Bitmap getThumbnail(String fileName, String filePath) {
        Bitmap returnBitmap;
        this.db = this.mHelper.getReadableDatabase();
        byte[] picData = null;
        Cursor mCursor = this.db.query(true, PrivateSpaceHelper.TB_NAME, new String[]{"_id", PrivateSpaceHelper.FILE, PrivateSpaceHelper.THUMB, PrivateSpaceHelper.TYPE}, "filename = \"" + fileName + "\" and type = " + this.mFlag, null, null, null, null, null);
        if (mCursor != null && mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            picData = mCursor.getBlob(mCursor.getColumnIndex(PrivateSpaceHelper.THUMB));
        }
        if (picData == null) {
            if (this.mFlag == 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(filePath, options);
                options.inJustDecodeBounds = false;
                int be = (int) (((float) options.outHeight) / 76.0f);
                if (be <= 0) {
                    be = 1;
                }
                options.inSampleSize = be;
                returnBitmap = BitmapFactory.decodeFile(filePath, options);
            } else {
                Bitmap bmVideo = ThumbnailUtils.createVideoThumbnail(filePath, 1);
                if (bmVideo != null) {
                    returnBitmap = scaleBitmap(bmVideo);
                } else {
                    returnBitmap = null;
                }
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            if (returnBitmap != null) {
                returnBitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
                ContentValues args = new ContentValues();
                args.put(PrivateSpaceHelper.THUMB, os.toByteArray());
                Log.i(this.TAG, "UPDATE RESULT :" + (this.db.update(PrivateSpaceHelper.TB_NAME, args, new StringBuilder().append("filename = \"").append(fileName).append("\"").toString(), null) > 0));
            }
        } else {
            returnBitmap = BitmapFactory.decodeByteArray(picData, 0, picData.length);
        }
        if (mCursor != null) {
            mCursor.close();
        }
        this.db.close();
        return returnBitmap;
    }

    public Bitmap scaleBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) 83) / ((float) width), ((float) 83) / ((float) height));
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
    }

    public void recycleBitmap() {
    }
}

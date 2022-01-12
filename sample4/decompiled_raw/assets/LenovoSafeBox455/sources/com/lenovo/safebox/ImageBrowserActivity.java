package com.lenovo.safebox;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.lenovo.lps.sus.d.b;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* loaded from: classes.dex */
public class ImageBrowserActivity extends Activity implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private File[] childList;
    private Animation downInAnimation;
    private Map<String, WeakReference<Bitmap>> imageCache;
    private ImageView iv;
    private int ivHeight;
    private int ivWidth;
    private Animation leftInAnimation;
    private Context mContext;
    private int mCurrentIndex;
    private GestureDetector mGestureDetector;
    private int mImageNum;
    private String mImagePath;
    private TextView numText;
    private File pFolder;
    private ProgressBar pgBar;
    private Animation rightInAnimation;
    private Bitmap showBitmap;
    private Toast toast;
    private Animation upInAnimation;
    private String TAG = "ImageBrowserActivity";
    private ExecutorService executorService = Executors.newFixedThreadPool(3);
    private final int SHOW_NUMBER = 1;
    private Handler handler = new Handler() { // from class: com.lenovo.safebox.ImageBrowserActivity.2
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ImageBrowserActivity.this.updateUI();
                    return;
                case 1:
                    if (ImageBrowserActivity.this.numText.getVisibility() == 0) {
                        ImageBrowserActivity.this.numText.setVisibility(8);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.image_browser);
        this.mContext = this;
        initview();
        initData();
    }

    private void initview() {
        this.iv = (ImageView) findViewById(R.id.imageViewBrowser);
        this.iv.setVisibility(8);
        this.pgBar = (ProgressBar) findViewById(R.id.widget196);
        this.pgBar.setVisibility(0);
        this.numText = (TextView) findViewById(R.id.numBar);
        this.ivWidth = getWindowManager().getDefaultDisplay().getWidth();
        this.ivHeight = getWindowManager().getDefaultDisplay().getHeight();
        this.iv.setOnTouchListener(this);
        this.iv.setFocusable(true);
        this.iv.setClickable(true);
        this.iv.setLongClickable(true);
    }

    private void resolveBitmapThread(final String imagePath) {
        this.iv.setVisibility(8);
        try {
            this.executorService.execute(new Thread() { // from class: com.lenovo.safebox.ImageBrowserActivity.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    if (imagePath != null) {
                        Log.i("AndroidRuntime", "iv :" + ImageBrowserActivity.this.iv);
                        ImageBrowserActivity.this.showBitmap = ImageBrowserActivity.this.createPic(imagePath);
                    }
                    Message msg = new Message();
                    msg.what = 0;
                    ImageBrowserActivity.this.handler.sendMessage(msg);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        this.imageCache = new HashMap();
        this.mGestureDetector = new GestureDetector(this);
        this.mGestureDetector.setIsLongpressEnabled(true);
        this.mImagePath = getIntent().getExtras().getString("ImagePath");
        Log.i("AndroidRuntime", "ImagePath :" + this.mImagePath);
        resolveBitmapThread(this.mImagePath);
        if (this.mImagePath != null) {
            this.pFolder = new File(PrivateSpaceTools.priImgFolder);
            this.childList = this.pFolder.listFiles();
            this.mImageNum = this.childList.length;
            for (int i = 0; i < this.childList.length; i++) {
                if (this.childList[i].getAbsolutePath().equals(this.mImagePath)) {
                    this.mCurrentIndex = i;
                }
            }
            return;
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap createPic(String path) {
        if (this.showBitmap != null && !this.showBitmap.isRecycled()) {
            Log.i("AndroidRuntime", "do recycle");
            this.showBitmap.recycle();
            this.showBitmap = null;
        }
        Log.i("AndroidRuntime", "showBitmap :" + this.showBitmap);
        Log.i("AndroidRuntime", "path :" + path);
        if (this.imageCache.containsKey(path)) {
            Log.i("AndroidRuntime", "" + this.imageCache.get(path).get());
            if (this.imageCache.get(path).get() != null && !this.imageCache.get(path).get().isRecycled()) {
                return this.imageCache.get(path).get();
            }
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = computeSampleSize(options, Math.min(this.ivWidth, this.ivHeight), this.ivWidth * this.ivHeight);
        Log.i("AndroidRuntime", "ivWidth  :" + this.ivWidth);
        Log.i("AndroidRuntime", "ivHeight  :" + this.ivHeight);
        options.inInputShareable = true;
        options.inPurgeable = true;
        Log.i("AndroidRuntime", "Width  :" + options.outWidth);
        Log.i("AndroidRuntime", "Height  :" + options.outHeight);
        Log.i("AndroidRuntime", "inDensity  :" + options.inDensity);
        Log.i("AndroidRuntime", "inSampleSize :" + options.inSampleSize);
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        Log.i("AndroidRuntime", "bm :" + bm);
        this.imageCache.put(path, new WeakReference<>(bm));
        Iterator iter = this.imageCache.entrySet().iterator();
        while (iter.hasNext()) {
            Log.i("AndroidRuntime", "check :" + iter.next().getValue().get());
        }
        return bm;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUI() {
        if (this.pgBar.getVisibility() == 0) {
            this.pgBar.setVisibility(8);
        }
        if (this.iv.getVisibility() == 8) {
            this.iv.setVisibility(0);
        }
        if (this.showBitmap == null || this.showBitmap.isRecycled()) {
            Log.i("AndroidRuntime", "updateUI  finish");
            Toast.makeText(this.mContext, this.mContext.getString(R.string.cantOpen), 0).show();
            this.iv.setImageResource(R.drawable.invalid_img);
            return;
        }
        this.iv.setVisibility(0);
        this.iv.setImageBitmap(this.showBitmap);
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        Log.i("AndroidRuntime", "initialSize :" + initialSize);
        if (initialSize > 8) {
            return ((initialSize + 7) / 8) * 8;
        }
        int roundedSize = 1;
        while (roundedSize < initialSize) {
            roundedSize <<= 1;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = (double) options.outWidth;
        double h = (double) options.outHeight;
        int lowerBound = maxNumOfPixels == -1 ? 1 : (int) Math.ceil(Math.sqrt((w * h) / ((double) maxNumOfPixels)));
        int upperBound = minSideLength == -1 ? 128 : (int) Math.min(Math.floor(w / ((double) minSideLength)), Math.floor(h / ((double) minSideLength)));
        if (upperBound < lowerBound) {
            Log.i("AndroidRuntime", "return lowerBound :" + lowerBound);
            return lowerBound;
        } else if (maxNumOfPixels == -1 && minSideLength == -1) {
            return 1;
        } else {
            if (minSideLength == -1) {
                Log.i("AndroidRuntime", "return lowerBound :" + lowerBound);
                return lowerBound;
            }
            Log.i("AndroidRuntime", "return upperBound :" + upperBound);
            return upperBound;
        }
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View v, MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("AndroidRuntime", "onFling");
        if (e1.getX() - e2.getX() <= ((float) 100) || Math.abs(velocityX) <= ((float) b.e)) {
            if (e2.getX() - e1.getX() <= ((float) 100) || Math.abs(velocityX) <= ((float) b.e)) {
                if (e1.getY() - e2.getY() <= ((float) 100) || Math.abs(velocityY) <= ((float) b.e)) {
                    if (e2.getY() - e1.getY() > ((float) 100) && Math.abs(velocityY) > ((float) b.e)) {
                        if (this.mCurrentIndex == 0) {
                            Toast.makeText(this.mContext, this.mContext.getString(R.string.isFirstImage), 0).show();
                        } else {
                            this.mCurrentIndex--;
                            this.pgBar.setVisibility(0);
                            resolveBitmapThread(this.childList[this.mCurrentIndex].getAbsolutePath());
                            this.numText.setText((this.mCurrentIndex + 1) + "/" + this.childList.length);
                            this.numText.setVisibility(0);
                            this.handler.sendMessageDelayed(this.handler.obtainMessage(1), 1000);
                        }
                    }
                } else if (this.mCurrentIndex == this.mImageNum - 1) {
                    Toast.makeText(this.mContext, this.mContext.getString(R.string.isLastImage), 0).show();
                } else {
                    this.mCurrentIndex++;
                    this.pgBar.setVisibility(0);
                    resolveBitmapThread(this.childList[this.mCurrentIndex].getAbsolutePath());
                    this.numText.setText((this.mCurrentIndex + 1) + "/" + this.childList.length);
                    this.numText.setVisibility(0);
                    this.handler.sendMessageDelayed(this.handler.obtainMessage(1), 1000);
                }
            } else if (this.mCurrentIndex == 0) {
                Toast.makeText(this.mContext, this.mContext.getString(R.string.isFirstImage), 0).show();
            } else {
                this.mCurrentIndex--;
                this.pgBar.setVisibility(0);
                resolveBitmapThread(this.childList[this.mCurrentIndex].getAbsolutePath());
                this.numText.setText((this.mCurrentIndex + 1) + "/" + this.childList.length);
                this.numText.setVisibility(0);
                this.handler.sendMessageDelayed(this.handler.obtainMessage(1), 1000);
            }
        } else if (this.mCurrentIndex == this.mImageNum - 1) {
            this.toast = Toast.makeText(this.mContext, this.mContext.getString(R.string.isLastImage), 0);
            this.toast.show();
        } else {
            this.mCurrentIndex++;
            this.pgBar.setVisibility(0);
            resolveBitmapThread(this.childList[this.mCurrentIndex].getAbsolutePath());
            this.numText.setText((this.mCurrentIndex + 1) + "/" + this.childList.length);
            this.numText.setVisibility(0);
            this.handler.sendMessageDelayed(this.handler.obtainMessage(1), 1000);
        }
        Log.i("AndroidRuntime", "mCurrentIndex :" + this.mCurrentIndex);
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent e) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent e) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override // android.app.Activity
    public void onPause() {
        Log.i("AndroidRuntime", "onStop");
        if (this.showBitmap != null && this.showBitmap.isRecycled()) {
            this.showBitmap.recycle();
        }
        for (Map.Entry<String, WeakReference<Bitmap>> entry : this.imageCache.entrySet()) {
            if (entry.getValue().get() != null && !entry.getValue().get().isRecycled()) {
                Log.i("AndroidRuntime", "do recycle :" + entry.getValue().get());
                entry.getValue().get().recycle();
            }
        }
        finish();
        System.gc();
        super.onPause();
    }
}

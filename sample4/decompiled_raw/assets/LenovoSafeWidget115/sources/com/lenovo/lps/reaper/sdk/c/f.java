package com.lenovo.lps.reaper.sdk.c;

import android.util.Log;
import com.lenovo.lps.reaper.sdk.e.b;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/* loaded from: classes.dex */
public final class f {
    private static f b = new f();
    private final ThreadPoolExecutor a = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue(1000));

    public static f a() {
        return b;
    }

    public final void a(Runnable runnable) {
        try {
            this.a.submit(runnable);
        } catch (RejectedExecutionException e) {
            Log.e("TaskHandler", "Too Many Task At a Time. Please Wait...");
        } catch (Exception e2) {
            b.a("TaskHandler", e2.getMessage(), e2);
        }
    }
}

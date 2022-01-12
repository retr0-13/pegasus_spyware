package com.lenovo.lps.reaper.sdk.d;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
/* loaded from: classes.dex */
public final class c {
    private String a;
    private Context b;
    private RandomAccessFile c;
    private FileChannel d;

    public c(String str, Context context) {
        this.a = str;
        this.b = context;
    }

    public final int a(ByteBuffer byteBuffer) {
        return this.d.write(byteBuffer);
    }

    public final void a() {
        String str = Environment.getDataDirectory() + "/data/" + this.b.getPackageName() + "/files/";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            if (this.d != null) {
                try {
                    this.d.close();
                } catch (IOException e) {
                }
            }
            if (this.c != null) {
                try {
                    this.c.close();
                } catch (IOException e2) {
                }
            }
            this.c = new RandomAccessFile(new File(String.valueOf(str) + this.a), "rw");
        } catch (IOException e3) {
            Log.e("FileStorage", "Error to Close or Create DataFile. " + e3.getMessage());
        }
        this.d = this.c.getChannel();
    }

    public final void a(Long l) {
        this.d.position(l.longValue());
    }

    public final void b() {
        this.d.force(true);
    }

    public final void b(ByteBuffer byteBuffer) {
        byteBuffer.clear();
        this.d.read(byteBuffer);
        byteBuffer.flip();
    }
}

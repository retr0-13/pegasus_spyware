package com.lenovo.lps.reaper.sdk.e;

import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
/* loaded from: classes.dex */
public class c {
    private static final String a = c.class.getName();

    public static final byte[] a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return new byte[0];
        }
        Deflater deflater = new Deflater();
        deflater.setLevel(9);
        deflater.setInput(bArr);
        deflater.finish();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
        byte[] bArr2 = new byte[1024];
        while (!deflater.finished()) {
            byteArrayOutputStream.write(bArr2, 0, deflater.deflate(bArr2));
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            Log.e(a, "exception when close output stream. " + e.getMessage());
        }
        deflater.end();
        return byteArrayOutputStream.toByteArray();
    }
}

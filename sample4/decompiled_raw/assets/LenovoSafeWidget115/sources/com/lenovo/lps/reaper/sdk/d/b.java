package com.lenovo.lps.reaper.sdk.d;

import android.util.Log;
import com.lenovo.lps.reaper.sdk.a.a;
import com.lenovo.lps.reaper.sdk.a.c;
import com.lenovo.lps.reaper.sdk.a.d;
import com.lenovo.lps.reaper.sdk.e.g;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;
/* loaded from: classes.dex */
public final class b implements d {
    private c a;
    private d b;
    private ByteBuffer c = ByteBuffer.allocate(4096);
    private Random d = new Random(System.currentTimeMillis());

    private static String a(ByteBuffer byteBuffer) {
        int i = byteBuffer.getInt();
        if (i == -1 || i > 4096) {
            return null;
        }
        byte[] bArr = new byte[i];
        byteBuffer.get(bArr);
        return new String(bArr);
    }

    private static void a(ByteBuffer byteBuffer, String str) {
        if (str == null) {
            byteBuffer.putInt(-1);
            return;
        }
        byte[] bytes = str.getBytes();
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
    }

    private void a(ByteBuffer byteBuffer, a[] aVarArr) {
        if (aVarArr == null || aVarArr.length <= 0) {
            byteBuffer.putInt(0);
            return;
        }
        byteBuffer.putInt(aVarArr.length);
        for (a aVar : aVarArr) {
            byteBuffer.putInt(aVar.c());
            a(byteBuffer, aVar.d());
            a(byteBuffer, aVar.e());
            byteBuffer.putInt(aVar.f());
        }
    }

    private void d() {
        this.a.a((Long) 0L);
        this.a.a(this.b.e());
        this.a.b();
    }

    @Override // com.lenovo.lps.reaper.sdk.a.d
    public final void a() {
        try {
            this.a.a();
            ByteBuffer allocate = ByteBuffer.allocate(4096);
            this.a.b(allocate);
            long currentTimeMillis = System.currentTimeMillis();
            synchronized (this.a) {
                if (this.b.a(allocate)) {
                    com.lenovo.lps.reaper.sdk.e.b.a("FileEventDaoImpl", "DB load success (in activeSession())");
                    com.lenovo.lps.reaper.sdk.e.b.a("FileEventDaoImpl", "sessionID=" + this.b.l());
                    this.b.b(this.b.k());
                    this.b.c(currentTimeMillis);
                    this.b.c(this.b.m() + 1);
                } else {
                    com.lenovo.lps.reaper.sdk.e.b.a("FileEventDaoImpl", "DB load false (in activeSession())");
                    this.b.c(currentTimeMillis);
                    this.b.a(currentTimeMillis);
                    this.b.b(currentTimeMillis);
                    this.b.b(new SecureRandom().nextInt() & Integer.MAX_VALUE);
                    this.b.c(1);
                }
                d();
            }
        } catch (Exception e) {
            Log.e("FileEventDaoImpl", "Error when initialize file storage. " + e.getMessage());
        }
    }

    @Override // com.lenovo.lps.reaper.sdk.a.d
    public final void a(c cVar) {
        com.lenovo.lps.reaper.sdk.e.b.a("FileEventDaoImpl", "SavingEvent.");
        try {
            synchronized (this.a) {
                if (this.b.f()) {
                    com.lenovo.lps.reaper.sdk.e.b.c("FileEventDaoImpl", "Index of " + this.b.d() + " has been deleted");
                }
                this.a.a(Long.valueOf((((long) this.b.b()) << 12) + 4096));
                this.c.clear();
                this.c.putLong(this.b.h()).putInt(this.b.l());
                a(this.c, cVar.d());
                this.c.putInt(Integer.valueOf(this.d.nextInt(Integer.MAX_VALUE)).intValue()).putLong(this.b.i()).putLong(this.b.j()).putLong(this.b.k()).putLong(cVar.o()).putInt(this.b.m());
                a(this.c, cVar.j());
                a(this.c, cVar.k());
                a(this.c, cVar.l());
                this.c.putInt(cVar.m());
                this.c.putInt(g.a());
                a(this.c, cVar.b());
                this.c.flip();
                this.a.a(this.c);
                this.b.c();
                d();
            }
        } catch (BufferOverflowException e) {
            Log.e("FileEventDaoImpl", "Event Infomation is Too Long Than " + this.c.capacity());
        } catch (Exception e2) {
            Log.e("FileEventDaoImpl", "Error when save Event object to storage. " + e2.getClass() + ", " + e2.getMessage());
        }
    }

    public final void a(c cVar) {
        this.a = cVar;
    }

    public final void a(d dVar) {
        this.b = dVar;
    }

    @Override // com.lenovo.lps.reaper.sdk.a.d
    public final boolean a(c[] cVarArr) {
        try {
            com.lenovo.lps.reaper.sdk.e.a.b("deleteEvents");
            try {
                synchronized (this.a) {
                    if (cVarArr != null) {
                        for (int i = 0; i < cVarArr.length; i++) {
                            this.b.d();
                        }
                    }
                    d();
                }
                com.lenovo.lps.reaper.sdk.e.a.a();
                return true;
            } catch (IOException e) {
                Log.e("FileEventDaoImpl", "Error when delete events. " + e.getMessage());
                com.lenovo.lps.reaper.sdk.e.a.a();
                return false;
            }
        } catch (Throwable th) {
            com.lenovo.lps.reaper.sdk.e.a.a();
            throw th;
        }
    }

    @Override // com.lenovo.lps.reaper.sdk.a.d
    public final c[] a(int i) {
        c[] cVarArr;
        try {
            synchronized (this.a) {
                int g = i > this.b.g() ? this.b.g() : i;
                cVarArr = new c[g];
                int a = this.b.a();
                for (int i2 = 0; i2 < g; i2++) {
                    this.a.a(Long.valueOf((((long) a) << 12) + 4096));
                    this.a.b(this.c);
                    c cVar = new c(this.c.getLong(), this.c.getInt(), a(this.c), this.c.getInt(), this.c.getLong(), this.c.getLong(), this.c.getLong(), this.c.getLong(), this.c.getInt(), a(this.c), a(this.c), a(this.c), this.c.getInt(), this.c.getInt());
                    ByteBuffer byteBuffer = this.c;
                    int i3 = byteBuffer.getInt();
                    if (i3 > 0 && i3 <= 5) {
                        a[] aVarArr = new a[i3];
                        for (int i4 = 0; i4 < i3; i4++) {
                            aVarArr[i4] = new a(byteBuffer.getInt(), a(byteBuffer), a(byteBuffer), byteBuffer.getInt());
                        }
                        cVar.a(aVarArr);
                    }
                    a = this.b.a(a);
                    cVarArr[i2] = cVar;
                }
                if (com.lenovo.lps.reaper.sdk.e.b.a()) {
                    com.lenovo.lps.reaper.sdk.e.b.b("SendingEvent", "Sending......");
                    for (c cVar2 : cVarArr) {
                        com.lenovo.lps.reaper.sdk.e.b.a("SendingEvent", " ");
                        com.lenovo.lps.reaper.sdk.e.b.a("SendingEvent", cVar2.a());
                        if (cVar2.b() != null) {
                            a[] b = cVar2.b();
                            for (a aVar : b) {
                                com.lenovo.lps.reaper.sdk.e.b.c("SendingEvent", "The Event Param:  [Index]" + aVar.c() + " [Name]" + aVar.d() + " [Value]" + aVar.e());
                            }
                        }
                    }
                }
            }
            return cVarArr;
        } catch (Exception e) {
            Log.e("FileEventDaoImpl", "Error when fetch Event object from storage. " + e.getMessage());
            return null;
        }
    }

    @Override // com.lenovo.lps.reaper.sdk.a.d
    public final c[] b() {
        return a(200);
    }

    @Override // com.lenovo.lps.reaper.sdk.a.d
    public final int c() {
        return this.b.g();
    }
}

package com.lenovo.lps.sus.d;

import android.os.Message;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public class e implements a {
    final /* synthetic */ c a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public e(c cVar) {
        this.a = cVar;
    }

    @Override // com.lenovo.lps.sus.d.a
    public void a(long j, long j2) {
        if (0 != j2) {
            this.a.i = System.currentTimeMillis();
            this.a.d = 100 * j;
            if (this.a.d > ((long) this.a.e) * j2 && this.a.i >= this.a.h + 400) {
                this.a.f = (int) (this.a.d / j2);
                this.a.g = this.a.f;
                if (this.a.f > 100) {
                    this.a.g = 100;
                    b.b(a.b, "progressNum error!!! downloadSize =" + j + "   fileSize=" + j2 + "   progressNum=" + this.a.f + "   tempProgressNums=" + this.a.g);
                }
                Message message = new Message();
                message.what = 1;
                message.getData().putInt("progressId", 1);
                message.getData().putInt("progress", this.a.g);
                b.a(a.b, "downloadSize =" + j + "   fileSize=" + j2 + "   progressNum=" + this.a.f);
                if (this.a.j != null) {
                    this.a.j.sendMessage(message);
                }
                this.a.e = this.a.f;
                this.a.h = this.a.i;
            }
        }
    }

    @Override // com.lenovo.lps.sus.d.a
    public void a(String str) {
        this.a.e = 0;
        Message message = new Message();
        message.what = 2;
        message.obj = str;
        if (this.a.j != null) {
            this.a.j.sendMessage(message);
        }
    }

    @Override // com.lenovo.lps.sus.d.a
    public void a(boolean z) {
        this.a.e = 0;
        Message message = new Message();
        message.what = 3;
        message.obj = Boolean.valueOf(z);
        if (this.a.j != null) {
            this.a.j.sendMessage(message);
        }
    }
}

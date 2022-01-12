package com.lenovo.lps.reaper.sdk.c;

import com.lenovo.lps.reaper.sdk.a.c;
import com.lenovo.lps.reaper.sdk.b.a;
import com.lenovo.lps.reaper.sdk.d.f;
import com.lenovo.lps.reaper.sdk.e.e;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
/* loaded from: classes.dex */
public final class b {
    private a a;

    private void a(byte[] bArr, String str) {
        DefaultHttpClient defaultHttpClient;
        if (bArr != null && bArr.length != 0) {
            try {
                HttpPost httpPost = new HttpPost(str);
                ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bArr);
                byteArrayEntity.setContentType("binary/octet-stream");
                httpPost.setEntity(byteArrayEntity);
                httpPost.addHeader("User-Agent", this.a.k());
                BasicHttpParams basicHttpParams = new BasicHttpParams();
                basicHttpParams.setParameter("http.connection.timeout", 6000);
                httpPost.setParams(basicHttpParams);
                defaultHttpClient = new DefaultHttpClient();
                defaultHttpClient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
                try {
                    HttpResponse execute = defaultHttpClient.execute(httpPost);
                    HttpEntity entity = execute.getEntity();
                    if (entity != null) {
                        entity.consumeContent();
                    }
                    if (execute.getStatusLine().getStatusCode() != 200) {
                        throw new Exception("response is not ok!");
                    }
                } catch (Exception e) {
                    throw e;
                }
            } finally {
                defaultHttpClient.getConnectionManager().shutdown();
            }
        }
    }

    public final void a(a aVar) {
        this.a = aVar;
    }

    public final c[] a(c[] cVarArr) {
        int length;
        String format;
        if (cVarArr == null || (length = cVarArr.length) == 0) {
            return null;
        }
        a aVar = this.a;
        if (a.n() == null) {
            com.lenovo.lps.reaper.sdk.e.b.d("HttpRequestHandler", "don't report events because server url is null.");
            return null;
        }
        StringBuilder sb = new StringBuilder(500);
        int i = length;
        for (c cVar : cVarArr) {
            if (cVar == null) {
                com.lenovo.lps.reaper.sdk.e.b.d("HttpRequestHandler", "event is null object.");
                i--;
            } else {
                if (cVar.j().equals("__PAGEVIEW__")) {
                    format = String.format("ctx=%s!%s!%s!%s!%s!%d!%s!%d!%d!%d!%d!%d!%d!%d!%s!%s!%s&evt=%s!%s", "1.8.6", Integer.valueOf(cVar.m()), cVar.d(), this.a.e(), this.a.g(), Integer.valueOf(cVar.e()), this.a.d(), Integer.valueOf(cVar.c()), Long.valueOf(cVar.f()), Long.valueOf(cVar.g()), Long.valueOf(cVar.h()), Long.valueOf(cVar.o()), Integer.valueOf(cVar.i()), Integer.valueOf(cVar.n()), e.b(), com.lenovo.lps.reaper.sdk.e.a.a(e.a()), this.a.j(), com.lenovo.lps.reaper.sdk.e.a.a(cVar.k()), com.lenovo.lps.reaper.sdk.e.a.a(cVar));
                } else {
                    Object[] objArr = new Object[21];
                    objArr[0] = "1.8.6";
                    objArr[1] = cVar.d();
                    objArr[2] = this.a.e();
                    objArr[3] = this.a.g();
                    objArr[4] = Integer.valueOf(cVar.e());
                    objArr[5] = this.a.d();
                    objArr[6] = Integer.valueOf(cVar.c());
                    objArr[7] = Long.valueOf(cVar.f());
                    objArr[8] = Long.valueOf(cVar.g());
                    objArr[9] = Long.valueOf(cVar.h());
                    objArr[10] = Long.valueOf(cVar.o());
                    objArr[11] = Integer.valueOf(cVar.i());
                    objArr[12] = Integer.valueOf(cVar.n());
                    objArr[13] = e.b();
                    objArr[14] = com.lenovo.lps.reaper.sdk.e.a.a(e.a());
                    objArr[15] = this.a.j();
                    objArr[16] = com.lenovo.lps.reaper.sdk.e.a.a(cVar.j());
                    objArr[17] = com.lenovo.lps.reaper.sdk.e.a.a(cVar.k());
                    objArr[18] = cVar.l() == null ? "" : com.lenovo.lps.reaper.sdk.e.a.a(cVar.l());
                    objArr[19] = Integer.valueOf(cVar.m());
                    objArr[20] = com.lenovo.lps.reaper.sdk.e.a.a(cVar);
                    format = String.format("ctx=%s!0!%s!%s!%s!%d!%s!%d!%d!%d!%d!%d!%d!%d!%s!%s!%s&evt=%s!%s!%s!%d!%s", objArr);
                }
                sb.append(format);
                sb.append("\n");
            }
        }
        try {
            com.lenovo.lps.reaper.sdk.e.b.b("HttpRequestHandler", "events number: " + i);
            com.lenovo.lps.reaper.sdk.e.b.b("HttpRequestHandler", "events length: " + sb.length());
            if (f.a(i)) {
                byte[] a = com.lenovo.lps.reaper.sdk.e.c.a(sb.toString().getBytes());
                com.lenovo.lps.reaper.sdk.e.b.b("HttpRequestHandler", "bytes length after compress: " + a.length);
                a aVar2 = this.a;
                a(a, a.m());
            } else {
                byte[] bytes = sb.toString().getBytes();
                com.lenovo.lps.reaper.sdk.e.b.b("HttpRequestHandler", "no compress: " + bytes.length);
                a aVar3 = this.a;
                a(bytes, a.n());
            }
            com.lenovo.lps.reaper.sdk.e.b.b("HttpRequestHandler", "post finished.");
            return cVarArr;
        } catch (Exception e) {
            com.lenovo.lps.reaper.sdk.e.b.d("HttpRequestHandler", "Exception when post events. " + e.getMessage());
            return null;
        }
    }
}

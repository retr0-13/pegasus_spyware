package com.lenovo.lps.reaper.sdk.c;

import android.util.Log;
import com.lenovo.lps.reaper.sdk.e.b;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/* loaded from: classes.dex */
public final class a implements Runnable {
    private final com.lenovo.lps.reaper.sdk.b.a a;

    public a(com.lenovo.lps.reaper.sdk.b.a aVar) {
        this.a = aVar;
    }

    private void a(String str) {
        try {
            JSONArray jSONArray = new JSONArray(str);
            int length = jSONArray.length();
            this.a.b();
            b.c("ConfigurationUpdateTask", "Configuration Update: ");
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("Category");
                JSONObject jSONObject2 = jSONObject.getJSONObject("Action");
                String[] strArr = new String[jSONObject2.length()];
                boolean[] zArr = new boolean[jSONObject2.length()];
                Iterator<String> keys = jSONObject2.keys();
                int i2 = 0;
                while (keys.hasNext()) {
                    String next = keys.next();
                    strArr[i2] = next;
                    zArr[i2] = jSONObject2.getBoolean(next);
                    if (b.a()) {
                        b.c("ConfigurationUpdateTask", String.valueOf(strArr[i2]) + ":" + zArr[i2]);
                    }
                    i2++;
                }
                if (!this.a.a(string, strArr, zArr)) {
                    Log.e("ConfigurationUpdateTask", "process response fail. ");
                }
            }
        } catch (JSONException e) {
            Log.e("ConfigurationUpdateTask", "process response fail. " + e.getMessage());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [org.apache.http.client.HttpClient] */
    /* JADX WARN: Type inference failed for: r1v7, types: [org.apache.http.impl.client.DefaultHttpClient, org.apache.http.client.HttpClient] */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Unknown variable types count: 1 */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void run() {
        /*
            Method dump skipped, instructions count: 292
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.lps.reaper.sdk.c.a.run():void");
    }
}

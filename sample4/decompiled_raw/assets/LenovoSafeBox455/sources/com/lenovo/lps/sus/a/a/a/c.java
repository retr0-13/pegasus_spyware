package com.lenovo.lps.sus.a.a.a;

import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/* loaded from: classes.dex */
public class c {
    private static d a = new d();

    public static String a(String str) {
        b.a(a.b, "downloadurl=" + str);
        if (str.indexOf(a.l) > 0) {
            str = a(str.replace(a.m, ""), -1, 0, 0);
        }
        b.a(a.b, "HDSdownloadurl=" + str);
        return str;
    }

    private static String a(String str, long j, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append(a.k);
        sb.append(String.format("/*%s*", a.a()));
        try {
            sb.append(URLEncoder.encode(a.a(String.format("%s|%d|%d|%d", str, Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2))), b.a));
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new Exception("don't support charset UTF-8!");
        }
    }
}

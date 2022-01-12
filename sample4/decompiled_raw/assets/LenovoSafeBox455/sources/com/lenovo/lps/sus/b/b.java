package com.lenovo.lps.sus.b;

import java.util.HashMap;
import java.util.Map;
/* loaded from: classes.dex */
public enum b {
    SUS_STARTUPDATE_EVENT(0),
    SUS_UPDATE_PROMPT_EVENT(1),
    SUS_INSTALLAPK_EVENT(2),
    SUS_REQNEWAPPVERSION_RESPONE_EVENT(3),
    SUS_USER_REQUPDATE_EVENT(4),
    SUS_USER_CHANGESETTINGS_EVENT(5),
    SUS_QUERY_EXCEPTION_EVENT(6),
    SUS_DOWNLOAD_EXCEPTION_EVENT(7),
    SUS_TESTSUSSERVER_EVENT(8),
    SUS_FINISH_EVENT(9);
    
    static Map l = new HashMap();
    int k;

    b(int i) {
        this.k = i;
    }

    public static int a(b bVar) {
        if (l.size() == 0) {
            c();
        }
        return bVar.ordinal();
    }

    public static b a(int i) {
        if (l.size() == 0) {
            c();
        }
        return (b) l.get(Integer.valueOf(i));
    }

    private static void c() {
        System.out.println("init");
        b[] b = b();
        for (b bVar : b) {
            l.put(Integer.valueOf(bVar.a()), bVar);
        }
    }

    public int a() {
        return this.k;
    }
}

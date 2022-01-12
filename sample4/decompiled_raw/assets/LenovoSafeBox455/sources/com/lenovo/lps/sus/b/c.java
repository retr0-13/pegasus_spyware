package com.lenovo.lps.sus.b;
/* loaded from: classes.dex */
public class c {
    public static d a = d.UPDATEACTION_NORMAL;

    public static int a() {
        return a.ordinal();
    }

    public static void a(int i) {
        if (i == d.UPDATEACTION_NORMAL.ordinal()) {
            a = d.UPDATEACTION_NORMAL;
        } else if (i == d.UPDATEACTION_NEVERPROMPT.ordinal()) {
            a = d.UPDATEACTION_NEVERPROMPT;
        }
    }

    public static void a(d dVar) {
        a = dVar;
    }

    public static d b() {
        return a;
    }

    public static d b(int i) {
        if (i == d.UPDATEACTION_NORMAL.ordinal()) {
            return d.UPDATEACTION_NORMAL;
        }
        if (i == d.UPDATEACTION_NEVERPROMPT.ordinal()) {
            return d.UPDATEACTION_NEVERPROMPT;
        }
        return null;
    }
}

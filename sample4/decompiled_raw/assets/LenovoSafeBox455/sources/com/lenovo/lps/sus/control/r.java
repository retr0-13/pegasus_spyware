package com.lenovo.lps.sus.control;

import android.content.Context;
import android.widget.Toast;
import com.lenovo.lps.sus.EventType;
import com.lenovo.lps.sus.SUSListener;
import com.lenovo.lps.sus.b.e;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
/* loaded from: classes.dex */
public final class r {
    private static int a = 0;
    private static m b = null;
    private static SUSListener c = null;
    private static Boolean d = false;
    private static Boolean e = false;
    private static Boolean f = false;
    private static String g = null;
    private static long h = 0;
    private static long i = 0;

    public static void a(SUSListener sUSListener) {
        c = sUSListener;
        if (b != null) {
            b.a(sUSListener);
        }
    }

    public static void a(String str, long j, long j2) {
        g = str;
        h = j;
        i = j2;
        if (b != null) {
            b.a(str, j, j2);
        }
    }

    public static void a(boolean z) {
        d = Boolean.valueOf(z);
    }

    public static boolean a() {
        return 1 == g();
    }

    public static boolean a(Context context, y yVar, e eVar) {
        String b2;
        if (context == null) {
            System.out.println("The context is illegal!");
            b.b(a.b, "The context is illegal!");
            return false;
        }
        b.a(a.b, String.format("SUS input params: context=%s; updateTransactionType=%s", String.valueOf(context), String.valueOf(yVar)));
        if (!a.o(context)) {
            System.out.println("Network unavailable!");
            b.a(a.b, "Network unavailable!");
            if (a()) {
                if (a.q()) {
                    return false;
                }
                a.e(false);
                return false;
            } else if (!a.b() || d() || context == null) {
                return false;
            } else {
                String b3 = a.b(context, "SUS_MSG_FAIL_NETWORKUNAVAILABLE");
                if (b3 != null && b3.length() > 0) {
                    Toast.makeText(context, b3, 1).show();
                }
                if (c == null) {
                    return false;
                }
                c.onUpdateNotification(EventType.SUS_FAIL_NETWORKUNAVAILABLE, "Network unavailable!");
                return false;
            }
        } else if (d.booleanValue() && !a.j(context)) {
            System.out.println("isn't wlan connect");
            b.a(a.b, "isn't wlan connect");
            if (a.b() && !d() && context != null && (b2 = a.b(context, "SUS_MSG_FAIL_NOWLANCONNECTED")) != null && b2.length() > 0) {
                Toast.makeText(context, b2, 1).show();
            }
            if (c == null) {
                return false;
            }
            c.onUpdateNotification(EventType.SUS_FAIL_NOWLANCONNECTED, "Please open the WLAN!");
            return false;
        } else if (!f()) {
            System.out.println("Update pengding!");
            b.a(a.b, "Update pengding!");
            if (!a.q()) {
                a.e(false);
            }
            if (c == null) {
                return false;
            }
            c.onUpdateNotification(EventType.SUS_WARNING_PENDING, "Update pengding!");
            return false;
        } else {
            b = new m(context);
            if (b == null) {
                System.out.println("UpdateTransaction fail!");
                b.b(a.b, "UpdateTransaction fail!");
                b();
                return false;
            }
            a(c);
            a(g, h, i);
            b.a(yVar, eVar);
            return true;
        }
    }

    public static void b() {
        b.a(a.b, "SUSController finish entry");
        if (a()) {
            h();
        }
        if (b != null) {
            if (a.r()) {
                SUSCustdefNotificationActivity.a(b.p());
            } else {
                SUSNotificationActivity.a();
            }
            b.a();
            b = null;
        }
    }

    public static void b(boolean z) {
        b.a(z);
    }

    public static void c(boolean z) {
        a.a(z);
    }

    public static boolean c() {
        return d.booleanValue();
    }

    public static void d(boolean z) {
        e = Boolean.valueOf(z);
    }

    public static boolean d() {
        return e.booleanValue();
    }

    public static void e(boolean z) {
        f = Boolean.valueOf(z);
    }

    public static boolean e() {
        return f.booleanValue();
    }

    public static void f(boolean z) {
        a.f(z);
    }

    public static boolean f() {
        if (a > 0) {
            return false;
        }
        if (a == 0) {
            a++;
            return true;
        }
        System.out.println("updateTransactionRefNum < 0 !!!");
        return false;
    }

    public static int g() {
        return a;
    }

    public static void g(boolean z) {
        a.c(z);
    }

    public static boolean h() {
        if (a > 0) {
            a--;
            return true;
        }
        System.out.println("updateTransactionRefNum <= 0 !!!");
        return false;
    }

    public static Context i() {
        if (b != null) {
            return b.p();
        }
        return null;
    }
}

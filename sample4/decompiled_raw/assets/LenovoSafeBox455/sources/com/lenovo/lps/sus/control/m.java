package com.lenovo.lps.sus.control;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.widget.Toast;
import com.lenovo.lps.sus.EventType;
import com.lenovo.lps.sus.SUSListener;
import com.lenovo.lps.sus.b.c;
import com.lenovo.lps.sus.b.d;
import com.lenovo.lps.sus.c.a;
import com.lenovo.lps.sus.c.b;
import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
/* loaded from: classes.dex */
public class m {
    private static /* synthetic */ int[] Q;
    private static /* synthetic */ int[] R;
    private Context c;
    private Handler d;
    private v e;
    public static String a = null;
    public static boolean b = true;
    private static w N = null;
    private c f = null;
    private int g = 0;
    private String h = null;
    private String i = null;
    private String j = null;
    private String k = null;
    private String l = null;
    private String m = null;
    private String n = null;
    private String o = null;
    private String p = null;
    private String q = null;
    private String r = null;
    private String s = null;
    private String t = null;
    private String u = null;
    private boolean v = true;
    private boolean w = true;
    private boolean x = false;
    private String y = null;
    private p z = null;
    private String A = null;
    private File B = null;
    private String C = null;
    private long D = 0;
    private String E = null;
    private SUSListener F = null;
    private SUSListener G = null;
    private String H = null;
    private long I = 0;
    private long J = 0;
    private int K = 0;
    private long L = 0;
    private long M = 0;
    private y O = y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_USERSETTINGS;
    private u P = u.SUS_CUSTOM_DOWNLOADMANAGER_NOTIFICATION;

    public m(Context context) {
        this.c = null;
        this.d = null;
        this.e = null;
        this.c = context.getApplicationContext();
        this.d = new s(this);
        b.a(a.b, "myMSGHandler=" + this.d);
        this.e = new v(this.c, this.d);
        b.a(a.b, "mySUSUIHandler=" + this.e);
    }

    public static void b(String str) {
        a = str;
    }

    public static String c(String str) {
        if (str == null) {
            return "{\"RES\":\"NOTFOUND\"}";
        }
        if (str != null && str.length() <= 0) {
            return "{\"RES\":\"NOTFOUND\"}";
        }
        String[] split = str.split(a.f);
        if (split == null || split.length <= 8) {
            return (split == null || split.length <= 2 || split.length >= 4 || !a.M.equals(split[1])) ? (split == null || split.length <= 2 || split.length >= 4 || a.N.equals(split[1])) ? "{\"RES\":\"NOTFOUND\"}" : "{\"RES\":\"NOTFOUND\"}" : "{\"RES\":\"LATESTVERSION\"}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{\"RES\":\"");
        sb.append(URLEncoder.encode("SUCCESS"));
        sb.append("\"");
        sb.append(",\"ChannelKey\":\"");
        String str2 = "null";
        if (!"Common".equals(split[2])) {
            str2 = split[2];
        }
        sb.append(URLEncoder.encode(str2));
        sb.append("\"");
        sb.append(",\"VerCode\":\"");
        sb.append(URLEncoder.encode(split[3]));
        sb.append("\"");
        sb.append(",\"VerName\":\"");
        sb.append(URLEncoder.encode(split[4]));
        sb.append("\"");
        sb.append(",\"DownloadURL\":\"");
        sb.append(URLEncoder.encode(split[5]));
        sb.append("\"");
        sb.append(",\"Size\":\"");
        sb.append(URLEncoder.encode(split[6]));
        sb.append("\"");
        sb.append(",\"UpdateDesc\":\"");
        sb.append(URLEncoder.encode(split[7]));
        sb.append("\"");
        sb.append(",\"FileName\":\"");
        sb.append(URLEncoder.encode(split[8]));
        sb.append("\"");
        sb.append("}");
        return sb.toString();
    }

    public static String l() {
        return a;
    }

    public static String m() {
        return "{\"RES\":\"NOTFOUND\"}";
    }

    public static String n() {
        return "{\"RES\":\"LATESTVERSION\"}";
    }

    static /* synthetic */ int[] q() {
        int[] iArr = Q;
        if (iArr == null) {
            iArr = new int[y.a().length];
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_BYAPPKEY_CUSTINPUT_NOUSERSETTINGS.ordinal()] = 6;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_CUSTDM2ACTIVITY__NOUSERSETTINGS.ordinal()] = 7;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_CUSTINPUT_NOUSERSETTINGS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_CUSTINPUT_NOUSERSETTINGS_AUTOINSTALL.ordinal()] = 5;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_CUSTINPUT_NOUSERSETTINGS_CUSTCONF.ordinal()] = 4;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_CUSTINPUT_QUERYALLVER.ordinal()] = 9;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_CUSTINPUT_QUERYLATESTVER.ordinal()] = 8;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_IGNOREUSERSETTINGS.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_USERSETTINGS.ordinal()] = 1;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_DOWNLOAD_BYURL.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_INSTALLAPP.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                iArr[y.SUS_UPDATETRANSATION_TYPE_TESTSUSSERVER.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            Q = iArr;
        }
        return iArr;
    }

    static /* synthetic */ int[] r() {
        int[] iArr = R;
        if (iArr == null) {
            iArr = new int[com.lenovo.lps.sus.b.b.b().length];
            try {
                iArr[com.lenovo.lps.sus.b.b.SUS_DOWNLOAD_EXCEPTION_EVENT.ordinal()] = 8;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[com.lenovo.lps.sus.b.b.SUS_FINISH_EVENT.ordinal()] = 10;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[com.lenovo.lps.sus.b.b.SUS_INSTALLAPK_EVENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[com.lenovo.lps.sus.b.b.SUS_QUERY_EXCEPTION_EVENT.ordinal()] = 7;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[com.lenovo.lps.sus.b.b.SUS_REQNEWAPPVERSION_RESPONE_EVENT.ordinal()] = 4;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[com.lenovo.lps.sus.b.b.SUS_STARTUPDATE_EVENT.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[com.lenovo.lps.sus.b.b.SUS_TESTSUSSERVER_EVENT.ordinal()] = 9;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[com.lenovo.lps.sus.b.b.SUS_UPDATE_PROMPT_EVENT.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[com.lenovo.lps.sus.b.b.SUS_USER_CHANGESETTINGS_EVENT.ordinal()] = 6;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[com.lenovo.lps.sus.b.b.SUS_USER_REQUPDATE_EVENT.ordinal()] = 5;
            } catch (NoSuchFieldError e10) {
            }
            R = iArr;
        }
        return iArr;
    }

    public void a() {
        b.a(a.b, "UpdateTransaction finish entry");
        if (this.e != null) {
            this.e.a();
            this.e = null;
        }
        this.c = null;
        this.d = null;
        this.H = null;
        this.I = 0;
        this.J = 0;
        this.M = 0;
        this.L = 0;
    }

    void a(int i) {
        String n;
        boolean a2;
        String b2;
        this.x = false;
        if (this.H != null) {
            n = this.H;
            a2 = a.a(this.I, this.D, this.J);
        } else {
            n = a.n(this.c);
            this.x = a.e();
            a2 = a.a(this.D);
            if (!a2) {
                a.c(n);
                a2 = a.a(this.D);
            }
        }
        b.a(a.b, "downloadpath=" + n);
        if (!a2) {
            if (a.b() && !r.d() && this.c != null && (b2 = a.b(this.c, "SUS_MSG_INSUFFICIENTSTORAGESPACE")) != null && b2.length() > 0) {
                Toast.makeText(this.c, b2, 1).show();
            }
            this.G = this.F;
            r.b();
            if (this.G != null) {
                this.G.onUpdateNotification(EventType.SUS_FAIL_INSUFFICIENTSTORAGESPACE, "insufficient storage space");
                this.G = null;
            }
            b.a(a.b, "INSUFFICIENTSTORAGESPACE");
            return;
        }
        this.B = new File(n);
        if (this.B != null && !this.B.exists()) {
            this.B.mkdirs();
        }
        if (this.B == null || (this.B != null && !this.B.exists())) {
            b.a(a.b, "The folder isn't exit! path=" + n);
            this.G = this.F;
            r.b();
            if (this.G != null) {
                this.G.onUpdateNotification(EventType.SUS_FAIL_DOWNOLADFOLDER_FOLDER_NOTEXIST, "The download folder does not exist.");
                this.G = null;
            }
            b.a(a.b, "The download folder does not exist.");
            return;
        }
        b(n);
        if (this.F != null) {
            this.F.onUpdateNotification(EventType.SUS_DOWNLOADSTART, "Start the downloading");
        }
        if (this.e == null || this.A == null || n == null || this.C == null || this.D <= 0) {
            b.a(a.b, "updateHandler exception.");
            r.b();
            return;
        }
        this.e.a(i, this.A, n, this.C, this.D, this.P);
    }

    public void a(Message message) {
        String b2;
        String b3;
        String str;
        String b4;
        String b5;
        int i = 0;
        boolean z = true;
        switch (r()[com.lenovo.lps.sus.b.b.a(message.what).ordinal()]) {
            case 1:
                c();
                return;
            case 2:
                b.a(a.b, "SUS_UPDATE_PROMPT_MSG mySUSUIHandler=" + this.e);
                switch (q()[this.O.ordinal()]) {
                    case 4:
                        if (!this.v) {
                            r.b();
                            return;
                        }
                    case 3:
                    case 6:
                    case MotionEventCompat.ACTION_HOVER_MOVE /* 7 */:
                        z = false;
                        break;
                    case 5:
                        Message message2 = new Message();
                        message.what = com.lenovo.lps.sus.b.b.a(com.lenovo.lps.sus.b.b.SUS_USER_REQUPDATE_EVENT);
                        message.obj = 0;
                        this.d.sendMessage(message2);
                        break;
                    case 8:
                    case MotionEventCompat.ACTION_HOVER_ENTER /* 9 */:
                        r.b();
                        return;
                }
                if (this.e != null) {
                    this.e.a((com.lenovo.lps.sus.b.a) message.obj, c.b(), z);
                    return;
                }
                return;
            case 3:
                if (y.SUS_UPDATETRANSATION_TYPE_BYPACKAGENAME_CUSTINPUT_NOUSERSETTINGS_CUSTCONF != this.O || this.w) {
                    String str2 = (String) message.obj;
                    if (str2 == null || (str2 != null && str2.length() <= 0)) {
                        r.b();
                        return;
                    } else if (y.SUS_UPDATETRANSATION_TYPE_DOWNLOAD_BYURL == this.O) {
                        if (a.b() && !r.d() && this.c != null && (b5 = a.b(this.c, "SUS_MSG_DOWNLOADCOMPLETE")) != null && b5.length() > 0) {
                            Toast.makeText(this.c, b5, 1).show();
                        }
                        this.G = this.F;
                        r.b();
                        if (this.G != null) {
                            this.G.onUpdateNotification(EventType.SUS_DOWNLOADCOMPLETE, str2);
                        }
                        this.G = null;
                        return;
                    } else {
                        if (y.SUS_UPDATETRANSATION_TYPE_INSTALLAPP != this.O) {
                            if (a.b() && !r.d() && this.c != null && (b4 = a.b(this.c, "SUS_MSG_DOWNLOADCOMPLETE")) != null && b4.length() > 0) {
                                Toast.makeText(this.c, b4, 1).show();
                            }
                            if (this.F != null) {
                                this.F.onUpdateNotification(EventType.SUS_DOWNLOADCOMPLETE, str2);
                            }
                        }
                        if (this.x && str2 != null) {
                            a.a("777", str2);
                        }
                        File file = new File(str2);
                        if (!(file == null || !file.exists() || ("file://" + file.getAbsolutePath()) == null || this.c == null)) {
                            Intent intent = new Intent("android.intent.action.VIEW");
                            intent.setFlags(268435456);
                            intent.setDataAndType(Uri.parse(str), "application/vnd.android.package-archive");
                            this.c.startActivity(intent);
                        }
                        r.b();
                        return;
                    }
                } else {
                    r.b();
                    return;
                }
            case 4:
                a((String) message.obj);
                return;
            case 5:
                if (!(message == null || message.obj == null)) {
                    i = ((Integer) message.obj).intValue();
                }
                if (i == 0) {
                    this.L = System.currentTimeMillis();
                }
                a(i);
                return;
            case 6:
                b(((Integer) message.obj).intValue());
                return;
            case MotionEventCompat.ACTION_HOVER_MOVE /* 7 */:
                if (a.r()) {
                    SUSCustdefNotificationActivity.a(this.c);
                } else {
                    SUSNotificationActivity.a();
                }
                if (a.b() && !r.d() && this.c != null && (b3 = a.b(this.c, "SUS_MSG_UPDATE_EXCEPTION")) != null && b3.length() > 0) {
                    Toast.makeText(this.c, b3, 1).show();
                }
                this.G = this.F;
                r.b();
                if (this.G != null) {
                    this.G.onUpdateNotification(EventType.SUS_QUERY_RESP, "EXCEPTION");
                    this.G = null;
                    return;
                }
                return;
            case 8:
                boolean booleanValue = ((Boolean) message.obj).booleanValue();
                if (this.K >= 2 || booleanValue) {
                    if (a.r()) {
                        SUSCustdefNotificationActivity.a(this.c);
                    } else {
                        SUSNotificationActivity.a();
                    }
                    if (!r.d() && this.c != null && (b2 = a.b(this.c, "SUS_MSG_UPDATE_EXCEPTION")) != null && b2.length() > 0) {
                        Toast.makeText(this.c, b2, 1).show();
                    }
                    if (this.d != null) {
                        Message message3 = new Message();
                        message3.what = com.lenovo.lps.sus.b.b.a(com.lenovo.lps.sus.b.b.SUS_FINISH_EVENT);
                        this.d.sendMessageDelayed(message3, 300);
                    }
                    this.G = this.F;
                    if (this.G != null && !booleanValue) {
                        this.G.onUpdateNotification(EventType.SUS_FAIL_DOWNLOAD_EXCEPTION, null);
                        this.G = null;
                        return;
                    }
                    return;
                }
                this.K++;
                if (this.c != null) {
                    if (1 == this.K) {
                        if (this.e != null && System.currentTimeMillis() - this.L > 4000) {
                            a.e(true);
                            this.M = System.currentTimeMillis();
                        }
                    } else if (2 == this.K && (((this.M > 0 && System.currentTimeMillis() - this.M > 9000) || (0 == this.M && System.currentTimeMillis() - this.L > 6000)) && this.e != null)) {
                        a.e(true);
                    }
                }
                if (this.d != null) {
                    Message message4 = new Message();
                    message4.what = com.lenovo.lps.sus.b.b.a(com.lenovo.lps.sus.b.b.SUS_USER_REQUPDATE_EVENT);
                    message4.obj = Integer.valueOf(this.K);
                    this.d.sendMessage(message4);
                    return;
                }
                return;
            case MotionEventCompat.ACTION_HOVER_ENTER /* 9 */:
                this.G = this.F;
                r.b();
                if (this.G != null) {
                    String str3 = (String) message.obj;
                    if (str3 == null || str3.length() <= 0) {
                        str3 = "FAIL";
                    }
                    this.G.onUpdateNotification(EventType.SUS_TESTSERVER_RESP, str3);
                }
                this.G = null;
                return;
            case MotionEventCompat.ACTION_HOVER_EXIT /* 10 */:
                r.b();
                return;
            default:
                return;
        }
    }

    public void a(SUSListener sUSListener) {
        this.F = sUSListener;
    }

    public void a(p pVar) {
        this.z = pVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void a(com.lenovo.lps.sus.control.y r5, com.lenovo.lps.sus.b.e r6) {
        /*
            Method dump skipped, instructions count: 286
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.lps.sus.control.m.a(com.lenovo.lps.sus.control.y, com.lenovo.lps.sus.b.e):void");
    }

    public void a(String str) {
        String str2;
        String str3;
        String b2;
        String b3;
        String str4 = null;
        if (str == null || (str != null && str.length() <= 0)) {
            r.b();
            return;
        }
        b.a(a.b, "responeStr = " + str);
        String decode = URLDecoder.decode(str);
        b.a(a.b, "myParseStr = " + decode);
        String[] split = decode.split(a.f);
        if (split == null || (split != null && split.length < 3)) {
            this.G = this.F;
            r.b();
            if (this.G != null) {
                this.G.onUpdateNotification(EventType.SUS_QUERY_RESP, c(decode));
                this.G = null;
                return;
            }
            return;
        }
        switch (q()[this.O.ordinal()]) {
            case 8:
            case MotionEventCompat.ACTION_HOVER_ENTER /* 9 */:
                this.G = this.F;
                r.b();
                if (decode != null && this.G != null) {
                    this.G.onUpdateNotification(EventType.SUS_QUERY_RESP, c(decode));
                    return;
                }
                return;
            default:
                if (this.F != null) {
                    this.F.onUpdateNotification(EventType.SUS_QUERY_RESP, c(decode));
                }
                String str5 = split[1];
                if (str5 == null || !str5.equals(a.L) || split.length < 8) {
                    str2 = null;
                    str3 = null;
                } else {
                    b.a(a.b, "updateInfoStr = " + str5);
                    str3 = split[3];
                    b.a(a.b, "newVersionCode = " + str3);
                    if (str3 == null || !a.d(str3)) {
                        r.b();
                        return;
                    }
                    str2 = split[4];
                    b.a(a.b, "newVersionName = " + str2);
                    this.A = split[5];
                    b.a(a.b, "myPackageUpdateURL = " + this.A);
                    if (split[6] == null || !a.d(split[6])) {
                        r.b();
                        return;
                    }
                    this.D = Long.valueOf(split[6]).longValue();
                    b.a(a.b, "myPackageSize = " + this.D);
                    str4 = split[7];
                    b.a(a.b, "newVersionDescribe = " + str4);
                    this.C = split[8];
                    b.a(a.b, "myDownloadFileName = " + this.C);
                    this.E = split[9];
                    b.a(a.b, "myCtrlType = " + this.E);
                }
                if (str5 != null && str5.equals(a.L)) {
                    com.lenovo.lps.sus.b.a aVar = new com.lenovo.lps.sus.b.a(String.valueOf(this.g), this.h, str3, str2, this.A, Long.valueOf(this.D), str4);
                    if (aVar == null || str2 == null || str3 == null || this.A == null || 0 >= this.D || str4 == null || this.E == null) {
                        r.b();
                        return;
                    }
                    Message message = new Message();
                    message.what = com.lenovo.lps.sus.b.b.a(com.lenovo.lps.sus.b.b.SUS_UPDATE_PROMPT_EVENT);
                    message.obj = aVar;
                    if (this.d != null) {
                        this.d.sendMessage(message);
                        return;
                    }
                    return;
                } else if (str5 == null || !str5.equals(a.M)) {
                    if (a.b() && !r.d() && this.c != null && (b2 = a.b(this.c, "SUS_MSG_NOTFOUND")) != null && b2.length() > 0) {
                        Toast.makeText(this.c, b2, 1).show();
                    }
                    if (this.F != null) {
                        this.F.onUpdateNotification(EventType.SUS_QUERY_RESP, m());
                    }
                    b.a(a.b, "NOTFOUND");
                    r.b();
                    return;
                } else {
                    if (a.b() && !r.d() && this.c != null && (b3 = a.b(this.c, "SUS_MSG_LATESTVERSION")) != null && b3.length() > 0) {
                        Toast.makeText(this.c, b3, 1).show();
                    }
                    if (this.F != null) {
                        this.F.onUpdateNotification(EventType.SUS_QUERY_RESP, n());
                    }
                    b.a(a.b, "LATESTVERSION");
                    r.b();
                    return;
                }
        }
    }

    public void a(String str, long j, long j2) {
        this.H = str;
        this.I = j;
        this.J = j2;
    }

    public void b() {
        File file;
        if (this.B != null && this.B.exists() && this.C != null && (file = new File(String.valueOf(this.B.toString()) + "/" + this.C)) != null && file.exists()) {
            file.delete();
        }
    }

    public void b(int i) {
        if (this.f != null && this.c != null) {
            c.a(i);
            SharedPreferences.Editor edit = this.c.getSharedPreferences(a.t, 0).edit();
            edit.putInt(a.u, c.a());
            edit.putString(a.v, "");
            edit.commit();
        }
    }

    public void c() {
        this.z = new p(this.d, this.y);
        if (this.z != null) {
            this.z.start();
        } else {
            b.b(a.b, "myReqNewAppVersionThread == null!");
        }
    }

    public boolean d() {
        if (this.c == null) {
            b.b(a.b, " null == myApplicationContext ");
            return false;
        }
        this.g = a.d(this.c);
        if (this.g < 0) {
            System.out.println("VersionCode fail!");
            b.b(a.b, "VersionCode fail!");
            return false;
        }
        this.h = a.e(this.c);
        if (this.h == null || (this.h != null && this.h.length() == 0)) {
            System.out.println("VersionName is null!");
        }
        this.j = a.a(this.c);
        if (this.j != null) {
            this.j.length();
        }
        this.l = a.f(this.c);
        if (this.l == null || this.l.length() == 0) {
            System.out.println("PackageName fail!");
            b.b(a.b, "PackageName fail!");
            return false;
        }
        a.b(this.c);
        this.c.getSharedPreferences(a.t, 0);
        String c = a.c(this.c);
        this.k = a.b(this.c);
        if (this.k == null || (this.k != null && this.k.length() == 0)) {
            this.k = c;
        }
        this.i = a.g(this.c);
        if (this.i != null && this.i.length() != 0) {
            return true;
        }
        System.out.println("AppName fail!");
        b.b(a.b, "AppName fail!");
        return false;
    }

    public boolean e() {
        this.u = a.q(this.c);
        this.n = a.r(this.c);
        this.o = a.s(this.c);
        this.p = a.k();
        this.q = a.m();
        this.r = a.n();
        this.s = a.o();
        this.t = a.p();
        return true;
    }

    public String f() {
        switch (q()[this.O.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case MotionEventCompat.ACTION_HOVER_MOVE /* 7 */:
            case 8:
                this.y = a.a(2, this.i, this.l, String.valueOf(this.g), this.h, this.k, this.m, this.u, this.n, this.o, this.p, this.q, this.r, this.s, this.t);
                break;
            case 6:
                this.y = a.b(1, this.i, this.j, String.valueOf(this.g), this.h, this.k, this.m, this.u, this.n, this.o, this.p, this.q, this.r, this.s, this.t);
                break;
            case 12:
                this.y = a.n;
                break;
        }
        if (this.y == null || this.y.length() == 0) {
            System.out.println("ReqKey fail!");
            b.b(a.b, "ReqKey fail!");
            return null;
        }
        b.a(a.b, "myReqKey=" + this.y);
        return this.y;
    }

    public d g() {
        if (this.f == null) {
            this.f = new c();
            c.a(this.c.getSharedPreferences(a.t, 0).getInt(a.u, 0));
        }
        return c.b();
    }

    public String h() {
        return this.y;
    }

    public int i() {
        return this.g;
    }

    public String j() {
        return this.i;
    }

    public p k() {
        return this.z;
    }

    public void o() {
        N = new w(this.d);
        if (N != null) {
            N.start();
        } else {
            b.b(a.b, "startTestSUSServerThread == null!");
        }
    }

    public Context p() {
        return this.c;
    }
}

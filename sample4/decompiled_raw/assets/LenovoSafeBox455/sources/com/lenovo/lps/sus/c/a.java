package com.lenovo.lps.sus.c;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.lenovo.lps.sus.a.a.a.b;
import com.lenovo.lps.sus.control.SUSCustdefNotificationActivity;
import com.lenovo.lps.sus.control.SUSNotificationActivity;
import com.lenovo.lps.sus.control.r;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
/* loaded from: classes.dex */
public final class a {
    public static final int A = 86400;
    public static final int B = 2;
    public static final int C = 6;
    public static final int D = 2048;
    public static final long E = 3000;
    public static final int F = 6000;
    public static final int G = 10000;
    public static final String H = "SUSDownloadManager";
    public static final String I = "SUS_APPKEY";
    public static final String J = "SUS_CHANNEL";
    public static final String K = "SUS_VERTYPE";
    public static final String L = "Yes";
    public static final String M = "Latest";
    public static final String N = "No";
    public static final int O = 1;
    public static final int P = 8192;
    public static final int Q = 16384;
    public static String R = null;
    public static boolean S = false;
    public static boolean T = false;
    public static boolean U = false;
    public static boolean V = false;
    public static boolean W = false;
    static final /* synthetic */ boolean X;
    private static Boolean Y = null;
    public static Random a = null;
    public static final String b = "SUS";
    public static final String c = "1.0";
    public static final String d = "SUSdownload";
    public static final String e = "|$|$";
    public static final String f = "\\|\\$\\|\\$";
    public static final Long g;
    public static final String h = "http://10.109.2.218:8080/adpserver/";
    public static String i = null;
    public static final String j = "http://susapi.lenovomm.com/adpserver/";
    public static final String k = "http://lcs.lenovomm.com";
    public static final String l = "adp@cluster-1";
    public static final String m = "http://lcs.lenovomm.com/get?path=";
    public static final String n = "http://susapi.lenovomm.com/adpserver/ctrl?CtrlType=testConnect";
    public static final String o = "GetVerInfo?SDKVer=1.0&OSType=Android&ReqType=%s&AppKey=%s&PackName=%s&AppVerCode=%s&AppVerName=%s&VerSubType=%s&AppTags=%s&Resolution=%s&DevID=%s&AndID=%s&OSVer=%s&Lang=%s&Count=%s&DModel=%s&Mfr=%s";
    public static final String p = "GetVIByPN?SDKVer=1.0&OSType=Android&ReqType=%s&PackName=%s&AppVerCode=%s&AppVerName=%s&VerSubType=%s&AppTags=%s&Resolution=%s&DevID=%s&AndID=%s&OSVer=%s&Lang=%s&Count=%s&DModel=%s&Mfr=%s";
    public static final String q = "GetVIByAK?SDKVer=1.0&OSType=Android&ReqType=%s&AppKey=%s&AppVerCode=%s&AppVerName=%s&VerSubType=%s&AppTags=%s&Resolution=%s&DevID=%s&AndID=%s&OSVer=%s&Lang=%s&Count=%s&DModel=%s&Mfr=%s";
    public static final String r = "QueryAVInfo?SDKVer=1.0&OSType=Android&ReqType=%s&PackName=%s&AppVerCode=%s&AppVerName=%s&VerSubType=%s&AppTags=%s&Resolution=%s&DevID=%s&AndID=%s&OSVer=%s&Lang=%s&Count=%s&DModel=%s&Mfr=%s";
    public static final String s = "queryupgrade?PackNameList=%s";
    public static final String t = "SUS_SETTINGS";
    public static final String u = "SUS_UPDATEACTIONTYPE";
    public static final String v = "CHANGEDATE";
    public static final String w = "SUS_IDENTIFICATIONFILE";
    public static final String x = "SUS_IDENTIFICATIONFILE_DOWNLOADURL";
    public static final String y = "SUS_DOWNLOAD_FAIL_NUM";
    public static final int z = 30;

    static {
        X = !a.class.desiredAssertionStatus();
        a = new Random(SystemClock.uptimeMillis());
        g = 5242880L;
        i = "http://susapi.dev.surepush.cn/adpserver/";
        R = null;
        S = true;
        T = X;
        U = X;
        V = X;
        W = X;
        Y = Boolean.valueOf((boolean) X);
    }

    public static int a(Context context, String str, String str2) {
        return context.getResources().getIdentifier(str2, str, context.getPackageName());
    }

    public static String a(int i2) {
        switch (i2) {
            case 0:
                return String.valueOf(d()) + o;
            case 1:
                return String.valueOf(d()) + q;
            case 2:
                return String.valueOf(d()) + p;
            case 3:
                return String.valueOf(d()) + s;
            default:
                if (X) {
                    return null;
                }
                throw new AssertionError();
        }
    }

    public static String a(int i2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14) {
        String encode;
        String str15 = null;
        String str16 = String.valueOf(d()) + p;
        if (str4 == null) {
            encode = null;
        } else {
            try {
                encode = URLEncoder.encode(str4, b.a);
            } catch (UnsupportedEncodingException e2) {
                return str15;
            }
        }
        str15 = String.format(str16, String.valueOf(i2), str2 == null ? null : URLEncoder.encode(str2, b.a), str3 == null ? null : URLEncoder.encode(str3, b.a), encode, str5 == null ? null : URLEncoder.encode(str5, b.a), str6 == null ? null : URLEncoder.encode(str6, b.a), str7 == null ? null : URLEncoder.encode(str7, b.a), str8 == null ? null : URLEncoder.encode(str8, b.a), str9 == null ? null : URLEncoder.encode(str9, b.a), str10 == null ? null : URLEncoder.encode(str10, b.a), str11 == null ? null : URLEncoder.encode(str11, b.a), str12 == null ? null : URLEncoder.encode(str12, b.a), str13 == null ? null : URLEncoder.encode(str13, b.a), str14 == null ? null : URLEncoder.encode(str14, b.a));
        return str15;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String a(android.content.Context r4) {
        /*
            r0 = 0
            android.content.pm.PackageManager r1 = r4.getPackageManager()     // Catch: Exception -> 0x0022
            java.lang.String r2 = r4.getPackageName()     // Catch: Exception -> 0x0022
            r3 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r1 = r1.getApplicationInfo(r2, r3)     // Catch: Exception -> 0x0022
            if (r1 == 0) goto L_0x0026
            android.os.Bundle r1 = r1.metaData     // Catch: Exception -> 0x0022
            if (r1 == 0) goto L_0x0026
            java.lang.String r2 = "SUS_APPKEY"
            java.lang.String r1 = r1.getString(r2)     // Catch: Exception -> 0x0022
        L_0x001b:
            if (r1 == 0) goto L_0x0021
            java.lang.String r0 = r1.trim()
        L_0x0021:
            return r0
        L_0x0022:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0026:
            r1 = r0
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.lps.sus.c.a.a(android.content.Context):java.lang.String");
    }

    public static String a(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e2) {
        }
        messageDigest.update(str.getBytes());
        byte[] digest = messageDigest.digest();
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : digest) {
            stringBuffer.append(Integer.toHexString(b2 & 255));
        }
        return stringBuffer.toString();
    }

    public static String a(String str, Boolean bool, int i2) {
        InputStream inputStream;
        InputStream inputStream2;
        Throwable th;
        BasicHttpParams basicHttpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(basicHttpParams, i2);
        HttpConnectionParams.setSoTimeout(basicHttpParams, i2);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient(basicHttpParams);
        HttpGet httpGet = new HttpGet(str);
        StringBuilder sb = new StringBuilder();
        if (bool.booleanValue()) {
            try {
                HttpResponse execute = defaultHttpClient.execute(httpGet);
                int statusCode = execute.getStatusLine().getStatusCode();
                HttpEntity entity = execute.getEntity();
                if (200 != statusCode || entity == null) {
                    inputStream = null;
                } else {
                    inputStream = entity.getContent();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 8192);
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb.append(readLine);
                        }
                    } catch (IOException e2) {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e3) {
                                return null;
                            }
                        }
                        return sb.toString();
                    } catch (IllegalStateException e4) {
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e5) {
                                return null;
                            }
                        }
                        return sb.toString();
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream2 = inputStream;
                        if (inputStream2 != null) {
                            try {
                                inputStream2.close();
                            } catch (IOException e6) {
                                return null;
                            }
                        }
                        throw th;
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e7) {
                        return null;
                    }
                }
            } catch (IOException e8) {
                inputStream = null;
            } catch (IllegalStateException e9) {
                inputStream = null;
            } catch (Throwable th3) {
                th = th3;
                inputStream2 = null;
            }
            return sb.toString();
        }
        try {
            defaultHttpClient.execute(httpGet);
            return null;
        } catch (IOException e10) {
            return null;
        } catch (ClientProtocolException e11) {
            return null;
        }
    }

    public static void a(String str, String str2) {
        try {
            Runtime.getRuntime().exec("chmod " + str + " " + str2);
        } catch (IOException e2) {
        }
    }

    public static void a(boolean z2) {
        T = z2;
    }

    public static boolean a() {
        return T;
    }

    public static boolean a(long j2) {
        if ((Environment.getExternalStorageState().equals("mounted") ? i() : h()) >= g.longValue() + j2) {
            return true;
        }
        return X;
    }

    public static boolean a(long j2, long j3, long j4) {
        if (j2 >= j3 + j4) {
            return true;
        }
        return X;
    }

    public static boolean a(Context context, String str) {
        if (context.getPackageManager().checkPermission(str, context.getPackageName()) == 0) {
            return true;
        }
        return X;
    }

    public static String b(int i2, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14) {
        String encode;
        String str15 = null;
        String str16 = String.valueOf(d()) + q;
        if (str2 == null) {
            encode = null;
        } else {
            try {
                encode = URLEncoder.encode(str2, b.a);
            } catch (UnsupportedEncodingException e2) {
                return str15;
            }
        }
        str15 = String.format(str16, String.valueOf(i2), encode, str3 == null ? null : URLEncoder.encode(str3, b.a), str4 == null ? null : URLEncoder.encode(str4, b.a), str5 == null ? null : URLEncoder.encode(str5, b.a), str6 == null ? null : URLEncoder.encode(str6, b.a), str7 == null ? null : URLEncoder.encode(str7, b.a), str8 == null ? null : URLEncoder.encode(str8, b.a), str9 == null ? null : URLEncoder.encode(str9, b.a), str10 == null ? null : URLEncoder.encode(str10, b.a), str11 == null ? null : URLEncoder.encode(str11, b.a), str12 == null ? null : URLEncoder.encode(str12, b.a), str13 == null ? null : URLEncoder.encode(str13, b.a), str14 == null ? null : URLEncoder.encode(str14, b.a));
        return str15;
    }

    public static String b(long j2) {
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        return j2 < 1048576 ? String.valueOf(decimalFormat.format(new Float(((float) j2) / 1024.0f).doubleValue())) + "KB" : String.valueOf(decimalFormat.format(new Float(((float) j2) / 1048576.0f).doubleValue())) + "MB";
    }

    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String b(android.content.Context r4) {
        /*
            r0 = 0
            android.content.pm.PackageManager r1 = r4.getPackageManager()     // Catch: Exception -> 0x0022
            java.lang.String r2 = r4.getPackageName()     // Catch: Exception -> 0x0022
            r3 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r1 = r1.getApplicationInfo(r2, r3)     // Catch: Exception -> 0x0022
            if (r1 == 0) goto L_0x0026
            android.os.Bundle r1 = r1.metaData     // Catch: Exception -> 0x0022
            if (r1 == 0) goto L_0x0026
            java.lang.String r2 = "SUS_VERTYPE"
            java.lang.String r1 = r1.getString(r2)     // Catch: Exception -> 0x0022
        L_0x001b:
            if (r1 == 0) goto L_0x0021
            java.lang.String r0 = r1.trim()
        L_0x0021:
            return r0
        L_0x0022:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0026:
            r1 = r0
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.lps.sus.c.a.b(android.content.Context):java.lang.String");
    }

    public static String b(Context context, String str) {
        int identifier = context.getResources().getIdentifier(str, "string", context.getPackageName());
        if (identifier > 0) {
            return context.getResources().getString(identifier);
        }
        return null;
    }

    public static void b(String str) {
        R = str;
    }

    public static void b(boolean z2) {
        U = z2;
    }

    public static boolean b() {
        return U;
    }

    public static int c(Context context, String str) {
        int identifier = context.getResources().getIdentifier(str, "id", context.getPackageName());
        if (identifier < 0) {
            return 0;
        }
        return identifier;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String c(android.content.Context r4) {
        /*
            r0 = 0
            android.content.pm.PackageManager r1 = r4.getPackageManager()     // Catch: Exception -> 0x0022
            java.lang.String r2 = r4.getPackageName()     // Catch: Exception -> 0x0022
            r3 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r1 = r1.getApplicationInfo(r2, r3)     // Catch: Exception -> 0x0022
            if (r1 == 0) goto L_0x0026
            android.os.Bundle r1 = r1.metaData     // Catch: Exception -> 0x0022
            if (r1 == 0) goto L_0x0026
            java.lang.String r2 = "SUS_CHANNEL"
            java.lang.String r1 = r1.getString(r2)     // Catch: Exception -> 0x0022
        L_0x001b:
            if (r1 == 0) goto L_0x0021
            java.lang.String r0 = r1.trim()
        L_0x0021:
            return r0
        L_0x0022:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0026:
            r1 = r0
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.lps.sus.c.a.c(android.content.Context):java.lang.String");
    }

    public static void c(String str) {
        if (str == null) {
            return;
        }
        if (str == null || str.contains("/SUSdownload")) {
            long currentTimeMillis = System.currentTimeMillis();
            File file = new File(str);
            if (file != null && file.isDirectory()) {
                File[] listFiles = file.listFiles();
                for (File file2 : listFiles) {
                    if (file2 != null && file2.isFile() && currentTimeMillis > file2.lastModified() + 1800000) {
                        file2.delete();
                    }
                }
            }
        }
    }

    public static void c(boolean z2) {
        S = z2;
    }

    public static boolean c() {
        return S;
    }

    public static int d(Context context) {
        int i2 = 0;
        try {
            i2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e2) {
        }
        if (i2 < 0) {
            Log.i("SUSSDK", "Versioncode is invalid");
        }
        return i2;
    }

    public static String d() {
        return T ? i : j;
    }

    private void d(Context context, String str) {
        Signature[] signatureArr = null;
        try {
            signatureArr = context.getPackageManager().getPackageInfo(str, 64).signatures;
        } catch (PackageManager.NameNotFoundException e2) {
        }
        Log.d(b, "sigs.len=" + signatureArr.length);
        Log.d(b, signatureArr[0].toCharsString());
    }

    public static void d(boolean z2) {
        W = z2;
    }

    public static boolean d(String str) {
        if (str == null) {
            return X;
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (!Character.isDigit(str.charAt(i2))) {
                return X;
            }
        }
        return true;
    }

    public static String e(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            return null;
        }
    }

    public static void e(boolean z2) {
        Context i2 = r.i();
        if (i2 != null && !q()) {
            Intent intent = new Intent();
            intent.putExtra("FailFlag", z2);
            if (r()) {
                intent.setClass(i2, SUSCustdefNotificationActivity.class);
            } else {
                intent.setClass(i2, SUSNotificationActivity.class);
            }
            intent.setFlags(268435456);
            i2.startActivity(intent);
        }
    }

    public static boolean e() {
        return V;
    }

    public static String f(Context context) {
        return context.getPackageName();
    }

    public static void f(boolean z2) {
        Y = Boolean.valueOf(z2);
    }

    public static boolean f() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return X;
    }

    public static String g() {
        return R;
    }

    public static String g(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (packageInfo != null) {
                return packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
            }
            return null;
        } catch (PackageManager.NameNotFoundException e2) {
            return null;
        }
    }

    public static long h() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String h(android.content.Context r3) {
        /*
            java.lang.String r0 = "phone"
            java.lang.Object r0 = r3.getSystemService(r0)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            if (r0 != 0) goto L_0x0011
            java.lang.String r1 = "MobclickAgent"
            java.lang.String r2 = "No IMEI."
            android.util.Log.w(r1, r2)
        L_0x0011:
            java.lang.String r1 = ""
            java.lang.String r2 = "android.permission.READ_PHONE_STATE"
            boolean r2 = a(r3, r2)     // Catch: Exception -> 0x003f
            if (r2 == 0) goto L_0x0043
            java.lang.String r0 = r0.getDeviceId()     // Catch: Exception -> 0x003f
        L_0x001f:
            if (r0 == 0) goto L_0x0029
            if (r0 == 0) goto L_0x0045
            int r1 = r0.length()
            if (r1 > 0) goto L_0x0045
        L_0x0029:
            java.lang.String r0 = "MobclickAgent"
            java.lang.String r1 = "No IMEI."
            android.util.Log.w(r0, r1)
            java.lang.String r0 = t(r3)
            if (r0 != 0) goto L_0x0045
            java.lang.String r0 = "MobclickAgent"
            java.lang.String r1 = "Failed to take mac as IMEI."
            android.util.Log.w(r0, r1)
            r0 = 0
        L_0x003e:
            return r0
        L_0x003f:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0043:
            r0 = r1
            goto L_0x001f
        L_0x0045:
            java.lang.String r0 = a(r0)
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.lps.sus.c.a.h(android.content.Context):java.lang.String");
    }

    public static long i() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return -1;
        }
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    public static String[] i(Context context) {
        String[] strArr = new String[2];
        strArr[0] = "Unknown";
        strArr[1] = "Unknown";
        if (context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", context.getPackageName()) != 0) {
            strArr[0] = "Unknown";
            return strArr;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            strArr[0] = "Unknown";
            return strArr;
        } else if (connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            strArr[0] = "WLAN";
            return strArr;
        } else {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
            if (networkInfo.getState() != NetworkInfo.State.CONNECTED) {
                return strArr;
            }
            strArr[0] = "2G/3G";
            strArr[1] = networkInfo.getSubtypeName();
            return strArr;
        }
    }

    public static boolean j(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager != null && connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return X;
    }

    public static String k() {
        return Build.VERSION.RELEASE;
    }

    public static boolean k(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return X;
        }
        if (!(connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED)) {
            return X;
        }
        return true;
    }

    public static String l(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (!(activeNetworkInfo == null || activeNetworkInfo.getType() == 1)) {
            String extraInfo = activeNetworkInfo.getExtraInfo();
            Log.i("TAG", "net type:" + extraInfo);
            if (extraInfo == null) {
                return null;
            }
            if (extraInfo.equals("cmwap") || extraInfo.equals("3gwap") || extraInfo.equals("uniwap")) {
                return "10.0.0.172";
            }
            return null;
        }
        return null;
    }

    public static boolean l() {
        String k2 = k();
        if (k2 != null && !k2.startsWith("1") && !k2.startsWith("2.0") && !k2.startsWith("2.1") && !k2.startsWith("2.2")) {
            return true;
        }
        return X;
    }

    public static String m() {
        return Locale.getDefault().getLanguage();
    }

    public static String n() {
        return Locale.getDefault().getLanguage();
    }

    public static String n(Context context) {
        String file;
        File file2;
        V = X;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            file = String.valueOf(externalStorageDirectory.getParent()) + "/" + externalStorageDirectory.getName() + "/SUSdownload";
        } else {
            file = context.getCacheDir().toString();
            V = true;
        }
        if (!(file == null || (file2 = new File(file)) == null || file2.exists())) {
            file2.mkdirs();
        }
        return file;
    }

    public static String o() {
        return Build.MODEL;
    }

    public static boolean o(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
        if (connectivityManager == null) {
            return X;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
            return X;
        }
        return true;
    }

    public static String p() {
        return Build.MANUFACTURER;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0089 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String q(android.content.Context r9) {
        /*
            Method dump skipped, instructions count: 293
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lenovo.lps.sus.c.a.q(android.content.Context):java.lang.String");
    }

    public static boolean q() {
        return W;
    }

    public static String r(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
    }

    public static boolean r() {
        return Y.booleanValue();
    }

    public static String s(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), "android_id");
    }

    private static String t(Context context) {
        try {
            return ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        } catch (Exception e2) {
            Log.i("MobclickAgent", "Could not read MAC, forget to include ACCESS_WIFI_STATE permission?", e2);
            return null;
        }
    }

    public String j() {
        return String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf("Product: " + Build.PRODUCT) + ", CPU_ABI: " + Build.CPU_ABI) + ", TAGS: " + Build.TAGS) + ", VERSION_CODES.BASE: 1") + ", MODEL: " + Build.MODEL) + ", SDK: " + Build.VERSION.SDK) + ", VERSION.RELEASE: " + Build.VERSION.RELEASE) + ", DEVICE: " + Build.DEVICE) + ", DISPLAY: " + Build.DISPLAY) + ", BRAND: " + Build.BRAND) + ", BOARD: " + Build.BOARD) + ", FINGERPRINT: " + Build.FINGERPRINT) + ", ID: " + Build.ID) + ", MANUFACTURER: " + Build.MANUFACTURER) + ", USER: " + Build.USER;
    }

    public Location m(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService("location");
        if (!a(context, "android.permission.ACCESS_FINE_LOCATION")) {
            return null;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation("gps");
        if (lastKnownLocation == null) {
            Log.i(b, "get location from gps:" + lastKnownLocation.getLatitude() + "," + lastKnownLocation.getLongitude());
            return lastKnownLocation;
        }
        a(context, "android.permission.ACCESS_COARSE_LOCATION");
        Location lastKnownLocation2 = locationManager.getLastKnownLocation("network");
        if (lastKnownLocation2 == null) {
            Log.i(b, "get location from network:" + lastKnownLocation2.getLatitude() + "," + lastKnownLocation2.getLongitude());
            return lastKnownLocation2;
        }
        try {
            Log.i(b, "Could not get location from GPS or Cell-id, lack ACCESS_COARSE_LOCATION or ACCESS_COARSE_LOCATION permission?");
            return null;
        } catch (Exception e2) {
            Log.e(b, e2.getMessage());
            return null;
        }
    }

    public String p(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        String line1Number = telephonyManager.getLine1Number();
        return String.valueOf(line1Number) + "|" + telephonyManager.getDeviceId();
    }
}

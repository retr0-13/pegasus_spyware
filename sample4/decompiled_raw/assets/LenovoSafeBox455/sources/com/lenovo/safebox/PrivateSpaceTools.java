package com.lenovo.safebox;

import android.content.Context;
import android.content.Intent;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class PrivateSpaceTools {
    public static final String DB_NAME = "from.db";
    public static final int VERSION = 1;
    public static final String coverFileOri = "cover.img";
    public static final String coverFilePath = "/sdcard/.cover.img";
    public static final String createLoopNod = "mknod /dev/loop100 b 7 300 ";
    public static final String createMountPoint = "mkdir /mnt/sdcard/.pFolder";
    public static ArrayList<String> exculdeFolders = null;
    public static final String executaleFile = "/data/data/com.lenovo.safebox/files/execute.sh";
    private static BufferedReader in = null;
    public static final String losetupLoop = "losetup /dev/loop100 /sdcard/.cover.img";
    private static InputStream mInputStream = null;
    private static DataOutputStream mOutputStream = null;
    public static final String mPkgName = "com.lenovo.safebox";
    private static Process mProcess = null;
    public static final String priFilesFolder = "/mnt/sdcard/.pFolder/files";
    public static final String priImgFolder = "/mnt/sdcard/.pFolder/pictures";
    public static final String priThumbsFolder = "/mnt/sdcard/.pFolder/thumbs";
    public static final String priVideoFolder = "/mnt/sdcard/.pFolder/videos";
    public static final String recoveryFolder = "leSafeRecovery";
    public static final String sdFullDir = "/mnt/sdcard/";
    private static String TAG = "PrivateSpaceTools";
    public static String busybox = "/data/data/com.lenovo.safebox/busybox";
    public static final String sdDir = "/sdcard/";
    public static final String coverFile = ".cover.img";
    public static final String mountPoint = ".pFolder";
    public static String hideFolder = busybox + " mount -o loop " + sdDir + coverFile + " " + sdDir + mountPoint;
    public static String showFolder = busybox + " umount " + sdDir + mountPoint;
    public static ArrayList allFiles = new ArrayList();
    public static boolean needPwd = true;
    private static boolean haveRoot = false;
    public static boolean isLenovoProduct = true;
    public static boolean forceCmcc = false;

    public static boolean socketClient(String cmd) {
        boolean success = false;
        Log.i(TAG, "forceCmcc  :" + forceCmcc + "  " + cmd);
        if (!forceCmcc) {
            try {
                if (isLenovoProduct) {
                    Socket client = new Socket("127.0.0.1", 30001);
                    Log.d(TAG, "Socket: " + client);
                    PrintWriter socketWriter = new PrintWriter(client.getOutputStream(), true);
                    BufferedReader socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    Log.d(TAG, "Socket write ");
                    socketWriter.write(cmd);
                    socketWriter.flush();
                    Log.d(TAG, "Socket flush done ");
                    String a = socketReader.readLine();
                    if (a != null) {
                        success = a.startsWith("success");
                        if (cmd.equals("/data/data/com.lenovo.safebox/files/execute.sh")) {
                            SafeBoxApplication.mContext.sendBroadcast(new Intent(SafeBoxApplication.NAC_DONE));
                        }
                    }
                    Log.d(TAG, "Socket success: " + success);
                    socketWriter.close();
                    socketReader.close();
                    client.close();
                    return success;
                }
            } catch (Exception e) {
            }
            try {
                LocalSocketAddress address = new LocalSocketAddress("nac_server");
                LocalSocket localSocket = new LocalSocket();
                localSocket.connect(address);
                Log.d(TAG, "LocalSocket connect: " + localSocket.isConnected());
                PrintWriter socketWriter2 = new PrintWriter(localSocket.getOutputStream(), true);
                BufferedReader socketReader2 = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
                socketWriter2.write(cmd);
                socketWriter2.flush();
                String a2 = socketReader2.readLine();
                if (a2 != null) {
                    success = a2.startsWith("success");
                    if (cmd.equals("/data/data/com.lenovo.safebox/files/execute.sh")) {
                        SafeBoxApplication.mContext.sendBroadcast(new Intent(SafeBoxApplication.NAC_DONE));
                    }
                }
                Log.d(TAG, "LocalSocket success: " + success);
                socketWriter2.close();
                socketReader2.close();
                localSocket.close();
                return success;
            } catch (IOException e2) {
                Log.i("EXCEPTION", "This is socketClient: " + cmd);
                success = false;
                if (!haveRoot) {
                    if (!obtainLenovoRoot()) {
                        forceCmcc = false;
                    }
                    if (haveRoot) {
                        if (cmd.equals("/data/data/com.lenovo.safebox/files/execute.sh")) {
                            rootCmdFile("sh " + cmd);
                        } else {
                            rootCmd(cmd);
                        }
                    } else if (cmd.equals("/data/data/com.lenovo.safebox/files/execute.sh")) {
                        try {
                            normalCmd(new String[]{"sh", "/data/data/com.lenovo.safebox/files/execute.sh"});
                        } catch (IOException e3) {
                        }
                    } else {
                        try {
                            normalCmd(cmd.split(" "));
                        } catch (IOException e4) {
                        }
                    }
                } else if (cmd.equals("/data/data/com.lenovo.safebox/files/execute.sh")) {
                    rootCmdFile("sh " + cmd);
                } else {
                    rootCmd(cmd);
                }
            }
        } else {
            Log.i(TAG, "haveRoot forcecmcc :" + haveRoot + "   cmd " + cmd);
            if (!haveRoot) {
                if (!obtainLenovoRoot()) {
                    forceCmcc = false;
                }
                Log.i(TAG, "haveRoot forcecmcc :" + haveRoot + "   cmd " + cmd);
                if (haveRoot) {
                    if (cmd.equals("/data/data/com.lenovo.safebox/files/execute.sh")) {
                        rootCmdFile("sh " + cmd);
                    } else {
                        rootCmd(cmd);
                    }
                } else if (cmd.equals("/data/data/com.lenovo.safebox/files/execute.sh")) {
                    try {
                        normalCmd(new String[]{"sh", "/data/data/com.lenovo.safebox/files/execute.sh"});
                    } catch (IOException e5) {
                    }
                } else {
                    try {
                        normalCmd(cmd.split(" "));
                    } catch (IOException e6) {
                    }
                }
            } else if (cmd.equals("/data/data/com.lenovo.safebox/files/execute.sh")) {
                rootCmdFile("sh " + cmd);
            } else {
                rootCmd(cmd);
            }
        }
        return success;
    }

    public static boolean prepareExecuteFile(Context context, String command) {
        FileOutputStream fos = null;
        try {
            try {
                fos = context.openFileOutput("execute.sh", 0);
                fos.write(command.getBytes());
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e2) {
                    }
                }
            }
            return socketClient("/data/data/com.lenovo.safebox/files/execute.sh");
        } catch (Throwable th) {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e3) {
                }
            }
            throw th;
        }
    }

    public static void checkRoot() {
        String line;
        if (!obtainLenovoRoot()) {
            try {
                mProcess = Runtime.getRuntime().exec("su");
                mOutputStream = new DataOutputStream(mProcess.getOutputStream());
                haveRoot = true;
                mOutputStream.writeBytes("ls data\n");
                mOutputStream.flush();
                mInputStream = mProcess.getInputStream();
                in = new BufferedReader(new InputStreamReader(mInputStream));
                String readStr = "denied";
                while (in.ready() && (line = in.readLine()) != null) {
                    readStr = readStr + line + "\n";
                }
            } catch (Exception e) {
                haveRoot = false;
            }
        }
    }

    public static boolean obtainLenovoRoot() {
        try {
            mProcess = Runtime.getRuntime().exec("/system/bin/cmcc_ps");
            mOutputStream = new DataOutputStream(mProcess.getOutputStream());
            mOutputStream.writeBytes("id\n");
            mOutputStream.flush();
            mInputStream = mProcess.getInputStream();
            in = new BufferedReader(new InputStreamReader(mInputStream));
            if (in.readLine() == null) {
                return true;
            }
            haveRoot = true;
            return true;
        } catch (Exception e) {
            haveRoot = false;
            return false;
        }
    }

    public static boolean rootCmd(String cmd) {
        try {
            DataOutputStream os = mOutputStream;
            if (os == null) {
                return false;
            }
            os.writeBytes(cmd + "\n");
            os.flush();
            while (in.ready() && in.readLine() != null) {
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean rootCmdFile(String cmd) {
        String line;
        try {
            DataOutputStream os = mOutputStream;
            if (os == null) {
                return false;
            }
            os.writeBytes(cmd + "\n");
            os.flush();
            while (true) {
                if (in.ready() && (line = in.readLine()) != null && line.equals("done")) {
                    break;
                }
            }
            if (cmd.equals("sh /data/data/com.lenovo.safebox/files/execute.sh")) {
                SafeBoxApplication.mContext.sendBroadcast(new Intent(SafeBoxApplication.NAC_DONE));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void normalCmd(String[] cmmand) throws IOException {
        try {
            ProcessBuilder builder = new ProcessBuilder(cmmand);
            builder.directory(new File("/data/data/com.lenovo.safebox"));
            builder.redirectErrorStream(true);
            builder.start();
            InputStream is = builder.start().getInputStream();
            do {
            } while (is.read(new byte[1024]) != -1);
            is.close();
        } catch (Exception e) {
        }
    }

    public static boolean isFolder(String filepath) {
        if (new File(filepath).isDirectory()) {
            return true;
        }
        return false;
    }

    public static String getFileSize(String filepath) {
        File tmpFile = new File(filepath);
        DecimalFormat df = new DecimalFormat("#.00");
        long size = tmpFile.length();
        String fileSizeString = df.format(((double) size) / 1048576.0d) + "MB";
        if (!fileSizeString.split("\\.")[0].isEmpty()) {
            return fileSizeString;
        }
        String kbStr = df.format(((double) size) / 1024.0d) + "KB";
        if (kbStr.split("\\.")[0].isEmpty()) {
            return ((int) size) + "B";
        }
        return kbStr;
    }

    public static String getMimeTypeOfFile(String name) {
        if (name == null || name.trim().length() == 0 || name.isEmpty()) {
            return null;
        }
        String lowerCase = name.toLowerCase();
        String extension = null;
        int dot = lowerCase.lastIndexOf(".");
        if (dot >= 0) {
            extension = lowerCase.substring(dot + 1);
        }
        if (extension != null) {
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return "unknown";
    }

    public static boolean isVideo(String str) {
        if (str == null || str.trim().length() == 0 || str.isEmpty()) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        String extension = null;
        int dot = lowerCase.lastIndexOf(46);
        if (dot >= 0) {
            extension = lowerCase.substring(dot + 1);
        }
        if (extension == null) {
            return false;
        }
        if (extension.equals("3gp") || extension.equals("mp4")) {
            return true;
        }
        return false;
    }

    public static boolean isImage(String str) {
        if (str == null || str.trim().length() == 0 || str.isEmpty()) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        String extension = null;
        int dot = lowerCase.lastIndexOf(46);
        if (dot >= 0) {
            extension = lowerCase.substring(dot + 1);
        }
        if (extension == null) {
            return false;
        }
        if (extension.equals("png") || extension.equals("jpeg") || extension.equals("gif") || extension.equals("jpg")) {
            return true;
        }
        return false;
    }

    public static String getFileName(String absPath) {
        String[] tmpString = absPath.split("/");
        return tmpString[tmpString.length - 1];
    }

    public static boolean checkSdSpace() {
        File sd = new File(sdDir);
        File fullSd = new File(sdFullDir);
        StatFs stat = new StatFs(sd.getPath());
        StatFs fullStat = new StatFs(fullSd.getPath());
        long blockSize = (long) stat.getBlockSize();
        long availableBlocks = (long) stat.getAvailableBlocks();
        long blockSizeFull = (long) fullStat.getBlockSize();
        long availableBlocksFull = (long) fullStat.getAvailableBlocks();
        if (availableBlocks * blockSize == 0 && blockSizeFull * availableBlocksFull == 0) {
            return false;
        }
        return true;
    }

    public static long getSdSpace() {
        StatFs sf = new StatFs(new File(sdDir).getPath());
        return ((long) sf.getBlockSize()) * ((long) sf.getAvailableBlocks());
    }

    public static ArrayList<String> resolveExcludeFolders() {
        exculdeFolders = new ArrayList<>();
        exculdeFolders.add("/sdcard/.android_secure");
        exculdeFolders.add("/sdcard/.lenovodata");
        exculdeFolders.add("/sdcard/.lenovonotepad");
        return exculdeFolders;
    }

    public static String resolveFilename(String filename) {
        String resolvedName = "";
        char[] charArray = filename.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] == ' ') {
                resolvedName = resolvedName + "\\ ";
            } else if (charArray[i] == '(') {
                resolvedName = resolvedName + "\\(";
            } else if (charArray[i] == ')') {
                resolvedName = resolvedName + "\\)";
            } else if (charArray[i] == '\'') {
                resolvedName = resolvedName + "\\'";
            } else if (charArray[i] == '[') {
                resolvedName = resolvedName + "\\[";
            } else if (charArray[i] == ']') {
                resolvedName = resolvedName + "\\]";
            } else if (charArray[i] == '$') {
                resolvedName = resolvedName + "\\$";
            } else if (charArray[i] == '\"') {
                resolvedName = resolvedName + "\\\"";
            } else if (charArray[i] == '`') {
                resolvedName = resolvedName + "\\`";
            } else if (charArray[i] == '=') {
                resolvedName = resolvedName + "\\=";
            } else if (charArray[i] == '&') {
                resolvedName = resolvedName + "\\&";
            } else {
                resolvedName = resolvedName + charArray[i];
            }
        }
        return resolvedName;
    }

    public static String getThumbnail(String absPath) {
        return null;
    }

    public static void scanSD(Context mContext) {
        mContext.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath())));
    }

    public static boolean isLenovo() {
        if (new File("/system/bin/nac_server").exists()) {
            return true;
        }
        return false;
    }
}

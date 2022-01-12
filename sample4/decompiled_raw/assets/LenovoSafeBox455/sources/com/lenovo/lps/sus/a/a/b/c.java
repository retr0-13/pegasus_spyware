package com.lenovo.lps.sus.a.a.b;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
/* loaded from: classes.dex */
public class c {
    private static final String a = "ASCII";
    private static final String b = "ASCII is not supported!";
    private static final String c = "Close IO error";

    public static String a(String str) {
        try {
            return new String(a(str.getBytes()), a);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(b, e);
        }
    }

    public static String a(String str, String str2) {
        try {
            try {
                return new String(a(str.getBytes(str2)), a);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(b, e);
            }
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("Unsupported charset: " + str2, e2);
        }
    }

    private static void a(InputStream inputStream, OutputStream outputStream) {
        b(new a(inputStream), outputStream);
    }

    private static void a(InputStream inputStream, OutputStream outputStream, int i) {
        d dVar = new d(outputStream, i);
        b(inputStream, dVar);
        dVar.a();
    }

    public static byte[] a(byte[] bArr) {
        return a(bArr, 0);
    }

    private static byte[] a(byte[] bArr, int i) {
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bArr);
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                a(byteArrayInputStream, byteArrayOutputStream, i);
                try {
                    byteArrayInputStream.close();
                    try {
                        byteArrayOutputStream.close();
                        return byteArrayOutputStream.toByteArray();
                    } catch (IOException e) {
                        throw new RuntimeException(c, e);
                    }
                } catch (IOException e2) {
                    throw new RuntimeException(c, e2);
                }
            } catch (IOException e3) {
                throw new RuntimeException("Unexpected I/O error", e3);
            }
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
                try {
                    byteArrayOutputStream.close();
                    throw th;
                } catch (IOException e4) {
                    throw new RuntimeException(c, e4);
                }
            } catch (IOException e5) {
                throw new RuntimeException(c, e5);
            }
        }
    }

    public static String b(String str) {
        try {
            return new String(b(str.getBytes(a)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(b, e);
        }
    }

    public static String b(String str, String str2) {
        try {
            try {
                return new String(b(str.getBytes(a)), str2);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Unsupported charset: " + str2, e);
            }
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(b, e2);
        }
    }

    private static void b(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    public static byte[] b(byte[] bArr) {
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bArr);
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                a(byteArrayInputStream, byteArrayOutputStream);
                try {
                    byteArrayInputStream.close();
                    try {
                        byteArrayOutputStream.close();
                        return byteArrayOutputStream.toByteArray();
                    } catch (IOException e) {
                        throw new RuntimeException(c, e);
                    }
                } catch (IOException e2) {
                    throw new RuntimeException(c, e2);
                }
            } catch (IOException e3) {
                throw new RuntimeException("Unexpected I/O error", e3);
            }
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
                try {
                    byteArrayOutputStream.close();
                    throw th;
                } catch (IOException e4) {
                    throw new RuntimeException(c, e4);
                }
            } catch (IOException e5) {
                throw new RuntimeException(c, e5);
            }
        }
    }
}

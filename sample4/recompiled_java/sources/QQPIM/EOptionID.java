package QQPIM;

public final class EOptionID {
    public static final EOptionID EOI_Cancel = new EOptionID(4, 4, "EOI_Cancel");
    public static final EOptionID EOI_CloudCheck = new EOptionID(9, 9, "EOI_CloudCheck");
    public static final EOptionID EOI_DeductibleScan = new EOptionID(7, 7, "EOI_DeductibleScan");
    public static final EOptionID EOI_END = new EOptionID(16, 16, "EOI_END");
    public static final EOptionID EOI_EnvScanReport = new EOptionID(13, 13, "EOI_EnvScanReport");
    public static final EOptionID EOI_EnvScanSave = new EOptionID(14, 14, "EOI_EnvScanSave");
    public static final EOptionID EOI_Examination = new EOptionID(8, 8, "EOI_Examination");
    public static final EOptionID EOI_Force = new EOptionID(2, 2, "EOI_Force");
    public static final EOptionID EOI_None = new EOptionID(0, 0, "EOI_None");
    public static final EOptionID EOI_Normal = new EOptionID(1, 1, "EOI_Normal");
    public static final EOptionID EOI_OpenMyQQSecure = new EOptionID(15, 15, "EOI_OpenMyQQSecure");
    public static final EOptionID EOI_OpenRootLauch = new EOptionID(12, 12, "EOI_OpenRootLauch");
    public static final EOptionID EOI_OpenSecServ = new EOptionID(10, 10, "EOI_OpenSecServ");
    public static final EOptionID EOI_OpenTrafficStat = new EOptionID(11, 11, "EOI_OpenTrafficStat");
    public static final EOptionID EOI_Reboot = new EOptionID(3, 3, "EOI_Reboot");
    public static final EOptionID EOI_Silent = new EOptionID(5, 5, "EOI_Silent");
    public static final EOptionID EOI_Virus_Scan = new EOptionID(6, 6, "EOI_Virus_Scan");
    public static final int _EOI_Cancel = 4;
    public static final int _EOI_CloudCheck = 9;
    public static final int _EOI_DeductibleScan = 7;
    public static final int _EOI_END = 16;
    public static final int _EOI_EnvScanReport = 13;
    public static final int _EOI_EnvScanSave = 14;
    public static final int _EOI_Examination = 8;
    public static final int _EOI_Force = 2;
    public static final int _EOI_None = 0;
    public static final int _EOI_Normal = 1;
    public static final int _EOI_OpenMyQQSecure = 15;
    public static final int _EOI_OpenRootLauch = 12;
    public static final int _EOI_OpenSecServ = 10;
    public static final int _EOI_OpenTrafficStat = 11;
    public static final int _EOI_Reboot = 3;
    public static final int _EOI_Silent = 5;
    public static final int _EOI_Virus_Scan = 6;
    static final /* synthetic */ boolean a = (!EOptionID.class.desiredAssertionStatus());
    private static EOptionID[] b = new EOptionID[17];
    private int c;
    private String d = new String();

    private EOptionID(int i, int i2, String str) {
        this.d = str;
        this.c = i2;
        b[i] = this;
    }

    public static EOptionID convert(int i) {
        for (int i2 = 0; i2 < b.length; i2++) {
            if (b[i2].value() == i) {
                return b[i2];
            }
        }
        if (a) {
            return null;
        }
        throw new AssertionError();
    }

    public static EOptionID convert(String str) {
        for (int i = 0; i < b.length; i++) {
            if (b[i].toString().equals(str)) {
                return b[i];
            }
        }
        if (a) {
            return null;
        }
        throw new AssertionError();
    }

    public final String toString() {
        return this.d;
    }

    public final int value() {
        return this.c;
    }
}

.class public final LQQPIM/EMessageType;
.super Ljava/lang/Object;


# static fields
.field public static final EMT_Android_Notice_Bar:LQQPIM/EMessageType;

.field public static final EMT_Android_Pop_Up:LQQPIM/EMessageType;

.field public static final EMT_Android_QXinFriend_Top:LQQPIM/EMessageType;

.field public static final EMT_Android_QxinSms_Top:LQQPIM/EMessageType;

.field public static final EMT_Android_SettingPage:LQQPIM/EMessageType;

.field public static final EMT_Android_Sms_Top:LQQPIM/EMessageType;

.field public static final EMT_Android_SysPhoneBook_Top:LQQPIM/EMessageType;

.field public static final EMT_Android_Top:LQQPIM/EMessageType;

.field public static final EMT_END:LQQPIM/EMessageType;

.field public static final EMT_External_Pop_Up:LQQPIM/EMessageType;

.field public static final EMT_Iphone_Pop_Up:LQQPIM/EMessageType;

.field public static final EMT_Iphone_SecureAdsList:LQQPIM/EMessageType;

.field public static final EMT_Iphone_ToolBox_Top:LQQPIM/EMessageType;

.field public static final EMT_None:LQQPIM/EMessageType;

.field public static final EMT_Notice_Bar:LQQPIM/EMessageType;

.field public static final EMT_Pop_UP:LQQPIM/EMessageType;

.field public static final EMT_Symbian_Pop_Up:LQQPIM/EMessageType;

.field public static final EMT_Symbian_Top:LQQPIM/EMessageType;

.field public static final EMT_Top:LQQPIM/EMessageType;

.field public static final _EMT_Android_Notice_Bar:I = 0x8

.field public static final _EMT_Android_Pop_Up:I = 0x6

.field public static final _EMT_Android_QXinFriend_Top:I = 0x106b

.field public static final _EMT_Android_QxinSms_Top:I = 0x106c

.field public static final _EMT_Android_SettingPage:I = 0x106d

.field public static final _EMT_Android_Sms_Top:I = 0x106a

.field public static final _EMT_Android_SysPhoneBook_Top:I = 0x1069

.field public static final _EMT_Android_Top:I = 0x7

.field public static final _EMT_END:I = 0x10ce

.field public static final _EMT_External_Pop_Up:I = 0x9

.field public static final _EMT_Iphone_Pop_Up:I = 0xa

.field public static final _EMT_Iphone_SecureAdsList:I = 0x515

.field public static final _EMT_Iphone_ToolBox_Top:I = 0x10cd

.field public static final _EMT_None:I = 0x0

.field public static final _EMT_Notice_Bar:I = 0x3

.field public static final _EMT_Pop_UP:I = 0x1

.field public static final _EMT_Symbian_Pop_Up:I = 0x4

.field public static final _EMT_Symbian_Top:I = 0x5

.field public static final _EMT_Top:I = 0x2

.field static final synthetic a:Z

.field private static b:[LQQPIM/EMessageType;


# instance fields
.field private c:I

.field private d:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 7

    const/4 v6, 0x4

    const/4 v5, 0x3

    const/4 v4, 0x2

    const/4 v1, 0x1

    const/4 v2, 0x0

    const-class v0, LQQPIM/EMessageType;

    invoke-virtual {v0}, Ljava/lang/Class;->desiredAssertionStatus()Z

    move-result v0

    if-nez v0, :cond_0

    move v0, v1

    :goto_0
    sput-boolean v0, LQQPIM/EMessageType;->a:Z

    const/16 v0, 0x13

    new-array v0, v0, [LQQPIM/EMessageType;

    sput-object v0, LQQPIM/EMessageType;->b:[LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const-string v3, "EMT_None"

    invoke-direct {v0, v2, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_None:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const-string v2, "EMT_Pop_UP"

    invoke-direct {v0, v1, v1, v2}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Pop_UP:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const-string v1, "EMT_Top"

    invoke-direct {v0, v4, v4, v1}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Top:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const-string v1, "EMT_Notice_Bar"

    invoke-direct {v0, v5, v5, v1}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Notice_Bar:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const-string v1, "EMT_Symbian_Pop_Up"

    invoke-direct {v0, v6, v6, v1}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Symbian_Pop_Up:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/4 v1, 0x5

    const/4 v2, 0x5

    const-string v3, "EMT_Symbian_Top"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Symbian_Top:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/4 v1, 0x6

    const/4 v2, 0x6

    const-string v3, "EMT_Android_Pop_Up"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Android_Pop_Up:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/4 v1, 0x7

    const/4 v2, 0x7

    const-string v3, "EMT_Android_Top"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Android_Top:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0x8

    const/16 v2, 0x8

    const-string v3, "EMT_Android_Notice_Bar"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Android_Notice_Bar:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0x9

    const/16 v2, 0x9

    const-string v3, "EMT_External_Pop_Up"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_External_Pop_Up:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0xa

    const/16 v2, 0xa

    const-string v3, "EMT_Iphone_Pop_Up"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Iphone_Pop_Up:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0xb

    const/16 v2, 0x515

    const-string v3, "EMT_Iphone_SecureAdsList"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Iphone_SecureAdsList:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0xc

    const/16 v2, 0x1069

    const-string v3, "EMT_Android_SysPhoneBook_Top"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Android_SysPhoneBook_Top:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0xd

    const/16 v2, 0x106a

    const-string v3, "EMT_Android_Sms_Top"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Android_Sms_Top:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0xe

    const/16 v2, 0x106b

    const-string v3, "EMT_Android_QXinFriend_Top"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Android_QXinFriend_Top:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0xf

    const/16 v2, 0x106c

    const-string v3, "EMT_Android_QxinSms_Top"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Android_QxinSms_Top:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0x10

    const/16 v2, 0x106d

    const-string v3, "EMT_Android_SettingPage"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Android_SettingPage:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0x11

    const/16 v2, 0x10cd

    const-string v3, "EMT_Iphone_ToolBox_Top"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_Iphone_ToolBox_Top:LQQPIM/EMessageType;

    new-instance v0, LQQPIM/EMessageType;

    const/16 v1, 0x12

    const/16 v2, 0x10ce

    const-string v3, "EMT_END"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EMessageType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EMessageType;->EMT_END:LQQPIM/EMessageType;

    return-void

    :cond_0
    move v0, v2

    goto/16 :goto_0
.end method

.method private constructor <init>(IILjava/lang/String;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/lang/String;

    invoke-direct {v0}, Ljava/lang/String;-><init>()V

    iput-object v0, p0, LQQPIM/EMessageType;->d:Ljava/lang/String;

    iput-object p3, p0, LQQPIM/EMessageType;->d:Ljava/lang/String;

    iput p2, p0, LQQPIM/EMessageType;->c:I

    sget-object v0, LQQPIM/EMessageType;->b:[LQQPIM/EMessageType;

    aput-object p0, v0, p1

    return-void
.end method

.method public static convert(I)LQQPIM/EMessageType;
    .locals 2

    const/4 v0, 0x0

    :goto_0
    sget-object v1, LQQPIM/EMessageType;->b:[LQQPIM/EMessageType;

    array-length v1, v1

    if-ge v0, v1, :cond_1

    sget-object v1, LQQPIM/EMessageType;->b:[LQQPIM/EMessageType;

    aget-object v1, v1, v0

    invoke-virtual {v1}, LQQPIM/EMessageType;->value()I

    move-result v1

    if-ne v1, p0, :cond_0

    sget-object v1, LQQPIM/EMessageType;->b:[LQQPIM/EMessageType;

    aget-object v0, v1, v0

    :goto_1
    return-object v0

    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    sget-boolean v0, LQQPIM/EMessageType;->a:Z

    if-nez v0, :cond_2

    new-instance v0, Ljava/lang/AssertionError;

    invoke-direct {v0}, Ljava/lang/AssertionError;-><init>()V

    throw v0

    :cond_2
    const/4 v0, 0x0

    goto :goto_1
.end method

.method public static convert(Ljava/lang/String;)LQQPIM/EMessageType;
    .locals 2

    const/4 v0, 0x0

    :goto_0
    sget-object v1, LQQPIM/EMessageType;->b:[LQQPIM/EMessageType;

    array-length v1, v1

    if-ge v0, v1, :cond_1

    sget-object v1, LQQPIM/EMessageType;->b:[LQQPIM/EMessageType;

    aget-object v1, v1, v0

    invoke-virtual {v1}, LQQPIM/EMessageType;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    sget-object v1, LQQPIM/EMessageType;->b:[LQQPIM/EMessageType;

    aget-object v0, v1, v0

    :goto_1
    return-object v0

    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    sget-boolean v0, LQQPIM/EMessageType;->a:Z

    if-nez v0, :cond_2

    new-instance v0, Ljava/lang/AssertionError;

    invoke-direct {v0}, Ljava/lang/AssertionError;-><init>()V

    throw v0

    :cond_2
    const/4 v0, 0x0

    goto :goto_1
.end method


# virtual methods
.method public final toString()Ljava/lang/String;
    .locals 1

    iget-object v0, p0, LQQPIM/EMessageType;->d:Ljava/lang/String;

    return-object v0
.end method

.method public final value()I
    .locals 1

    iget v0, p0, LQQPIM/EMessageType;->c:I

    return v0
.end method

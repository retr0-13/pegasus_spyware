.class public final LQQPIM/EIllegaReason;
.super Ljava/lang/Object;


# static fields
.field public static final IR_Ad:LQQPIM/EIllegaReason;

.field public static final IR_Conn:LQQPIM/EIllegaReason;

.field public static final IR_Damage:LQQPIM/EIllegaReason;

.field public static final IR_GetInfo:LQQPIM/EIllegaReason;

.field public static final IR_InvalidDownLoad:LQQPIM/EIllegaReason;

.field public static final IR_InvalidInstall:LQQPIM/EIllegaReason;

.field public static final IR_NeedUpdate:LQQPIM/EIllegaReason;

.field public static final IR_Other:LQQPIM/EIllegaReason;

.field public static final IR_Pay:LQQPIM/EIllegaReason;

.field public static final IR_Plugin:LQQPIM/EIllegaReason;

.field public static final IR_SMS:LQQPIM/EIllegaReason;

.field public static final IR_TermProc:LQQPIM/EIllegaReason;

.field public static final IR_Virus:LQQPIM/EIllegaReason;

.field public static final IR_WrongInfo:LQQPIM/EIllegaReason;

.field public static final _IR_Ad:I = 0x6

.field public static final _IR_Conn:I = 0x2

.field public static final _IR_Damage:I = 0x8

.field public static final _IR_GetInfo:I = 0x4

.field public static final _IR_InvalidDownLoad:I = 0x9

.field public static final _IR_InvalidInstall:I = 0xa

.field public static final _IR_NeedUpdate:I = 0xd

.field public static final _IR_Other:I = 0x0

.field public static final _IR_Pay:I = 0x1

.field public static final _IR_Plugin:I = 0x5

.field public static final _IR_SMS:I = 0x3

.field public static final _IR_TermProc:I = 0x7

.field public static final _IR_Virus:I = 0xb

.field public static final _IR_WrongInfo:I = 0xc

.field static final synthetic a:Z

.field private static b:[LQQPIM/EIllegaReason;


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

    const-class v0, LQQPIM/EIllegaReason;

    invoke-virtual {v0}, Ljava/lang/Class;->desiredAssertionStatus()Z

    move-result v0

    if-nez v0, :cond_0

    move v0, v1

    :goto_0
    sput-boolean v0, LQQPIM/EIllegaReason;->a:Z

    const/16 v0, 0xe

    new-array v0, v0, [LQQPIM/EIllegaReason;

    sput-object v0, LQQPIM/EIllegaReason;->b:[LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const-string v3, "IR_Other"

    invoke-direct {v0, v2, v2, v3}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_Other:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const-string v2, "IR_Pay"

    invoke-direct {v0, v1, v1, v2}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_Pay:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const-string v1, "IR_Conn"

    invoke-direct {v0, v4, v4, v1}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_Conn:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const-string v1, "IR_SMS"

    invoke-direct {v0, v5, v5, v1}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_SMS:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const-string v1, "IR_GetInfo"

    invoke-direct {v0, v6, v6, v1}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_GetInfo:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const/4 v1, 0x5

    const/4 v2, 0x5

    const-string v3, "IR_Plugin"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_Plugin:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const/4 v1, 0x6

    const/4 v2, 0x6

    const-string v3, "IR_Ad"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_Ad:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const/4 v1, 0x7

    const/4 v2, 0x7

    const-string v3, "IR_TermProc"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_TermProc:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const/16 v1, 0x8

    const/16 v2, 0x8

    const-string v3, "IR_Damage"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_Damage:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const/16 v1, 0x9

    const/16 v2, 0x9

    const-string v3, "IR_InvalidDownLoad"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_InvalidDownLoad:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const/16 v1, 0xa

    const/16 v2, 0xa

    const-string v3, "IR_InvalidInstall"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_InvalidInstall:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const/16 v1, 0xb

    const/16 v2, 0xb

    const-string v3, "IR_Virus"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_Virus:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const/16 v1, 0xc

    const/16 v2, 0xc

    const-string v3, "IR_WrongInfo"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_WrongInfo:LQQPIM/EIllegaReason;

    new-instance v0, LQQPIM/EIllegaReason;

    const/16 v1, 0xd

    const/16 v2, 0xd

    const-string v3, "IR_NeedUpdate"

    invoke-direct {v0, v1, v2, v3}, LQQPIM/EIllegaReason;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EIllegaReason;->IR_NeedUpdate:LQQPIM/EIllegaReason;

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

    iput-object v0, p0, LQQPIM/EIllegaReason;->d:Ljava/lang/String;

    iput-object p3, p0, LQQPIM/EIllegaReason;->d:Ljava/lang/String;

    iput p2, p0, LQQPIM/EIllegaReason;->c:I

    sget-object v0, LQQPIM/EIllegaReason;->b:[LQQPIM/EIllegaReason;

    aput-object p0, v0, p1

    return-void
.end method

.method public static convert(I)LQQPIM/EIllegaReason;
    .locals 2

    const/4 v0, 0x0

    :goto_0
    sget-object v1, LQQPIM/EIllegaReason;->b:[LQQPIM/EIllegaReason;

    array-length v1, v1

    if-ge v0, v1, :cond_1

    sget-object v1, LQQPIM/EIllegaReason;->b:[LQQPIM/EIllegaReason;

    aget-object v1, v1, v0

    invoke-virtual {v1}, LQQPIM/EIllegaReason;->value()I

    move-result v1

    if-ne v1, p0, :cond_0

    sget-object v1, LQQPIM/EIllegaReason;->b:[LQQPIM/EIllegaReason;

    aget-object v0, v1, v0

    :goto_1
    return-object v0

    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    sget-boolean v0, LQQPIM/EIllegaReason;->a:Z

    if-nez v0, :cond_2

    new-instance v0, Ljava/lang/AssertionError;

    invoke-direct {v0}, Ljava/lang/AssertionError;-><init>()V

    throw v0

    :cond_2
    const/4 v0, 0x0

    goto :goto_1
.end method

.method public static convert(Ljava/lang/String;)LQQPIM/EIllegaReason;
    .locals 2

    const/4 v0, 0x0

    :goto_0
    sget-object v1, LQQPIM/EIllegaReason;->b:[LQQPIM/EIllegaReason;

    array-length v1, v1

    if-ge v0, v1, :cond_1

    sget-object v1, LQQPIM/EIllegaReason;->b:[LQQPIM/EIllegaReason;

    aget-object v1, v1, v0

    invoke-virtual {v1}, LQQPIM/EIllegaReason;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    sget-object v1, LQQPIM/EIllegaReason;->b:[LQQPIM/EIllegaReason;

    aget-object v0, v1, v0

    :goto_1
    return-object v0

    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    sget-boolean v0, LQQPIM/EIllegaReason;->a:Z

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

    iget-object v0, p0, LQQPIM/EIllegaReason;->d:Ljava/lang/String;

    return-object v0
.end method

.method public final value()I
    .locals 1

    iget v0, p0, LQQPIM/EIllegaReason;->c:I

    return v0
.end method

.class public final LQQPIM/EListType;
.super Ljava/lang/Object;


# static fields
.field public static final ELT_Black:LQQPIM/EListType;

.field public static final ELT_White:LQQPIM/EListType;

.field public static final _ELT_Black:I = 0x0

.field public static final _ELT_White:I = 0x1

.field static final synthetic a:Z

.field private static b:[LQQPIM/EListType;


# instance fields
.field private c:I

.field private d:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 4

    const/4 v1, 0x1

    const/4 v2, 0x0

    const-class v0, LQQPIM/EListType;

    invoke-virtual {v0}, Ljava/lang/Class;->desiredAssertionStatus()Z

    move-result v0

    if-nez v0, :cond_0

    move v0, v1

    :goto_0
    sput-boolean v0, LQQPIM/EListType;->a:Z

    const/4 v0, 0x2

    new-array v0, v0, [LQQPIM/EListType;

    sput-object v0, LQQPIM/EListType;->b:[LQQPIM/EListType;

    new-instance v0, LQQPIM/EListType;

    const-string v3, "ELT_Black"

    invoke-direct {v0, v2, v2, v3}, LQQPIM/EListType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EListType;->ELT_Black:LQQPIM/EListType;

    new-instance v0, LQQPIM/EListType;

    const-string v2, "ELT_White"

    invoke-direct {v0, v1, v1, v2}, LQQPIM/EListType;-><init>(IILjava/lang/String;)V

    sput-object v0, LQQPIM/EListType;->ELT_White:LQQPIM/EListType;

    return-void

    :cond_0
    move v0, v2

    goto :goto_0
.end method

.method private constructor <init>(IILjava/lang/String;)V
    .locals 1

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    new-instance v0, Ljava/lang/String;

    invoke-direct {v0}, Ljava/lang/String;-><init>()V

    iput-object v0, p0, LQQPIM/EListType;->d:Ljava/lang/String;

    iput-object p3, p0, LQQPIM/EListType;->d:Ljava/lang/String;

    iput p2, p0, LQQPIM/EListType;->c:I

    sget-object v0, LQQPIM/EListType;->b:[LQQPIM/EListType;

    aput-object p0, v0, p1

    return-void
.end method

.method public static convert(I)LQQPIM/EListType;
    .locals 2

    const/4 v0, 0x0

    :goto_0
    sget-object v1, LQQPIM/EListType;->b:[LQQPIM/EListType;

    array-length v1, v1

    if-ge v0, v1, :cond_1

    sget-object v1, LQQPIM/EListType;->b:[LQQPIM/EListType;

    aget-object v1, v1, v0

    invoke-virtual {v1}, LQQPIM/EListType;->value()I

    move-result v1

    if-ne v1, p0, :cond_0

    sget-object v1, LQQPIM/EListType;->b:[LQQPIM/EListType;

    aget-object v0, v1, v0

    :goto_1
    return-object v0

    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    sget-boolean v0, LQQPIM/EListType;->a:Z

    if-nez v0, :cond_2

    new-instance v0, Ljava/lang/AssertionError;

    invoke-direct {v0}, Ljava/lang/AssertionError;-><init>()V

    throw v0

    :cond_2
    const/4 v0, 0x0

    goto :goto_1
.end method

.method public static convert(Ljava/lang/String;)LQQPIM/EListType;
    .locals 2

    const/4 v0, 0x0

    :goto_0
    sget-object v1, LQQPIM/EListType;->b:[LQQPIM/EListType;

    array-length v1, v1

    if-ge v0, v1, :cond_1

    sget-object v1, LQQPIM/EListType;->b:[LQQPIM/EListType;

    aget-object v1, v1, v0

    invoke-virtual {v1}, LQQPIM/EListType;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1, p0}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    sget-object v1, LQQPIM/EListType;->b:[LQQPIM/EListType;

    aget-object v0, v1, v0

    :goto_1
    return-object v0

    :cond_0
    add-int/lit8 v0, v0, 0x1

    goto :goto_0

    :cond_1
    sget-boolean v0, LQQPIM/EListType;->a:Z

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

    iget-object v0, p0, LQQPIM/EListType;->d:Ljava/lang/String;

    return-object v0
.end method

.method public final value()I
    .locals 1

    iget v0, p0, LQQPIM/EListType;->c:I

    return v0
.end method

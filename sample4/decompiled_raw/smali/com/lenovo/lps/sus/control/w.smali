.class final Lcom/lenovo/lps/sus/control/w;
.super Ljava/lang/Object;

# interfaces
.implements Landroid/widget/CompoundButton$OnCheckedChangeListener;


# instance fields
.field private final synthetic a:I


# direct methods
.method constructor <init>(I)V
    .locals 0

    iput p1, p0, Lcom/lenovo/lps/sus/control/w;->a:I

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public final onCheckedChanged(Landroid/widget/CompoundButton;Z)V
    .locals 3

    const/4 v0, 0x0

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    if-eqz p2, :cond_1

    const/4 v0, 0x1

    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    invoke-static {}, Lcom/lenovo/lps/sus/c/a;->d()V

    :goto_0
    invoke-static {}, Lcom/lenovo/lps/sus/control/am;->o()Landroid/os/Handler;

    move-result-object v1

    if-eqz v1, :cond_0

    new-instance v1, Landroid/os/Message;

    invoke-direct {v1}, Landroid/os/Message;-><init>()V

    sget-object v2, Lcom/lenovo/lps/sus/b/b;->f:Lcom/lenovo/lps/sus/b/b;

    invoke-static {v2}, Lcom/lenovo/lps/sus/b/b;->a(Lcom/lenovo/lps/sus/b/b;)I

    move-result v2

    iput v2, v1, Landroid/os/Message;->what:I

    iget v2, p0, Lcom/lenovo/lps/sus/control/w;->a:I

    invoke-static {v2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    iput-object v2, v1, Landroid/os/Message;->obj:Ljava/lang/Object;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    iput v0, v1, Landroid/os/Message;->arg1:I

    invoke-static {}, Lcom/lenovo/lps/sus/control/am;->o()Landroid/os/Handler;

    move-result-object v0

    invoke-virtual {v0, v1}, Landroid/os/Handler;->sendMessage(Landroid/os/Message;)Z

    :cond_0
    return-void

    :cond_1
    invoke-static {v0}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v0

    goto :goto_0
.end method

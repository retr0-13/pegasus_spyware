.class public interface abstract Lcom/lenovo/safecenter/encoders/Encoder;
.super Ljava/lang/Object;
.source "Encoder.java"


# virtual methods
.method public abstract decode(Ljava/lang/String;Ljava/io/OutputStream;)I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation
.end method

.method public abstract decode([BIILjava/io/OutputStream;)I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation
.end method

.method public abstract encode([BIILjava/io/OutputStream;)I
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation
.end method

package com.lenovo.lps.reaper.sdk.c;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class d extends DefaultHandler {
    private List a;
    private long b;
    private StringBuilder c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public d(c cVar) {
    }

    public final List a() {
        return this.a;
    }

    public final long b() {
        return this.b;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void characters(char[] cArr, int i, int i2) {
        super.characters(cArr, i, i2);
        this.c.append(cArr, i, i2);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void endElement(String str, String str2, String str3) {
        super.endElement(str, str2, str3);
        if (str2.equalsIgnoreCase("Address")) {
            this.a.add(this.c.toString());
        } else if (str2.equalsIgnoreCase("TTL")) {
            this.b = Long.parseLong(this.c.toString());
        }
        this.c.setLength(0);
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void startDocument() {
        super.startDocument();
        this.a = new ArrayList(1);
        this.c = new StringBuilder();
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public final void startElement(String str, String str2, String str3, Attributes attributes) {
        super.startElement(str, str2, str3, attributes);
    }
}

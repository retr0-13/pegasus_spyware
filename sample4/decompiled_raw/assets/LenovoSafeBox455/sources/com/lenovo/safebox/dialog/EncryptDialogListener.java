package com.lenovo.safebox.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
/* loaded from: classes.dex */
public class EncryptDialogListener implements DialogInterface.OnClickListener {
    private String TAG = "EncryptDialogListener  aaaaaaaaaaaaaaaaaaaaaa";
    Class mClass;
    Context mContext;
    boolean mFlag;

    public EncryptDialogListener(Context context, boolean flag, Class startClass) {
        this.mContext = context;
        this.mClass = startClass;
        this.mFlag = flag;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        Intent manIntent = new Intent(this.mContext, this.mClass);
        manIntent.putExtra("image", this.mFlag);
        manIntent.addFlags(8388608);
        this.mContext.startActivity(manIntent);
    }
}

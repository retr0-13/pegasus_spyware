package com.lenovo.safebox.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import com.lenovo.safebox.AddAutoVideoActivity;
import com.lenovo.safebox.AddManualMediaActivity;
/* loaded from: classes.dex */
public class VideoDialogListener implements DialogInterface.OnClickListener {
    private String TAG = "VideoDialogListener vvvvvvvvvvvvvvvvvvvvvvvvv";
    Context mContext;

    public VideoDialogListener(Context context) {
        this.mContext = context;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        Log.i(this.TAG, "onClick which is " + which);
        switch (which) {
            case 1:
                Intent manIntent = new Intent(this.mContext, AddManualMediaActivity.class);
                manIntent.setFlags(67141632);
                manIntent.addFlags(8388608);
                manIntent.putExtra("image", false);
                this.mContext.startActivity(manIntent);
                return;
            case 2:
                return;
            default:
                Intent intent = new Intent(this.mContext, AddAutoVideoActivity.class);
                intent.setFlags(67141632);
                intent.addFlags(8388608);
                this.mContext.startActivity(intent);
                return;
        }
    }
}

package com.lenovo.safebox.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import com.lenovo.safebox.R;
import com.lenovo.safebox.dialog.CustomDialog;
/* loaded from: classes.dex */
public class CustomProgressDialog extends ProgressDialog {
    private String TAG = "CustomProgressDialog";

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomDialog createThreadDialog(Context context) {
        CustomDialog.Builder mediaBuilder = new CustomDialog.Builder(context).setTitle(context.getString(R.string.warnning)).setMessage(context.getString(R.string.running_warn));
        ThreadcontrolDialogListener mListener = new ThreadcontrolDialogListener();
        mediaBuilder.setPositiveButton(context.getString(R.string.confirm), mListener);
        mediaBuilder.setNegativeButton(context.getString(R.string.cancel), mListener);
        return mediaBuilder.create();
    }

    /* loaded from: classes.dex */
    class ThreadcontrolDialogListener implements DialogInterface.OnClickListener {
        ThreadcontrolDialogListener() {
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                default:
                    return;
                case 1:
                    Log.i(CustomProgressDialog.this.TAG, "case 1 paused=false");
                    return;
            }
        }
    }

    @Override // android.app.AlertDialog, android.view.KeyEvent.Callback, android.app.Dialog
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
        }
        return super.onKeyDown(keyCode, event);
    }
}

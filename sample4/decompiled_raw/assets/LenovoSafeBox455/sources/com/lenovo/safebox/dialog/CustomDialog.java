package com.lenovo.safebox.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.lenovo.safebox.R;
/* loaded from: classes.dex */
public class CustomDialog extends Dialog {
    public CustomDialog(Context context, int theme) {
        super(context, theme);
    }

    public CustomDialog(Context context) {
        super(context);
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private static final int MULTIPE_CHOICE_LIST = 1;
        private static final int NO_CHOICE_LIST = 2;
        private static final int SINGLE_CHOICE_LIST = 0;
        private LinearLayout buttonLL;
        private DialogInterface.OnCancelListener cancelListener;
        private View contentView;
        private Context context;
        private int mIcon;
        private TextView mTitleView;
        private String message;
        private TextView negativeButton;
        private DialogInterface.OnClickListener negativeButtonClickListener;
        private String negativeButtonText;
        private TextView neutralButton;
        private DialogInterface.OnClickListener neutralButtonClickListener;
        private String neutralButtonText;
        private DialogInterface.OnClickListener noChoiceListonClickListener;
        private TextView positiveButton;
        private DialogInterface.OnClickListener positiveButtonClickListener;
        private String positiveButtonText;
        private DialogInterface.OnClickListener singleChoiceItemsCheckListener;
        private String title;
        private int contentType = -1;
        private int mSingleChoiceItemsId = -1;
        private int mSingleChoiceCheckedId = -1;
        private String[] mSingleChoiceItemsArray = null;
        private int mNoChoiceList = -1;
        private String[] sNoChoiceList = null;
        private boolean PositiveButtonAutoDismiss = true;
        private boolean NegativeButtonAutoDismiss = true;
        private boolean NeutralButtonAutoDismiss = true;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setIcon(int icon) {
            this.mIcon = icon;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setMessage(int message) {
            this.message = (String) this.context.getText(message);
            return this;
        }

        public Builder setTitle(int title) {
            this.title = (String) this.context.getText(title);
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder reSetTitle(String title) {
            this.title = title;
            if (this.mTitleView != null) {
                this.mTitleView.setText(title);
            }
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = (String) this.context.getText(positiveButtonText);
            if (listener == null) {
                this.positiveButtonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.positiveButtonClickListener = listener;
            }
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, DialogInterface.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            if (listener == null) {
                this.positiveButtonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.positiveButtonClickListener = listener;
            }
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText, DialogInterface.OnClickListener listener, boolean autoDismiss) {
            this.PositiveButtonAutoDismiss = autoDismiss;
            this.positiveButtonText = (String) this.context.getText(positiveButtonText);
            if (listener == null) {
                this.positiveButtonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.3
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.positiveButtonClickListener = listener;
            }
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = (String) this.context.getText(negativeButtonText);
            if (listener == null) {
                this.negativeButtonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.4
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.negativeButtonClickListener = listener;
            }
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, DialogInterface.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            if (listener == null) {
                this.negativeButtonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.5
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.negativeButtonClickListener = listener;
            }
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText, DialogInterface.OnClickListener listener, boolean autoDismiss) {
            this.NegativeButtonAutoDismiss = autoDismiss;
            this.negativeButtonText = (String) this.context.getText(negativeButtonText);
            if (listener == null) {
                this.negativeButtonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.6
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.negativeButtonClickListener = listener;
            }
            return this;
        }

        public Builder setNeutralButton(int neutralButtonText, DialogInterface.OnClickListener listener) {
            this.neutralButtonText = (String) this.context.getText(neutralButtonText);
            if (listener == null) {
                this.neutralButtonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.7
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.neutralButtonClickListener = listener;
            }
            return this;
        }

        public Builder setNeutralButton(String neutralButtonText, DialogInterface.OnClickListener listener) {
            this.neutralButtonText = neutralButtonText;
            if (listener == null) {
                this.neutralButtonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.8
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.neutralButtonClickListener = listener;
            }
            return this;
        }

        public Builder setNeutralButton(int neutralButtonText, DialogInterface.OnClickListener listener, boolean autoDismiss) {
            this.NeutralButtonAutoDismiss = autoDismiss;
            this.neutralButtonText = (String) this.context.getText(neutralButtonText);
            if (listener == null) {
                this.neutralButtonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.9
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.neutralButtonClickListener = listener;
            }
            return this;
        }

        public Builder setItems(int itemsId, DialogInterface.OnClickListener listener) {
            this.contentType = 2;
            this.mNoChoiceList = itemsId;
            if (listener == null) {
                this.noChoiceListonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.10
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.noChoiceListonClickListener = listener;
            }
            return this;
        }

        public Builder setItems(String[] itemsId, DialogInterface.OnClickListener listener) {
            this.contentType = 2;
            this.sNoChoiceList = itemsId;
            if (listener == null) {
                this.noChoiceListonClickListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.11
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.noChoiceListonClickListener = listener;
            }
            return this;
        }

        public Builder setSingleChoiceItems(int singleChoiceItemsId, int singleChoiceCheckedId, DialogInterface.OnClickListener listener) {
            this.contentType = 0;
            this.mSingleChoiceItemsId = singleChoiceItemsId;
            this.mSingleChoiceCheckedId = singleChoiceCheckedId;
            if (listener == null) {
                this.singleChoiceItemsCheckListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.12
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.singleChoiceItemsCheckListener = listener;
            }
            return this;
        }

        public Builder setSingleChoiceItems(String[] singleChoiceItems, int singleChoiceCheckedId, DialogInterface.OnClickListener listener) {
            this.contentType = 0;
            this.mSingleChoiceItemsArray = singleChoiceItems;
            this.mSingleChoiceCheckedId = singleChoiceCheckedId;
            if (listener == null) {
                this.singleChoiceItemsCheckListener = new DialogInterface.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.13
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
            } else {
                this.singleChoiceItemsCheckListener = listener;
            }
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.cancelListener = onCancelListener;
            return this;
        }

        public Builder setPositiveButtonVisible(boolean isVisible) {
            if (this.positiveButton != null) {
                if (isVisible) {
                    this.positiveButton.setVisibility(0);
                } else {
                    this.positiveButton.setVisibility(8);
                }
            }
            return this;
        }

        public Builder setNeutralButtonVisible(boolean isVisible) {
            if (this.neutralButton != null) {
                if (isVisible) {
                    this.neutralButton.setVisibility(0);
                } else {
                    this.neutralButton.setVisibility(8);
                }
            }
            return this;
        }

        public Builder setNegativeButtonVisible(boolean isVisible) {
            if (this.negativeButton != null) {
                if (isVisible) {
                    this.negativeButton.setVisibility(0);
                } else {
                    this.negativeButton.setVisibility(8);
                }
            }
            return this;
        }

        public Builder setButtonVisible(boolean isVisible) {
            if (this.buttonLL != null) {
                if (isVisible) {
                    this.buttonLL.setVisibility(0);
                } else {
                    this.buttonLL.setVisibility(8);
                }
            }
            return this;
        }

        public CustomDialog create() {
            final CustomDialog dialog = new CustomDialog(this.context, R.style.Dialog);
            View layout = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.dialog, (ViewGroup) null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(-1, -2));
            ImageView icon = (ImageView) layout.findViewById(R.id.icon);
            if (this.mIcon != 0) {
                icon.setVisibility(0);
                icon.setImageResource(this.mIcon);
            } else {
                icon.setVisibility(8);
            }
            this.buttonLL = (LinearLayout) layout.findViewById(R.id.button);
            this.mTitleView = (TextView) layout.findViewById(R.id.title);
            this.mTitleView.setText(this.title);
            if (this.positiveButtonText != null) {
                this.positiveButton = (TextView) layout.findViewById(R.id.positiveButton);
                this.positiveButton.setText(this.positiveButtonText);
                if (this.positiveButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.14
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            Builder.this.positiveButtonClickListener.onClick(dialog, -1);
                            if (Builder.this.PositiveButtonAutoDismiss) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.positiveButton).setVisibility(8);
            }
            if (this.neutralButtonText != null) {
                this.neutralButton = (TextView) layout.findViewById(R.id.neutralButton);
                this.neutralButton.setText(this.neutralButtonText);
                if (this.neutralButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.neutralButton)).setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.15
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            Builder.this.neutralButtonClickListener.onClick(dialog, -3);
                            if (Builder.this.NeutralButtonAutoDismiss) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.neutralButton).setVisibility(8);
            }
            if (this.negativeButtonText != null) {
                this.negativeButton = (TextView) layout.findViewById(R.id.negativeButton);
                this.negativeButton.setText(this.negativeButtonText);
                if (this.negativeButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.16
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            Builder.this.negativeButtonClickListener.onClick(dialog, -2);
                            if (Builder.this.NegativeButtonAutoDismiss) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.negativeButton).setVisibility(8);
            }
            if (this.positiveButtonText == null && this.negativeButtonText == null && this.neutralButtonText == null) {
                layout.findViewById(R.id.button).setVisibility(8);
            }
            if (this.message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(this.message);
            } else if (this.contentType == 0) {
                listItemSingleChoiceMode(layout, dialog);
            } else if (this.contentType == 2) {
                listItemNoChoiceList(layout, dialog);
            } else if (this.contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(this.contentView, new ViewGroup.LayoutParams(-1, -2));
                this.contentView.setPadding(20, 0, 20, 0);
            }
            dialog.setContentView(layout);
            return dialog;
        }

        public CustomDialog create(boolean needWallpaper) {
            final CustomDialog dialog;
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
            if (needWallpaper) {
                dialog = new CustomDialog(this.context, R.style.DialogWallpaper);
            } else {
                dialog = new CustomDialog(this.context, R.style.Dialog);
            }
            View layout = inflater.inflate(R.layout.dialog, (ViewGroup) null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(-1, -2));
            ImageView icon = (ImageView) layout.findViewById(R.id.icon);
            if (this.mIcon != 0) {
                icon.setVisibility(0);
                icon.setImageResource(this.mIcon);
            } else {
                icon.setVisibility(8);
            }
            this.buttonLL = (LinearLayout) layout.findViewById(R.id.button);
            this.mTitleView = (TextView) layout.findViewById(R.id.title);
            this.mTitleView.setText(this.title);
            if (this.positiveButtonText != null) {
                this.positiveButton = (TextView) layout.findViewById(R.id.positiveButton);
                this.positiveButton.setText(this.positiveButtonText);
                if (this.positiveButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.17
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            Builder.this.positiveButtonClickListener.onClick(dialog, -1);
                            if (Builder.this.PositiveButtonAutoDismiss) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.positiveButton).setVisibility(8);
            }
            if (this.neutralButtonText != null) {
                this.neutralButton = (TextView) layout.findViewById(R.id.neutralButton);
                this.neutralButton.setText(this.neutralButtonText);
                if (this.neutralButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.neutralButton)).setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.18
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            Builder.this.neutralButtonClickListener.onClick(dialog, -3);
                            if (Builder.this.NeutralButtonAutoDismiss) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.neutralButton).setVisibility(8);
            }
            if (this.negativeButtonText != null) {
                this.negativeButton = (TextView) layout.findViewById(R.id.negativeButton);
                this.negativeButton.setText(this.negativeButtonText);
                if (this.negativeButtonClickListener != null) {
                    ((TextView) layout.findViewById(R.id.negativeButton)).setOnClickListener(new View.OnClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.19
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            Builder.this.negativeButtonClickListener.onClick(dialog, -2);
                            if (Builder.this.NegativeButtonAutoDismiss) {
                                dialog.dismiss();
                            }
                        }
                    });
                }
            } else {
                layout.findViewById(R.id.negativeButton).setVisibility(8);
            }
            if (this.message != null) {
                ((TextView) layout.findViewById(R.id.message)).setText(this.message);
            } else if (this.contentType == 0) {
                listItemSingleChoiceMode(layout, dialog);
            } else if (this.contentType == 2) {
                listItemNoChoiceList(layout, dialog);
            } else if (this.contentView != null) {
                ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.content)).addView(this.contentView, new ViewGroup.LayoutParams(-1, -2));
                this.contentView.setPadding(20, 0, 20, 0);
            }
            dialog.setContentView(layout);
            return dialog;
        }

        private void listItemNoChoiceList(View layout, final CustomDialog dialog) {
            String[] listContent;
            if (this.sNoChoiceList == null) {
                listContent = this.context.getResources().getStringArray(this.mNoChoiceList);
            } else {
                listContent = this.sNoChoiceList;
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.context, (int) R.layout.simple_list_item, listContent);
            ListView list = new ListView(this.context);
            list.setAdapter((ListAdapter) arrayAdapter);
            ((LinearLayout) layout.findViewById(R.id.button)).setVisibility(8);
            ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
            ((LinearLayout) layout.findViewById(R.id.content)).addView(list, new ViewGroup.LayoutParams(-1, -2));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.20
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (Builder.this.noChoiceListonClickListener != null) {
                        Builder.this.noChoiceListonClickListener.onClick(dialog, position);
                        dialog.dismiss();
                    }
                }
            });
        }

        private void listItemSingleChoiceMode(View layout, final CustomDialog dialog) {
            ArrayAdapter<String> arrayAdapter;
            if (this.mSingleChoiceItemsArray != null) {
                arrayAdapter = new ArrayAdapter<>(this.context, 17367055, this.mSingleChoiceItemsArray);
            } else {
                arrayAdapter = new ArrayAdapter<>(this.context, 17367055, this.context.getResources().getStringArray(this.mSingleChoiceItemsId));
            }
            ListView list = new ListView(this.context);
            list.setChoiceMode(1);
            list.setAdapter((ListAdapter) arrayAdapter);
            list.setItemsCanFocus(false);
            list.setItemChecked(this.mSingleChoiceCheckedId, true);
            ((LinearLayout) layout.findViewById(R.id.content)).removeAllViews();
            ((LinearLayout) layout.findViewById(R.id.content)).addView(list, new ViewGroup.LayoutParams(-1, -2));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.lenovo.safebox.dialog.CustomDialog.Builder.21
                @Override // android.widget.AdapterView.OnItemClickListener
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (Builder.this.singleChoiceItemsCheckListener != null) {
                        Builder.this.singleChoiceItemsCheckListener.onClick(dialog, position);
                    }
                }
            });
        }

        public CustomDialog show() {
            CustomDialog dialog = create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            return dialog;
        }
    }
}

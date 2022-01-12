package com.lenovo.safebox.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lenovo.safebox.AppInfo;
import com.lenovo.safebox.R;
import java.util.ArrayList;
/* loaded from: classes.dex */
public class LockedAppAdapter extends BaseAdapter {
    private ArrayList<AppInfo> allList;
    private Context mContext;
    private int totalCount;

    public LockedAppAdapter(Context context, ArrayList<AppInfo> list) {
        this.mContext = context;
        this.allList = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.allList.size();
    }

    @Override // android.widget.Adapter
    public AppInfo getItem(int position) {
        return this.allList.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return 0;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        AppViewHolder holder = new AppViewHolder();
        if (convertView == null) {
            convertView = View.inflate(this.mContext, R.layout.gui_selectapp_item, null);
            holder.iconView = (ImageView) convertView.findViewById(R.id.appitem_icon);
            holder.appName = (TextView) convertView.findViewById(R.id.appitem_name);
            holder.isLocked = (ImageView) convertView.findViewById(R.id.appitem_islock);
            convertView.setTag(holder);
        } else {
            holder = (AppViewHolder) convertView.getTag();
        }
        if (this.allList.get(position).getDrawable() != null) {
            holder.iconView.setImageDrawable(this.allList.get(position).getDrawable());
        }
        if (this.allList.get(position).getAppName() != null) {
            holder.appName.setText(this.allList.get(position).getAppName());
        }
        holder.isLocked.setImageResource(R.drawable.gui_app_locked);
        return convertView;
    }

    /* loaded from: classes.dex */
    class AppViewHolder {
        TextView appName;
        ImageView iconView;
        ImageView isLocked;

        AppViewHolder() {
        }
    }
}

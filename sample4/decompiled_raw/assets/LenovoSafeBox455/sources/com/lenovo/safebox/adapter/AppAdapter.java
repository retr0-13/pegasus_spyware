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
public class AppAdapter extends BaseAdapter {
    private ArrayList<AppInfo> allList;
    private ArrayList<AppInfo> dataAppList;
    private Context mContext;
    private ArrayList<AppInfo> sysAppList;
    private int totalCount;

    public AppAdapter(Context context, ArrayList<AppInfo> sys, ArrayList<AppInfo> data) {
        this.mContext = context;
        this.sysAppList = sys;
        this.dataAppList = data;
        resolveList(sys, data);
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

    private void resolveList(ArrayList<AppInfo> sys, ArrayList<AppInfo> data) {
        this.allList = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            this.allList.add(data.get(i));
        }
        for (int i2 = 0; i2 < sys.size(); i2++) {
            this.allList.add(sys.get(i2));
        }
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
        } else if (convertView.getTag() == null) {
            convertView = View.inflate(this.mContext, R.layout.gui_selectapp_item, null);
            holder.iconView = (ImageView) convertView.findViewById(R.id.appitem_icon);
            holder.appName = (TextView) convertView.findViewById(R.id.appitem_name);
            holder.isLocked = (ImageView) convertView.findViewById(R.id.appitem_islock);
            convertView.setTag(holder);
        } else {
            holder = (AppViewHolder) convertView.getTag();
            holder.iconView = (ImageView) convertView.findViewById(R.id.appitem_icon);
            holder.appName = (TextView) convertView.findViewById(R.id.appitem_name);
            holder.isLocked = (ImageView) convertView.findViewById(R.id.appitem_islock);
        }
        if (this.allList.get(position).getDrawable() != null) {
            holder.iconView.setImageDrawable(this.allList.get(position).getDrawable());
        }
        if (this.allList.get(position).getAppName() != null) {
            holder.appName.setText(this.allList.get(position).getAppName());
        }
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

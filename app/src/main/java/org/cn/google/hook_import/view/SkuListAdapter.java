package org.cn.google.hook_import.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;

public class SkuListAdapter extends BaseAdapter {

    private List<String> skuList = new ArrayList();

    private Context mContext;

    public SkuListAdapter(Context context) {
        this.mContext = context;
        for (int i = 0; i < 10; i++) {
            skuList.add(i + "测试");
        }
    }

    @Override
    public int getCount() {
        return skuList.size();
    }

    @Override
    public Object getItem(int i) {
        return skuList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LinearLayout linearLayout = new LinearLayout(this.mContext);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));

            viewHolder.skuName = new TextView(this.mContext);
            viewHolder.skuName.setGravity(Gravity.CENTER);
            viewHolder.skuName.setTextColor(Color.WHITE);
            viewHolder.skuName.setTextSize(12.0f);
            viewHolder.skuName.setPadding(0, 25, 0, 25);
            viewHolder.skuName.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1.0f));

            viewHolder.skuPrice = new TextView(this.mContext);
            viewHolder.skuPrice.setGravity(Gravity.CENTER);
            viewHolder.skuPrice.setTextColor(Color.WHITE);
            viewHolder.skuPrice.setTextSize(12.0f);
            viewHolder.skuPrice.setPadding(0, 25, 0, 25);
            viewHolder.skuPrice.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1.0f));

            linearLayout.addView(viewHolder.skuName);
            linearLayout.addView(viewHolder.skuPrice);
            linearLayout.setTag(viewHolder);
            view = linearLayout;
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.skuName.setText("sku名称" + this.skuList.get(i));
        viewHolder.skuPrice.setText("sku价格US$20.99");

        return view;
    }

    static class ViewHolder {
        TextView skuName, skuPrice;
    }

}

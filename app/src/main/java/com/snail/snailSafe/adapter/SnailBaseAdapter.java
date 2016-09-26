package com.snail.snailSafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by snail on 2016/9/26.
 */

public abstract class SnailBaseAdapter<T> extends BaseAdapter {

    private List<T> data;
    private Context mContext;

    public SnailBaseAdapter(Context mContext, List<T> data) {
        this.data = data;
        this.mContext = mContext;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}

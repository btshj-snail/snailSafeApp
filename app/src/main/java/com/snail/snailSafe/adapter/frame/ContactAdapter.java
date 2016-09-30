package com.snail.snailSafe.adapter.frame;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snail.snailSafe.R;
import com.snail.snailSafe.adapter.SnailBaseAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by snail on 2016/9/28.
 */

public class ContactAdapter extends SnailBaseAdapter {

    public ContactAdapter(Context mContext, List data) {
        super(mContext, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = View.inflate(getContext(), R.layout.item_lv_contact,null);
        }
        TextView item_contact_tv_name = (TextView) convertView.findViewById(R.id.item_contact_tv_name);
        TextView item_contact_tv_tel_num = (TextView) convertView.findViewById(R.id.item_contact_tv_tel_num);

        Map<String,String> map = (Map<String, String>) getItem(position);
        item_contact_tv_name.setText(map.get("contactName"));
        item_contact_tv_tel_num.setText(map.get("contactTelNum"));
        return convertView;
    }
}

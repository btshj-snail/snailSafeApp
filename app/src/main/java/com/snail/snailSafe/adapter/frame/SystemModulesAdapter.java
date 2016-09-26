package com.snail.snailSafe.adapter.frame;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.snailSafe.R;
import com.snail.snailSafe.adapter.SnailBaseAdapter;
import com.snail.snailSafe.pojo.frame.SystemModule;

import java.util.List;

/**
 * Created by snail on 2016/9/26.
 */

public class SystemModulesAdapter extends SnailBaseAdapter {
    private Context mContext ;

    public SystemModulesAdapter(Context context, List<SystemModule> list){
       super(context,list);
        this.mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gv_home_item,parent,false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_item_system_module);
        SystemModule systemModule = (SystemModule) getData().get(position);
//
        Drawable top = mContext.getResources().getDrawable(systemModule.getModuleIcon());
        textView.setCompoundDrawablesWithIntrinsicBounds(null,top,null,null);
        textView.setText(systemModule.getModuleTitle());
        return convertView;
    }
}

package com.snail.snailSafe.activity.help;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.snail.snailSafe.R;
import com.snail.snailSafe.adapter.SnailBaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snail on 2016/10/8.
 */

public class HelpOtherActivity extends AppCompatActivity {
    private Context mContext;
    private GridView gv_help_other;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_help_other);
        initUI();
        initData();
    }

    private void initData() {
        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("name","获取丢失手机位置");
        list.add(map1);

        Map<String,String> map2 = new HashMap<>();
        map1.put("name","删除丢失手机照片");
        list.add(map2);

        Map<String,String> map3 = new HashMap<>();
        map1.put("name","删除丢失手机通讯录");
        list.add(map3);

        Map<String,String> map4 = new HashMap<>();
        map1.put("name","获取丢失手机状态");
        list.add(map4);


        HelpOtherAdapter helpOtherAdapter = new HelpOtherAdapter(mContext,list);
        gv_help_other.setAdapter(helpOtherAdapter);
    }

    private void initUI() {
        gv_help_other = (GridView) findViewById(R.id.gv_help_other);

    }





    class HelpOtherAdapter extends SnailBaseAdapter<Map<String ,String>> {
        public HelpOtherAdapter(Context mContext, List data) {
            super(mContext, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.gv_help_other_item,parent,false);
            }
            TextView tv_help_other_item_text = (TextView)convertView.findViewById(R.id.tv_help_other_item_text);
            tv_help_other_item_text.setText(getItem(position).get("name"));
            return convertView;
        }
    }
}

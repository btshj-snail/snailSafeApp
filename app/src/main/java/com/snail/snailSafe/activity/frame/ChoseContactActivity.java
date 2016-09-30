package com.snail.snailSafe.activity.frame;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.snail.snailSafe.R;
import com.snail.snailSafe.adapter.frame.ContactAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snail on 2016/9/28.
 */

public class ChoseContactActivity extends AppCompatActivity {
    private Context mContext;
    private ListView lv_contact_list;
    private List<Map<String, String>> contactList = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            assembleContactData();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_chose_contact);
        initUI();
        initData();
    }

    private void initData() {
        queryContactList();
    }

    private void initUI() {
        lv_contact_list = (ListView) findViewById(R.id.lv_contact_list);
        lv_contact_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,String> map = contactList.get(position);
                Intent intent = new Intent();
                intent.putExtra("contactName",map.get("contactName"));
                intent.putExtra("contactTelNum",map.get("contactTelNum"));
                setResult(0, intent);
                finish();
            }
        });
    }


    /**
     * 组装数据到list view中
     */
    private void assembleContactData() {
        ContactAdapter contactAdapter = new ContactAdapter(mContext,contactList);
        lv_contact_list.setAdapter(contactAdapter);
    }


    /**
     * 查询电话联系人
     * 耗时操作，使用另外的线程进行，而不要占用UI线程
     */
    private void queryContactList() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        contactList.clear();
                        ContentResolver resolver = getContentResolver();
                        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                        Cursor cursor = resolver.query(uri, null, null, null, null);
                        while (cursor.moveToNext()) {
                            //获取联系人姓名,手机号码
                            String cName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                            String cNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("contactName", cName);
                            map.put("contactTelNum", cNum);
                            contactList.add(map);
                        }
                        cursor.close();
                        mHandler.sendEmptyMessage(0);
                    }
                }
        ).start();
    }
}

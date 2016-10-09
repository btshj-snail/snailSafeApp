package com.snail.snailSafe.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.List;

/**
 * Created by snail on 2016/10/9.
 */

public class SendMessageUtil {
    //处理返回的接收状态
    private String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
    private String SENT_SMS_ACTION = "SENT_SMS_ACTION";

    private Context context;

    public SendMessageUtil(Context context) {
        this.context = context;
    }


    /**
     * 发送短信
     * @param phoneNumber
     * @param message
     */
    public  void sendSms(String phoneNumber,String message){
        //返回接受状态
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        PendingIntent deliverPI = PendingIntent.getBroadcast(context, 0,
                deliverIntent, 0);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                onReceive(_context,_intent);
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));



        //返回的发送状态

        Intent sentIntent = new Intent(SENT_SMS_ACTION);
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent,
                0);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context _context, Intent _intent) {
                int resultCode = getResultCode();
                onSendStatus(_context, _intent,resultCode);
            }
        }, new IntentFilter(SENT_SMS_ACTION));


        SmsManager smsManager = SmsManager.getDefault();
        List<String> list = smsManager.divideMessage(message);
        for(String m : list){
            smsManager.sendTextMessage(phoneNumber,null,m,sentPI,deliverPI);
        }
    }

    /**
     * 监听对方是否成功接收
     * @param _context
     * @param _intent
     */
    public void onSuccessReceive(Context _context, Intent _intent){
        Toast.makeText(context,
                "收信人已经成功接收", Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * 监听短信发送状态
     * @param _context
     * @param _intent
     * @param resultCode
     */
    private void onSendStatus(Context _context, Intent _intent, int resultCode) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                Toast.makeText(context,
                        "短信发送成功", Toast.LENGTH_SHORT)
                        .show();
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                break;
        }
    }
}

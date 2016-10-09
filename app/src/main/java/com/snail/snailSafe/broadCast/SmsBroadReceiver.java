package com.snail.snailSafe.broadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.snail.snailSafe.businessLogic.lostMobile.MessageBody;
import com.snail.snailSafe.businessLogic.lostMobile.SendMessage_lost;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.service.mobileLost.LocationService;
import com.snail.snailSafe.sharedPerfences.MobileLostSPO;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by snail on 2016/10/9.
 */

public class SmsBroadReceiver extends BroadcastReceiver {
    //广播消息类型  短信广播
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
    private String safeTelNum;
    private String secretKey;
    private String helpObjectTelNum;

    private Context mContext;


    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        MobileLostSPO mobileLostSPO = new MobileLostSPO(context);
        safeTelNum = mobileLostSPO.getString(Constant.SAFE_TEL_NUM,"");
        secretKey = mobileLostSPO.getString(Constant.MOBILE_LOST_PSD,"");
        helpObjectTelNum = mobileLostSPO.getString(Constant.HELP_OBJECT_TEL_NUM,"");
        String action = intent.getAction();
        if(action.equals(SMS_RECEIVED_ACTION)){
            //获取intent参数
            Bundle bundle=intent.getExtras();
            if(bundle!=null){
                //取pdus内容,转换为Object[]
                Object[] pdus=(Object[])bundle.get("pdus");
                //解析短信
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for(int i=0;i<messages.length;i++)
                {
                    byte[] pdu=(byte[])pdus[i];
                    messages[i]=SmsMessage.createFromPdu(pdu);
                }

                //解析完内容后分析具体参数
                for(SmsMessage msg:messages)
                {
                    onReceiveSms(msg);

                }
            }
        }
    }


    /**
     * 接收到的短信
     * @param msg
     */
    public void onReceiveSms(SmsMessage msg){
        //获取短信内容
        String content=msg.getMessageBody();
        String sender=msg.getOriginatingAddress();
        Date date = new Date(msg.getTimestampMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sendTime = sdf.format(date);

        //如果发送者是安全联系人，表明该app处于丢失手机上
        if(safeTelNum.equals(sender)){
            String body = MessageBody.getMessageBody(secretKey, SendMessage_lost.BODY_OBTAIN_POSITION);
            if(content.equals(body)){
                whenGetPosition();
            }
        }
        //如果发送者是被帮助对象，表明该app处于帮助者手机上
        else if(helpObjectTelNum.equals(sender)){
            whenBackPositionInfo();
        }
    }

    private void whenBackPositionInfo() {

    }

    private void whenGetPosition(){
        //开启短信发送位置服务
        mContext.startService(new Intent(mContext, LocationService.class));
    }
}

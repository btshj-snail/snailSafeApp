package com.snail.snailSafe.businessLogic.lostMobile;

import android.content.Context;

import com.snail.snailSafe.utils.SendMessageUtil;

/**
 * Created by snail on 2016/10/9.
 */

public class SendMessage_lost extends SendMessageUtil {

    public static final String BODY_OBTAIN_POSITION = "BODY_OBTAIN_POSITION";
    public static final String BODY_OBTAIN_STATUS = "BODY_OBTAIN_STATUS";
    public static final String BODY_DELETE_PICTURE = "BODY_DELETE_PICTURE";
    public static final String BODY_DELETE_CONSTANT = "BODY_DELETE_CONSTANT";

    public SendMessage_lost(Context context) {
        super(context);
    }

    private String obtainMessageBody(String secretKey, String bodyMode) {
        return MessageBody.getMessageBody(secretKey,bodyMode);
    }

    /**
     * 通过指定不同的模式，发送不同短信
     *
     * @param phoneNum
     * @param secretKey
     * @param bodyMode
     */
    public void sendSmsByMode(String phoneNum, String secretKey, String bodyMode) {
        String message = obtainMessageBody(secretKey, bodyMode);
        super.sendSms(phoneNum, message);
    }

}

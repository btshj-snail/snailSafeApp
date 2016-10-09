package com.snail.snailSafe.businessLogic.lostMobile;

import com.snail.snailSafe.utils.MD5Util;

/**
 * Created by snail on 2016/10/9.
 */

public class MessageBody {

    public static String getMessageBody(String secretKey, String bodyMode){
        StringBuffer body = new StringBuffer();
        body.append("#*");
        body.append(MD5Util.encoder(secretKey));
        body.append(",");
        body.append(MD5Util.encoder(bodyMode));
        body.append("*#");
        return body.toString();
    }

}

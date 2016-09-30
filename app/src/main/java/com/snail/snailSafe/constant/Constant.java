package com.snail.snailSafe.constant;

/**
 * Created by runningSnail on 2016/9/25.
 */
public class Constant {
    /**
     * 服务器地址
     *
     */
//    public static  final String SERVER_ADDRESS = "http://192.168.1.3:8080/snailApp";
    public static  final String SERVER_ADDRESS = "http://172.16.130.214:8080/snailApp";


    /**
     * =====================================================================================================================================================
     *
     * 系统设置 对应字段
     *
     *  =====================================================================================================================================================
     */

    /**
     * 系统设置中，是否更新字段对应的英文名
     */
    public static final String IS_AUTO_UPDATE = "isAutoUpdate";
    /**
     * 系统设置中，是否开启电话归属地查询字段对应的英文名
     */
    public static final String IS_QUERY_TEL_ADDRESS = "isQueryTelAddress";




    /**
     * =====================================================================================================================================================
     *
     * 用户信息记录sharedPreference 对应字段
     *
     *  =====================================================================================================================================================
     */
    public static final String ASK_APP_COUNT = "askAppCount";

    public static final String MOBILE_LOST_PSD = "mobileLostPsd";



    /**
     * =====================================================================================================================================================
     *
     * 手机防盗sharedPreference 对应字段
     *
     *  =====================================================================================================================================================
     */

    public static final String COMPLETE_SET = "completeSet";
    public static final String SIM_CODE = "simCode";
    public static final String SAFE_TEL_NUM = "safeTelNum";
    public static final String IS_OPEN_LOST_MOBILE = "isOpenMobileLostFun";
}

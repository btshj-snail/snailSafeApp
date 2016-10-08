package com.snail.snailSafe.activity.frame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.snail.snailSafe.R;
import com.snail.snailSafe.activity.mobileLost.MobileLostActivity;
import com.snail.snailSafe.activity.systemSetting.SystemSettingActivity;
import com.snail.snailSafe.adapter.frame.SystemModulesAdapter;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.pojo.frame.SystemModule;
import com.snail.snailSafe.sharedPerfences.MobileLostSPO;
import com.snail.snailSafe.utils.LogcatUtils;
import com.snail.snailSafe.utils.MD5Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by runningSnail on 2016/9/25.
 */
public class HomeActivity extends AppCompatActivity{
    private LogcatUtils logcatUtils = LogcatUtils.getLogCat(HomeActivity.class);
    private Context mContext;
    private GridView mGridView;
    private List<SystemModule> systemModuleList;
    private MobileLostSPO mobileLostSPO;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext = HomeActivity.this;
        setContentView(R.layout.activity_home);
        mobileLostSPO = new MobileLostSPO(mContext);
        initView();
        initData();
    }

    private void initData() {
        systemModuleList = new ArrayList<>();
        systemModuleList.add(new SystemModule(1,"手机防盗",R.drawable.home_safe, MobileLostActivity.class));
        systemModuleList.add(new SystemModule(2,"通信卫士",R.drawable.home_callmsgsafe, MobileLostActivity.class));
        systemModuleList.add(new SystemModule(3,"软件管理",R.drawable.home_apps, MobileLostActivity.class));
        systemModuleList.add(new SystemModule(4,"进程管理",R.drawable.home_taskmanager, MobileLostActivity.class));
        systemModuleList.add(new SystemModule(5,"流量统计",R.drawable.home_netmanager, MobileLostActivity.class));
        systemModuleList.add(new SystemModule(6,"手机杀毒",R.drawable.home_trojan, MobileLostActivity.class));
        systemModuleList.add(new SystemModule(7,"缓存清理",R.drawable.home_sysoptimize, MobileLostActivity.class));
        systemModuleList.add(new SystemModule(8,"高级工具",R.drawable.home_tools, MobileLostActivity.class));
        systemModuleList.add(new SystemModule(9,"设置中心",R.drawable.home_settings, SystemSettingActivity.class));

        mGridView.setAdapter(new SystemModulesAdapter(mContext, systemModuleList) );


    }

    private void initView() {
        mGridView = (GridView) findViewById(R.id.gv_home);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SystemModule systemModule =  systemModuleList.get(position);
                if(systemModule.getModuleInitActivity()!=null){
                    if(systemModule.getId()==1){
                        doMobileLost(systemModule.getModuleInitActivity());
                    }else{
                        Intent intent = new Intent(mContext,systemModule.getModuleInitActivity());
                        startActivity(intent);
                    }

                }
            }
        });
    }

    /**
     * 手机防盗逻辑
     * @param clazz  手机防盗界面的activity 字节码文件
     */
    private void doMobileLost(Class clazz) {
        String psd = mobileLostSPO.getString(Constant.MOBILE_LOST_PSD, "");
        if(TextUtils.isEmpty(psd)){
            showSetPsdDialog(clazz);
        }else{
            showConfirmPsdDialog(clazz);
        }


    }

    /**
     * 弹出设置密码对话框
     */
    private void showSetPsdDialog(Class activityClazz) {
        AlertDialog.Builder build = new AlertDialog.Builder(mContext);
        final Class clzz = activityClazz;
        final AlertDialog alertDialog = build.create();
        final View view = View.inflate(mContext,R.layout.set_psd_dialog,null);
        alertDialog.setView(view);
        alertDialog.show();

        Button cancelBtn = (Button) view.findViewById(R.id.btn_cancel);
        Button confirmBtn = (Button) view.findViewById(R.id.btn_confirm);

        cancelBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText inputPsd = (EditText) view.findViewById(R.id.et_input_psd);
                EditText confirmPsd = (EditText) view.findViewById(R.id.et_input_confirm_psd);
                String inputPsdText = String.valueOf(inputPsd.getText());
                String confirmPsdText = String.valueOf(confirmPsd.getText());

                if(TextUtils.isEmpty(inputPsdText)){
                    Toast.makeText(mContext,"请输入密码",Toast.LENGTH_LONG).show();
                    return;
                }else if(TextUtils.isEmpty(confirmPsdText)){
                    Toast.makeText(mContext,"请确认输入的密码",Toast.LENGTH_LONG).show();
                    return;
                }else if(!inputPsdText.equals(confirmPsdText)){
                    Toast.makeText(mContext,"输入的确认密码与密码不一致",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    // 存储密码
                    mobileLostSPO.putString(Constant.MOBILE_LOST_PSD,MD5Util.encoder(inputPsdText));
                    //隐藏弹出框
                    alertDialog.dismiss();
                    //跳转界面
                    Intent intent = new Intent(mContext,clzz);
                    startActivity(intent);

                }
            }
        });
    }

    /**
     * 弹出录入密码对话框
     * @param activityClazz
     */
    private void showConfirmPsdDialog(Class activityClazz) {


        AlertDialog.Builder build = new AlertDialog.Builder(mContext);
        final Class clzz = activityClazz;
        final AlertDialog alertDialog = build.create();
        final View view = View.inflate(mContext,R.layout.write_psd_dialog,null);
        alertDialog.setView(view);
        alertDialog.show();

        Button cancelBtn = (Button) view.findViewById(R.id.btn_cancel);
        Button confirmBtn = (Button) view.findViewById(R.id.btn_confirm);

        cancelBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取密码
                String psd = mobileLostSPO.getString(Constant.MOBILE_LOST_PSD, "");
                EditText inputPsd = (EditText) view.findViewById(R.id.et_input_psd);
                String inputPsdText = String.valueOf(inputPsd.getText());

                if(TextUtils.isEmpty(inputPsdText)){
                    Toast.makeText(mContext,"请输入密码",Toast.LENGTH_LONG).show();
                    return;
                }else if(!MD5Util.encoder(inputPsdText).equals(psd)){
                    Toast.makeText(mContext,"密码不正确",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    //隐藏弹出框
                    alertDialog.dismiss();
                    //跳转界面
                    Intent intent = new Intent(mContext,clzz);
                    startActivity(intent);
                }
            }
        });

    }
}

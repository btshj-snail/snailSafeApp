package com.snail.snailSafe.activity.frame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.snail.snailSafe.R;
import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.utils.LogcatUtils;
import com.snail.snailSafe.utils.StreamUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StartAppActivity extends AppCompatActivity {

 
    private LogcatUtils logcatUtils = LogcatUtils.getLogCat(StartAppActivity.class);
    private Context mContext;

    private static final int ENTER_HOME = 100;
    private static final int SYS_EXCEPTION = 101;
    private static final int UPDATE_VERSION = 102;
    private static final String URL_VERSION_INFO = "updateVersionInfo.json";



    private TextView tv_versionName;

    private String mVersionDesc = "";
    private String mDownloadUrl = "";


    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_VERSION:
                    showUpdateVersionDialog();
                case ENTER_HOME:
                    //进入home界面
                    skipPage_home();
                    break;
                case SYS_EXCEPTION:
                    //系统异常提示
                    exceptionTip();
                    skipPage_home();
                    break;
            }
        }
    };

    private void showUpdateVersionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.drawable.tip);
        builder.setTitle("版本更新");
        builder.setMessage(mVersionDesc);
        builder.setPositiveButton("立即更新",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //todo 立即更新
                downloadApk();
            }
        });
        builder.setNegativeButton("下次再说",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                skipPage_home();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                skipPage_home();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void downloadApk() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"snailMobileSafe.apk";
            HttpUtils httpUtils = new HttpUtils();
            httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    //todo 执行apk包
                    File file = responseInfo.result;
                    installApk(file);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    logcatUtils.e("下载失败");
                }
            });
        }
    }

    private void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        startActivityForResult(intent,0);
    }

    private void exceptionTip() {
        Toast.makeText(mContext,"系统异常",Toast.LENGTH_LONG).show();
    }

    private void skipPage_home() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = StartAppActivity.this;
        setContentView(R.layout.activity_start_app);
        initView();
        initData();

    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        skipPage_home();
    }

    private void initData() {
        tv_versionName.setText("版本：" + obtainVersionName());
        //检测版本
        checkVersion();
    }

    private void initView() {
        tv_versionName = (TextView) findViewById(R.id.tv_version_name);
    }


    /**
     * 界面跳转
     */
    private void checkVersion() {
        //新开一个线程处理业务逻辑
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean updateFlag = false;
                Message message = Message.obtain();
                long startTime = System.currentTimeMillis();
                //获取服务器最新版本信息
                try {

                    URL url = new URL(Constant.SERVER_ADDRESS+ File.separator+URL_VERSION_INFO);

                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //请求超时
                    httpURLConnection.setConnectTimeout(2000);
                    //读取超时
                    httpURLConnection.setReadTimeout(2000);

                       InputStream in =  httpURLConnection.getInputStream();
                        String jsonString = StreamUtils.stream2String(in);
                       JSONObject json =  new JSONObject(jsonString);
                       int versionCode = json.getInt("versionCode");
                        mVersionDesc = json.getString("versionDesc");
                        mDownloadUrl = json.getString("downloadUrl");

                    if(versionCode>obtainVersionCode()){
                        updateFlag = true;
                        message.what = UPDATE_VERSION;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                long endTime = System.currentTimeMillis();
                if (endTime - startTime < 4000) {
                    try {
                        Thread.sleep(4000 - (endTime - startTime));
                        if(!updateFlag){
                            message.what = ENTER_HOME;
                        }
                       
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        message.what = SYS_EXCEPTION;
                    } finally {
                        mHandler.sendMessage(message);
                    }
                }
            }
        });
        thread.start();

    }

    /**
     * 获取系统的版本名称
     *
     * @return 版本名称 返回null 表示异常
     */
    private String obtainVersionName() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取系统的版本号
     *
     * @return 版本名称 返回null 表示异常
     */
    private int obtainVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}

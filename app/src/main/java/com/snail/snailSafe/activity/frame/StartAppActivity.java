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
    private static final int URL_EXCEPTION = 103;
    private static final int IO_EXCEPTION = 104;
    private static final int JSON_CONVERT_EXCEPTION = 105;
    private static final String URL_VERSION_INFO = "updateVersionInfo.json";



    private TextView tv_versionName;

    private String mVersionDesc = "";
    private String mDownloadUrl = "";
    private File mDownloadApkFile = null;


    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_VERSION:
                    showUpdateVersionDialog();
                    break;
                case ENTER_HOME:
                    //进入home界面
                    skipPage_home();
                    break;
                case SYS_EXCEPTION:
                    //系统异常提示
                    exceptionTip();
                    skipPage_home();
                    break;
                case URL_EXCEPTION:
                    Toast.makeText(mContext,"URL 异常，检查URL路径",Toast.LENGTH_SHORT).show();
                    break;
                case IO_EXCEPTION:
                    Toast.makeText(mContext,"IO 异常，读取文件失败",Toast.LENGTH_SHORT).show();
                    break;
                case JSON_CONVERT_EXCEPTION:
                    Toast.makeText(mContext,"JSON 异常，数据转换失败",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    /**
     * 打开是否更新对话框
     */
    private void showUpdateVersionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(R.drawable.tip);
        builder.setTitle("版本更新");
        builder.setMessage(mVersionDesc);
        builder.setPositiveButton("立即更新",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadApk();
            }
        });
        builder.setNegativeButton("下次再说",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                skipPage_home();
            }
        });
        //用户直接点击返回键 取消安装的情况
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                skipPage_home();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 下载apk
     * 注意一定要是snailMobileSafe.apk这个名字
     * 通过Environment获取sdk的状态。如果没有sdk卡，则保存在应用私有数据文件中
     */
    private void downloadApk() {
        String path_prefix = "";
        String path;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            path_prefix = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
            path_prefix = mContext.getFilesDir().getAbsolutePath();
        }
        path = path_prefix+File.separator+"snailMobileSafe.apk";
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                //todo 执行apk包
                mDownloadApkFile = responseInfo.result;
                installApk(mDownloadApkFile);

            }

            @Override
            public void onFailure(HttpException e, String s) {
                logcatUtils.e("下载失败 "+e.getMessage());
                Toast.makeText(mContext,"下载apk失败，请下次再试。",Toast.LENGTH_SHORT).show();
                skipPage_home();
            }
        });
    }

    /**
     * 安装apk
     * 利用系统提供的统一安装界面实现。
     * @param file
     */
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

    /**
     * 跳转下一个home界面，并且关闭首页
     */
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
        if(mDownloadApkFile!=null){
            mDownloadApkFile.deleteOnExit();
        }
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
     * 检测版本信息
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
                    httpURLConnection.setConnectTimeout(4000);
                    //读取超时
                    httpURLConnection.setReadTimeout(4000);

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
                    message.what = URL_EXCEPTION;
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = IO_EXCEPTION;
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.what = JSON_CONVERT_EXCEPTION;
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
            Toast.makeText(mContext,"versionName"+packageInfo.versionName,Toast.LENGTH_LONG);
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

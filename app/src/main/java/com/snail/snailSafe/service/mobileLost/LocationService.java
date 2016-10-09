package com.snail.snailSafe.service.mobileLost;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;

import com.snail.snailSafe.constant.Constant;
import com.snail.snailSafe.sharedPerfences.MobileLostSPO;

/**
 * Created by snail on 2016/10/9.
 */

public class LocationService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        //获取位置管理者对象
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        // 以最优的方式获取坐标
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);//运行使用流量
        criteria.setAccuracy(Criteria.ACCURACY_FINE);//指定获取经纬度的精确度
        String provider = lm.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(provider, 5 * 60 * 1000, 100, new MyLocationListener());



    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    class MyLocationListener implements LocationListener {


        @Override
        public void onLocationChanged(Location location) {
            Context context = getApplicationContext();
            MobileLostSPO mobileLostSPO = new MobileLostSPO(context);
            String phoneNum = mobileLostSPO.getString(Constant.SAFE_TEL_NUM,"");
            //经度
            double longitude =  location.getLongitude();
            //维度
            double latitude =  location.getLatitude();
            //发送短信
            if(!TextUtils.isEmpty(phoneNum)){
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNum,null,"longitude="+longitude+",latitude="+latitude,null,null);
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}

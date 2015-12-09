package com.example.enpit_itw.miclient;

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
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by yoshikawa on 2015/11/18.
 */
public class LocationSendingService  extends Service implements LocationListener{
    private final String TAG = "SimpleService";
    LocationManager loc;
    MyAsyncTask task;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        //LocationManagerインスタンスの生成
        loc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i(TAG, "onStartCommand");
        if(loc != null){
            loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 100, this);
            Log.d("位置情報設定","True");

        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        if(loc != null) {
            loc.removeUpdates(this);
        }

    }

    public void onLocationChanged(Location location){
        //小数点以下の桁数を揃える
        DecimalFormat df=new DecimalFormat();
        df.setMaximumFractionDigits(14);
        df.setMinimumFractionDigits(14);

        //緯度を取得
        double latitude = location.getLatitude();
        String Lat = df.format(new Double(latitude));

        //経度を取得
        double longitude = location.getLongitude();
        String Lon = df.format(new Double(longitude));

        //時間を取得
        Time time = new Time("Asia/Tokyo");
        time.setToNow();
        String date = time.year + "-" + (time.month+1) + "-" + time.monthDay + " " + time.hour + ":" + time.minute + ":" + time.second;
        //Toast.makeText(this, Lat + " ," + Lon, Toast.LENGTH_SHORT).show();

        task= new MyAsyncTask();
        task.execute("position", Lat, Lon, date);

        Log.v("ReceiverActivity", "経度 : " + Lat + "  緯度 : " + Lon + "  取得時間 : " + date);
    }

    public void onProviderDisabled(String provider){}

    public void onProviderEnabled(String provider){}

    public void onStatusChanged(String provider, int status, Bundle extras){}

}

package com.example.enpit_itw_esaka.myapplication2;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.app.Activity;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.example.enpit_itw_esaka.myapplication2.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Enumeration;

public class MainActivity extends Activity implements LocationListener{

    MyAsyncTask task;
    LocationManager loc;
    WebView webview;
    ImageView image;
    PendingIntent pendingintent;
    Context context;
    Socket connection;
    BufferedWriter writer;
    private ShareActionProvider mShareActionProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //WebViewインスタンスを生成
        webview = new WebView(this);

        //JavaScriptを有効にする
        webview.getSettings().setJavaScriptEnabled(true);

        //setContentViewに作成したWebビューを設定する
        setContentView(webview);

        //読み込み時にページ幅を画面幅に合わせる
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);

        //ネットワークに接続していないときはキャッシュを表示する
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //指定したURLをWebビューに設定する
        webview.loadUrl("https://www.google.co.jp/");


        //位置情報取得時に実行するインテントを生成
        //Intent intent = new Intent(this, ReceiverActivity.class);
        //pendingintent = PendingIntent.getActivity(this, 0, intent, 0);

        //精度を設定
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        //LocationManagerインスタンスの生成
        loc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }

    //バーにボタンを追加する
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //「action_bar_menu2.xml」で定義したメニュー項目を適用する
        getMenuInflater().inflate(R.menu.action_bar_menu2, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    //バーのボタンを押されたとき
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item){
        switch(item.getItemId()) {
            case R.id.menu_reg:
                // 編集画面への遷移処理
                Intent intent = new Intent(MainActivity.this, RegFriends.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //位置情報更新の設定(更新時間：5秒、更新距離：1m)
        if(loc != null){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                //許可されているならばここを実行
                loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);
                //Toast.makeText(this, "位置情報を設定しました", Toast.LENGTH_LONG).show();
                Log.d("位置情報設定","True");
            }
            else{
                Log.d("位置情報設定", "False");
                //Toast.makeText(this, "位置情報の許可がありません！", Toast.LENGTH_LONG).show();
            }
        }
    }

    //位置情報が更新された時
    public void onLocationChanged(Location location){
        //小数点以下の桁数を揃える
        DecimalFormat df=new DecimalFormat();
        df.setMaximumFractionDigits(7);
        df.setMinimumFractionDigits(7);

        //緯度を取得
        double latitude = location.getLatitude();
        String Lat = df.format(new Double(latitude));

        //経度を取得
        double longitude = location.getLongitude();
        String Lon = df.format(new Double(longitude));

        //時間を取得
        Time time = new Time("Asia/Tokyo");
        time.setToNow();
        String date = time.year + "年" + (time.month+1) + "月" + time.monthDay + "日" + time.hour + "時" + time.minute + "分" + time.second + "秒";

        Toast.makeText(this, Lat, Toast.LENGTH_SHORT).show();
        task= new MyAsyncTask(this);
        task.execute(Lat, Lon);
        Log.v("ReceiverActivity", "経度" + Lat + "  緯度" + Lon + "  取得時間" + date);
    }

    public void onProviderDisabled(String provider){}

    public void onProviderEnabled(String provider){}

    public void onStatusChanged(String provider, int status, Bundle extras){}

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //位置情報更新の解除
        if(loc != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                loc.removeUpdates(this);
            }
        }
    }
}

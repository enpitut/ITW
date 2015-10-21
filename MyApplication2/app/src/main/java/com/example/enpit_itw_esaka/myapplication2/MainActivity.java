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

    LocationManager loc;
    WebView webview;
    ImageView image;
    PendingIntent pendingintent;
    Context context;
    Socket connection;
    BufferedWriter writer;
    private ShareActionProvider mShareActionProvider;
    private int mode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        //QRコードの作成
        String number = "111111";
        int width = 100;
        int height = 100;
        try {
            QRCodeWriter writer = new QRCodeWriter();
            //エンコード
            BitMatrix bm = null;
            bm = writer.encode(number, BarcodeFormat.QR_CODE, width, height);
            //ピクセルを作る
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            //データがあるところだけ黒にする
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    bitmap.setPixel(x, y, bm.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

            //QRコードを表示する
            if(bitmap==null){
                //エンコード失敗
                Log.v("Error", "エンコード失敗");
            }else {
                image = (ImageView) findViewById(R.id.image_qr);
                image.setImageBitmap(bitmap);
                setContentView(image);
                //image.setVisibility(View.GONE);
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
*/

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

        mode=0;
/*
        //サーバへ接続
        connection = null;
        writer = null;
        try{
            connection = new Socket("192.168.128.103", 1000);//サーバのホスト名とポート番号を書く
        }catch(UnknownHostException e){
            e.printStackTrace();
            Log.v("Error", e.toString());
            Log.v("Server connection", "サーバとの接続に失敗しました");
        }catch(IOException e){
            e.printStackTrace();
            Log.v("Error", e.toString());
            Log.v("Server connection", "サーバとの接続に失敗しました");
        }
*/
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //「action_bar_menu.xml」で定義したメニュー項目を適用する
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        //「ShareActionProvider」インスタンスを取得する
        mShareActionProvider = (ShareActionProvider)menu.findItem(R.id.menu_share).getActionProvider();

        //テキストを共有するためのインテントを生成
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        //「サンプルテキスト」という文字列を共有するテキストとして指定
        shareIntent.putExtra(Intent.EXTRA_TEXT, "サンプルテキスト");

        //共有ボタン押下時の共有インテントをセットする
        mShareActionProvider.setShareIntent(shareIntent);

        return true;
    }
*/

    //バーにボタンを追加する
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //「action_bar_menu2.xml」で定義したメニュー項目を適用する
        getMenuInflater().inflate(R.menu.action_var_menu2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //ボタンを押されたとき
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(mode==0) {//ON
            webview.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            mode=1;
        }else if(mode==1){//OFF
            image.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
            mode=0;
        }
        return true;
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
        Log.v("ReceiverActivity", "経度" + Lat + "  緯度" + Lon + "  取得時間" + date);
/*
        //位置情報をサーバに送る
        String msg = "Location" + " " + String.valueOf(longitude) + " " + String.valueOf(latitude);
        try{
            writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            writer.write(msg);
            writer.flush();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                writer.close();
            }catch(IOException e){
                e.printStackTrace();
                Log.v("Error", e.toString());
                Log.v("Server Message", "サーバとの接続に失敗しました");
            }
        }
        */
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

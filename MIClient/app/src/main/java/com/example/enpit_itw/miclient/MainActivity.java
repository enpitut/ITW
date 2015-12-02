package com.example.enpit_itw.miclient;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import java.io.BufferedWriter;
import java.net.Socket;

public class MainActivity extends Activity{

    MyAsyncTask task;
    LocationManager loc;
    WebView webview;
    ImageView image;
    PendingIntent pendingintent;
    Context context;
    Socket connection;
    BufferedWriter writer;
    private ShareActionProvider mShareActionProvider;
    final String url = "http://192.168.1.64/cakephp/Users/login";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //WebViewインスタンスを生成
        webview = (WebView) findViewById(R.id.webview);

        //JavaScriptを有効にする
        webview.getSettings().setJavaScriptEnabled(true);

        //新しいタブ・ウィンドウをWebviewないで立ち上げられるようにする
        webview.getSettings().setSupportMultipleWindows(true);

        //あたらしいWindowやタブをChromeを立ち上げずにWebView内で立ち上げる
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        //ネットワークに接続していないときはキャッシュを表示する
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        //指定したURLをWebビューに設定する
        webview.loadUrl(url);


        //位置情報取得時に実行するインテントを生成
        //Intent intent = new Intent(this, ReceiverActivity.class);
        //pendingintent = PendingIntent.getActivity(this, 0, intent, 0);

        //精度を設定
        // Criteria criteria = new Criteria();
        // criteria.setAccuracy(Criteria.ACCURACY_FINE);

        //LocationManagerインスタンスの生成
        // loc = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Intent i = new Intent(this, com.example.enpit_itw.miclient.LocationSendingService.class);
        startService(i);


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
                Intent edit_intent = new Intent(MainActivity.this, RegFriends.class);
                startActivity(edit_intent);
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //位置情報更新の設定(更新時間：50秒、更新距離：1m)

    }

    //位置情報が更新された時

    public void onStartClick(View view) {
        Intent i = new Intent(this, com.example.enpit_itw.miclient.LocationSendingService.class);
        startService(i);
    }

    public void onStopClick(View view) {
        Intent i = new Intent(this, com.example.enpit_itw.miclient.LocationSendingService.class);
        stopService(i);
    }

    @Override
    protected void onDestroy(){
        Intent i = new Intent(this, com.example.enpit_itw.miclient.LocationSendingService.class);
        stopService(i);
        super.onDestroy();


    }
}

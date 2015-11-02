package com.example.enpit_itw_esaka.myapplication2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RegFriends extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regfriends);
        /*
        //受け取ったインテントを取得
        Intent intent = getIntent();
        showIntentData(intent);
        */
    }

    //バーにボタンを追加する
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //「reg.xml」で定義したメニュー項目を適用する
        getMenuInflater().inflate(R.menu.reg, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    //バーのボタンを押したとき
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item){
        switch(item.getItemId()) {
            case R.id.action_del:
                // メイン画面に戻る
                this.finish();
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    //ボタンを押したとき
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.button2:
                //処理を書く
                Log.v("event","push button!")
        }
    }

    /*
    private void showIntentData(Intent intent){
        //QRコードの作成
        try {
            //インテントからBundleを取得
            Bundle bundle = intent.getExtras();

            //ユーザIDオブジェクトを取得
            String number = (String) bundle.get("userID");

            QRCodeWriter writer = new QRCodeWriter();
            //エンコード
            BitMatrix bm = null;
            bm = writer.encode(number, BarcodeFormat.QR_CODE, 100, 100);
            //ピクセルを作る
            int width = bm.getWidth();
            int height = bm.getHeight();
            int[] pixels = new int[width * height];
            //データがあるところだけ黒にする
            for(int y=0; y<height; y++){
                int offset = y*width;
                for(int x=0;x<width; x++){
                    pixels[offset+x] = bm.get(x, y) ? Color.BLACK : Color.WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            //QRコードを表示する
            ImageView image = new ImageView(this);
            image.setImageBitmap(bitmap);
            setContentView(image);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    */
}

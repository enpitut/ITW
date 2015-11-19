package com.example.enpit_itw.miclient;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/*
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
*/
public class QRCodeCreate extends ActionBarActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcodecreate);
        /*
        //受け取ったインテントを取得
        Intent intent = getIntent();
        showIntentData(intent);
        */
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.qr, menu);
        return true;
    }
    */

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

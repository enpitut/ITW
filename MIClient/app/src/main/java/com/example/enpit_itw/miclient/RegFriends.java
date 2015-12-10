package com.example.enpit_itw.miclient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class RegFriends extends Activity implements OnClickListener, GetId.AsyncTaskCallback {
    
    Integer user_id;
    MyAsyncTask task;
    GetId task2;
    CheckBox checkbox;
	String USERID=null;
	EditText editText;
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regfriends);
        task2 = new GetId((GetId.AsyncTaskCallback) this);
        task2.execute("user_id");
        /*
        TextView idText = (TextView) this.findViewById(R.id.idText);
        user_id = 123456789;//実際はここに取得した自分のIDが入る
        idText.setText(Integer.toString(user_id));
        */


        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //処理を書く
                Log.v("event", "push button!");
                //Toast.makeText(this, "ボタンがクリックされました", Toast.LENGTH_SHORT).show();
                editText = (EditText) findViewById(R.id.editText);
                SpannableStringBuilder fid = (SpannableStringBuilder)editText.getText();
                String friend_id = fid.toString();
            	while(friend_id.substring(0,1) == "0"){
            		if(friend_id.length() > 1){friend_id = friend_id.substring(1);}
            	}
                task = new MyAsyncTask();
                checkbox = (CheckBox) findViewById(R.id.parent_check);
                if(checkbox.isChecked() == true){
                    task.execute("parent", friend_id, String.valueOf(user_id));
                }
                else{
                    task.execute("friends", friend_id, String.valueOf(user_id));
                }
            }
        });

        Button qr_reader = (Button)findViewById(R.id.qr_reader);
        qr_reader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //処理を書く
                Log.v("event", "open reader!");
                //Toast.makeText(this, "リーダーが開きます", Toast.LENGTH_SHORT).show();

                IntentIntegrator integrator = new IntentIntegrator(RegFriends.this);
                integrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });
        
        createQRcode();
    }

    public void postExecute(String result){
        TextView idText = (TextView) this.findViewById(R.id.idText);
        user_id = Integer.parseInt(result);//実際はここに取得した自分のIDが入る
    	USERID = String.format("%1$09d", user_id);//9桁にそろえる(先頭0埋め)
        idText.setText(USERID.substring(0,3) + " " + USERID.substring(3,6) + " " + USERID.substring(6,9));
        createQRcode();
    }

    //バーにボタンを追加する
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //「reg.xml」で定義したメニュー項目を適用する
        getMenuInflater().inflate(R.menu.reg, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    //バーのボタンを押されたとき
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
    public void onClick(View v){}
    
    private void createQRcode() {
        Bitmap qr = null;
        try{
            qr = createQRCodeByZxing("####################"+USERID, 600);
        }catch (WriterException e){
            Log.d("createQRCode", "error:", e);
        }

        //QRコードの表示
        ImageView QRCodeImage = (ImageView)findViewById(R.id.qr_image);
        QRCodeImage.setImageBitmap(qr);

        try{
            File root = Environment.getExternalStorageDirectory();

            //日付でファイル名を作成
            Date mDate = new Date();
            SimpleDateFormat fileName = new SimpleDateFormat("yyyyMMdd_HHmmSS");

            //保存処理開始
            FileOutputStream fos = null;
            fos = new FileOutputStream(new File(root, fileName.format(mDate)+".jpg"));

            //jpegで保存
            qr.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            //保存処理終了
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("Error", ""+e.toString());
        } catch (IOException e) {
            Log.e("Error", "" + e.toString());
        }
    }

    //contentsは変換する文字列、sizeは出力するQRコードの大きさ
    public Bitmap createQRCodeByZxing(String contents, int size) throws WriterException{
        //QRコードをエンコードするクラス
        QRCodeWriter writer = new QRCodeWriter();

        //異なる型のあたいを入れるためgenericは使えない
        Hashtable encodeHint = new Hashtable();

        //日本語を扱うためにシフトJISを指定
        encodeHint.put(EncodeHintType.CHARACTER_SET, "shiftjis");
        //エラー修復レベルを指定
        encodeHint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        BitMatrix qrCodeData = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, encodeHint);

        //QRコードのbitmap画像を作成
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.argb(255, 255, 255, 255));
        for(int x=0; x<qrCodeData.getWidth(); x++) {
            for(int y=0; y<qrCodeData.getHeight(); y++){
                if(qrCodeData.get(x, y) == true){
                    bitmap.setPixel(x, y, Color.argb(255, 0, 0, 0));//trueはBlack
                }else{
                    bitmap.setPixel(x, y, Color.argb(255, 255, 255, 255));//falseはWhite
                }
            }
        }
        return bitmap;
    }

    protected void onActivityResult(int requestCode, int resultcode, Intent data){
        super.onActivityResult(requestCode, resultcode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultcode, data);
        if(scanResult != null){
            editText = (EditText) findViewById(R.id.editText);
            editText.setText(scanResult.getContents().substring(20));
            Log.d("scan", "==-----: " + scanResult.getContents());
        }
    }
}

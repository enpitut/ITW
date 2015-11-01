package com.example.enpit_itw_esaka.myapplication2;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoshikawa on 2015/11/01.
 */
public class MyAsyncTask extends AsyncTask<String, Integer, Integer> implements DialogInterface.OnCancelListener {
    final String TAG = "MyAsyncTask";

    private Activity m_Activity;

    private static final String url = "http://192.168.1.67/test.php";

    //クライアント設定
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost(url);

    List<NameValuePair> nameValuePair;
    HttpResponse response;
    ByteArrayOutputStream byteArrayOutputStream;
    HttpResponse res = null;

    public MyAsyncTask(Activity activity) {
        this.m_Activity = activity;
    }


    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute");
    }

    @Override
    protected Integer doInBackground(String... contents) {
        Log.d(TAG, "doInBackground - " + contents[0] + contents[1]);
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        ArrayList <NameValuePair> params = new ArrayList<NameValuePair>();
        params.add( new BasicNameValuePair("lat", contents[0]));
        params.add(new BasicNameValuePair("lon", contents[1]));

        try {
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            res = httpClient.execute(post);

            byteArrayOutputStream = new ByteArrayOutputStream();
            res.getEntity().writeTo(byteArrayOutputStream);

        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.valueOf(res.getStatusLine().getStatusCode());

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d(TAG, "onProgressUpdate - " + values[0]);

    }

    @Override
    protected void onCancelled() {
        Log.d(TAG, "onCancelled");

    }

    @Override
    protected void onPostExecute(Integer result) {
        Log.d(TAG, "onPostExecute - " + result);


        //サーバーからの応答を取得
        if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
        {
//デバッグ用にリザルトコードをTextViewに表示させておく
            /*
            TextView tv = (TextView) this.m_Activity.findViewById(R.id.textView1);
            tv.setText(""+result);

            tv.setText(result+"\n"+byteArrayOutputStream.toString());
*/
//サーバーから受けとった文字列の処理
            //Toast.makeText(this.m_Activity, "結果 : " + byteArrayOutputStream.toString(),Toast.LENGTH_LONG).show();
            Log.d(TAG, "サーバからの応答 : " + byteArrayOutputStream.toString());
            /*
            if(byteArrayOutputStream.toString().equals("1")){
                Toast.makeText(this.m_Activity, "[ここには処理１] ", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this.m_Activity, "[ここには処理２] ", Toast.LENGTH_LONG).show();
            }
            */
        }
        else
        {
            Toast.makeText(this.m_Activity, "[error]: "+response.getStatusLine(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Log.d(TAG, "Dialog onCancel... calling cancel(true)");
        this.cancel(true);
    }
}
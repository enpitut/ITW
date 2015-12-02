package com.example.enpit_itw.miclient;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoshikawa on 2015/11/01.
 */
public class MyAsyncTask extends AsyncTask<String, Integer, Integer> implements DialogInterface.OnCancelListener {
    final String TAG = "MyAsyncTask";

    private Activity m_Activity;

    private static final String url = "http://192.168.1.64/position_registor.php";
    private static final String urlF = "http://192.168.1.64/friend_registor.php";


    List<NameValuePair> nameValuePair;
    HttpResponse response;
    ByteArrayOutputStream byteArrayOutputStream;
    HttpResponse res = null;

    /*public MyAsyncTask(Activity activity) {
        this.m_Activity = activity;
    }*/


    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute");
    }

    @Override
    protected Integer doInBackground(String... contents) {
        ArrayList <NameValuePair> params = new ArrayList<NameValuePair>();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post;


        if(contents[0] == "friends") {
            Log.d(TAG, "doInBackground - " + contents[1] + contents[2]);
            post = new HttpPost(urlF);

            params.add(new BasicNameValuePair("friend_id", contents[1]));
            params.add(new BasicNameValuePair("user_id", contents[2]));
        }else if(contents[0] == "position"){
            Log.d(TAG, "doInBackground - " + contents[1] + contents[2]);
            post = new HttpPost(url);
            SessionSync.webView2HttpClient(httpClient);

            params.add(new BasicNameValuePair("lat", contents[1]));
            params.add(new BasicNameValuePair("lon", contents[2]));
            params.add(new BasicNameValuePair("date", contents[3]));
            //params.add(new BasicNameValuePair("user_id", contents[4]));
        }
        else{
            Log.v(TAG, "contents error!");
            return -1;
        }

        try {
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            res = httpClient.execute(post);

            byteArrayOutputStream = new ByteArrayOutputStream();
            res.getEntity().writeTo(byteArrayOutputStream);

        }
        catch(Exception e){
            Log.v("MyAsyncTask", e.toString());
            return -1;
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

        if(result == -1) {//通信に失敗
            Log.d(TAG, "communication error");
            return;
        }
        else{
            Log.d(TAG,"Status : " + res.getStatusLine().getStatusCode());
        }
        //サーバーからの応答を取得
        if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
        {
            try {
                Log.d(TAG, "サーバからの応答 : " + EntityUtils.toString(res.getEntity()));
            }
            catch(Exception e){
                Log.d(TAG, "Entityが空");
            }
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
            Log.d(TAG, "error(connection?)");
            //Toast.makeText(this.m_Activity, "[error]: "+response.getStatusLine(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        Log.d(TAG, "Dialog onCancel... calling cancel(true)");
        this.cancel(true);
    }
}

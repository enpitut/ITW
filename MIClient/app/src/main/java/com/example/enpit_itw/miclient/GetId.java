package com.example.enpit_itw.miclient;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GetId extends AsyncTask<String, Integer, Integer> implements DialogInterface.OnCancelListener{
    final String TAG = "GetId";

    List<NameValuePair> nameValuePair;
    HttpResponse response;
    ByteArrayOutputStream byteArrayOutputStream;
    HttpResponse res = null;
    Integer user_id = null;

    private static final String url = "http://192.168.1.58/getid.php";

    //RegFriendsへのコールバック用interface
    public interface AsyncTaskCallback{
        void postExecute(String result);
    }
    private AsyncTaskCallback callback = null;
    public GetId(AsyncTaskCallback _callback){
        this.callback = _callback;
    }

    @Override
    protected void onPreExecute(){
        Log.d(TAG, "onPreExecute");
    }

    @Override
    protected Integer doInBackground(String... contents){
        ArrayList <NameValuePair> params = new ArrayList<NameValuePair>();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post;
        post = new HttpPost(url);
        SessionSync.user_idHttpClient(httpClient);

        try{
            res = httpClient.execute(post);

            byteArrayOutputStream = new ByteArrayOutputStream();
            res.getEntity().writeTo(byteArrayOutputStream);
        }catch (Exception e){
            Log.v("MyAsyncTask", e.toString());
            return -1;
        }

        return Integer.valueOf(res.getStatusLine().getStatusCode());
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        Log.d(TAG, "onProgressUpdate - " + values[0]);
    }

    @Override
    protected void onCancelled(){
        Log.d(TAG, "onCancelled");
    }

    @Override
    protected void onPostExecute(Integer result) {
        Log.d(TAG, "onPostExecute - " + result);

        if(result == -1){
            //通信に失敗
            Log.d(TAG, "communication error");
            return;
        }else{
            Log.d(TAG, "Status : " + res.getStatusLine().getStatusCode());
        }
        //サーバーからの応答を取得
        if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            try{
                Log.d(TAG, "サーバからの応答 : " + EntityUtils.toString(res.getEntity()));
            }catch(Exception e){
                Log.d(TAG, "Entityが空");
            }
            String UserId = byteArrayOutputStream.toString();
            callback.postExecute(UserId);
        }else{
            Log.v(TAG, "サーバからの応答がありませんでした。");
        }
    }

    @Override
    public void onCancel(DialogInterface dialog){
        Log.d(TAG, "Dialog onCancel... calling cancel(true)");
        this.cancel(true);
    }
}

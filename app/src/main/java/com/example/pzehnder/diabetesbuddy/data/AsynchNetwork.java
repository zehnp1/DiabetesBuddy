package com.example.pzehnder.diabetesbuddy.data;

import android.os.AsyncTask;
import android.util.JsonReader;

import com.example.pzehnder.diabetesbuddy.activitys.Login;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ivan on 19.12.2014.
 */
public class AsynchNetwork extends AsyncTask<String,Integer,String> {

    @Override
    protected String doInBackground(String... params) {
        HttpClient httpClient = new DefaultHttpClient();
        // replace with your url
        HttpPost httpPost = new HttpPost("http://192.168.0.17/DiabestesBuddy_service.php");

        //making POST request.
        try {
            HttpResponse response = httpClient.execute(httpPost);
            // write response to log
            InputStream inputStream = null;
            String result = "";
            inputStream = response.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null) {


                DatabaseHandler db = Login.getDb();
                db.open();
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
                reader.beginArray();
                while (reader.hasNext()) {
                    reader.beginObject();
                    reader.nextName();
                    String frage = reader.nextString();
                    reader.nextName();
                    String antwort1 = reader.nextString();
                    reader.nextName();
                    String antwort2 = reader.nextString();
                    reader.nextName();
                    String antwort3 = reader.nextString();
                    reader.nextName();
                    String antwort4 = reader.nextString();
                    reader.nextName();
                    String correct = reader.nextString();
                    reader.nextName();
                    String language = reader.nextString();
                    reader.endObject();
                    db.insertQuizData(frage,antwort1,antwort2,antwort3,antwort4,Integer.parseInt(correct),language);
                }
                reader.endArray();
                reader.close();
                inputStream.close();
                db.close();
            }

        } catch (ClientProtocolException e) {
            // Log exception
            e.printStackTrace();
        } catch (IOException e) {
            // Log exception
            e.printStackTrace();
        }
        return  null;
    }

    protected void onProgressUpdate() {
    }

    protected void onPostExecute(Long result) {
    }

}

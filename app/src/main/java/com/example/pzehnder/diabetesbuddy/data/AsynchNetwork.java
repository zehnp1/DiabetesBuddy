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
 * Log:
 * Erstellt von Ivan Wissler 19.12.2014
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * Die Klasse AsynchNetwork erstelle eine Asynchrone Internetverbindung zu einem bestimmten Server
 * auf welchme neue Quiz Fragen definiert werden könne und per Webservice in die Datenbank geladen werden.
 *
 */
public class AsynchNetwork extends AsyncTask<String,Integer,String> {

    @Override
    protected String doInBackground(String... params) {
        HttpClient httpClient = new DefaultHttpClient();

        //HttpPost adresse
        HttpPost httpPost = new HttpPost("http://192.168.0.17/DiabestesBuddy_service.php");

        //POST request
        try {
            HttpResponse response = httpClient.execute(httpPost);
            // Antwort Speichern
            InputStream inputStream = null;
            String result = "";
            inputStream = response.getEntity().getContent();

            // Wenn neu Fragen forhanden sind werden diese in die Datenbank geladen
            if(inputStream != null) {

                //Datenbak wird geladen
                DatabaseHandler db = Login.getDb();
                //Datenbank wrid geöffnet
                db.open();
                //Da die Fragen im JOSN Format hinterlegt sind muss ein JsonReader instanziert werden
                JsonReader reader = new JsonReader(new InputStreamReader(inputStream));
                reader.beginArray();
                //Solange weiter Fragen vorhanden sind werden diese Geladen und in die Datenbank geschrieben
                while (reader.hasNext()) {
                    reader.beginObject();
                    reader.nextName();
                    //Laden der Frage
                    String frage = reader.nextString();
                    reader.nextName();
                    //Laden von Antwort 1
                    String antwort1 = reader.nextString();
                    reader.nextName();
                    //Laden von Antwort 2
                    String antwort2 = reader.nextString();
                    reader.nextName();
                    //Laden von Antwort 3
                    String antwort3 = reader.nextString();
                    reader.nextName();
                    //Laden von Antwort 4
                    String antwort4 = reader.nextString();
                    reader.nextName();
                    //Laden der richtigen Antwort
                    String correct = reader.nextString();
                    reader.nextName();
                    //Laden der Sprache der Frage
                    String language = reader.nextString();
                    reader.endObject();
                    //Abfüllen der geladenen Informationen in die Datenbank
                    db.insertQuizData(frage,antwort1,antwort2,antwort3,antwort4,Integer.parseInt(correct),language);
                }
                reader.endArray();
                reader.close();
                inputStream.close();
                //Datenbank schliessen
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

package com.example.pzehnder.diabetesbuddy.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost.TabSpec;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.pzehnder.diabetesbuddy.R;
import com.example.pzehnder.diabetesbuddy.components.SugarCompWidget;
import com.example.pzehnder.diabetesbuddy.data.AsynchNetwork;
import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;

import java.security.Timestamp;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Log:
 * Erstellt von Michael Heeb am 13.11.2014.
 * Lezte Änderung von Ivan Wissler 26.12.2014
 *
 * Beschreibung:
 * In der Blutzucker Activity kann der Benutzer angaben zu seinen Täglichen Blutzuckerwerten machen
 * Wird ein Kritischer wert Eingegeben wird automatisch ein SMS an die Bezugsperson gesendet.
 *
 */
public class Blutzucker extends Activity {

    //Instatnziert Container zur Anzeige der Eingegebenen Werte
    SugarCompWidget sugarCompWidget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blutzucker_essen);

        //Zeigt das Aktuelle Datum im Format "dd.M.yyyy" an
        TextView datum = (TextView)findViewById(R.id.sugarDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        datum.setText(sdf.format(new Date()));

        //Instanziert den Dialog der Geöffnet werden Kann um einen Neuen Eintrag zu machen
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sugar_dialog);
        final TextView dialogDate =(TextView)dialog.findViewById(R.id.sugarDialogDate);

        //Der Cancel Button schliesst den Dialog ohne einen neuen Eintrag zu machen
        Button cancelbtn = (Button)dialog.findViewById(R.id.sugerDialogCancel);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog wird geschlossen
                dialog.hide();
            }
        });

        //Der Button schliesst den Dialog und speichert die Eingaben in die Datenbank
        Button button = (Button)dialog.findViewById(R.id.sugarDialogButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog wird geschlossen
                dialog.hide();
                //Lädt Datenbank
                DatabaseHandler db = Login.getDb();
                //öffnet Datenbank
                db.open();
                //Lädt aktuelles Datumg im Format ""dd.M.yyyy"
                SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
                String date = sdf.format(new Date());
                //Lädt aktuelle Zeit im Fromat "HH:mm:ss"
                sdf = new SimpleDateFormat("HH:mm:ss");
                String time = sdf.format(new Date());
                //Instanziert Eingabewerte
                Float bz = null;
                Integer be = null;
                Integer basal = null;
                Integer bolus = null;
                //Wenn ein Blutzuckerwert angegeben wurde wird dieser Geladen
                if(((TextView) dialog.findViewById(R.id.sugarDialogBZ)).getText().length() != 0) {
                    bz = Float.parseFloat(((TextView) dialog.findViewById(R.id.sugarDialogBZ)).getText() + "");
                }
                //Wenn ein BrotEinheitWert angegeben wurde wird dieser Geladen
                if(((TextView) dialog.findViewById(R.id.sugarDialogBE)).getText().length() != 0) {
                    be = Integer.parseInt(((TextView) dialog.findViewById(R.id.sugarDialogBE)).getText() + "");
                }
                //Wenn ein BasalWert angegeben wurde wird dieser Geladen
                if(((TextView) dialog.findViewById(R.id.sugarDialogBasal)).getText().length() != 0) {
                    basal = Integer.parseInt(((TextView) dialog.findViewById(R.id.sugarDialogBasal)).getText() + "");
                }
                //Wenn ein BolusWert angegeben wurde wird dieser Geladen
                if(((TextView) dialog.findViewById(R.id.sugarDialogBolus)).getText().length() != 0) {
                    bolus = Integer.parseInt(((TextView) dialog.findViewById(R.id.sugarDialogBolus)).getText() + "");
                }
                //Ist der Blutzuckerwert in einem kritischen bereich wird eine Nachricht an die Bezugsperson gesendet
               try {
                   if ((bz < 3.6 || bz > 7) && bz != 0) {
                       sendSMS(Login.phonenumber, "Der Blutzuckerwert von " + Login.user +" lieg bei " + bz +"mmol/l");
                   }
               }
               catch (NullPointerException e)
               {
                   Log.d("bz = null",e.toString());
               }
                // die geladenen Werte werden in die Datenbank geschrieben
                db.insertBs_valuesData(date, time, bz, be, basal, bolus, "anote", Login.user);
                // die Datenbank wird geschlossen
                db.close();
                // Die in der Datenbank gespeicherten werte werden Angezeigt
                refreshValues();
            }
        });

        //Buton NewSugarValue öffnet den Dialog um einen neuen Eintrag einzugeben
        Button newSugarValue = (Button)findViewById(R.id.buttonSugar);
        newSugarValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy, HH:mm");
                dialogDate.setText(sdf.format(new Date()));
                dialog.show();
            }
        });

        //Die In der Datenbank gespeicherten Einträge werden Angezeigt
        refreshValues();
    }

    private void refreshValues()
    {
        //Lädt die in der Datenbank gespeicherte werte vom aktuellen Tag und zeigt diese an.

        //Lädt aktuelles Datum im Fromat "dd.M.yyyy"
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        String date = sdf.format(new Date());
        //Lädt Datenbank
        DatabaseHandler db = Login.getDb();
        //öffnet Datenbank
        db.open();
        //Lädt alle Werte vom aktuellen Datum des eingelogten Users
        Cursor cursor = db.returnBsValuesData(date, Login.user);

        //Mit einer While Schlaufen werden die letzten 10 eingetragenen Werte angezeigt
        int i = 1;
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && i<=10)
        {
            //Lädt Container um Wert abzufüllenn zwischen 1-10
            String idString = "sugarComp"+i;
            int id = getResources().getIdentifier(idString,"id","com.example.pzehnder.diabetesbuddy");
            //Werte werdden in den geladenen Container gefüllt
            sugarCompWidget = (SugarCompWidget)findViewById(id);
            sugarCompWidget.setVisibility(true);
            sugarCompWidget.setTime(cursor.getString(1));
            float bz = cursor.getFloat(2);
            //Wenn der Blutzucker Wert 0 ist, da Beim Eintragen kein Wert angegeben wurde wird dieser mit "-" anstatt "0" angezeigt
            if(bz == 0)
            {
                sugarCompWidget.setBz("-");
            }
            else
            {
                sugarCompWidget.setBz(bz +"");
            }
            //Wenn ein kritischer Bltzuckerwert geladen wurde wird der Entsprechende Container rot dargestellt
            if((bz < 3.6 || bz>7) && bz !=0)
            {
                sugarCompWidget.setBgColor("#F79C7D");
                Log.d("test", "Farbe");
            }
            else
            {
                sugarCompWidget.setBgColor("#FFFFFF");
            }
            sugarCompWidget.setBe(cursor.getInt(3) + "");
            sugarCompWidget.setBasal(cursor.getInt(4)+"");
            sugarCompWidget.setBolus(cursor.getInt(5)+"");
            //Nächster Container wirde geladen
            i++;
            //Nächste Werte zum abfüllen werden geladen
            cursor.moveToNext();
        }
        //Datenbank wird geschlossen
       db.close();


    }

    /**
     * getValues erlaubt es von jeder Activity die Blutzuckerwerte aus der Datenbank zu lesen und in
     * ein ArrayList<String[]> abtzufüllen.
     * @return Values
     */
    public static ArrayList<String[]> getValues()
    {
        //ArrayList wird instanziert
        ArrayList<String[]> values = new ArrayList<String[]>();
        //Aktuelles Datum wird geladen
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        String date = sdf.format(new Date());
        //Datenbank wird geladen
        DatabaseHandler db = Login.getDb();
        //Datenbank wird geöffnet
        db.open();
        //Einträge vom aktuellen Datum zum Eingeloggten user werden geladen
        Cursor cursor = db.returnBsValuesData(date, Login.user);

        cursor.moveToFirst();
        //Alle Werte werden in die Arrylist übertragen
        while (!cursor.isAfterLast())
        {
            values.add(new String[]{cursor.getString(1),cursor.getFloat(2)+"",cursor.getInt(3)+"",cursor.getInt(4)+"",cursor.getInt(5)+""});

            cursor.moveToNext();
        }
        db.close();
        //Werte werdden zrückgegeben
        return values;
    }
    //Sendet ein sms an die nummer: phoneNumber mit der Nachricht: message
    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Erstellt das Menu in der Home Activity
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Reagiert auf Menu Eingaben

        int id = item.getItemId();
        if (id == R.id.action_profil) {
            return true;
        }
        // Durch auswahl von Quiz update im Menu werden neue Quizzfragen per Webservice geladen.
        if (id == R.id.quiz_update) {
            new AsynchNetwork().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


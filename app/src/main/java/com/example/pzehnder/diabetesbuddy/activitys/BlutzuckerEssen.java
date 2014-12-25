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
import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;

import java.security.Timestamp;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class BlutzuckerEssen extends Activity {

    SugarCompWidget sugarCompWidget;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blutzucker_essen);

        TextView datum = (TextView)findViewById(R.id.sugarDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        datum.setText(sdf.format(new Date()));

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sugar_dialog);
        final TextView dialogDate =(TextView)dialog.findViewById(R.id.sugarDialogDate);

        Button cancelbtn = (Button)dialog.findViewById(R.id.sugerDialogCancel);
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
            }
        });
        Button button = (Button)dialog.findViewById(R.id.sugarDialogButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.hide();
                DatabaseHandler db = Login.getDb();
                db.open();
                SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
                String date = sdf.format(new Date());
                sdf = new SimpleDateFormat("HH:mm:ss");
                String time = sdf.format(new Date());
                Float bz = null;
                Integer be = null;
                Integer basal = null;
                Integer bolus = null;
                if(((TextView) dialog.findViewById(R.id.sugarDialogBZ)).getText().length() != 0) {
                    bz = Float.parseFloat(((TextView) dialog.findViewById(R.id.sugarDialogBZ)).getText() + "");
                }
                if(((TextView) dialog.findViewById(R.id.sugarDialogBE)).getText().length() != 0) {
                    be = Integer.parseInt(((TextView) dialog.findViewById(R.id.sugarDialogBE)).getText() + "");
                }
                if(((TextView) dialog.findViewById(R.id.sugarDialogBasal)).getText().length() != 0) {
                    basal = Integer.parseInt(((TextView) dialog.findViewById(R.id.sugarDialogBasal)).getText() + "");
                }
                if(((TextView) dialog.findViewById(R.id.sugarDialogBolus)).getText().length() != 0) {
                    bolus = Integer.parseInt(((TextView) dialog.findViewById(R.id.sugarDialogBolus)).getText() + "");
                }
               try {
                   if ((bz < 3.6 || bz > 7) && bz != 0) {
                       sendSMS(Login.phonenumber, "Der Blutzuckerwert von " + Login.user +" lieg bei " + bz +"mmol/l");
                   }
               }
               catch (NullPointerException e)
               {
                   Log.d("bz = null",e.toString());
               }

                db.insertBs_valuesData(date, time, bz, be, basal, bolus, "anote", Login.user);
                db.close();
                refreshValues();
            }
        });

        Button newSugarValue = (Button)findViewById(R.id.buttonSugar);
        newSugarValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy, HH:mm");
                dialogDate.setText(sdf.format(new Date()));
                dialog.show();
            }
        });
        refreshValues();
    }

    private void refreshValues()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        String date = sdf.format(new Date());
        DatabaseHandler db = Login.getDb();
        db.open();
        Cursor cursor = db.returnBsValuesData(date, Login.user);

        int i = 1;
        cursor.moveToFirst();
        while (!cursor.isAfterLast() && i<=10)
        {
            String idString = "sugarComp"+i;
            int id = getResources().getIdentifier(idString,"id","com.example.pzehnder.diabetesbuddy");
            sugarCompWidget = (SugarCompWidget)findViewById(id);
            sugarCompWidget.setVisibility(true);
            sugarCompWidget.setTime(cursor.getString(1));
            float bz = cursor.getFloat(2);
            if(bz == 0)
            {
                sugarCompWidget.setBz("-");
            }
            else
            {
                sugarCompWidget.setBz(bz +"");
            }
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
            Log.d("value"+i, cursor.getString(1));
            i++;
            cursor.moveToNext();
        }
       db.close();


    }
    public static ArrayList<String[]> getValues()
    {
        ArrayList<String[]> values = new ArrayList<String[]>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy");
        String date = sdf.format(new Date());
        DatabaseHandler db = Login.getDb();
        db.open();
        Cursor cursor = db.returnBsValuesData(date, Login.user);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            values.add(new String[]{cursor.getString(1),cursor.getFloat(2)+"",cursor.getInt(3)+"",cursor.getInt(4)+"",cursor.getInt(5)+""});

            cursor.moveToNext();
        }
        db.close();
        return values;
    }
    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_profil) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





}


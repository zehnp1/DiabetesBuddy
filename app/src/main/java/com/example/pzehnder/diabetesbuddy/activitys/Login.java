package com.example.pzehnder.diabetesbuddy.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import android.view.View;

import com.example.pzehnder.diabetesbuddy.R;
import com.example.pzehnder.diabetesbuddy.data.AsynchNetwork;
import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;
import com.example.pzehnder.diabetesbuddy.data.InitDbValues;


public class Login extends Activity {

    private static DatabaseHandler dbHandler;
    public static String user;
    public static String phonenumber = "";
    public static int bananas = 0;
    public static boolean login = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHandler = new DatabaseHandler(this);
        InitDbValues.init(this);
        Button buttonLogin = (Button) findViewById(R.id.homeButton1);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText userfeld = (EditText) findViewById(R.id.nameFeld);
                EditText passwortfeld = (EditText) findViewById(R.id.Passwort);
                String passwort = passwortfeld.getText().toString();
                user = userfeld.getText().toString();
                dbHandler.open();
                try {
                    Cursor cursor = dbHandler.returnUserData(user);
                    cursor.moveToFirst();

                    if (passwort.equals(cursor.getString(4)))
                    {
                        Log.d("Login",passwort + "="+cursor.getString(4));
                        phonenumber = cursor.getString(9);
                        bananas = cursor.getInt(10);
                        login = true;

                        Intent homeView = new Intent(Login.this, Home.class);
                        startActivity(homeView);

                    }
                } catch (Exception e) {
                    Log.d("Loginerror", e.toString());
                }
                dbHandler.close();
            }
        });

        Button buttonReg = (Button) findViewById(R.id.Registrieren);
        buttonReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent regView = new Intent(Login.this, Registrierung.class);
                startActivity(regView);
            }

        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);//Menu Resource, Menu
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
        if (id == R.id.quiz_update) {
            Log.d("test","sucsess");
            new AsynchNetwork().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public static DatabaseHandler getDb()
    {
        return dbHandler;
    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }



    }

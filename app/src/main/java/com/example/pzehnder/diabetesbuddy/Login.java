package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.*;
import android.view.View;

import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;


public class Login extends Activity {

    private static DatabaseHandler dbHandler;
    private static final String DATABASE_NAME = "diabetsbuddy_db";
    private static final int DATABASE_VERSION = 1;

    private static final String SHOP_TABLE = "shop";
    private final static String ARTICLE_NAME = "name";
    private final static String ARTICLE_PRICE = "price";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button buttonLogin = (Button) findViewById(R.id.homeButton1);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (isFirstTime()) {
                    Intent welcomeView = new Intent(Login.this, Welcome.class);
                    startActivity(welcomeView);
                }

                else{
                    Intent homeView = new Intent(Login.this, Home.class);
                    startActivity(homeView);
                }
                }
            });



        Button buttonReg = (Button) findViewById(R.id.Registrieren);
        buttonReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent regView = new Intent(Login.this, Registrierung.class);
                startActivity(regView);
            }

        });
        dbHandler = new DatabaseHandler(this);
        String crateShopTable = "CREATE TABLE " + SHOP_TABLE + " ("
                + ARTICLE_NAME + " TEXT,"
                + ARTICLE_PRICE + " INTEGER)";
        Log.d("bal", crateShopTable);
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

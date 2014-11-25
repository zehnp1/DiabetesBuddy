package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import static com.example.pzehnder.diabetesbuddy.R.*;

/**
 * Created by michaelheeb on 13.11.14.
 */
public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.home);


        Button buttonBlutzucker = (Button) findViewById(id.homeButton1);
        buttonBlutzucker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent blutzuckerView = new Intent(Home.this, BlutzuckerEssen.class);
                startActivity(blutzuckerView);
            }

        });

        Button buttonSpiele = (Button) findViewById(id.homeButton2);
        buttonSpiele.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent spieleView = new Intent(Home.this, Spiele.class);
                startActivity(spieleView);
            }

        });

        Button buttonShop = (Button) findViewById(id.homeButton3);
        buttonShop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent shopView = new Intent(Home.this, Shop.class);
                startActivity(shopView);
            }

        });

        Button buttonLogout = (Button) findViewById(id.logoutButton);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent loginView = new Intent(Home.this, Login.class);
                startActivity(loginView);
            }

        });
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


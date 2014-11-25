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
public class Welcome extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.welcome);

        Button buttonWeiter = (Button) findViewById(id.welcomeButtonWeiter);
        buttonWeiter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent homeView = new Intent(Welcome.this, Home.class);
                startActivity(homeView);
            }

        });
    }


    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;






public class Registrierung extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrierung);


        Spinner dropdown = (Spinner)findViewById(R.id.Sprache);
        String[] items = new String[]{"Deutsch", "Französisch", "Italienisch"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        dropdown.setAdapter(adapter);

        Button buttonRegAbschluss = (Button) findViewById(R.id.RegistrierungsButton);
        buttonRegAbschluss.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent homeView = new Intent(Registrierung.this, Home.class);
                startActivity(homeView);
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


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.maleCheckBox:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.femaleCheckBox:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }


}



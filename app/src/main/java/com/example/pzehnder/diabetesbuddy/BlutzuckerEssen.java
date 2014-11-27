package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost.TabSpec;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.Calendar;


public class BlutzuckerEssen extends Activity {

    private TextView bzDisplayDate;
    private DatePicker dpResult;

    private TextView essenDisplayDate;
    private DatePicker dpResultEssen;

    private int year;
    private int month;
    private int day;

    final Context context = this;
    private Button button;
    private TextView result;
    private TextView result2;
    private TextView result3;

    private Button buttonEssen;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blutzucker_essen);


        // Get TabHost Refference
        TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabSpec spec1=tabHost.newTabSpec("Tab 1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Essen");

        TabSpec spec2=tabHost.newTabSpec("Tab 2");
        spec2.setIndicator("Blutzucker");
        spec2.setContent(R.id.tab2);

        tabHost.addTab(spec1);
        tabHost.addTab(spec2);

        setCurrentDateOnView();



    }


    private void updateDisplay() {
        bzDisplayDate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append("Werte vom: ").append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        essenDisplayDate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append("Essen vom: ").append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
    }

    public void setCurrentDateOnView() {

        bzDisplayDate = (TextView) findViewById(R.id.bzDate);
        dpResult = (DatePicker) findViewById(R.id.dpResult);

        essenDisplayDate = (TextView) findViewById(R.id.essenDate);
        dpResultEssen = (DatePicker) findViewById(R.id.essenResult);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();


        dpResult.init(year, month, day, new DatePicker.OnDateChangedListener(){

            @Override
            public void onDateChanged(DatePicker view,
                                      int year, int month,int day) {


                bzDisplayDate.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append("Werte vom: ").append(month + 1).append("-").append(day).append("-")
                        .append(year).append(" "));

            }});


        dpResultEssen.init(year, month, day, new DatePicker.OnDateChangedListener(){

            @Override
            public void onDateChanged(DatePicker view,
                                      int year, int month,int day) {


                essenDisplayDate.setText(new StringBuilder()
                        // Month is 0 based so add 1
                        .append("Werte vom: ").append(month + 1).append("-").append(day).append("-")
                        .append(year).append(" "));

            }});


        // Essen blabla button

        buttonEssen = (Button) findViewById(R.id.buttonPromptessen);

        buttonEssen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.eingabeessen, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder.setView(promptsView);

                final Spinner spinner = (Spinner) promptsView
                        .findViewById(R.id.spinnerEssen1);


                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {



                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });












        // Blutzucker blabla button
        button = (Button) findViewById(R.id.buttonPrompt);
        result = (TextView) findViewById(R.id.wertMorgen);
        result2 = (TextView) findViewById(R.id.wertMittag);
        result3 = (TextView) findViewById(R.id.wertAbend);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.eingabebz, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                alertDialogBuilder.setView(promptsView);

                final Spinner spinner = (Spinner) promptsView
                        .findViewById(R.id.spinnerTageszeit);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        String item = (String)spinner.getSelectedItem();

                                        if (item.equals("Morgen")) {
                                            result.setText(userInput.getText());
                                        }
                                        else if (item.equals("Mittag")) {
                                            result2.setText(userInput.getText());
                                        }
                                        else if (item.equals("Abend")) {
                                            result3.setText(userInput.getText());
                                        }

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

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


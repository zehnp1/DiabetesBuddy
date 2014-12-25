package com.example.pzehnder.diabetesbuddy.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.pzehnder.diabetesbuddy.activitys.BESchaetzen;
import com.example.pzehnder.diabetesbuddy.R;

/**
 * Created by Ivan on 29.11.2014.
 */
public class BeCompWidget extends LinearLayout {

    private ImageView nahrungsmittel;
    private Button beAntwort1Button;
    private Button beAntwort2Button;
    private Button beAntwort3Button;

    public BeCompWidget(Context context)
    {
        super(context);
    }
    public BeCompWidget(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.be_component, this,true);
        setUpViewItems();
    }

    private void setUpViewItems()
    {
        nahrungsmittel = (ImageView)findViewById(R.id.beImage);

        beAntwort1Button = (Button) findViewById(R.id.beAntwort1);
        beAntwort1Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BESchaetzen.evaluateAnswer(1);
            }
        });
        beAntwort2Button = (Button) findViewById(R.id.beAntwort2);
        beAntwort2Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                BESchaetzen.evaluateAnswer(2);
            }
        });
        beAntwort3Button = (Button) findViewById(R.id.beAntwort3);
        beAntwort3Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               BESchaetzen.evaluateAnswer(3);
            }
        });
    }

    public void setNahrungsmittel(String nahrungsmittel) {
        int id = getResources().getIdentifier(nahrungsmittel,"drawable","com.example.pzehnder.diabetesbuddy");
        this.nahrungsmittel.setImageResource(id);
    }

    public void setBeAntwort1ButtonText (String beAntwort1)
    {
        this.beAntwort1Button.setText(beAntwort1);
    }
    public void setBeAntwort2ButtonText (String beAntwort2)
    {
        this.beAntwort2Button.setText(beAntwort2);
    }
    public void setBeAntwort3ButtonText (String beAntwort3)
    {
        this.beAntwort3Button.setText(beAntwort3);
    }
}

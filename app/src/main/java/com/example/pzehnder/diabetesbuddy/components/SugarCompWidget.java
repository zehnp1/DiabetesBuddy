package com.example.pzehnder.diabetesbuddy.components;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pzehnder.diabetesbuddy.R;

/**
 * Created by Ivan on 19.12.2014.
 */
public class SugarCompWidget extends LinearLayout{
    private TextView time;
    private TextView bz;
    private TextView be;
    private TextView basal;
    private TextView bolus;
    private ImageView note;
    private LinearLayout layout;
    public SugarCompWidget(Context context)
    {
        super(context);
    }
    public SugarCompWidget(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.blutzucker_component, this,true);
        setUpViewItems();
        setVisibility(GONE);
    }
    private void setUpViewItems()
    {
        time = (TextView)findViewById(R.id.sugarComp_time);
        bz = (TextView)findViewById(R.id.sugarComp_bz);
        be = (TextView)findViewById(R.id.sugarComp_be);
        basal = (TextView)findViewById(R.id.sugarComp_basal);
        bolus = (TextView)findViewById(R.id.sugarComp_bolus);
        note = (ImageView)findViewById(R.id.sugarComp_note);
        layout = (LinearLayout)findViewById(R.id.sugarComp);
    }
    public void setTime(String time) {
        this.time.setText(time);
    }
    public void setBz(String bz)
    {
        this.bz.setText(bz);
    }
    public void setBe(String be){
        this.be.setText(be);
    }
    public void setBasal(String basal)
    {
        this.basal.setText(basal);
    }
    public void setBolus(String bolus)
    {
        this.bolus.setText(bolus);
    }
    public void setVisibility(boolean visibility)
    {
        if(visibility)
        {
            this.setVisibility(VISIBLE);
        }
        else
        {
            this.setVisibility(INVISIBLE);
        }

    }
    public void setBgColor(String color)
    {
        layout.setBackgroundColor(Color.parseColor(color));
    }
}

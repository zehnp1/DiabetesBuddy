package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;
import com.example.pzehnder.diabetesbuddy.data.InitDbValues;

import org.xmlpull.v1.XmlPullParser;


public class Shop extends Activity {
    private ShopCompWidget shopCompWidget1;
    private ShopCompWidget shopCompWidget2;
    private ShopCompWidget shopCompWidget3;
    private LinearLayout list;
    private LinearLayout test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);
   //     InitDbValues.init(getApplicationContext());
        list = (LinearLayout)findViewById(R.id.shoplist);

        shopCompWidget2 = new ShopCompWidget(list.getContext());
//        shopCompWidget2.setPriceText("20");

      // LayoutInflater inflater = LayoutInflater.from(this);
      // shopCompWidget2 = (LinearLayout)inflater.inflate(R.layout.shop_component,list,false);




         list.addView(shopCompWidget2);



       shopCompWidget1 = (ShopCompWidget)findViewById(R.id.shopcomp);
       shopCompWidget1.setPriceText("80");
       shopCompWidget1.setArticleImage("sonnenbrille");

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

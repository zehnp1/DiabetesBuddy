package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;
import com.example.pzehnder.diabetesbuddy.data.InitDbValues;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;


public class Shop extends Activity {
    private ShopCompWidget shopCompWidget1;
    private ShopCompWidget shopCompWidget2;
    private ShopCompWidget shopCompWidget3;
    private DatabaseHandler dbHandler;
    private static TextView anzahlBanana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        ArrayList<String[]> shopList = new ArrayList<String[]>();

        anzahlBanana = (TextView)findViewById(R.id.anzahlBananas);
        anzahlBanana.setText(Login.bananas + "");

        dbHandler = Login.getDb();
        dbHandler.open();
        Cursor shopData = dbHandler.returnShopData(Login.user +"");
        if (shopData.moveToFirst()) {
            do {
                    shopList.add(new String[]{shopData.getString(0),shopData.getString(1),shopData.getString(2),shopData.getString(3)});
            } while (shopData.moveToNext());
        }

        dbHandler.close();

        for(int i = 0; i < shopList.size();i++) {
            Log.d("bal", shopList.get(i)[0] + " " + shopList.get(i)[1] + " " + shopList.get(i)[2] + " " + shopList.get(i)[3]);
        }

        shopCompWidget1 = (ShopCompWidget)findViewById(R.id.shopcomp1);
        shopCompWidget1.setPriceText(shopList.get(0)[1]);
        shopCompWidget1.setArticleImage(shopList.get(0)[0]);
        if(shopList.get(0)[2] != null)
        {
            shopCompWidget1.setVisability(false);
        }

        shopCompWidget2 = (ShopCompWidget)findViewById(R.id.shopcomp2);
        shopCompWidget2.setPriceText(shopList.get(1)[1]);
        shopCompWidget2.setArticleImage(shopList.get(1)[0]);
        if(shopList.get(1)[2]!= null)
        {
            shopCompWidget2.setVisability(false);
        }

        shopCompWidget3 = (ShopCompWidget)findViewById(R.id.shopcomp3);
        shopCompWidget3.setPriceText(shopList.get(2)[1]);
        shopCompWidget3.setArticleImage(shopList.get(2)[0]);
        if(shopList.get(2)[2] != null)
        {
            shopCompWidget3.setVisability(false);
        }

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


    public static void setBananas(int bananas)
    {
        anzahlBanana.setText(bananas + "");
    }


}

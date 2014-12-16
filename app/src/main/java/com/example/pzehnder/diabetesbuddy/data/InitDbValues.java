package com.example.pzehnder.diabetesbuddy.data;

import android.content.Context;

import com.example.pzehnder.diabetesbuddy.Login;

/**
 * Created by Ivan on 03.12.2014.
 */
public class InitDbValues {
    private static DatabaseHandler dbHandler;
    public InitDbValues()
    {}
    public static void init(Context context)
    {
        dbHandler = Login.getDb();
        dbHandler.open();
        dbHandler.insertShopData("sonnenbrille",17);
        dbHandler.insertShopData("board",20);
        dbHandler.insertShopData("zufall",30);
        dbHandler.insertUser_ShopData("kai","board");
        dbHandler.close();
    }
}

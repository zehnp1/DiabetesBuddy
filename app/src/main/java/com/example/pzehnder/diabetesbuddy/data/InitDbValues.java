package com.example.pzehnder.diabetesbuddy.data;

import android.content.Context;

/**
 * Created by Ivan on 03.12.2014.
 */
public class InitDbValues {
    public InitDbValues()
    {}
    public static void init(Context context)
    {
        DatabaseHandler dbHandler = new DatabaseHandler(context);
        dbHandler.open();
        dbHandler.insertShopData("sonnenbrille",17);
    }
}

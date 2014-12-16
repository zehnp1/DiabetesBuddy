package com.example.pzehnder.diabetesbuddy.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLData;

/**
 * Created by Ivan on 30.11.2014.
 */
public class DatabaseHandler
{
    private static final String DATABASE_NAME = "diabetsbuddy_db";
    private static final int DATABASE_VERSION = 1;

    //shop Table
    private static final String SHOP_TABLE = "shop";
        private final static String ARTICLE_NAME = "article_name";
        private final static String ARTICLE_PRICE = "price";

    //user Table
    private static final String USER_TABLE = "user";
        private final static String USER_NAME = "user_name";
        private final static String PASSWORD = "password";


    //userShop Table
    private static final String USER_SHOP_TABLE = "user_shop";


    DatabaseHelper dbHelper;
    Context context;
    SQLiteDatabase db;
    public DatabaseHandler(Context context)
    {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            String crateShopTable = "CREATE TABLE IF NOT EXISTS " + SHOP_TABLE + " ("
                    + ARTICLE_NAME + " TEXT  PRIMARY KEY,"
                    + ARTICLE_PRICE + " INTEGER)";

            String crateUser_ShopTable = "CREATE TABLE IF NOT EXISTS " + USER_SHOP_TABLE + " ("
                    + ARTICLE_NAME + " TEXT,"
                    + USER_NAME + " TEXT, PRIMARY KEY("+ARTICLE_NAME+", "+USER_NAME+"))";

            Log.d("bal",crateUser_ShopTable);
            try
            {
                db.execSQL(crateShopTable);
                db.execSQL(crateUser_ShopTable);
            }
            catch (Exception e)
            {
                Log.d("bal","test");
                e.printStackTrace();
            }

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + SHOP_TABLE);
            onCreate(db);
        }
    }
    public DatabaseHandler open()
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        dbHelper.close();
    }
    public long insertShopData(String name, int price)
    {
        ContentValues content = new ContentValues();
        content.put(ARTICLE_NAME,name);
        content.put(ARTICLE_PRICE,price);
        try{
            return db.insertOrThrow(SHOP_TABLE,null,content);
        }
        catch (Exception e)
        {
            Log.d("DB insert Error",e.toString());
            return 0;
        }
    }
    public long insertUser_ShopData(String user_name, String article_name)
    {
        ContentValues content = new ContentValues();
        content.put(ARTICLE_NAME,article_name);
        content.put(USER_NAME,user_name);
        try{
            return db.insertOrThrow(USER_SHOP_TABLE,null,content);
        }
        catch (Exception e)
        {
            Log.d("DB insert Error",e.toString());
            return 0;
        }
    }
    public Cursor returnShopData()
    {
       return db.query(SHOP_TABLE,new String[]{ARTICLE_NAME,ARTICLE_PRICE},null,null,null,null,null);
    }
    public Cursor returnUSER_ShopData(String user)
    {
        final String MY_QUERY = "SELECT * FROM shop LEFT JOIN user_shop ON user_shop.user_name =? AND shop.article_name = user_shop.article_name ";
        return db.rawQuery(MY_QUERY,new String[]{user});
    }

}

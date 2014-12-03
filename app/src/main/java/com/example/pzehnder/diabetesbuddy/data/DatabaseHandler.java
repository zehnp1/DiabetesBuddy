package com.example.pzehnder.diabetesbuddy.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLData;

/**
 * Created by Ivan on 30.11.2014.
 */
public class DatabaseHandler
{
    private static final String DATABASE_NAME = "diabetsbuddy_db";
    private static final int DATABASE_VERSION = 1;

    private static final String SHOP_TABLE = "shop";
        private final static String ARTICLE_NAME = "name";
        private final static String ARTICLE_PRICE = "price";

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
            String crateShopTable = "CREATE TABLE " + SHOP_TABLE + " ("
                    + ARTICLE_NAME + "TEXT  PRIMAREY KEY,"
                    + ARTICLE_PRICE + "INTEGER)";
            try
            {
                db.execSQL(crateShopTable);
            }
            catch (Exception e)
            {
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
        return db.insertOrThrow(SHOP_TABLE,null,content);
    }
    public Cursor returnShopData()
    {
        return db.query(SHOP_TABLE,new String[]{ARTICLE_NAME,ARTICLE_PRICE},null,null,null,null,null);
    }

}

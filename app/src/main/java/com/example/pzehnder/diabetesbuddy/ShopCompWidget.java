package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pzehnder.diabetesbuddy.data.DatabaseHandler;

/**
 * Created by Ivan on 29.11.2014.
 */
public class ShopCompWidget extends LinearLayout {

    private ShopCompWidget shopComp;
    private String articleName;
    private TextView priceText;
    private ImageView articleImage;
    private String price;
    private String picture;
    public ShopCompWidget(Context context)
    {
        super(context);
    }
    public ShopCompWidget(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.shop_component, this,true);
        setUpViewItems();
        shopComp = this;
    }

    private void setUpViewItems()
    {
        priceText = (TextView) findViewById(R.id.price);
        articleImage =(ImageView) findViewById(R.id.imageArticle);
        Button btn = (Button)findViewById(R.id.kaufen);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test", articleName);
                DatabaseHandler db = Login.getDb();
                db.open();
                db.insertUser_ShopData("ivan",articleName);
                db.close();
                shopComp.setVisability(false);
            }
        });
    }

    public void setPriceText(String priceText) {
        this.priceText.setText(priceText);
    }

    public void setArticleImage(String articleName) {
        this.articleName = articleName;
        int id = getResources().getIdentifier(articleName,"drawable","com.example.pzehnder.diabetesbuddy");
        this.articleImage.setImageResource(id);
    }
    public void setVisability(boolean visability)
    {
        if(visability)
        {
            this.setVisibility(VISIBLE);
        }
        else
        {
            this.setVisibility(GONE);
        }
    }
}

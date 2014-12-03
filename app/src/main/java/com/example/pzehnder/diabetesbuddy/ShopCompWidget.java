package com.example.pzehnder.diabetesbuddy;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ivan on 29.11.2014.
 */
public class ShopCompWidget extends LinearLayout {

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
    }

    private void setUpViewItems()
    {
        priceText = (TextView) findViewById(R.id.price);
        articleImage =(ImageView) findViewById(R.id.imageArticle);
        setPriceText("40");
    }

    public void setPriceText(String priceText) {
        this.priceText.setText(priceText);
    }

    public void setArticleImage(String articleName) {
        int id = getResources().getIdentifier(articleName,"drawable","com.example.pzehnder.diabetesbuddy");
        this.articleImage.setImageResource(id);
    }
}

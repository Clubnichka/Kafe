package com.example.test3.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Models.SpecialOffer;
import com.example.test3.R;
import com.example.test3.Services.Adapters.CustomAdapter4;
import com.example.test3.Services.DataBaseServices.DBHelper;
import com.example.test3.Services.ModelServices.SpecialOfferService;

public class SpecialOffersActivity extends AppCompatActivity {



    private SpecialOfferService offerService;
    RecyclerView contentView;
    DBHelper mDbHelper;
    Button toOrder;
    Button toProfile;
    Button toPromos;
    Button toMenu;
    DBHelper dbHelper;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specialoffers);
        toOrder=(Button)findViewById(R.id.toOrder);
        toProfile=(Button)findViewById(R.id.toProfile);
        toPromos=(Button)findViewById(R.id.toPromos);
        toMenu=(Button)findViewById(R.id.toMenu);
        contentView = (RecyclerView) findViewById(R.id.contentList);
        contentView.setLayoutManager(new LinearLayoutManager((this)));
        mDbHelper = new DBHelper(this);
        toOrder=(Button)findViewById(R.id.toOrder);
        toProfile=(Button)findViewById(R.id.toProfile);
        CustomAdapter4 adapter=new CustomAdapter4(this);
        dbHelper=new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("User", null, null, null, null, null, null);
        if (c.moveToFirst()){
            int nameColIndex=c.getColumnIndex("Name");
            int levelColIndex=c.getColumnIndex("Level");
            int useridColIndex=c.getColumnIndex("userid");
            Log.w("FromSQLITE", c.getString(nameColIndex)+c.getString(levelColIndex));
            userid=c.getInt(useridColIndex);
        }
        else {
            Log.e("FROMSQLITE", "Nothing there");
        }
        offerService=new SpecialOfferService(userid, new SpecialOfferService.OffersCallback() {
            @Override
            public void onOffersLoaded(SpecialOffer[] offers) {
                adapter.setContentValues(offers);
                contentView.setAdapter(adapter);
            }
        });
        toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(intent);
            }
        });
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
        toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(intent);
            }
        });
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });


        toPromos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), PromoActivity.class);
                startActivity(intent);
            }
        });
        toMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });
    }






//    private void displayProductsData(Product[] products){
//        String productsInfo=new String();
//        for (Product p:products
//        ) {
//            productsInfo +="ID: " + p.getId() + "\n" +
//                    "Имя: " + p.getName() + "\n" +
//                    "Цена: " + p.getPrice()+ "\n" +"Описание"+ p.getDescription()+ "\n";
//        }
//        productsTextView.setText(productsInfo);
//    }

}

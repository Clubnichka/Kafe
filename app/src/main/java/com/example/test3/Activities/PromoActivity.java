package com.example.test3.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Models.Product;
import com.example.test3.Models.Promotion;
import com.example.test3.Models.SpecialOffer;
import com.example.test3.R;
import com.example.test3.Services.Adapters.CustomAdapter2;
import com.example.test3.Services.Adapters.CustomAdapter5;
import com.example.test3.Services.Adapters.CustomAdapter6;
import com.example.test3.Services.DataBaseServices.DBHelper;
import com.example.test3.Services.ModelServices.OrderService;
import com.example.test3.Services.ModelServices.PromotionsService;
import com.example.test3.Services.ModelServices.SpecialOfferService;

import java.util.ArrayList;
import java.util.List;

public class PromoActivity extends AppCompatActivity {



    RecyclerView contentView;
    TextView empty;
    PromotionsService promotionsService;
    Button toOrder;
    Button toProfile;
    Button toOffers;
    Button toMenu;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("OrderActivity", "I'm alive");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promos);
        toOrder=(Button)findViewById(R.id.toOrder);
        toProfile=(Button)findViewById(R.id.toProfile);
        toOffers=(Button)findViewById((R.id.toOffers));
        toMenu=(Button)findViewById(R.id.toMenu);
        contentView = (RecyclerView) findViewById(R.id.contentList);
        contentView.setLayoutManager(new LinearLayoutManager((this)));
        promotionsService=new PromotionsService(new PromotionsService.PromosCallback() {
            @Override
            public void onPromosLoaded(List<Promotion> promos) {
                contentView.setAdapter(new CustomAdapter6(promos));
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
        toOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SpecialOffersActivity.class);
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

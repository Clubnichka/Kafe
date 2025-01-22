package com.example.test3.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Services.Adapters.CustomAdapter;
import com.example.test3.Models.Product;
import com.example.test3.R;
import com.example.test3.Services.DataBaseServices.DBHelper;
import com.example.test3.Services.ModelServices.ProductService;

public class MenuActivity extends AppCompatActivity {



    private ProductService productService;
    RecyclerView contentView;
    DBHelper mDbHelper;
    Button toOrder;
    Button toProfile;
    Button toOffers;
    Button toPromos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        contentView = (RecyclerView) findViewById(R.id.contentList);
        contentView.setLayoutManager(new LinearLayoutManager((this)));
        mDbHelper = new DBHelper(this);
        toOrder=(Button)findViewById(R.id.toOrder);
        toProfile=(Button)findViewById(R.id.toProfile);
        toOffers=(Button)findViewById((R.id.toOffers));
        toPromos=(Button)findViewById(R.id.toPromos);
        CustomAdapter adapter=new CustomAdapter(this);

        productService=new ProductService(new ProductService.ProductsCallback() {
            @Override
            public void onProductsLoaded(Product[] products) {
                adapter.setContentValues(products);
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
        toOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), SpecialOffersActivity.class);
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

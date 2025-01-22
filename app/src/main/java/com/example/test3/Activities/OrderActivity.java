package com.example.test3.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Models.SpecialOffer;
import com.example.test3.Services.Adapters.CustomAdapter2;
import com.example.test3.Models.Product;
import com.example.test3.R;
import com.example.test3.Services.Adapters.CustomAdapter5;
import com.example.test3.Services.CheckerService;
import com.example.test3.Services.DataBaseServices.DBHelper;
import com.example.test3.Services.ModelServices.OrderService;
import com.example.test3.Services.ModelServices.SpecialOfferService;
import com.example.test3.Services.TotalCounter;

import java.util.LinkedList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {



    private OrderService orderService;
    RecyclerView contentView;
    RecyclerView contentView2;
    DBHelper mDbHelper;
    Button doOrder;
    TextView empty;
    SpecialOfferService offerService;
    Button toProfile;
    Button toOffers;
    Button toPromos;
    Button toMenu;
    TextView disclaimer1;
    TextView disclaimer2;
    ConstraintLayout disclaimer1Layout;
    ConstraintLayout disclaimer2Layout;
    LinearLayout disclaimer;
    TextView total;
    int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("OrderActivity", "I'm alive");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        toMenu=(Button)findViewById(R.id.toMenu);
        disclaimer=(LinearLayout)findViewById(R.id.disclaimer);
        toProfile=(Button)findViewById(R.id.toProfile);
        toOffers=(Button)findViewById((R.id.toOffers));
        toPromos=(Button)findViewById(R.id.toPromos);
        disclaimer1=(TextView)findViewById(R.id.disclaimer1);
        disclaimer2=(TextView)findViewById(R.id.disclaimer2);
        disclaimer1Layout=(ConstraintLayout)findViewById(R.id.disclaimer1Layout);
        disclaimer2Layout=(ConstraintLayout)findViewById(R.id.disclaimer2Layout);
        contentView = (RecyclerView) findViewById(R.id.contentList);
        contentView.setLayoutManager(new LinearLayoutManager((this)));
        contentView2 = (RecyclerView) findViewById(R.id.contentList2);
        contentView2.setLayoutManager(new LinearLayoutManager((this)));
        empty=(TextView)findViewById(R.id.Empty);
        total=(TextView)findViewById(R.id.total);
        mDbHelper = new DBHelper(this);
        doOrder=(Button)findViewById(R.id.doOrder);
        TotalCounter totalCounter=new TotalCounter();
        Context context=this;
        CheckerService checker=new CheckerService();
        CustomAdapter2 adapter2=new CustomAdapter2(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor c = db.query("MyOrder", null, null, null, null, null, null);
        List<Integer> idList1=new LinkedList<Integer>();
        List<Integer> idList2=new LinkedList<Integer>();
        boolean active=false;
        if (c.moveToFirst()){
            int idColIndex=c.getColumnIndex("productid");
            int typeColIndex=c.getColumnIndex("type");
            do{
                if(c.getInt(typeColIndex)==1){
                    idList1.add(c.getInt(idColIndex));
                }
                else{
                    idList2.add(c.getInt(idColIndex));
                }

            } while(c.moveToNext());
            c = db.query("User", null, null, null, null, null, null);
            if (c.moveToFirst()){
                int useridColIndex=c.getColumnIndex("userid");
                userid=c.getInt(useridColIndex);
            }
            else {
                Log.e("FROMSQLITE", "Nothing there");
            }
            if(!idList1.isEmpty()){
                disclaimer1.setText("Блюда");
                orderService =new OrderService(idList1, new OrderService.ProductsCallback() {
                    @Override
                    public void onProductsLoaded(List<Product> products) {
                        totalCounter.sumProducts(products);
                        adapter2.setContentValues(products);
                        contentView.setAdapter( adapter2);
                        total.setText("Итого к оплате - "+totalCounter.getTotal()+" руб.");
                    }
                });
            }
            else{
                disclaimer.setVisibility(View.GONE);
                disclaimer1Layout.setVisibility(View.GONE);
            }

            if(!idList2.isEmpty()){
                disclaimer2.setText("Специальные предложения");
                offerService=new SpecialOfferService(idList2, new SpecialOfferService.OffersCallback() {
                    @Override
                    public void onOffersLoaded(SpecialOffer[] offers) {
                        totalCounter.sumOffers(offers);
                        for (SpecialOffer so:offers){

                            Log.w("Offers", so.getName());
                        }
                        contentView2.setAdapter(new CustomAdapter5(offers));
                        total.setText("Итого к оплате - "+totalCounter.getTotal()+" руб.");
                    }
                });
            }
            else{
                disclaimer2Layout.setVisibility(View.GONE);
            }
            active=true;
        }
        if(!active){
            disclaimer.setVisibility(View.GONE);
            disclaimer1Layout.setVisibility(View.GONE);
            disclaimer2Layout.setVisibility(View.GONE);
            doOrder.setVisibility(View.GONE);
            empty.setText("В корзине ничего нет");
        }

    orderService=new OrderService(new OrderService.emptycallback() {
        @Override
        public void onLoaded() {
            doOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(checker.check(userid)){
                        if (!idList1.isEmpty()&&!idList2.isEmpty()){
                            orderService.addOrder(idList1, idList2, userid);
                            db.delete("MyOrder",null,null);}
                        else if(!idList1.isEmpty()){
                            int type=1;
                            orderService.addOrder(idList1, userid, type);
                            db.delete("MyOrder",null,null);
                        }

                        else if(!idList2.isEmpty()){
                            int type=2;
                            orderService.addOrder(idList2, userid, type);
                            db.delete("MyOrder",null,null);
                        }
                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(context,"Нельзя делать два заказа за раз",Toast.LENGTH_SHORT).show();
                    }


                }

            });
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

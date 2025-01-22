package com.example.test3.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Models.Order;
import com.example.test3.R;
import com.example.test3.Services.Adapters.CustomAdapter3;
import com.example.test3.Services.DataBaseServices.DBHelper;
import com.example.test3.Services.ModelServices.OrderService;

public class ProfileActivity extends AppCompatActivity {
    TextView Name;
    TextView Level;
    TextView Status;
    DBHelper dbHelper;
    RecyclerView contentView;
    RecyclerView currentOrder;
    OrderService orderService;
    Button toOrder;
    Button toOffers;
    Button toPromos;
    Button toMenu;
    Button exit;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        toOrder=(Button)findViewById(R.id.toOrder);
        toOffers=(Button)findViewById((R.id.toOffers));
        toPromos=(Button)findViewById(R.id.toPromos);
        toMenu=(Button)findViewById(R.id.toMenu);
        contentView = (RecyclerView) findViewById(R.id.contentList);
        contentView.setLayoutManager(new LinearLayoutManager((this)));
        currentOrder = (RecyclerView) findViewById(R.id.currentOrder);
        currentOrder.setLayoutManager(new LinearLayoutManager((this)));
        Name=(TextView)findViewById(R.id.Name);
        Level=(TextView)findViewById(R.id.level);
        Status=(TextView)findViewById(R.id.Status);
        dbHelper=new DBHelper(this);
        exit=(Button)findViewById(R.id.exit);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("User", null, null, null, null, null, null);
        if (c.moveToFirst()){
            int nameColIndex=c.getColumnIndex("Name");
            int levelColIndex=c.getColumnIndex("Level");
            int useridColIndex=c.getColumnIndex("userid");
            Log.w("FromSQLITE", c.getString(nameColIndex)+c.getString(levelColIndex));
           Name.setText("Имя пользователя - "+c.getString(nameColIndex));
           Level.setText("Уровень лояльности - "+c.getString(levelColIndex));
           userid=c.getInt(useridColIndex);
        }
        else {
            Log.e("FROMSQLITE", "Nothing there");
        }
        orderService=new OrderService(userid, new OrderService.OrdersCallback() {
            @Override
            public void onOrdersLoaded(Order[] orders) {
                contentView.setAdapter(new CustomAdapter3(orders,userid));
                orderService.getCurrentOrder(userid, new OrderService.OrderCallback() {
                    @Override
                    public void onOrderLoaded(Order order) {
                        Log.w("Profile Activity order.status ", ""+order.getStatus());
                        boolean show=false;
                        switch (order.getStatus()){
                            case(1):
                                Status.setText("Статус заказа - принят");
                                show=true;
                                break;
                            case(2):
                                Status.setText("Статус заказа - готовится");
                                show=true;
                                break;
                            case(3):
                                Status.setText("Статус заказа - ждёт вас");
                                show=true;
                                break;
                            default:
                                break;
                        }
                        if (show){
                            Order[] orders= {order};
                            currentOrder.setAdapter(new CustomAdapter3(orders,userid));
                        }
                    }
                });
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("User",null,null);
                db.delete("MyOrder",null,null);
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
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
}
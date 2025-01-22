package com.example.test3.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test3.Models.User;
import com.example.test3.R;
import com.example.test3.Services.DataBaseServices.DBHelper;
import com.example.test3.Services.ModelServices.ProductService;
import com.example.test3.Services.ModelServices.UserService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView userTextView;
    private UserService userService;
    private ProductService productService;
    private Button goToMenu;
    private Button goToOrder;
    private DBHelper dbHelper;
    private EditText login;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userTextView = findViewById(R.id.uTV);
        goToMenu=findViewById(R.id.gTM);
        goToOrder=findViewById(R.id.gTO);
        goToMenu.setOnClickListener(this);
        goToOrder.setOnClickListener(this);
        login=findViewById(R.id.login);
        password=findViewById(R.id.password);
        dbHelper=new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        userService=new UserService(new UserService.UserCallback() {
            @Override
            public void onUserLoaded(User user) {
                Cursor c = db.query("User", null, null, null, null, null, null);
                if (c.moveToFirst()){
                    int useridColIndex=c.getColumnIndex("userid");
                    int userid=c.getInt(useridColIndex);
                    userService.getLevel(userid,dbHelper);
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }



    private void displayUserData(User user) {
        String userInfo = "ID: " + user.getId() + "\n" +
                "Имя: " + user.getName() + "\n" +
                "Уровень: " + user.getLevel();
        userTextView.setText(userInfo);
    }


    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        if (view.getId() == R.id.gTM) {
            Log.w("Login", login.getText().toString());
            Log.w("Password", password.getText().toString());
            if (userService.enter(login.getText().toString(),password.getText().toString(),dbHelper)){
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(MainActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
            }
        }
        if (view.getId() == R.id.gTO) {
            User user = new User();
            user.setName(login.getText().toString());
            Log.w("Login", user.getName());
            user.setPassword(password.getText().toString());
            Log.w("Password", user.getPassword());
            if (userService.register(user,dbHelper)){
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(MainActivity.this, "Имя занято", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
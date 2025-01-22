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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test3.Models.Product;
import com.example.test3.Models.User;
import com.example.test3.R;
import com.example.test3.Services.DataBaseServices.DBHelper;
import com.example.test3.Services.ImageService.ImageService;
import com.example.test3.Services.ModelServices.ProductService;
import com.example.test3.Services.ModelServices.UserService;

public class ProductActivity extends AppCompatActivity{

    private ImageView image;
    private TextView name;
    private TextView description;
    private TextView price;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product); // Убедитесь, что это правильный файл разметки

        image = findViewById(R.id.dish_image);
        name = findViewById(R.id.name); // Проверьте правильность ID
        description = findViewById(R.id.description); // Проверьте правильность ID
        price = findViewById(R.id.price); // Проверьте правильность ID
        back = findViewById(R.id.back);

        ImageService imageService = new ImageService();

        String nameText = getIntent().getStringExtra("name");
        String descriptionText = getIntent().getStringExtra("description");
        int priceText = getIntent().getIntExtra("price", -1);
        int id = getIntent().getIntExtra("id", -1);

        image.setImageResource(imageService.setImage(id));

        if (name != null) {
            name.setText(nameText);
        } else {
            Log.e("ProductActivity", "name TextView is null");
        }

        if (description != null) {
            description.setText(descriptionText);
        } else {
            Log.e("ProductActivity", "description TextView is null");
        }

        if (price != null) {
            price.setText(String.valueOf(priceText)+" руб.");
        } else {
            Log.e("ProductActivity", "price TextView is null");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
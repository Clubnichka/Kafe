package com.example.test3.Services.Adapters;

import static androidx.core.content.ContextCompat.startActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Activities.ProductActivity;
import com.example.test3.Models.Product;
import com.example.test3.R;
import com.example.test3.Services.DataBaseServices.DBHelper;
import com.example.test3.Services.ImageService.ImageService;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Product[] contentValues;
    private DBHelper mDbHelper;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView Name;
        private final TextView Description;
        private final TextView Price;
        private final Button Buy;
        private final ImageView imageView;


        public ViewHolder(View view) {
            super(view);

            Name = (TextView) view.findViewById(R.id.name);
            Description = (TextView) view.findViewById(R.id.description);
            Price = (TextView) view.findViewById(R.id.price);
            Buy=(Button) view.findViewById(R.id.Buy);
            imageView=(ImageView) view.findViewById(R.id.dish_image);

        }

        public TextView getName() {
            return Name;
        }
        public TextView getDescription() {
            return Description;
        }
        public TextView getPrice() {
            return Price;
        }
        public Button getBuy(){return  Buy;}
        public ImageView getImageView(){return imageView;}
    }

    public CustomAdapter(Context context) {
        mDbHelper=new DBHelper(context);
        this.context=context;
    }

    public void setContentValues(Product[] products){
        this.contentValues=products;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ImageService imageService=new ImageService();

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getImageView().setImageResource(imageService.setImage(contentValues[position].getId()));
        viewHolder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductActivity.class);
                intent.putExtra("id",contentValues[position].getId());
                intent.putExtra("name",contentValues[position].getName());
                intent.putExtra("description",contentValues[position].getDescription());
                intent.putExtra("price",contentValues[position].getPrice());
                context.startActivity(intent);
            }
        });
        viewHolder.getName().setText(contentValues[position].getName());
//        viewHolder.getDescription().setText(contentValues[position].getDescription());
        viewHolder.getPrice().setText("" + contentValues[position].getPrice()+" руб.");
        viewHolder.getBuy().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("type", 1);
                values.put("productid", contentValues[position].getId()); // Замените "product_id" на имя вашего столбца

                db.insert("MyOrder", null, values);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contentValues.length;
    }
}

package com.example.test3.Services.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Activities.ProductActivity;
import com.example.test3.Models.Product;
import com.example.test3.R;
import com.example.test3.Services.ImageService.ImageService;

import java.util.List;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder> {

    private List<Product> contentValues;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView Name;
        private final TextView Description;
        private final TextView Price;
        private final ImageView imageView;



        public ViewHolder(View view) {
            super(view);

            Name = (TextView) view.findViewById(R.id.name);
            Description = (TextView) view.findViewById(R.id.description);
            Price = (TextView) view.findViewById(R.id.price);
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
        public ImageView getImageView(){return imageView;}
    }

    public CustomAdapter2(List<Product> products, Context context) {
        this.context=context;
        this.contentValues=products;
    }

    public CustomAdapter2(Context context) {
        this.context=context;

    }
    public CustomAdapter2(List<Product> products) {
        contentValues=products;

    }
    public void setContentValues(List<Product> products){
        contentValues=products;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content2, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        ImageService imageService=new ImageService();
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getImageView().setImageResource(imageService.setImage(contentValues.get(position).getId()));
        if(context!=null){
            viewHolder.getImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ProductActivity.class);
                    intent.putExtra("id",contentValues.get(position).getId());
                    intent.putExtra("name",contentValues.get(position).getName());
                    intent.putExtra("description",contentValues.get(position).getDescription());
                    intent.putExtra("price",contentValues.get(position).getPrice());
                    startActivity(context, intent, null);
                }
            });
        }

        viewHolder.getName().setText(contentValues.get(position).getName());
//        viewHolder.getDescription().setText(contentValues.get(position).getDescription());
        viewHolder.getPrice().setText("" + contentValues.get(position).getPrice()+" руб.");
        Log.w("Data From Server", contentValues.get(position).getName()+contentValues.get(position).getDescription()+contentValues.get(position).getPrice());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contentValues.size();
    }
}

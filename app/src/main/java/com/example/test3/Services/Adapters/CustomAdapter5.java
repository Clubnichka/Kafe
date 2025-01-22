package com.example.test3.Services.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Models.SpecialOffer;
import com.example.test3.R;
import com.example.test3.Services.DataBaseServices.DBHelper;

public class CustomAdapter5 extends RecyclerView.Adapter<CustomAdapter5.ViewHolder> {

    private SpecialOffer[] contentValues;
    private DBHelper mDbHelper;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView Name;
        private final TextView Description;
        private final TextView Price;
        private final RecyclerView contentView;

        public ViewHolder(View view, Context context) {
            super(view);

            Name = (TextView) view.findViewById(R.id.name);
            Description = (TextView) view.findViewById(R.id.description);
            Price = (TextView) view.findViewById(R.id.price);
            contentView = (RecyclerView) view.findViewById(R.id.contentList);
            contentView.setLayoutManager(new LinearLayoutManager((context)));

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

        public RecyclerView getContentView(){return contentView;}
    }

    public CustomAdapter5(SpecialOffer[] offers) {
        contentValues=offers;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content5, viewGroup, false);

        return new ViewHolder(view, viewGroup.getContext());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {



        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Log.w("Offers in Adapter 5",contentValues[position].getName()+" - "+position);
        viewHolder.getName().setText("СпецПредложение - "+contentValues[position].getName());
        viewHolder.getDescription().setText("Описание - "+contentValues[position].getDescription());
        viewHolder.getPrice().setText("Цена за всё предложение - " + contentValues[position].getPrice()+" руб.");
        viewHolder.getContentView().setAdapter(new CustomAdapter2(contentValues[position].getProducts()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contentValues.length;
    }
}

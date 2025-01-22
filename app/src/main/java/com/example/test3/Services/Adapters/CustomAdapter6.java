package com.example.test3.Services.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Models.Promotion;
import com.example.test3.Models.SpecialOffer;
import com.example.test3.R;
import com.example.test3.Services.DataBaseServices.DBHelper;

import java.util.List;

public class CustomAdapter6 extends RecyclerView.Adapter<CustomAdapter6.ViewHolder> {

    private List<Promotion> contentValues;
    private DBHelper mDbHelper;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView Name;
        private final TextView Description;
        private final RecyclerView contentView;

        public ViewHolder(View view, Context context) {
            super(view);

            Name = (TextView) view.findViewById(R.id.name);
            Description = (TextView) view.findViewById(R.id.description);
            contentView = (RecyclerView) view.findViewById(R.id.contentList);
            contentView.setLayoutManager(new LinearLayoutManager((context)));

        }

        public TextView getName() {
            return Name;
        }
        public TextView getDescription() {
            return Description;
        }

        public RecyclerView getContentView(){return contentView;}
    }

    public CustomAdapter6(List<Promotion> offers) {
        contentValues=offers;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content6, viewGroup, false);

        return new ViewHolder(view, viewGroup.getContext());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {



        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Log.w("Offers in Adapter 5",contentValues.get(position).getName()+" - "+position);
        viewHolder.getName().setText("Акция - "+contentValues.get(position).getName());
        viewHolder.getDescription().setText("Описание - "+contentValues.get(position).getDescription());
        viewHolder.getContentView().setAdapter(new CustomAdapter2(contentValues.get(position).getProducts()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contentValues.size();
    }
}

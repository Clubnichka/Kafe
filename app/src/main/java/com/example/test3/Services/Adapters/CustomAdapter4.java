package com.example.test3.Services.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Activities.OrderActivity;
import com.example.test3.Models.Product;
import com.example.test3.Models.SpecialOffer;
import com.example.test3.R;
import com.example.test3.Services.DataBaseServices.DBHelper;
import com.example.test3.Services.ModelServices.SpecialOfferService;

import java.util.LinkedList;
import java.util.List;

public class CustomAdapter4 extends RecyclerView.Adapter<CustomAdapter4.ViewHolder> {

    private SpecialOffer[] contentValues;
    private DBHelper mDbHelper;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView Name;
        private final TextView Description;
        private final TextView Price;
        private final Button Buy;
        private final RecyclerView contentView;

        public ViewHolder(View view, Context context) {
            super(view);

            Name = (TextView) view.findViewById(R.id.name);
            Description = (TextView) view.findViewById(R.id.description);
            Price = (TextView) view.findViewById(R.id.price);
            Buy=(Button) view.findViewById(R.id.Buy);
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
        public Button getBuy(){return  Buy;}
    }

    public CustomAdapter4(Context context) {
        mDbHelper=new DBHelper(context);
        this.context=context;
    }
    public void setContentValues(SpecialOffer[] offers){
        this.contentValues=offers;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content4, viewGroup, false);

        return new ViewHolder(view, viewGroup.getContext());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {



        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getName().setText("СпецПредложение - "+contentValues[position].getName());
        viewHolder.getDescription().setText("Описание - "+contentValues[position].getDescription());
        viewHolder.getPrice().setText("Цена за всё предложение - " + contentValues[position].getPrice()+" руб.");
        viewHolder.getContentView().setAdapter(new CustomAdapter2(contentValues[position].getProducts()));
        viewHolder.getBuy().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                Cursor c = db.query("MyOrder", null, null, null, null, null, null);
                List<Integer> idList2=new LinkedList<Integer>();
                boolean allowed=true;
                if (c.moveToFirst()){
                    int idColIndex=c.getColumnIndex("productid");
                    int typeColIndex=c.getColumnIndex("type");
                    do{
                        if(c.getInt(typeColIndex)==2){
                            if(c.getInt(idColIndex)==contentValues[position].getId()){
                                allowed=false;
                                Toast.makeText(context,"Нельзя добавить спецпредложение в заказ дважды",Toast.LENGTH_SHORT).show();
                                break;
                            };
                        }
                    } while(c.moveToNext());


                    }
                if (allowed){
                    ContentValues values=new ContentValues();
                    values.put("type",2);
                    values.put("productid", contentValues[position].getId()); // Замените "product_id" на имя вашего столбца

                    db.insert("MyOrder", null, values);
                }

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contentValues.length;
    }
}

package com.example.test3.Services.Adapters;

import static android.view.View.GONE;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test3.Models.Order;
import com.example.test3.Models.Product;
import com.example.test3.Models.SpecialOffer;
import com.example.test3.R;
import com.example.test3.Services.CheckerService;
import com.example.test3.Services.ModelServices.OrderService;
import com.example.test3.Services.ModelServices.ProductService;
import com.example.test3.Services.ModelServices.SpecialOfferService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CustomAdapter3 extends RecyclerView.Adapter<CustomAdapter3.ViewHolder> {

    private Order[] contentValues;
    private int userid;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView orderid;
        private final RecyclerView contentView;
        private final Button repeat;


        public ViewHolder(View view, Context context) {
            super(view);

            orderid = (TextView) view.findViewById(R.id.orderid);
            contentView = (RecyclerView) view.findViewById(R.id.contentList);
            repeat=(Button)view.findViewById((R.id.repeat));
            contentView.setLayoutManager(new LinearLayoutManager((context)));

        }

        public TextView getOrderId() {
            return orderid;
        }
        public RecyclerView getContentView() {
            return contentView;
        }
        public Button getRepeat(){return repeat;}
    }

    public CustomAdapter3(Order[] orders, int userid, Context context) {
        this.context=context;
        this.contentValues=orders;
        this.userid=userid;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content3, viewGroup, false);

        return new ViewHolder(view, viewGroup.getContext());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
       if(contentValues[position].getStatus()!=4){
           viewHolder.getRepeat().setVisibility(GONE);
       }

        ProductService productService=new ProductService(contentValues[position].getId(), new ProductService.ProductsCallback() {
            @Override
            public void onProductsLoaded(Product[] products) {
                List<Product> allProducts = new LinkedList<Product>(Arrays.asList(products));
                List<Integer> ids=new ArrayList<>();
                for(Product p:products){
                    ids.add(p.getId());
                }

                SpecialOfferService offerService=new SpecialOfferService(contentValues[position].getId(), true, new SpecialOfferService.OffersCallback() {
                    CheckerService checker =new CheckerService();
                    @Override
                    public void onOffersLoaded(SpecialOffer[] offers) {
                        List<Integer> ids2=new ArrayList<Integer>();
                        for (SpecialOffer offer:offers

                        ) {
                            allProducts.addAll(offer.getProducts());
                            ids2.add(offer.getId());
                        }
                        viewHolder.contentView.setAdapter(new CustomAdapter2(allProducts));
                        viewHolder.getRepeat().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(checker.check(userid)){
                                    OrderService orderService=new OrderService();
                                    orderService.addOrder(ids,ids2,userid);
                                }
                                else{
                                    Toast.makeText(context,"Нельзя делать два заказа за раз",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });


            }
        });
        SpecialOfferService offerService=new SpecialOfferService(contentValues[position].getId(), true, new SpecialOfferService.OffersCallback() {
            @Override
            public void onOffersLoaded(SpecialOffer[] offers) {

            }
        });

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getOrderId().setText("Заказ #"+contentValues[position].getId());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contentValues.length;
    }
}

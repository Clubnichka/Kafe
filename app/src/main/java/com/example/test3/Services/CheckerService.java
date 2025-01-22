package com.example.test3.Services;

import android.util.Log;

import com.example.test3.Models.Order;
import com.example.test3.Services.Adapters.CustomAdapter3;
import com.example.test3.Services.ModelServices.OrderService;

public class CheckerService {
    boolean result;
    OrderService orderService;
    public boolean check(int userid){

        orderService=new OrderService(userid, new OrderService.OrdersCallback() {
            @Override
            public void onOrdersLoaded(Order[] orders) {

                orderService.getCurrentOrder(userid, new OrderService.OrderCallback() {
                    @Override
                    public void onOrderLoaded(Order order) {
                        Log.w("Profile Activity order.status ", ""+order.getStatus());
                        boolean show=false;
                        switch (order.getStatus()){
                            case(1):
                                show=true;
                                break;
                            case(2):
                                show=true;
                                break;
                            case(3):
                                show=true;
                                break;
                            default:
                                break;
                        }
                        result=!show;

                    }
                });

            }
        });
        return result;
    }
}

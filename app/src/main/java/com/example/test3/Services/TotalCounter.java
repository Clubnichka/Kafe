package com.example.test3.Services;

import com.example.test3.Models.Product;
import com.example.test3.Models.SpecialOffer;

import java.util.List;

public class TotalCounter {
    private int total;

    public void sumProducts(List<Product> products){
        for (Product product:products
             ) {
            total+=product.getPrice();
        }
    }

    public void sumOffers(SpecialOffer[] offers){
        for (SpecialOffer offer:offers
        ) {
            total+=offer.getPrice();
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}

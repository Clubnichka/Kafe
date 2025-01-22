package com.example.test3.Services.ImageService;

import com.example.test3.R;

public class ImageService {
    public int setImage(int id){
        switch(id){
            case 1003:
                return R.drawable.pizza;
            case 1:
                return R.drawable.burger;
            case 2003:
                return R.drawable.cesar;
            case 2004:
                return R.drawable.coca_cola;
            case 2005:
                return R.drawable.cheburek;
            case 2008:
                return R.drawable.chillian;
            default:
                return R.drawable.default_image;
        }
    }
}

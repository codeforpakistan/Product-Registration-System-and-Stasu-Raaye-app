package com.example.halalfoodauthorityoss;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BaseClass {

   // private static String BASE_URL="http://192.168.0.22/halal-food_authority_api/Config_admin/";
   // private static String BASE_URL="http://halalfood.creativesafedriver.com/Config_admin/";
    private static String BASE_URL="http://halalfoods.testportal.famzsolutions.com/Config_admin/";
    private static BaseClass baseClass;
    private static Retrofit retrofit;

    private BaseClass()
    {
      /*  retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public  static synchronized BaseClass getInstance()
    {
        if (baseClass==null)
        {
            baseClass=new BaseClass();
        }
        return baseClass;
    }
    public Interface getApi()
    {
        return retrofit.create(Interface.class);
    }
}

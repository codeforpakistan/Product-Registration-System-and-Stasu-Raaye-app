package com.example.halalfoodauthorityoss;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class
BaseClass {

    private static String BASE_URL = "";

    private static BaseClass baseClass;
    private static Retrofit retrofit;

    private BaseClass() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized BaseClass getInstance() {
        if (baseClass == null) {
            baseClass = new BaseClass();
        }
        return baseClass;
    }

    public Interface getApi() {
        return retrofit.create(Interface.class);
    }
}

package com.tugasakhirpab2.rjn.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    private static String BASE_URL_JOB = "https://usahaqqu.000webhostapp.com/";
    private static Retrofit retrofit;

    public static APIEndpoint apiEndpoint()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_JOB)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(APIEndpoint.class);
    }

}

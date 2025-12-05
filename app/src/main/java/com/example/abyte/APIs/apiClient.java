package com.example.abyte.APIs;

import com.example.abyte.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class apiClient {
    private static Retrofit retrofit=null;

    private static final String URL_EXTERNAL_API= "https://www.themealdb.com/api/json/v2/"+ BuildConfig.MEALDB_API_KEY + "/";
    public static Retrofit getExternalClient(){
        if(retrofit==null){
            retrofit= new Retrofit.Builder()
                    .baseUrl(URL_EXTERNAL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    // for calling it in repo: MealApi externalApi = apiClient.getExternalClient().create(MealApi.class);
}

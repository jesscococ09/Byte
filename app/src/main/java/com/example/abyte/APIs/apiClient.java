package com.example.abyte.APIs;

import com.example.abyte.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class apiClient {
    private static Retrofit retrofit=null;
    private static Retrofit supabaseRetrofit=null;

    private static final String URL_EXTERNAL_API= "https://www.themealdb.com/api/json/v2/"+ BuildConfig.MEALDB_API_KEY + "/";

    private static final String URL_SUPABASE_API="https://kgntwbctdxiumdtsglgh.supabase.co/rest/v1/";
    public static Retrofit getExternalClient(){
        if(retrofit==null){
            retrofit= new Retrofit.Builder()
                    .baseUrl(URL_EXTERNAL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static final String SUPABASE_API_KEY="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtnbnR3YmN0ZHhpdW1kdHNnbGdoIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjQ5OTc3MTksImV4cCI6MjA4MDU3MzcxOX0.BZR5Pksfe-6zWYTG9EwYaRUd_mm4ljZ-DpeAU19dGig";

    public static Retrofit getSupabaseClient(){
        if(supabaseRetrofit==null){
            OkHttpClient supabaseClient= new OkHttpClient.Builder().addInterceptor(chain -> {
                Request request=chain.request().newBuilder().addHeader("apikey",SUPABASE_API_KEY)
                        .addHeader("Authorization", "Bearer " +SUPABASE_API_KEY).build();
                return chain.proceed(request);
            }).build();
            supabaseRetrofit= new Retrofit.Builder()
                    .baseUrl(URL_SUPABASE_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return supabaseRetrofit;
    }
    // for calling it in repo: MealApi externalApi = apiClient.getExternalClient().create(MealApi.class);
    // for calling it in repo: SupabaseApi supabaseApi = apiClient.getSupabaseClient().create(SupabaseApi.class);
}

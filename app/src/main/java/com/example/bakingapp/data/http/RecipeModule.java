package com.example.bakingapp.data.http;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RecipeModule {

    private final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    @Provides
    public OkHttpClient provideHttpClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
    }

    @Provides
    public Retrofit provideRetrofit(String baseURL, OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public RecipeAPI provideRecipeService(){
        return provideRetrofit(URL, provideHttpClient()).create(RecipeAPI.class);
    }

}

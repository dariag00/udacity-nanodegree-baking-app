package com.example.bakingapp.data.http;

import com.example.bakingapp.data.http.recipes.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface RecipeAPI {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();

}

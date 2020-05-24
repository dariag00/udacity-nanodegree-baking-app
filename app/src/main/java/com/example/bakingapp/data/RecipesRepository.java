package com.example.bakingapp.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bakingapp.data.http.RecipeAPI;
import com.example.bakingapp.data.http.recipes.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesRepository {

    private static final String LOG_TAG = RecipesRepository.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static RecipesRepository instance;

    private RecipeAPI recipeAPI;

    private RecipesRepository(RecipeAPI recipeAPI){
        this.recipeAPI = recipeAPI;
    }

    public synchronized static RecipesRepository getInstance(RecipeAPI recipeAPI){
        Log.d(LOG_TAG, "Getting the repository");
        if(instance == null){
            synchronized (LOCK) {
                instance = new RecipesRepository(recipeAPI);
                Log.d(LOG_TAG, "Made new movies repository");
            }
        }

        return instance;
    }

    /*
     * Network related operations
     */

    public LiveData<List<Recipe>> getRecipes(){
        Call<List<Recipe>> recipeCall = recipeAPI.getRecipes();
        MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();
        recipeCall.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipeLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                recipeLiveData.setValue(new ArrayList<>());
                t.printStackTrace();
            }
        });

        return recipeLiveData;
    }


}

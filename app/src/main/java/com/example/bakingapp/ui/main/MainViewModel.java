package com.example.bakingapp.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingapp.data.RecipesRepository;
import com.example.bakingapp.data.http.recipes.Recipe;

import java.util.List;

public class MainViewModel extends ViewModel {

    private LiveData<List<Recipe>> recipeLiveData;

    public MainViewModel(RecipesRepository recipesRepository){
        recipeLiveData = recipesRepository.getRecipes();
    }

    public LiveData<List<Recipe>> getRecipeLiveData() {
        return recipeLiveData;
    }
}

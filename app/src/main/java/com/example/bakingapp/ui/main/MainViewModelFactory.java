package com.example.bakingapp.ui.main;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.data.RecipesRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final RecipesRepository recipesRepository;

    public MainViewModelFactory(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(recipesRepository);
    }
}

package com.example.bakingapp.root;

import com.example.bakingapp.data.http.RecipeModule;
import com.example.bakingapp.ui.main.MainActivity;
import com.example.bakingapp.ui.main.RecipeListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, RecipeModule.class})
public interface ApplicationComponent {
    void inject(MainActivity target);
    void inject(RecipeListFragment target);
}

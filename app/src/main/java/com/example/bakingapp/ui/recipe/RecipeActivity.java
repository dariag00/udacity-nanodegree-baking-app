package com.example.bakingapp.ui.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.data.http.recipes.Recipe;

import static com.example.bakingapp.ui.recipe.RecipeFragment.RECIPEE;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if(savedInstanceState != null){
            recipe = (Recipe) savedInstanceState.getSerializable(RECIPEE);
        }

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            recipe = (Recipe) bundle.getSerializable("recipe");
        }

        if(recipe != null){
            setTitle(recipe.getName());

            RecipeFragment recipeFragment = new RecipeFragment();
            recipeFragment.setRecipe(recipe);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipee_container, recipeFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RECIPEE, recipe);
    }
}

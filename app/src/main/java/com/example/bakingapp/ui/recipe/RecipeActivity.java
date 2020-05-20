package com.example.bakingapp.ui.recipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.bakingapp.R;
import com.example.bakingapp.data.http.recipes.Recipe;
import com.example.bakingapp.ui.detail.StepDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

import static com.example.bakingapp.ui.recipe.RecipeFragment.RECIPE;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private boolean twoPane;
    @Nullable
    @BindView(R.id.recipe_steps_container)
    FrameLayout stepsRecipeContainer;
    @BindView(R.id.recipe_container)
    FrameLayout recipeContainer;
    private int currentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        ButterKnife.bind(this);

        if(savedInstanceState != null){
            recipe = (Recipe) savedInstanceState.getSerializable(RECIPE);
        }

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            recipe = (Recipe) bundle.getSerializable("recipe");
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = (String) recipeContainer.getTag();
        if(tag.equals(getString(R.string.large_tag))){
            twoPane = true;
            currentStep = 0;

            StepDetailFragment stepFragment = new StepDetailFragment();
            stepFragment.setStep(recipe.getSteps().get(currentStep));
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_steps_container, stepFragment)
                    .commit();
        } else {
            twoPane = false;
        }

        if(recipe != null){
            setTitle(recipe.getName());

            RecipeFragment recipeFragment = new RecipeFragment(twoPane);
            recipeFragment.setRecipe(recipe);
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_container, recipeFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RECIPE, recipe);
    }
}

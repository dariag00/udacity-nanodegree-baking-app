package com.example.bakingapp.ui.recipe;

import androidx.annotation.NonNull;
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

import static com.example.bakingapp.ui.recipe.RecipeFragment.RECIPEE;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private boolean twoPane;
    @BindView(R.id.recipee_steps_container)
    FrameLayout stepsRecipeContainer;
    private int currentStep;

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

        if(stepsRecipeContainer != null){
            twoPane = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            currentStep = 0;
            StepDetailFragment stepFragment = new StepDetailFragment();
            stepFragment.setStep(recipe.getSteps().get(currentStep));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipee_steps_container, stepFragment)
                    .commit();
        } else {
            twoPane = false;
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

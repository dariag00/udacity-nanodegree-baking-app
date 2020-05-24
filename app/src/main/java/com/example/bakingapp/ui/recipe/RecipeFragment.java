package com.example.bakingapp.ui.recipe;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.BakingAppWidget;
import com.example.bakingapp.R;
import com.example.bakingapp.data.http.recipes.Recipe;
import com.example.bakingapp.ui.detail.StepDetailActivity;
import com.example.bakingapp.ui.detail.StepDetailFragment;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeFragment extends Fragment implements StepsAdapter.StepClickListener {

    public static final String RECIPE = "recipe";

    private Recipe recipe;

    private StepsAdapter stepsAdapter;
    private boolean twoPane;
    private IngredientsAdapter ingredientsAdapter;

    @BindView(R.id.rv_ingredients)
    RecyclerView ingredientsRecyclerView;

    @BindView(R.id.rv_steps)
    RecyclerView stepsRecyclerView;

    @BindView(R.id.button_attach_widget)
    Button attachButton;

    @BindView(R.id.tv_recipe_name)
    TextView recipeName;

    public RecipeFragment(){
        this.twoPane = false;
    }

    public RecipeFragment(boolean twoPane){
        this.twoPane = twoPane;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null){
            recipe = (Recipe) savedInstanceState.getSerializable(RECIPE);
        }
        View rootView = inflater.inflate(R.layout.fragment_recipe, container, false);

        ButterKnife.bind(this, rootView);

        initializeData(recipe);

        attachButton.setOnClickListener(view -> attachInfoToWidget());

        return rootView;
    }

    private void attachInfoToWidget(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson =new Gson();
        String json=gson.toJson(recipe.getIngredients());
        editor.putString("ingredients",json).apply();
        editor.commit();
        Toast.makeText(getActivity(), "Widget updated!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(getActivity(), BakingAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        intent.putExtra(RECIPE, recipe);
        getActivity().getApplicationContext().sendBroadcast(intent);
    }

    private void initializeData(Recipe recipe){
        recipeName.setText(recipe.getName());
        initializeIngredientsData(recipe);
        initializeStepsData(recipe);
    }

    private void initializeIngredientsData(Recipe recipe){
        LinearLayoutManager  layoutManager = new LinearLayoutManager(getActivity());
        ingredientsRecyclerView.setLayoutManager(layoutManager);
        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsAdapter = new IngredientsAdapter(recipe.getIngredients());
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
    }

    private void initializeStepsData(Recipe recipe){
        LinearLayoutManager  layoutManager = new LinearLayoutManager(getActivity());
        stepsRecyclerView.setLayoutManager(layoutManager);
        stepsRecyclerView.setHasFixedSize(true);
        stepsAdapter = new StepsAdapter(recipe.getSteps(), this);
        stepsRecyclerView.setAdapter(stepsAdapter);
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(RECIPE, recipe);
    }

    @Override
    public void onStepClick(int clickedStep) {
        if(twoPane){
            StepDetailFragment stepFragment = new StepDetailFragment();
            stepFragment.setStep(recipe.getSteps().get(clickedStep));
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_steps_container, stepFragment)
                    .commit();
        }else {
            Intent intent = new Intent(getActivity(), StepDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipe", recipe);
            bundle.putInt("clickedStep", clickedStep);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}

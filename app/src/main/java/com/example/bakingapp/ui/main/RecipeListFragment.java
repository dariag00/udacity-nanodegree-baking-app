package com.example.bakingapp.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.RecipesRepository;
import com.example.bakingapp.data.http.RecipeAPI;
import com.example.bakingapp.data.http.recipes.Recipe;
import com.example.bakingapp.root.App;
import com.example.bakingapp.ui.recipe.RecipeActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment implements RecipeAdapter.RecipeClickListener{

    @BindView(R.id.rv_recipees)
    RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private RecipesRepository recipesRepository;

    @Inject
    RecipeAPI recipeAPI;


    public RecipeListFragment(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((App) context.getApplicationContext()).getComponent().inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, rootView);

        if(isTablet()){
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), numberOfColumns());
            recyclerView.setLayoutManager(layoutManager);
        }else{
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        }

        recyclerView.setHasFixedSize(true);
        recipeAdapter = new RecipeAdapter(this);
        recyclerView.setAdapter(recipeAdapter);

        recipesRepository = RecipesRepository.getInstance(recipeAPI);

        MainViewModelFactory factory = new MainViewModelFactory(recipesRepository);
        MainViewModel mainViewModel = new ViewModelProvider(getActivity(), factory).get(MainViewModel.class);
        mainViewModel.getRecipeLiveData().observe(this, recipeList -> {
            System.out.println("Size " + recipeList.size());
            recipeAdapter.setRecipeList(recipeList);
        });

        return rootView;
    }

    private boolean isTablet(){
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        return diagonalInches>=6.5;
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 500;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRecipeClick(Recipe clickedRecipe) {
        Intent intent = new Intent(getActivity(), RecipeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", clickedRecipe);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

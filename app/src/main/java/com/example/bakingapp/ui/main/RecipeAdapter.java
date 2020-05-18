package com.example.bakingapp.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.http.recipes.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private List<Recipe> recipeList;
    private RecipeClickListener recipeClickListener;

    public RecipeAdapter(RecipeClickListener recipeClickListener){
        this.recipeClickListener = recipeClickListener;
    }

    public void setRecipeList(List<Recipe> recipeList){
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(recipeList != null){
            return recipeList.size();
        }

        return 0;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.tv_servings) TextView servingsTextView;
        @BindView(R.id.tv_recipee) TextView recipeNameView;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipe = recipeList.get(getAdapterPosition());
            recipeClickListener.onRecipeClick(recipe);
        }

        void bind(int position){
            Recipe recipe = recipeList.get(position);
            recipeNameView.setText(recipe.getName());
            servingsTextView.setText(String.valueOf(recipe.getServings()));
        }
    }

    public interface RecipeClickListener {
        void onRecipeClick(Recipe clickedRecipe);
    }
}

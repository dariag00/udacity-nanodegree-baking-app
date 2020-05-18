package com.example.bakingapp.ui.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.data.http.recipes.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredientList;

    public IngredientsAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(ingredientList != null){
            return ingredientList.size();
        }
        return 0;
    }


    class IngredientViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_ingredient_name)
        TextView ingredientName;
        @BindView(R.id.tv_ingredient_qty)
        TextView ingredientQty;
        @BindView(R.id.tv_ingredient_unit)
        TextView ingredientUnit;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(int position){
            Ingredient ingredient = ingredientList.get(position);
            String modifiedString = ingredient.getIngredient().substring(0,1).toUpperCase() + ingredient.getIngredient().substring(1);
            ingredientName.setText(modifiedString);
            ingredientQty.setText(String.valueOf(ingredient.getQuantity()));
            ingredientUnit.setText(String.valueOf(ingredient.getMeasure()));
        }
    }

}

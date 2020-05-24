package com.example.bakingapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.preference.PreferenceManager;

import com.example.bakingapp.data.http.recipes.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RemoteWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory(this.getApplicationContext());
    }

    class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        Context context;
        List<Ingredient> collection = new ArrayList<>();

        public RemoteViewsFactory(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {
            initData();
        }

        @Override
        public void onDataSetChanged() {
            initData();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return collection.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_item);
            Ingredient ingredient = collection.get(position);
            views.setTextViewText(R.id.widget_ingredient_name, ingredient.getIngredient().substring(0,1).toUpperCase() + ingredient.getIngredient().substring(1));
            views.setTextViewText(R.id.widget_ingredient_qty, ingredient.getQuantity() + " " + ingredient.getMeasure());

            Intent fillInIntent = new Intent();
            views.setOnClickFillInIntent(R.id.widget_ingredient_name, fillInIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private void initData(){
            collection.clear();
            Gson gson =new Gson();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String json = preferences.getString("ingredients", "");
            Type type=new TypeToken<ArrayList<Ingredient>>(){}.getType();
            collection = gson.fromJson(json,type);
        }
    }

}

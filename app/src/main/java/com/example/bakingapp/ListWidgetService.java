package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext());
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    static List<Recipe> mRecipes;
    static int recipeIndex;

    public RecipeRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    // this is called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        // TODO get data from network resource
    }

    @Override
    public void onDestroy() {

    }

    // Returns the number of items necessary for the GridView to display
    @Override
    public int getCount() {
        return mRecipes.get(recipeIndex).getIngredients().size() + 1;
    }

    // This very important method is where the data is actually binded to the views.
    // Very similar to onBindViewHolder in a collection Adapter
    @Override
    public RemoteViews getViewAt(int position) {
        // TODO bind data to views
        if (position == 0) {
            // TODO handle first item case. This will be the recipe Title
        }
        else {
            // TODO handle normal ingredient cases
        }
        return null;
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
    public long getItemId(int position) {
        // We don't need any special id for items, so the item position is the same as its id
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    // The following function will receive an int that represents the recipe index
    // and converts that number into a usable index from 0 to mRecipes.size()
    private int processIndex(int i) {
        int index = i % mRecipes.size();
        if (index < 0)
            index += mRecipes.size();
        return index;
    }

    // The following function takes an ingredient object and builds a string
    private String buildIngredientString(Recipe.Ingredient ingredient) {
        return ingredient.getIngredient() + ": " + ingredient.getQuantity() + " " + ingredient.getMeasure();
    }
}

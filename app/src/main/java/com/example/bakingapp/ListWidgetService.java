package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.resourceAccess.ClientApi;
import com.example.bakingapp.resourceAccess.RecipeApi;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import timber.log.Timber;

public class ListWidgetService extends RemoteViewsService {

    public static final String RECIPE_INDEX_KEY = "recipeIndexKey";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int index = 0;
        if (intent.hasExtra(RECIPE_INDEX_KEY)) {
            index = intent.getIntExtra(RECIPE_INDEX_KEY, 0);
        }
        return new RecipeRemoteViewsFactory(this.getApplicationContext(), index);
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    static List<Recipe> mRecipes;
    static int recipeIndex;

    public RecipeRemoteViewsFactory(Context context, int index) {
        recipeIndex = processIndex(index);
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    // this is called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        Timber.d("Updating recipe data in the widget");
        if (mRecipes == null) {
            // Get Data from Network Resource if we have not already
            try
            {
                RecipeApi recipeApi = ClientApi.getClient().create(RecipeApi.class);
                Call<List<Recipe>> call = recipeApi.getRecipes();
                mRecipes = call.execute().body();
                Timber.d("Data was obtained for the widget.");
            }
            catch (IOException e)
            {
                Timber.d("Error getting data for the widget.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {

    }

    // Returns the number of items necessary for the GridView to display
    @Override
    public int getCount() {
        if (mRecipes == null) {
            return 1;
        }
        return mRecipes.get(recipeIndex).getIngredients().size() + 1;
    }

    // This very important method is where the data is actually binded to the views.
    // Very similar to onBindViewHolder in a collection Adapter
    @Override
    public RemoteViews getViewAt(int position) {
        Timber.d("Binding data to item: %s", position);
        // bind data to views
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_item);
        if (mRecipes != null) {
            // If we have obtained data for our recipes
            if (position == 0) {
                // handle first item case. This will be the recipe Title
                views.setTextViewText(R.id.tv_ingredient_item, mRecipes.get(recipeIndex).getName());
            }
            else {
                // handle normal ingredient cases
                String ingredient = buildIngredientString(mRecipes.get(recipeIndex).getIngredients().get(position - 1));
                views.setTextViewText(R.id.tv_ingredient_item, ingredient);
            }
        }
        else {
            // otherwise we give an error message
            views.setTextViewText(R.id.tv_ingredient_item, "Error Loading Data");
        }

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

package com.example.bakingapp.managers;

import androidx.lifecycle.LiveData;

import com.example.bakingapp.Recipe;
import com.example.bakingapp.resourceAccess.RecipeListAccess;

import java.util.List;

import timber.log.Timber;

public class ContentManager {
    static ContentManager instance = null;
    LiveData<List<Recipe>> recipes = null;

    public static ContentManager getInstance() {
        if (instance == null) {
            instance = new ContentManager();
        }

        return instance;
    }

    public void queryRecipeApi() {
        Timber.d("Querying the Server for data.");
        recipes = RecipeListAccess.getRecipeList();
    }

    public LiveData<List<Recipe>> getRecipes() {
        Timber.d("getting the recipe list.");
        return recipes;
    }
}

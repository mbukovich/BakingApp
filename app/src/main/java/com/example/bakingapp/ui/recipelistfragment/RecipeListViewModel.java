package com.example.bakingapp.ui.recipelistfragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingapp.Recipe;
import com.example.bakingapp.managers.ContentManager;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    LiveData<List<Recipe>> recipes;

    public LiveData<List<Recipe>> getRecipes() {
        ContentManager contentManager = ContentManager.getInstance();
        contentManager.queryRecipeApi();
        recipes = contentManager.getRecipes();
        return recipes;
    }

    public void removeObservers(LifecycleOwner owner) {
        recipes.removeObservers(owner);
    }
}

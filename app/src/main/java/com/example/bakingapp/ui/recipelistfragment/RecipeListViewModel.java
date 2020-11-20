package com.example.bakingapp.ui.recipelistfragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingapp.Recipe;
import com.example.bakingapp.managers.ContentManager;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    LiveData<List<Recipe>> recipes;

    public LiveData<List<Recipe>> getRecipes() {
        if (recipes == null) {
            ContentManager contentManager = ContentManager.getInstance();
            contentManager.queryRecipeApi();
            recipes = contentManager.getRecipes();
        }
        return recipes;
    }
}

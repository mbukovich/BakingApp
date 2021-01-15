package com.example.bakingapp.resourceAccess;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bakingapp.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RecipeListAccess {

    private static RecipeApi recipeApi = null;

    public static LiveData<List<Recipe>> getRecipeList() {
        final MutableLiveData<List<Recipe>> results = new MutableLiveData<>();
        if (recipeApi == null) {
            recipeApi = ClientApi.getClient().create(RecipeApi.class);
        }
        Call<List<Recipe>> call = recipeApi.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                // Happy Case
                results.setValue(response.body());
                Timber.d("Obtained data from server.");
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                // Sad Case
                // We create an empty error Recipe so the UI will know to display the appropriate message
                Recipe errorRecipe = new Recipe(-1,"error",null,null,0,null);
                List<Recipe> errorList = new ArrayList<>();
                errorList.add(errorRecipe);
                results.setValue(errorList);
                Timber.e(t);
            }
        });

        return results;
    }
}

package com.example.bakingapp.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bakingapp.Recipe;
import com.example.bakingapp.managers.ContentManager;

import java.util.List;

import timber.log.Timber;

public class MasterDetailSharedViewModel extends ViewModel {
    private int stepIndex = 0;
    private MutableLiveData<Recipe> currentRecipe = new MutableLiveData<>();
    private MutableLiveData<Recipe.Step> currentStep = new MutableLiveData<>();

    public void chooseCurrentRecipe(int index) {
        Timber.d("Choosing Recipe at index: %s", index);
        ContentManager contentManager = ContentManager.getInstance();
        currentRecipe.setValue(contentManager.getRecipes().getValue().get(index));
    }

    public void chooseCurrentStep(int index) {
        Timber.d("Choosing recipe step at index: %s", index);
        stepIndex = index;
        if (index == 0)
            currentStep.setValue(null);
        else {
            currentStep.setValue(currentRecipe.getValue().getSteps().get(index - 1));
        }
    }

    public void nextStep() {
        Timber.d("Changing to next recipe step.");
        if (stepIndex < currentRecipe.getValue().getSteps().size() + 1) {
            stepIndex++;
            chooseCurrentStep(stepIndex);
        }
    }

    public void previousStep() {
        Timber.d("Changing to previous recipe step.");
        if (stepIndex > 0) {
            stepIndex--;
            chooseCurrentStep(stepIndex);
        }
    }

    public Boolean isLastStep() {
        if (stepIndex == currentRecipe.getValue().getSteps().size())
            return true;
        return false;
    }

    public String retrieveIngredients() {
        List<Recipe.Ingredient> ingredients = currentRecipe.getValue().getIngredients();
        int ingredientNum = ingredients.size();
        String ingredientText = "";
        for (int i = 0; i < ingredientNum; i++) {
            ingredientText += ("Ingredient: " + ingredients.get(i).getIngredient() + "\n");
            ingredientText += ("Amount: " + ingredients.get(i).getQuantity() + " "
                    + ingredients.get(i).getMeasure() + "\n" + "\n");
        }
        return ingredientText;
    }

    public MutableLiveData<Recipe> getCurrentRecipe() {
        return currentRecipe;
    }

    public MutableLiveData<Recipe.Step> getCurrentStep() {
        return currentStep;
    }
}

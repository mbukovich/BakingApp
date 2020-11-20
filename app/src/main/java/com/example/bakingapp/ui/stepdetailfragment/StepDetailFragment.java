package com.example.bakingapp.ui.stepdetailfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.R;
import com.example.bakingapp.Recipe;
import com.example.bakingapp.databinding.FragmentStepDetailsBinding;
import com.example.bakingapp.ui.MasterDetailSharedViewModel;

import java.util.List;

public class StepDetailFragment extends Fragment {

    private FragmentStepDetailsBinding binding;

    private MasterDetailSharedViewModel model;
    // Necessary constructor
    public StepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentStepDetailsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(MasterDetailSharedViewModel.class);
        model.getCurrentStep().observe(getViewLifecycleOwner(), new Observer<Recipe.Step>() {
            @Override
            public void onChanged(Recipe.Step step) {
                // TODO update UI
                if (step == null) {
                    List<Recipe.Ingredient> ingredients = model.getCurrentRecipe().getValue().getIngredients();
                    int ingredientNum = ingredients.size();
                    String ingredientText = "";
                    for (int i = 0; i < ingredientNum; i++) {
                        ingredientText += ("Ingredient: " + ingredients.get(i).getIngredient() + "\n");
                        ingredientText += ("Amount: " + ingredients.get(i).getQuantity() + " "
                                + ingredients.get(i).getMeasure() + "\n" + "\n");
                    }
                    binding.textViewDetailDescription.setText(ingredientText);
                    binding.buttonPreviousStep.setVisibility(View.INVISIBLE);
                }
                else {
                    binding.buttonPreviousStep.setVisibility(View.VISIBLE);
                    binding.textViewDetailDescription.setText(step.getDescription());
                    if (model.isLastStep())
                        binding.buttonNextStep.setVisibility(View.INVISIBLE);
                    else
                        binding.buttonNextStep.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void onPreviousClicked(View view) {
        model.previousStep();
    }

    public void onNextClicked(View view) {
        model.nextStep();
    }
}

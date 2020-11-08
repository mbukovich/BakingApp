package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.bakingapp.databinding.ActivityRecipeBinding;
import com.example.bakingapp.ui.StepListFragment;

public class RecipeActivity extends AppCompatActivity implements StepListFragment.OnStepClickListener {

    private Boolean isPhone;
    private ActivityRecipeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set up view binding
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        isPhone = true; // TODO figure out if the device is a phone or tablet

        // This activity will be set up differently for a phone and for a tablet
        if (isPhone) {
            // TODO set up the activity for a phone screen
        }
        else {
            // TODO set up the activity for a tablet screen
        }
    }

    @Override
    public void onRecipeClicked(int index) {
        // When a step in the recipe is clicked, the resulting action will vary whether the device is a phone or tablet
        if (isPhone) {
            // TODO perform actions to launch an intent with the StepDetailActivity activity
        }
        else {
            // TODO perform actions to send selection id to the step detail fragment
        }
    }
}
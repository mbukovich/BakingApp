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

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(MasterDetailSharedViewModel.class);
        model.getCurrentStep().observe(getViewLifecycleOwner(), new Observer<Recipe.Step>() {
            @Override
            public void onChanged(Recipe.Step step) {
                if (step == null) {
                    binding.textViewDetailDescription.setText(model.retrieveIngredients());
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

        // set onClickListeners on the navigation buttons
        binding.buttonNextStep.setOnClickListener(v -> onNextClicked());
        binding.buttonPreviousStep.setOnClickListener(v -> onPreviousClicked());
    }

    public void onPreviousClicked() {
        model.previousStep();
    }

    public void onNextClicked() {
        model.nextStep();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

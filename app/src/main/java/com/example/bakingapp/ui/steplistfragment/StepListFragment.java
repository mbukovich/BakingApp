package com.example.bakingapp.ui.steplistfragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.bakingapp.R;
import com.example.bakingapp.Recipe;
import com.example.bakingapp.databinding.FragmentStepListBinding;
import com.example.bakingapp.ui.MasterDetailSharedViewModel;

import timber.log.Timber;

public class StepListFragment extends Fragment implements StepListAdapter.OnStepItemClicked {

    StepListAdapter stepListAdapter;

    private MasterDetailSharedViewModel model;

    private FragmentStepListBinding binding;

    private boolean isRecipeSet;

    private static final String KEY_IS_RECIPE_SET = "isRecipeSet";

    // We create an interface in order to communicate with the containing activity
    OnStepClickListener mCallback;

    @Override
    public void onClicked(int index) {
        model.chooseCurrentStep(index);
        mCallback.onStepClicked();
    }

    public interface OnStepClickListener {
        void onStepClicked();
    }

    // Now we override onAttach to make sure the host activity has implemented OnRecipeClickListener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepClickListener");
        }
    }

    // necessary empty constructor
    public StepListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentStepListBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        stepListAdapter = new StepListAdapter(this);
        binding.recyclerViewStepList.setAdapter(stepListAdapter);
        binding.recyclerViewStepList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        binding.recyclerViewStepList.setHasFixedSize(true);

        model = new ViewModelProvider(requireActivity()).get(MasterDetailSharedViewModel.class);

        if (savedInstanceState != null) {
            // if this is not the first time to create this view
            isRecipeSet = savedInstanceState.getBoolean(KEY_IS_RECIPE_SET, true);
        }
        else {
            // this is the first time for the view to be set up, so we set isRecipeSet to false
            isRecipeSet = false;
        }

        return rootView;
    }

    public void chooseRecipeIndex(int index) {
        // if the recipe hasn't been set yet, we set it up
        if (index == -1) {
            // error case
            binding.recyclerViewStepList.setVisibility(View.INVISIBLE);
            binding.tbErrorMessage.setVisibility(View.VISIBLE);
        }
        else {
            // happy case
            binding.recyclerViewStepList.setVisibility(View.VISIBLE);
            binding.tbErrorMessage.setVisibility(View.INVISIBLE);
            if (!isRecipeSet) {
                Timber.d("Choosing Recipe in fragment at index: %s", index);
                model.chooseCurrentRecipe(index);
                model.getCurrentRecipe().observe(getViewLifecycleOwner(), new Observer<Recipe>() {
                    @Override
                    public void onChanged(Recipe recipe) {
                        stepListAdapter.setData(recipe);
                    }
                });
                isRecipeSet = true;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_RECIPE_SET, isRecipeSet);
    }
}

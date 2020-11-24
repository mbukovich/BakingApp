package com.example.bakingapp.ui.recipelistfragment;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.bakingapp.R;
import com.example.bakingapp.Recipe;
import com.example.bakingapp.databinding.FragmentRecipeListBinding;

import java.util.List;

import timber.log.Timber;

public class RecipeListFragment extends Fragment implements RecipeListAdapter.OnRecipeClickedListener {
    // We create an interface in order to communicate with the containing activity
    OnRecipeClickListener mCallback;
    private FragmentRecipeListBinding binding;
    private RecipeListAdapter recipeListAdapter;
    private RecipeListViewModel viewModel;

    @Override
    public void onClick(int index) {
        Timber.d("click event passed to fragment. Index: %s", index);
        mCallback.onRecipeClicked(index);
    }

    public interface OnRecipeClickListener {
        void onRecipeClicked(int index);
    }

    // Now we override onAttach to make sure the host activity has implemented OnRecipeClickListener
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnRecipeClickListener");
        }
    }

    // necessary constructor
    public RecipeListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentRecipeListBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Set up recycler view
        // Adjusting the recycler view column number to the screen size
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float width = displayMetrics.widthPixels / displayMetrics.density;
        int colNum = (int) (width / 400);
        if (colNum < 1) colNum = 1;
        binding.recyclerViewRecipeList.setLayoutManager(new GridLayoutManager(rootView.getContext(), colNum));
        binding.recyclerViewRecipeList.setHasFixedSize(true);
        recipeListAdapter = new RecipeListAdapter(this);
        binding.recyclerViewRecipeList.setAdapter(recipeListAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // observe data and update UI
        viewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);
        viewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                Timber.d("Sending data to the recycler view. Number of Recipes: %s", recipes.size());
                recipeListAdapter.setData(recipes);
            }
        });
    }
}

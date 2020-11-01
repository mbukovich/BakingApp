package com.example.bakingapp.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingapp.R;

public class RecipeListFragment extends Fragment {
    // We create an interface in order to communicate with the containing activity
    OnRecipeClickListener mCallback;

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

        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        // TODO add reference to view inside fragment

        // TODO create a Recycler View for the Recipe List with an onClickListener that triggers OnRecipeClickListener

        return rootView;
    }
}

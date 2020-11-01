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

public class StepListFragment extends Fragment {
    // We create an interface in order to communicate with the containing activity
    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onRecipeClicked(int index);
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
        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        // TODO get reference to the view in the layout

        // TODO create ui for displaying ingredients and recipe steps

        // TODO create click listener and use it to fire OnStepClickListener

        return rootView;
    }
}

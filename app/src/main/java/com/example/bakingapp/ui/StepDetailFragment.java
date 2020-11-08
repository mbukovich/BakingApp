package com.example.bakingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bakingapp.R;
import com.example.bakingapp.databinding.FragmentStepDetailsBinding;

public class StepDetailFragment extends Fragment {

    private FragmentStepDetailsBinding binding;
    // Necessary constructor
    public StepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentStepDetailsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // TODO get reference to the view in the layout

        return rootView;
    }
}

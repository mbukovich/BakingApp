package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bakingapp.databinding.ActivityMainBinding;
import com.example.bakingapp.ui.recipelistfragment.RecipeListFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeClickListener {

    private ActivityMainBinding binding;

    private static final String INDEX_KEY = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // initialize Timber logging library
        Timber.plant(new Timber.DebugTree() {
            // include the line number with the logging tag
            @Override
            protected @Nullable String createStackElementTag(@NotNull StackTraceElement element) {
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });

        // Setting up the fragment
        RecipeListFragment recipeListFragment = new RecipeListFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.recipe_container, recipeListFragment).commit();
    }

    @Override
    public void onRecipeClicked(int index) {
        Timber.d("Recipe list fragment has registered a click event. Index: %s", index);
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(INDEX_KEY, index);
        startActivity(intent);
    }
}
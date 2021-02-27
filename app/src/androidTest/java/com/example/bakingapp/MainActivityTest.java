package com.example.bakingapp;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.bakingapp.ui.recipelistfragment.RecipeListFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void fragment_can_be_instantiated() {
        mMainActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RecipeListFragment recipeListFragment = startRecipeListFragment();
            }
        });
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view_recipe_list)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    private RecipeListFragment startRecipeListFragment() {
        // Start a recipe list fragment in the parent activity
        MainActivity mainActivity = (MainActivity) mMainActivityRule.getActivity();
        FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
        RecipeListFragment recipeListFragment = new RecipeListFragment();
        transaction.add(recipeListFragment, "recipeListFragment");
        transaction.commit();
        return recipeListFragment;
    }
}

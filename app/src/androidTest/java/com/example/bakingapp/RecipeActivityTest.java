package com.example.bakingapp;

import androidx.fragment.app.FragmentTransaction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.bakingapp.ui.steplistfragment.StepListFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {
    @Rule public ActivityTestRule<RecipeActivity> mRecipeActivity = new ActivityTestRule<>(RecipeActivity.class);

    @Test
    public void master_can_be_instantiated() {
        mRecipeActivity.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StepListFragment stepListFragment = startStepListFragment();
            }
        });
        Espresso.onView(ViewMatchers.withId(R.id.recipe_steps_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    private StepListFragment startStepListFragment() {
        // start step list fragment in the parent activity
        RecipeActivity recipeActivity = (RecipeActivity) mRecipeActivity.getActivity();
        FragmentTransaction transaction = recipeActivity.getSupportFragmentManager().beginTransaction();
        StepListFragment stepListFragment = new StepListFragment();
        transaction.add(stepListFragment, "stepListFragment");
        transaction.commit();
        return stepListFragment;
    }
}

package com.ezz.bakingapp.recipes;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ezz.bakingapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

    private IdlingResource idlingResource;
    @Rule
    public ActivityTestRule<RecipeActivity> recipeActivityTestRule = new ActivityTestRule<>(RecipeActivity.class);

    @Before
    public void registerIdlingResource() {
        idlingResource = recipeActivityTestRule.getActivity().getIdleResource();
        Espresso.registerIdlingResources(idlingResource);
    }
    @Test
    public void recipeActivityTest() {
        onView(withId(R.id.toolBar)).check(matches(isDisplayed()));

        ViewInteraction rvRecipes = onView(
                allOf(withId(R.id.rvRecipes), isDisplayed()));
        rvRecipes.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction rvSteps = onView(
                allOf(withId(R.id.rvSteps), isDisplayed()));
        rvSteps.perform(actionOnItemAtPosition(0, click()));
    }
    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}

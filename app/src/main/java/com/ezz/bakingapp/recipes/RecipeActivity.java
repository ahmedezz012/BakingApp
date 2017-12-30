package com.ezz.bakingapp.recipes;

import android.os.Bundle;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.ezz.bakingapp.R;
import com.ezz.bakingapp.helpers.ActivityBase;
import com.ezz.bakingapp.model.RecipeItem;
import com.ezz.bakingapp.recipeDetailes.RecipeDetailsActivity;

public class RecipeActivity extends ActivityBase implements RecipeClickListener {

    private Toolbar toolBar;
    private RecipeFragment recipeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        initializeView();
        setToolBar(toolBar, getString(R.string.app_name), false);
        if (savedInstanceState == null) {
            recipeFragment = RecipeFragment.newInstance(isTablet());
            loadFragment(recipeFragment);
        }
    }

    @Override
    protected void initializeView() {
        toolBar = (Toolbar) findViewById(R.id.toolBar);
    }

    @Override
    protected void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmRecipesContainer, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onRecipeClicked(RecipeItem recipeItem) {
        RecipeDetailsActivity.openRecipeDetailsActivity(this, recipeItem);
    }

    public IdlingResource getIdleResource() {
        return recipeFragment.getIdleResource();
    }
}

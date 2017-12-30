package com.ezz.bakingapp.recipeDetailes;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ezz.bakingapp.R;
import com.ezz.bakingapp.helpers.ActivityBase;
import com.ezz.bakingapp.helpers.GsonHelper;
import com.ezz.bakingapp.helpers.SharedPrefHelper;
import com.ezz.bakingapp.model.Ingredient;
import com.ezz.bakingapp.model.RecipeItem;
import com.ezz.bakingapp.model.Step;
import com.ezz.bakingapp.recipeStepDetailes.RecipeStepDetailsActivity;
import com.ezz.bakingapp.recipeStepDetailes.RecipeStepDetailsFragment;
import com.ezz.bakingapp.recipeStepDetailes.StepsNavigationClickListener;
import com.ezz.bakingapp.widget.IngredientService;
import com.ezz.bakingapp.widget.IngredientWidget;

import java.util.ArrayList;

public class RecipeDetailsActivity extends ActivityBase implements RecipeStepListener{

    public static final String RECIPE_ITEM = "RECIPE_ITEM";
    public static final String RECIPE_DETAILS_FRAGMENT_TAG = "RecipeDetailsFragment";
    private Toolbar toolBar;
    private boolean isTablet;
    private RecipeItem recipeItem = null;
    private String toolBarTitle = null;
    private RecipeStepDetailsFragment recipeStepDetailsFragment;
    private RecipeDetailsFragment recipeDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        initializeView();
        isTablet = isTablet();
        setRecipeItemFromIntent();
        setToolBarTitle();
        setToolBar(toolBar, toolBarTitle, false);
        if (savedInstanceState == null) {
            recipeDetailsFragment = RecipeDetailsFragment.newInstance(recipeItem);
            loadFragment(recipeDetailsFragment);
        } else {
            recipeDetailsFragment = (RecipeDetailsFragment) getSupportFragmentManager().findFragmentByTag(RECIPE_DETAILS_FRAGMENT_TAG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuItemAddToWidget) {
            addIngredientsToSharedPref();
            Intent intentUpdate = new Intent(this, IngredientWidget.class);
            intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            sendBroadcast(intentUpdate);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addIngredientsToSharedPref() {
        ArrayList<Ingredient> ingredientArrayList = recipeDetailsFragment.getIngredients();
        String ingredientsJson = GsonHelper.getIngredientsString(ingredientArrayList);
        SharedPrefHelper.saveIngredients(this, ingredientsJson);
    }

    @Override
    protected void initializeView() {
        toolBar = (Toolbar) findViewById(R.id.toolBar);
    }

    @Override
    protected void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmRecipeDetailContainer, fragment, RECIPE_DETAILS_FRAGMENT_TAG);
        fragmentTransaction.commit();
    }

    public static void openRecipeDetailsActivity(Context context, RecipeItem recipeItem) {
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_ITEM, recipeItem);
        context.startActivity(intent);
    }

    private void setRecipeItemFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            recipeItem = intent.getParcelableExtra(RECIPE_ITEM);
        }
    }

    private void setToolBarTitle() {
        if (TextUtils.isEmpty(recipeItem.getName()) || isTablet)
            toolBarTitle = getString(R.string.app_name);
        else
            toolBarTitle = recipeItem.getName();
    }

    private void loadStepDetailsFragment(ArrayList<Step> stepArrayList, int position) {
        recipeStepDetailsFragment = RecipeStepDetailsFragment.newInstance(stepArrayList, position, isTablet);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frmRecipeStepDetailContainer, recipeStepDetailsFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onRecipeStepClicked(ArrayList<Step> stepArrayList, int position) {
        if (isTablet) {
            loadStepDetailsFragment(stepArrayList, position);
        } else {
            RecipeStepDetailsActivity.openRecipeStepDetailsActivity(this, stepArrayList, position, toolBarTitle);
        }
    }
}

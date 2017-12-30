package com.ezz.bakingapp.recipeStepDetailes;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.ezz.bakingapp.R;
import com.ezz.bakingapp.helpers.ActivityBase;
import com.ezz.bakingapp.model.RecipeItem;
import com.ezz.bakingapp.model.Step;
import com.ezz.bakingapp.recipeDetailes.RecipeDetailsActivity;
import com.ezz.bakingapp.recipeDetailes.RecipeDetailsFragment;

import java.util.ArrayList;

public class RecipeStepDetailsActivity extends ActivityBase implements StepsNavigationClickListener {


    private static final String POSITION = "POSITION";
    private static final String STEPS = "STEPS";
    private static final String TOOL_BAR_TITLE = "TOOL_BAR_TITLE";
    private Toolbar toolBar;
    private ArrayList<Step> stepArrayList;
    private int position;
    private String toolBarTitle = null;
    private boolean isTablet;
    private RecipeStepDetailsFragment recipeStepDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details);
        initializeView();
        isTablet = isTablet();
        getDataFromIntent();
        setToolBar(toolBar, toolBarTitle, false);
        if (savedInstanceState == null) {
            openFragment(stepArrayList, position);
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
        fragmentTransaction.replace(R.id.frmRecipeStepDetailContainer, fragment);
        fragmentTransaction.commit();
    }

    private void getToolBarTitle(Intent intent) {
        if (intent != null)
            toolBarTitle = intent.getStringExtra(TOOL_BAR_TITLE);
    }

    public static void openRecipeStepDetailsActivity(Context context, ArrayList<Step> stepArrayList, int position, String toolBarTitle) {
        Intent intent = new Intent(context, RecipeStepDetailsActivity.class);
        intent.putExtra(POSITION, position);
        intent.putExtra(STEPS, stepArrayList);
        intent.putExtra(TOOL_BAR_TITLE, toolBarTitle);
        context.startActivity(intent);
    }

    private void getStepArrayList(Intent intent) {
        if (intent != null)
            stepArrayList = intent.getParcelableArrayListExtra(STEPS);
    }

    private void getPosition(Intent intent) {
        if (intent != null)
            position = intent.getIntExtra(POSITION, 0);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        getStepArrayList(intent);
        getPosition(intent);
        getToolBarTitle(intent);
    }

    @Override
    public void onPreviousClicked(ArrayList<Step> stepArrayList, int position) {
        openFragment(stepArrayList, position);
    }

    @Override
    public void onNextClicked(ArrayList<Step> stepArrayList, int position) {
        openFragment(stepArrayList, position);
    }

    private void openFragment(ArrayList<Step> stepArrayList, int position) {
        this.stepArrayList = stepArrayList;
        this.position = position;
        recipeStepDetailsFragment = RecipeStepDetailsFragment.newInstance(stepArrayList, position, isTablet);
        loadFragment(recipeStepDetailsFragment);
    }
}

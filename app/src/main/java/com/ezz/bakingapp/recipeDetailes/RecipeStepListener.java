package com.ezz.bakingapp.recipeDetailes;

import com.ezz.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by samar ezz on 12/22/2017.
 */

public interface RecipeStepListener {
    void onRecipeStepClicked(ArrayList<Step> stepArrayList, int position);
}

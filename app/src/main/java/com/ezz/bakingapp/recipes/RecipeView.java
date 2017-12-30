package com.ezz.bakingapp.recipes;

import com.ezz.bakingapp.model.RecipeItem;

import java.util.ArrayList;

/**
 * Created by samar ezz on 12/15/2017.
 */

public interface RecipeView {
    void showOrHideProgress(boolean show);
    void networkError();
    void noInternet();
    void onRecipesLoaded(ArrayList<RecipeItem> recipeItemArrayList);
}

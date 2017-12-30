package com.ezz.bakingapp.recipes;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ezz.bakingapp.helpers.GsonHelper;
import com.ezz.bakingapp.helpers.ServicesCaller;
import com.ezz.bakingapp.helpers.Utils;
import com.ezz.bakingapp.model.RecipeItem;

import java.util.ArrayList;

/**
 * Created by samar ezz on 12/15/2017.
 */

public class RecipePresenter {
    private RecipeView recipeView;
    private Context context;
    private ServicesCaller servicesCaller;

    public RecipePresenter(RecipeView recipeView, Context context) {
        this.recipeView = recipeView;
        this.context = context;
        servicesCaller = ServicesCaller.getInstance(context);
    }

    public void loadRecipe() {
        if (Utils.checkIfConnectedToTheInternet(context)) {
            recipeView.showOrHideProgress(true);
            servicesCaller.getRecipes(ServicesCaller.RECIPES_URL, successListener, errorListener, ServicesCaller.Tag.RECIPE);
        } else {
            recipeView.noInternet();
        }
    }

    private Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            recipeView.showOrHideProgress(false);
            if (response != null) {
                ArrayList<RecipeItem> recipeItemArrayList = GsonHelper.recipeResponse(response);
                if (recipeItemArrayList != null)
                    recipeView.onRecipesLoaded(recipeItemArrayList);
                else
                    recipeView.networkError();
            } else
                recipeView.networkError();
        }
    };
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            recipeView.showOrHideProgress(false);
            recipeView.networkError();
        }
    };
}

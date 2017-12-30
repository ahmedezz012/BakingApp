package com.ezz.bakingapp.helpers;


import com.ezz.bakingapp.model.Ingredient;
import com.ezz.bakingapp.model.RecipeItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by samar ezz on 12/15/2017.
 */

public class GsonHelper {

    public static ArrayList<RecipeItem> recipeResponse(String response) {
        try {
            Gson gson = new Gson();
            return new ArrayList<>(Arrays.asList(gson.fromJson(response, RecipeItem[].class)));
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Ingredient> getIngredients(String response) {
        try {
            Gson gson = new Gson();
            return new ArrayList<>(Arrays.asList(gson.fromJson(response, Ingredient[].class)));
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    public static String getIngredientsString(ArrayList<Ingredient> ingredientArrayList) {
        try {
            Gson gson = new Gson();
            return gson.toJson(ingredientArrayList);
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

}

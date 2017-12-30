package com.ezz.bakingapp.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ezz.bakingapp.model.Ingredient;

import java.util.ArrayList;

/**
 * Created by Ahmed.Ezz on 12/26/2017.
 */

public class SharedPrefHelper {
    public static final String INGREDIENTS = "INGREDIENTS";

    public static void saveIngredients(Context context, String ingredients) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(INGREDIENTS, ingredients);
        editor.apply();
    }

    public static ArrayList<Ingredient> getIngredients(Context context) {
        ArrayList<Ingredient> arrayList = null;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.contains(INGREDIENTS)) {
            arrayList = GsonHelper.getIngredients(sharedPreferences.getString((INGREDIENTS), null));
        }
        return arrayList;
    }
}

package com.ezz.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ezz.bakingapp.R;
import com.ezz.bakingapp.helpers.SharedPrefHelper;
import com.ezz.bakingapp.model.Ingredient;

import java.util.ArrayList;

/**
 * Created by samar ezz on 12/23/2017.
 */

public class IngredientListFactory implements RemoteViewsService.RemoteViewsFactory {


    private Context context;
    private ArrayList<Ingredient> ingredientArrayList;

    public IngredientListFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredientArrayList = SharedPrefHelper.getIngredients(context);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (ingredientArrayList != null && ingredientArrayList.size() > 0)
            return ingredientArrayList.size();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (ingredientArrayList != null && ingredientArrayList.size() > 0) {
            Ingredient ingredient = ingredientArrayList.get(i);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_ingredient);
            views.setTextViewText(R.id.txtIngredient, ingredient.getIngredient());
            views.setTextViewText(R.id.txtMeasure, ingredient.getMeasure());
            views.setTextViewText(R.id.txtQuantity, String.valueOf(ingredient.getQuantity()));
            return views;
        }
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}

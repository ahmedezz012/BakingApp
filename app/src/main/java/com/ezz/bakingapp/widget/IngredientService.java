package com.ezz.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;


public class IngredientService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientListFactory(this.getApplicationContext());
    }
}

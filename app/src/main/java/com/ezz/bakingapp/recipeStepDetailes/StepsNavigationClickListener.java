package com.ezz.bakingapp.recipeStepDetailes;

import com.ezz.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * Created by samar ezz on 12/29/2017.
 */

public interface StepsNavigationClickListener {
    void onPreviousClicked(ArrayList<Step> stepArrayList, int position);
    void onNextClicked(ArrayList<Step> stepArrayList, int position);
}

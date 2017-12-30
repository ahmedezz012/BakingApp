package com.ezz.bakingapp.recipeDetailes;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezz.bakingapp.R;
import com.ezz.bakingapp.helpers.FragmentBase;
import com.ezz.bakingapp.model.Ingredient;
import com.ezz.bakingapp.model.RecipeItem;
import com.ezz.bakingapp.model.Step;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsFragment extends FragmentBase {

    private static final String INGREDIENT_LIST_SCROLL_POSITION = "INGREDIENT_LIST_SCROLL_POSITION";
    private static final String STEP_LIST_SCROLL_POSITION = "STEP_LIST_SCROLL_POSITION";
    private static final String SELECTED_RECIPE_ITEM = "SELECTED_RECIPE_ITEM";
    private Context context;
    private RecipeItem recipeItem;
    private RecipeIngredientAdapter recipeIngredientAdapter;
    private RecipeStepAdapter recipeStepAdapter;
    private RecyclerView rvIngredients;
    private RecyclerView rvSteps;
    private RecipeStepListener recipeStepListener = new RecipeStepListener() {

        @Override
        public void onRecipeStepClicked(ArrayList<Step> stepArrayList, int position) {
            ((RecipeStepListener) context).onRecipeStepClicked(stepArrayList, position);
        }
    };

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailsFragment newInstance(RecipeItem recipeItem) {
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECTED_RECIPE_ITEM, recipeItem);
        recipeDetailsFragment.setArguments(args);
        return recipeDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipeItem = getArguments().getParcelable(SELECTED_RECIPE_ITEM);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        initializeViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        bindIngredientList(savedInstanceState);
        bindStepList(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipeItem.getIngredients() != null && recipeItem.getIngredients().size() > 0) {
            outState.putInt(INGREDIENT_LIST_SCROLL_POSITION,
                    ((LinearLayoutManager) rvIngredients.getLayoutManager()).findFirstVisibleItemPosition());
        }
        if (recipeItem.getSteps() != null && recipeItem.getSteps().size() > 0) {
            outState.putInt(STEP_LIST_SCROLL_POSITION,
                    ((LinearLayoutManager) rvSteps.getLayoutManager()).findFirstVisibleItemPosition());
        }
    }

    private void bindIngredientList(Bundle savedInstanceState) {
        if (recipeItem.getIngredients() != null) {
            if (recipeItem.getIngredients().size() > 0) {
                recipeIngredientAdapter = new RecipeIngredientAdapter(context, recipeItem.getIngredients());
                rvIngredients.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                rvIngredients.setAdapter(recipeIngredientAdapter);
                if (savedInstanceState != null && savedInstanceState.containsKey(INGREDIENT_LIST_SCROLL_POSITION))
                    rvIngredients.scrollToPosition(savedInstanceState.getInt(INGREDIENT_LIST_SCROLL_POSITION));

            } else
                rvIngredients.setVisibility(View.GONE);
        } else
            rvIngredients.setVisibility(View.GONE);
    }

    private void bindStepList(Bundle savedInstanceState) {
        if (recipeItem.getSteps() != null) {
            if (recipeItem.getSteps().size() > 0) {
                recipeStepAdapter = new RecipeStepAdapter(context, recipeItem.getSteps(), recipeStepListener);
                rvSteps.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                rvSteps.setAdapter(recipeStepAdapter);
                if (savedInstanceState != null && savedInstanceState.containsKey(STEP_LIST_SCROLL_POSITION))
                    rvSteps.scrollToPosition(savedInstanceState.getInt(STEP_LIST_SCROLL_POSITION));
            } else
                rvSteps.setVisibility(View.GONE);
        } else
            rvSteps.setVisibility(View.GONE);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initializeViews(View view) {
        rvIngredients = view.findViewById(R.id.rvIngredients);
        rvSteps = view.findViewById(R.id.rvSteps);
    }

    public ArrayList<Ingredient> getIngredients() {
        return recipeItem.getIngredients();
    }
}

package com.ezz.bakingapp.recipes;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ezz.bakingapp.R;
import com.ezz.bakingapp.helpers.FragmentBase;
import com.ezz.bakingapp.helpers.ServicesCaller;
import com.ezz.bakingapp.model.RecipeItem;

import java.util.ArrayList;

import android.support.test.espresso.IdlingResource;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends FragmentBase implements RecipeView {


    private IdlingResource idlingResource;
    private static final int SPAN_COUNT = 3;
    private static final String RECIPES = "RECIPES";
    private static final String SCROLL_POSITION = "SCROLL_POSITION";
    public static final String IS_TABLET = "IS_TABLET";
    private RecyclerView rvRecipes;
    private RelativeLayout rlProgress;
    private RecipePresenter recipePresenter;
    private RecipeAdapter recipeAdapter;
    private Context context;
    private boolean isTablet;
    private ArrayList<RecipeItem> recipeItemArrayList;
    private RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(RecipeItem recipeItem) {
            ((RecipeClickListener) context).onRecipeClicked(recipeItem);
        }
    };

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance(boolean isTablet) {
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_TABLET, isTablet);
        recipeFragment.setArguments(args);
        return recipeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isTablet = getArguments().getBoolean(IS_TABLET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        initializeViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        recipePresenter = new RecipePresenter(this, context);
        if (savedInstanceState == null)
            recipePresenter.loadRecipe();
        else {
            getDataFromInstanceState(savedInstanceState);
        }
    }

    public IdlingResource getIdleResource() {
        idlingResource = new IdlingResource() {
            @Override
            public String getName() {
                return "Recipe";
            }

            @Override
            public boolean isIdleNow() {
                return true;
            }

            @Override
            public void registerIdleTransitionCallback(ResourceCallback callback) {

            }
        };
        return idlingResource;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipeItemArrayList != null) {
            if (isTablet)
                outState.putInt(SCROLL_POSITION, ((GridLayoutManager) rvRecipes.getLayoutManager()).findFirstVisibleItemPosition());
            else
                outState.putInt(SCROLL_POSITION, ((LinearLayoutManager) rvRecipes.getLayoutManager()).findFirstVisibleItemPosition());
            outState.putParcelableArrayList(RECIPES, recipeItemArrayList);
        }
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void initializeViews(View view) {
        rlProgress = view.findViewById(R.id.rlProgress);
        rvRecipes = view.findViewById(R.id.rvRecipes);
    }

    @Override
    public void showOrHideProgress(boolean show) {
        if (show)
            rlProgress.setVisibility(View.VISIBLE);
        else
            rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void networkError() {
        Toast.makeText(context, R.string.networkingError, Toast.LENGTH_LONG).show();
    }

    @Override
    public void noInternet() {
        Toast.makeText(context, R.string.noInternetConnection, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRecipesLoaded(ArrayList<RecipeItem> recipeItemArrayList) {
        this.recipeItemArrayList = recipeItemArrayList;
        recipeAdapter = new RecipeAdapter(context, recipeItemArrayList, recipeClickListener);
        setLayoutManager();
        rvRecipes.setAdapter(recipeAdapter);
    }

    private void setLayoutManager() {
        if (isTablet)
            rvRecipes.setLayoutManager(new GridLayoutManager(context, SPAN_COUNT));
        else
            rvRecipes.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    private void getDataFromInstanceState(Bundle savedInstanceState) {
        recipeItemArrayList = savedInstanceState.getParcelableArrayList(RECIPES);
        onRecipesLoaded(recipeItemArrayList);
        rvRecipes.scrollToPosition(savedInstanceState.getInt(SCROLL_POSITION));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ServicesCaller.getInstance(context).getRequestQueue().cancelAll(ServicesCaller.Tag.RECIPE);
    }
}

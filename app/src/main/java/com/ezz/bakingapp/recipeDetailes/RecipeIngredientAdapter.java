package com.ezz.bakingapp.recipeDetailes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezz.bakingapp.R;
import com.ezz.bakingapp.model.Ingredient;

import java.util.ArrayList;

/**
 * Created by samar ezz on 12/18/2017.
 */

public class RecipeIngredientAdapter extends RecyclerView.Adapter<RecipeIngredientAdapter.RecipeIngredientViewHolder> {

    private Context context;
    private ArrayList<Ingredient> ingredients;


    public RecipeIngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public RecipeIngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeIngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeIngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        bindIngredient(holder, ingredient);
        bindMeasure(holder, ingredient);
        bindQuantity(holder, ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    private void bindIngredient(RecipeIngredientViewHolder holder, Ingredient ingredient) {
        if (TextUtils.isEmpty(ingredient.getIngredient())) {
            holder.lnrIngredient.setVisibility(View.GONE);
        } else {
            holder.txtIngredient.setText(ingredient.getIngredient());
        }
    }

    private void bindMeasure(RecipeIngredientViewHolder holder, Ingredient ingredient) {
        if (TextUtils.isEmpty(ingredient.getMeasure())) {
            holder.lnrMeasure.setVisibility(View.GONE);
        } else {
            holder.txtMeasure.setText(ingredient.getMeasure());
        }
    }

    private void bindQuantity(RecipeIngredientViewHolder holder, Ingredient ingredient) {
        holder.txtQuantity.setText(String.valueOf(ingredient.getQuantity()));
    }

    class RecipeIngredientViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout lnrIngredient;
        private TextView txtIngredient;
        private LinearLayout lnrMeasure;
        private TextView txtMeasure;
        private LinearLayout lnrQuantity;
        private TextView txtQuantity;


        public RecipeIngredientViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
        }

        private void findViews(View itemView) {
            lnrIngredient = itemView.findViewById(R.id.lnrIngredient);
            txtIngredient = itemView.findViewById(R.id.txtIngredient);
            lnrMeasure = itemView.findViewById(R.id.lnrMeasure);
            txtMeasure = itemView.findViewById(R.id.txtMeasure);
            lnrQuantity = itemView.findViewById(R.id.lnrQuantity);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
        }
    }
}

package com.ezz.bakingapp.recipes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezz.bakingapp.R;
import com.ezz.bakingapp.model.Ingredient;
import com.ezz.bakingapp.model.RecipeItem;
import com.ezz.bakingapp.recipeDetailes.RecipeIngredientAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by samar ezz on 12/15/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<RecipeItem> recipeItemArrayList;
    private Context context;
    private RecipeClickListener recipeClickListener;

    public RecipeAdapter(Context context, ArrayList<RecipeItem> recipeItemArrayList, RecipeClickListener recipeClickListener) {
        this.context = context;
        this.recipeItemArrayList = recipeItemArrayList;
        this.recipeClickListener = recipeClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        RecipeItem recipeItem = recipeItemArrayList.get(position);
        bindRecipeName(holder, recipeItem);
        bindRecipeServing(holder, recipeItem);
        bindRecipeImage(holder, recipeItem);
    }

    private void bindRecipeName(RecipeViewHolder holder, RecipeItem recipeItem) {
        if (!TextUtils.isEmpty(recipeItem.getName())) {
            holder.txtRecipeName.setText(recipeItem.getName());
        } else
            holder.lnrRecipeName.setVisibility(View.GONE);
    }

    private void bindRecipeServing(RecipeViewHolder holder, RecipeItem recipeItem) {
        holder.txtRecipeServing.setText(String.valueOf(recipeItem.getServings()));
    }

    private void bindRecipeImage(RecipeViewHolder holder, RecipeItem recipeItem) {
        if (!TextUtils.isEmpty(recipeItem.getImage())) {
            Picasso.with(context).load(recipeItem.getImage()).placeholder(R.drawable.food_place_holder).into(holder.imgRecipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return recipeItemArrayList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgRecipeImage;
        private TextView txtRecipeName, txtRecipeServing;
        private LinearLayout lnrRecipeName, lnrServing;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
            itemView.setOnClickListener(this);
        }

        private void findViews(View itemView) {
            imgRecipeImage = itemView.findViewById(R.id.imgRecipeImage);
            txtRecipeName = itemView.findViewById(R.id.txtRecipeName);
            txtRecipeServing = itemView.findViewById(R.id.txtRecipeServing);
            lnrRecipeName = itemView.findViewById(R.id.lnrRecipeName);
            lnrServing = itemView.findViewById(R.id.lnrServing);
        }

        @Override
        public void onClick(View view) {
            recipeClickListener.onRecipeClicked(recipeItemArrayList.get(getLayoutPosition()));
        }
    }
}

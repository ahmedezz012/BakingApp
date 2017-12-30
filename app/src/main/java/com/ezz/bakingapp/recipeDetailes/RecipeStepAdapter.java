package com.ezz.bakingapp.recipeDetailes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ezz.bakingapp.R;
import com.ezz.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by samar ezz on 12/19/2017.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepViewHolder> {

    private Context context;
    private RecipeStepListener recipeStepListener;
    private ArrayList<Step> stepArrayList;

    public RecipeStepAdapter(Context context, ArrayList<Step> stepArrayList, RecipeStepListener recipeStepListener) {
        this.context = context;
        this.recipeStepListener = recipeStepListener;
        this.stepArrayList = stepArrayList;
    }

    @Override
    public RecipeStepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeStepViewHolder(LayoutInflater.from(context).inflate(R.layout.item_step, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeStepViewHolder holder, int position) {
        Step step = stepArrayList.get(position);
        bindStepShortDescription(holder, step);
        bindStepThumbnailURL(holder, step);
    }

    private void bindStepShortDescription(RecipeStepViewHolder holder, Step step) {
        if (TextUtils.isEmpty(step.getShortDescription()))
            holder.txtStepShortDescription.setText(R.string.clickToSeeTheStep);
        else
            holder.txtStepShortDescription.setText(step.getShortDescription());
    }

    private void bindStepThumbnailURL(RecipeStepViewHolder holder, Step step) {
        if (!TextUtils.isEmpty(step.getThumbnailURL())) {
            Picasso.with(context).load(step.getThumbnailURL()).placeholder(R.drawable.food_place_holder).into(holder.imgStep);
        }
    }

    @Override
    public int getItemCount() {
        return stepArrayList.size();
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtStepShortDescription;
        private ImageView imgStep;

        public RecipeStepViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
            itemView.setOnClickListener(this);
        }

        private void findViews(View itemView) {
            imgStep = itemView.findViewById(R.id.imgStep);
            txtStepShortDescription = itemView.findViewById(R.id.txtStepShortDescription);
        }

        @Override
        public void onClick(View view) {
            recipeStepListener.onRecipeStepClicked(stepArrayList, getLayoutPosition());
        }
    }
}

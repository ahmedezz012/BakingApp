package com.ezz.bakingapp.recipeStepDetailes;

import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * Created by samar ezz on 12/22/2017.
 */

public interface RecipeStepDetailsView {
    void setPlayer(SimpleExoPlayer simpleExoPlayer);
    void play();
    void pause();
    void bindData();
}

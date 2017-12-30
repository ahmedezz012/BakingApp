package com.ezz.bakingapp.recipeStepDetailes;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ezz.bakingapp.R;
import com.ezz.bakingapp.helpers.FragmentBase;
import com.ezz.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

import static com.ezz.bakingapp.recipes.RecipeFragment.IS_TABLET;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeStepDetailsFragment extends FragmentBase implements RecipeStepDetailsView {


    private static final String CURRENT_POSITION = "CURRENT_POSITION";
    private static final String STEPS_LIST = "STEPS_LIST";
    private static final String CURRENT_PLAYING_POSITION = "CURRENT_PLAYING_POSITION";
    private RecipeStepDetailsPresenter recipeStepDetailsPresenter;
    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer simpleExoPlayer;
    private TextView txtStepDescription, txtNoVideo;
    private Button btnPrevious, btnNext;
    private Context context;
    private ArrayList<Step> stepArrayList;
    private int position;
    private boolean isUrlNull = true, isPortrait = true, isTablet;
    private Step step;
    private StepsNavigationClickListener stepsNavigationClickListener;

    public RecipeStepDetailsFragment() {
        // Required empty public constructor
    }

    public static RecipeStepDetailsFragment newInstance(ArrayList<Step> stepArrayList, int position, boolean isTablet) {
        RecipeStepDetailsFragment recipeStepDetailsFragment = new RecipeStepDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(STEPS_LIST, stepArrayList);
        args.putBoolean(IS_TABLET, isTablet);
        args.putInt(CURRENT_POSITION, position);
        recipeStepDetailsFragment.setArguments(args);
        return recipeStepDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stepArrayList = getArguments().getParcelableArrayList(STEPS_LIST);
            position = getArguments().getInt(CURRENT_POSITION);
            isTablet = getArguments().getBoolean(IS_TABLET);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            stepsNavigationClickListener = (StepsNavigationClickListener) (context);
        } catch (Exception exc) {
            stepsNavigationClickListener = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        stepsNavigationClickListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step_details, container, false);
        initializeViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        isPortrait = isPortrait(context);
        recipeStepDetailsPresenter = new RecipeStepDetailsPresenter(context, this);
        setCurrentStep(savedInstanceState);
        bindData();
        setListeners();
        getPlayPositionFromSaveInstanceIfExist(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (simpleExoPlayer != null)
            outState.putLong(CURRENT_PLAYING_POSITION, simpleExoPlayer.getCurrentPosition());
    }

    @Override
    public void onStop() {
        super.onStop();
        recipeStepDetailsPresenter.releasePlayer(simpleExoPlayer);
        simpleExoPlayer = null;
    }

    @Override
    protected void setListeners() {
        if (isPortrait && !isTablet) {
            btnNext.setOnClickListener(btnNextClickListener);
            btnPrevious.setOnClickListener(btnPreviousClickListener);
        }
        if (!isUrlNull)
            simpleExoPlayer.addListener(exoPlayerEventListener);
    }

    @Override
    protected void initializeViews(View view) {
        simpleExoPlayerView = view.findViewById(R.id.simpleExoPlayerView);
        txtStepDescription = view.findViewById(R.id.txtStepDescription);
        txtNoVideo = view.findViewById(R.id.txtNoVideo);
        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
    }

    @Override
    public void setPlayer(SimpleExoPlayer simpleExoPlayer) {
        this.simpleExoPlayer = simpleExoPlayer;
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
    }

    @Override
    public void play() {
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void pause() {
        simpleExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void bindData() {
        bindVideo();
        bindStepDescription();
    }

    private ExoPlayer.EventListener exoPlayerEventListener = new ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            recipeStepDetailsPresenter.onPlayerStateChanged(simpleExoPlayer, playWhenReady, playbackState);
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

        }

        @Override
        public void onPositionDiscontinuity() {

        }
    };

    private View.OnClickListener btnNextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (position == stepArrayList.size() - 1) {
                Toast.makeText(context, R.string.thisIsTheLastVideo, Toast.LENGTH_LONG).show();
            } else {
                stepsNavigationClickListener.onNextClicked(stepArrayList, ++position);
            }
        }
    };
    private View.OnClickListener btnPreviousClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (position == 0) {
                Toast.makeText(context, R.string.thisIsTheFirstVideo, Toast.LENGTH_LONG).show();
            } else {
                stepsNavigationClickListener.onNextClicked(stepArrayList, --position);
            }
        }
    };

    private void getPlayPositionFromSaveInstanceIfExist(Bundle savedInstanceState) {
        if (savedInstanceState != null && simpleExoPlayer != null) {
            simpleExoPlayer.seekTo(savedInstanceState.getLong(CURRENT_PLAYING_POSITION));
        }
    }

    private void setCurrentStep() {
        step = stepArrayList.get(position);
    }

    private void setCurrentStep(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            setCurrentStep();
        else {
            setCurrentStep();
        }
    }

    private void bindVideo() {
        if (!TextUtils.isEmpty(step.getVideoURL())) {
            playTheVideo();
        } else {
            videoNotExist();
        }
    }

    private void bindStepDescription() {
        if (!TextUtils.isEmpty(step.getDescription()) && (isPortrait || isTablet)) {
            txtStepDescription.setText(step.getDescription());
        }
    }

    private void playTheVideo() {
        isUrlNull = false;
        recipeStepDetailsPresenter.initializeMediaSession();
        simpleExoPlayerView.setVisibility(View.VISIBLE);
        txtNoVideo.setVisibility(View.GONE);
        recipeStepDetailsPresenter.initializePlayer(simpleExoPlayer, Uri.parse(step.getVideoURL()));
    }

    private void videoNotExist() {
        isUrlNull = true;
        simpleExoPlayerView.setVisibility(View.GONE);
        txtNoVideo.setVisibility(View.VISIBLE);
        simpleExoPlayer = null;
    }
}

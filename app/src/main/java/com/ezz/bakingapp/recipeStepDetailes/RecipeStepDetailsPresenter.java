package com.ezz.bakingapp.recipeStepDetailes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import com.ezz.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by samar ezz on 12/22/2017.
 */

public class RecipeStepDetailsPresenter {
    private static final String MEDIA_SESSION = "MEDIA_SESSION";
    private float playBackSpeed = 1f;
    private Context context;
    private RecipeStepDetailsView recipeStepDetailsView;

    private PlaybackStateCompat.Builder playbackStateCompatBuilder;
    private MediaSessionCompat mediaSessionCompat;


    public RecipeStepDetailsPresenter(Context context, RecipeStepDetailsView recipeStepDetailsView) {
        this.context = context;
        this.recipeStepDetailsView = recipeStepDetailsView;
    }

    public void initializePlayer(SimpleExoPlayer simpleExoPlayer, Uri mediaUri) {
        if (simpleExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            String userAgent = Util.getUserAgent(context, context.getString(R.string.app_name));

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(context, userAgent),
                    new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
            recipeStepDetailsView.setPlayer(simpleExoPlayer);
        }
    }

    public void initializeMediaSession() {
        mediaSessionCompat = new MediaSessionCompat(context, MEDIA_SESSION);
        mediaSessionCompat.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setMediaButtonReceiver(null);
        playbackStateCompatBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSessionCompat.setPlaybackState(playbackStateCompatBuilder.build());
        mediaSessionCompat.setCallback(new MySessionCallBack());
        mediaSessionCompat.setActive(true);
    }

    public void releasePlayer(SimpleExoPlayer simpleExoPlayer) {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            releaseMediaSession();
        }
    }

    public void onPlayerStateChanged(SimpleExoPlayer simpleExoPlayer, boolean playWhenReady, int playbackState) {
        if (simpleExoPlayer != null) {
            if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
                playbackStateCompatBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                        simpleExoPlayer.getCurrentPosition(), playBackSpeed);
            } else if ((playbackState == ExoPlayer.STATE_READY)) {
                playbackStateCompatBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                        simpleExoPlayer.getCurrentPosition(), playBackSpeed);
            }
        }
        if (mediaSessionCompat != null)
            mediaSessionCompat.setPlaybackState(playbackStateCompatBuilder.build());
    }

    private void releaseMediaSession() {
        if (mediaSessionCompat != null)
            mediaSessionCompat.setActive(false);
    }

    private class MySessionCallBack extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            super.onPlay();
            recipeStepDetailsView.play();
        }

        @Override
        public void onPause() {
            super.onPause();
            recipeStepDetailsView.pause();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
        }
    }

}

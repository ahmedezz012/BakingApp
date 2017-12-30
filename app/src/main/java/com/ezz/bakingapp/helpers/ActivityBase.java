package com.ezz.bakingapp.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ezz.bakingapp.R;

/**
 * Created by samar ezz on 11/10/2017.
 */

public abstract class ActivityBase extends AppCompatActivity {

    private Toolbar toolbar;

    protected void setToolBar(Toolbar toolBar, String title, boolean showUpButton) {
        this.toolbar = toolBar;
        setSupportActionBar(toolbar);
        setToolbarTitle(title);

        if (showUpButton) {
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    protected boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    protected abstract void initializeView();

    protected abstract void loadFragment(Fragment fragment);
}

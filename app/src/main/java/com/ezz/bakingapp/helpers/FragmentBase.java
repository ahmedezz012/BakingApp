package com.ezz.bakingapp.helpers;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by samar ezz on 11/10/2017.
 */

public abstract class FragmentBase extends Fragment {

    protected abstract void setListeners();

    protected abstract void initializeViews(View view);

    protected boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}

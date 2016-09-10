package com.ahao.wnacg.ui.preference;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ahao.wnacg.R;

/**
 * Created by Avalon on 2016/8/3.
 */
public class ReadPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_read);
    }
}

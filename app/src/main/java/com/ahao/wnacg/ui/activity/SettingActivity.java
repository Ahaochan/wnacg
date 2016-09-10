package com.ahao.wnacg.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ahao.androidlib.ui.activity.AppCompatPreferenceActivity;
import com.ahao.wnacg.R;
import com.ahao.wnacg.ui.preference.CommonPreferenceFragment;
import com.ahao.wnacg.ui.preference.DownloadPreferenceFragment;
import com.ahao.wnacg.ui.preference.ReadPreferenceFragment;

import java.util.List;

/**
 * Created by Avalon on 2016/7/28.
 */
public class SettingActivity extends AppCompatPreferenceActivity {
    private static final String TAG = SettingActivity.class.getSimpleName();

    private final String[] validFrags = new String[]{
            CommonPreferenceFragment.class.getName(),
            DownloadPreferenceFragment.class.getName(),
            ReadPreferenceFragment.class.getName()};
    private final String[] validFragNames = new String[]{"通用", "下载", "阅读"};

    private String currentFragName = "软件设置";


    @Override
    protected int getActLayoutId() {
        return R.layout.act_setting;
    }

    @Override
    protected int getActToolbarId() {
        return R.id.preference_tb;
    }

    @Override
    protected void initToolbar(Toolbar toolbar) {
        toolbar.setTitle(currentFragName);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        /** 防止NavigationOnClickListener被覆盖 */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preference_header, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {
        for(int i = 0; i < validFrags.length; i ++){
            if(fragmentName.equals(validFrags[i])){
                currentFragName = validFragNames[i];
                return true;
            }
        }
        return false;
    }

    public static void startAction(Context context){
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }
}

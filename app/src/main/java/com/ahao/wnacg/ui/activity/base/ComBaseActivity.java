package com.ahao.wnacg.ui.activity.base;

import android.content.Context;
import android.os.Build;
import android.transition.Explode;
import android.view.Window;

import com.ahao.androidlib.ui.activity.BaseActivity;
import com.ahao.androidlib.ui.fragment.BaseFragment;
import com.ahao.wnacg.R;

import butterknife.ButterKnife;

/**
 * Created by Avalon on 2016/5/15.
 */
public abstract class ComBaseActivity extends BaseActivity {

    @Override
    protected void beforeCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Explode());//new Slide()  new Fade()
        }
    }

    @Override
    protected void initView(Context context) {
        ButterKnife.bind(this);
    }

    @Override
    protected int actLayoutId() {
        return R.layout.act_base;
    }

    @Override
    protected int fragContentLayoutId() {
        return R.id.fragment_container;
    }

    @Override
    protected BaseFragment firstFragment() {
        return null;
    }
}

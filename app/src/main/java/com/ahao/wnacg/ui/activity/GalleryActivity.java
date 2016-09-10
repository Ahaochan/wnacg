package com.ahao.wnacg.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.ahao.androidlib.ui.fragment.BaseFragment;
import com.ahao.wnacg.entity.ComicEntity;
import com.ahao.wnacg.ui.activity.base.ComBaseActivity;
import com.ahao.wnacg.ui.fragment.GalleryFragment;

/**
 * Created by Avalon on 2016/5/13.
 */
public class GalleryActivity extends ComBaseActivity {
    public final static String className = GalleryActivity.class.getSimpleName();

    private final static String COMIC_DATA = "ComicEntity";

    @Override
    protected BaseFragment firstFragment() {
        ComicEntity comicEntity = (ComicEntity) getIntent().getSerializableExtra(COMIC_DATA);
        return GalleryFragment.newInstance(comicEntity);
    }

    @Override
    protected int toolbarId() {
        return 0;
    }


    public static void startAction(Context context, ComicEntity comicEntity){
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtra(COMIC_DATA, comicEntity);
        context.startActivity(intent);
    }
}

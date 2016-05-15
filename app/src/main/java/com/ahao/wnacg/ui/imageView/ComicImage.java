package com.ahao.wnacg.ui.imageView;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.entity.ComicData;
import com.ahao.wnacg.ui.activity.DetailActivity;

/**
 * Created by Avalon on 2016/5/7.
 */
public class ComicImage extends ImageView implements View.OnClickListener,View.OnLongClickListener{
    ComicData comicData;

    public ComicImage(Context context, AttributeSet attr) {
        super(context, attr);
        this.setOnClickListener(this);
        this.setOnLongClickListener(this);
    }

    public void setParam(ComicData comicData) {
        this.comicData = comicData;
    }

    @Override
    public void onClick(View v) {
        if(comicData != null) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra(Common.COMIC_DATA, comicData);
            getContext().startActivity(intent);
        } else {
            Toast.makeText(getContext(), "找不到漫画信息", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}

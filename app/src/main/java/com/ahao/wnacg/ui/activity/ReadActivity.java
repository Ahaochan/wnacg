package com.ahao.wnacg.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ahao.wnacg.R;
import com.ahao.wnacg.adapter.PicRecyclerAdapter;
import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.engine.LoadDataEngine;
import com.ahao.wnacg.engine.jsoup.ReadJsoupEngine;
import com.ahao.wnacg.entity.ComicData;
import com.ahao.wnacgnet.net.RequestCallback;
import com.dxjia.library.ImageTextButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Avalon on 2016/5/13.
 */
public class ReadActivity extends NetBaseActivity {
    public final static String className = ReadActivity.class.getSimpleName();


    ComicData comicData;
    Context context;
    @BindView(R.id.read_parent_layout) FrameLayout readParentLayout;
    @BindView(R.id.read_setting_layout) RelativeLayout readSettingLayout;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @BindView(R.id.back_btn)     ImageButton backBtn;
    @BindView(R.id.comic_title_text) TextView titleText;
    @BindView(R.id.detail_btn) ImageTextButton detailBtn;

    @BindView(R.id.down_btn) ImageTextButton downBtn;
    @BindView(R.id.share_btn) ImageTextButton shareBtn;
    @BindView(R.id.collect_btn) ImageTextButton collectBtn;

    @BindView(R.id.current_pic_text) TextView currentPicText;
    @BindView(R.id.all_pic_text) TextView allPicText;
    @BindView(R.id.process_seek) AppCompatSeekBar processSeekBar;
    @BindView(R.id.screen_btn) ImageTextButton screenBtn;
    @BindView(R.id.pager_btn) ImageTextButton pagerBtn;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        Log.i(className, "进入");
        ButterKnife.bind(this);
        context = this;
        comicData = (ComicData) getIntent().getSerializableExtra(Common.COMIC_DATA);

        loadDataFromNet();
        initView(false);
        setListener();


    }

    @Override
    protected void loadDataFromNet() {
        LoadDataEngine.getInstance().loadData(this, Common.home_m_org_desktop,
                LoadDataEngine.getParamsOfFeed(comicData.getId()), new AbstractRequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        comicData = ReadJsoupEngine.getPicList(content, comicData);
                        initView(true);
                    }
                });
    }

    @Override
    protected void initView(boolean loaded){
//        readSettingLayout.setVisibility(View.GONE);
        if(loaded){
            recyclerView.setAdapter(new PicRecyclerAdapter(context, comicData.getPicUrlList()));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true));
            recyclerView.setItemAnimator(new DefaultItemAnimator());


        }
    }

    @Override
    protected void setListener() {
        readParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readSettingLayout.getVisibility() == View.VISIBLE){
                    readSettingLayout.setVisibility(View.GONE);
                } else{
                    readSettingLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }


}

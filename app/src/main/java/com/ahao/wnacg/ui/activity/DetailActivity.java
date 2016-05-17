package com.ahao.wnacg.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahao.wnacg.R;
import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.engine.LoadDataEngine;
import com.ahao.wnacg.engine.jsoup.DetailJsoupEngine;
import com.ahao.wnacg.entity.ComicData;
import com.ahao.wnacg.engine.GlideEngine;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Avalon on 2016/5/7.
 */
public class DetailActivity extends NetBaseActivity {
    final static String className = DetailActivity.class.getSimpleName();

    @BindView(R.id.head_image) PhotoView headImageView;
    @BindView(R.id.thumbnail_image) ImageView thumbnailImageView;

    @BindView(R.id.comic_title_text)     TextView TitleText;
    @BindView(R.id.comic_updater_text)   TextView updaterText;
    @BindView(R.id.comic_pager_num_text) TextView pagerNumText;
    @BindView(R.id.comic_introduce_text) TextView introduceText;
    @BindView(R.id.comic_time_text)      TextView timeText;

    @BindView(R.id.read_button) Button readButton;
    @BindView(R.id.down_button)   Button downLoadButton;

    @BindView(R.id.comic_tag_layout) LinearLayout tagLayout;

    Context context;
    ComicData comicData;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        ButterKnife.bind(this);
        Log.i(className,"进入Activity");
        context = this;
        comicData = (ComicData) getIntent().getSerializableExtra(Common.COMIC_DATA);


        loadDataFromNet();
        initView(false);
        setListener();
    }

    @Override
    protected void loadDataFromNet() {
        LoadDataEngine.getInstance().loadData(DetailActivity.this, Common.home_m_org_mobile,
                LoadDataEngine.getParamsOfPhotos(comicData.getId()), new AbstractRequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        Log.i(className, "成功连接");
                        comicData = DetailJsoupEngine.UpdateComicData(content, comicData);
                        initView(true);
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    protected void initView(boolean loaded) {
        if(loaded){
//            Log.i(className, "头图:"+comicData.getFirstPicURL());
//            GlideEngine.init(DetailActivity.this, comicData.getFirstPicURL(), headImageView);
            TitleText.setText(comicData.getTitle());
            updaterText.setText(comicData.getUpdater());
            pagerNumText.setText(String.valueOf(comicData.getPicNum()));
            introduceText.setText(comicData.getIntroduce());
            timeText.setText(comicData.getCreateTime());

            for(int i = 0; i < comicData.getTag().size(); i++){
                Button tagButton = new Button(this);
                tagButton.setText(comicData.getTag().get(i));
                tagLayout.addView(tagButton);
            }
        } else {
            GlideEngine.init(DetailActivity.this, comicData.getThumbnailURL(), headImageView);
            GlideEngine.init(DetailActivity.this, comicData.getThumbnailURL(), thumbnailImageView);
            headImageView.setAllowParentInterceptOnEdge(false);

            progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("获取网络数据中...");
            progressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            progressDialog.show();
        }
    }

    @Override
    protected void setListener(){
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ReadActivity.class);
                intent.putExtra(Common.COMIC_DATA, comicData);
                startActivity(intent);
            }
        });
    }

}

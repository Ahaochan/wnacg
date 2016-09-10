package com.ahao.wnacg.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahao.androidlib.adapter.recyclerView.CommonAdapter;
import com.ahao.androidlib.adapter.recyclerView.ViewHolder;
import com.ahao.androidlib.util.IntentUtils;
import com.ahao.wnacg.R;
import com.ahao.wnacg.entity.ComicEntity;
import com.ahao.wnacg.presenter.IDetailPresenter;
import com.ahao.wnacg.ui.activity.base.ComBaseActivity;
import com.ahao.wnacg.ui.impl.IDetailView;
import com.bumptech.glide.Glide;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Avalon on 2016/5/7.
 */
public class DetailActivity extends ComBaseActivity implements IDetailView {
    private static final String TAG = "DetailActivity";

    private static final String COMIC_DATA = "Comic_data";

    @BindView(R.id.detail_image_head)    PhotoView headImageView;
    @BindView(R.id.detail_image_thumb)   ImageView thumbImageView;
    @BindView(R.id.detail_scroll_layout) NestedScrollView scrollView;

    @BindView(R.id.detail_text_title)     TextView titleText;
    @BindView(R.id.detail_text_updater)   TextView updaterText;
    @BindView(R.id.detail_text_pic)       TextView picText;
    @BindView(R.id.detail_text_introduce) TextView introduceText;
    @BindView(R.id.detail_text_time)      TextView timeText;

    @BindView(R.id.detail_btn_share)      TextView shareBtn;
    @BindView(R.id.detail_btn_collection) TextView collectBtn;
    @BindView(R.id.detail_btn_download)   TextView downloadBtn;
    @BindView(R.id.detail_btn_read)       TextView readBtn;

    @BindView(R.id.detail_tag_layout)    TagFlowLayout tagLayout;
    @BindView(R.id.detail_rv_thumb)      RecyclerView thumbRecyclerView;

    private Context mContext;
    private ComicEntity mComicEntity;
    private ProgressDialog mProgressDialog;
    private IDetailPresenter mPresenter;

    @Override
    protected int actLayoutId() {
        return R.layout.act_detail;
    }

    @Override
    protected int toolbarId() {
        return R.id.detail_toolbar;
    }

    @Override
    protected void initView(Context context) {
        super.initView(context);

        mContext = context;
        mComicEntity = (ComicEntity) getIntent().getSerializableExtra(COMIC_DATA);
        mPresenter = new IDetailPresenter(context, this, mComicEntity);

        mProgressDialog = new ProgressDialog(mContext, ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(mContext.getResources().getString(R.string.loading));
        mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        showLoading();

        headImageView.setAllowParentInterceptOnEdge(false);

        Glide.with(mContext).load(mComicEntity.getThumbUrl()).into(headImageView);
        titleText.setText(mComicEntity.getTitle());
        Glide.with(mContext).load(mComicEntity.getThumbUrl()).into(thumbImageView);
        picText.setText(mComicEntity.getPicNum() + context.getString(R.string.unit_pic));
        timeText.setText(mContext.getString(R.string.detail_create_time)+mComicEntity.getCreateTime());

        thumbRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        thumbRecyclerView.setNestedScrollingEnabled(false);
        thumbRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mPresenter.loadData();
    }

    @Override
    protected void setListener(Context context) {
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.shareString(mContext, R.string.intent_send_link, mComicEntity.getDetailUrl());
            }
        });

        collectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "收藏"+mComicEntity.getAId(), Toast.LENGTH_SHORT).show();
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.startAction(mContext, mComicEntity);
            }
        });

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollView.scrollTo(0,0);
            }
        });
    }

    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showDetail(ComicEntity comicEntity) {
        this.mComicEntity = comicEntity;
        updaterText.setText(comicEntity.getUpdater());
        introduceText.setText(mContext.getString(R.string.detail_introduce)+comicEntity.getIntroduce());

        tagLayout.setAdapter(new TagAdapter<String>(comicEntity.getTag()) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tagView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.list_item_tag, parent, false);
                tagView.setText(s);
                return tagView;
            }
        });

        thumbRecyclerView.setAdapter(new CommonAdapter<String>(mContext, R.layout.list_item_thumb,
                comicEntity.getThumbUrlList()) {
            @Override
            protected void convert(ViewHolder holder, String url, int position) {
                holder.setImage(R.id.img_thumb, url);
            }
        });

        scrollView.scrollTo(0, 0);
        scrollView.smoothScrollTo(0, 0);
    }

    public static void startAction(Context context, ComicEntity comicEntity, View view){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(COMIC_DATA, comicEntity);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) context, view, context.getString(R.string.pair_home2detail_image));
        context.startActivity(intent, options.toBundle());
    }

}

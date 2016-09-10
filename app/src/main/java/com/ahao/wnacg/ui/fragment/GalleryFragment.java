package com.ahao.wnacg.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ahao.androidlib.ui.widght.recyclerView.GalleryView;
import com.ahao.androidlib.util.MathUtils;
import com.ahao.androidlib.util.OSUtils;
import com.ahao.wnacg.R;
import com.ahao.wnacg.adapter.GalleryAdapter;
import com.ahao.wnacg.entity.ComicEntity;
import com.ahao.wnacg.presenter.IGalleryPresenter;
import com.ahao.wnacg.ui.fragment.base.ComBaseFragment;
import com.ahao.wnacg.ui.impl.IGalleryView;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Avalon on 2016/6/7.
 */
public class GalleryFragment extends ComBaseFragment implements IGalleryView {
    private static final String TAG = GalleryFragment.class.getSimpleName();

    private static final String KEY_COMIC_DATA = "KeyComicData";
    private static final String CLIP_LABEL = "clip_label";

    @BindView(R.id.read_list)           GalleryView    galleryView;
    @BindView(R.id.read_layout_setting) RelativeLayout settingLayout;

    @BindView(R.id.read_btn_back)   ImageButton backBtn;
    @BindView(R.id.read_text_title) TextView titleText;
    @BindView(R.id.read_btn_detail) TextView detailBtn;

    @BindView(R.id.read_btn_download) TextView downloadBtn;
    @BindView(R.id.read_btn_share)    TextView shareBtn;
    @BindView(R.id.read_btn_favorite) TextView favoriteBtn;

    @BindView(R.id.read_check_autoLight) CheckBox autoLightCheckBox;
    @BindView(R.id.read_seek_light)      VerticalSeekBar lightSeekBar;

    @BindView(R.id.read_text_pic_current) TextView currentPicText;
    @BindView(R.id.read_text_pic_all)     TextView allPicText;
    @BindView(R.id.read_seek_reading)     SeekBar readingSeekBar;
    @BindView(R.id.read_btn_screen)       TextView screenBtn;

    private Context mContext;
    private IGalleryPresenter mPresenter;
    private ComicEntity mComicEntity;
    private GalleryAdapter<String> mAdapter;
    private boolean isDrag;

    public static GalleryFragment newInstance(ComicEntity comicEntity) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_COMIC_DATA, comicEntity);
        GalleryFragment fragment = new GalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mComicEntity = (ComicEntity) getArguments().getSerializable(KEY_COMIC_DATA);
        mPresenter = new IGalleryPresenter(mContext, this, mComicEntity);
        mAdapter = new GalleryAdapter<>(mContext, R.layout.list_item_gallery, null, mComicEntity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getActivity().getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            mComicEntity = (ComicEntity) savedInstanceState.getSerializable(KEY_COMIC_DATA);
        }

    }

    @Override
    protected int setLayoutId() {
        return R.layout.frag_gallery;
    }


    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        titleText.setText(mComicEntity.getTitle());

        currentPicText.setText(String.valueOf(mComicEntity.getCurrentPic()));
        allPicText.setText(String.valueOf(mComicEntity.getPicNum()));
        readingSeekBar.setMax(mComicEntity.getPicNum());
        readingSeekBar.setProgress(mComicEntity.getCurrentPic());

        autoLightCheckBox.setChecked(OSUtils.isAutoBrightMode(getActivity()));
        lightSeekBar.setProgress(OSUtils.getSystemBrightness(getActivity()));

        galleryView.setItemAnimator(new DefaultItemAnimator());
        galleryView.setAdapter(mAdapter);
        mAdapter.setOnAdapterListener(new GalleryAdapter.SimpleOnAdapterListener() {
            @Override
            public void onChangePosition(int position) {
                if(!isDrag) {
                    mComicEntity.setCurrentPic(position);
                    readingSeekBar.setProgress(position);
                    currentPicText.setText(String.valueOf(position));
                }
            }

            @Override
            public void onSharePic(String s) {
                Snackbar.make(galleryView, mContext.getString(R.string.gallery_share_single_pic), Snackbar.LENGTH_SHORT).show();
            }
        });
        mPresenter.loadData();
    }


    @Override
    protected void setListener(View rootView) {
        galleryView.setOnGestureListener(new GalleryView.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                settingLayout.setVisibility(settingLayout.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
                return true;
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFragment.this.getActivity().finish();
            }
        });

        /** 详情 */
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                        .setTitle(mContext.getString(R.string.gallery_dialog_detail_title)+ mComicEntity.getAId())
                        .setView(R.layout.dialog_detail)
                        .setPositiveButton(mContext.getString(R.string.gallery_dialog_detail_positive), null)
                        .create();
                alertDialog.show();

                Window window = alertDialog.getWindow();
                setText(window, R.id.dialog_text_title, mComicEntity.getTitle());
                setText(window, R.id.dialog_text_num  , mComicEntity.getPicNum()+mContext.getString(R.string.unit_pic));
                setText(window, R.id.dialog_text_time , mComicEntity.getCreateTime());
                setText(window, R.id.dialog_text_updater, mComicEntity.getUpdater());
                setText(window, R.id.dialog_text_url, mComicEntity.getDetailUrl());
                setText(window, R.id.dialog_text_tag, mComicEntity.getTag().toString());
                setText(window, R.id.dialog_text_introduce, mComicEntity.getIntroduce());
            }

            private void setText(Window parent, int viewId, final String msg){
                TextView tv = (TextView) parent.findViewById(viewId);
                tv.setText(msg);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData textCd = ClipData.newPlainText(CLIP_LABEL,  msg);
                        clipboard.setPrimaryClip(textCd);
                        Snackbar.make(view, mContext.getString(R.string.gallery_dialog_detail_clip_tip), Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });

        /** 下载 */
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: "+"下载");
            }
        });

        /** 分享 */
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.shareUrl(mContext, mComicEntity.getDetailUrl());
            }
        });

        /** 收藏 */
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: "+"收藏");
            }
        });

        /** 屏幕方向 */
        screenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OSUtils.defaultSwitchOrientation(getActivity());
            }
        });

        /** 阅读进度 */
        readingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private static final int DEFAULT_SMOOTH_SLOP = 5;
            private int mLastProgress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    galleryView.smoothScrollToPosition(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mLastProgress = seekBar.getProgress();
                isDrag = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if(MathUtils.dist(progress, mLastProgress)>DEFAULT_SMOOTH_SLOP){
                    galleryView.scrollToPosition(progress);
                }
                currentPicText.setText(String.valueOf(progress));
                isDrag = false;
            }
        });

        /** 设置是否自动调节亮度 */
        autoLightCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    OSUtils.setSystemBrightMode(getActivity(), isChecked);
                } catch (SecurityException e){
                    buttonView.setChecked(!isChecked);
                    Snackbar.make(galleryView, mContext.getString(R.string.permission_no), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        /** 亮度调节 */
        lightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(!autoLightCheckBox.isChecked()) {
                    try {
                        OSUtils.setSystemBrightness(getActivity(), (int) (progress * 1.0f / seekBar.getMax() * 255));
                    } catch (SecurityException e){
                        Snackbar.make(galleryView, mContext.getString(R.string.permission_no), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    public void showLocalPic() {

    }

    @Override
    public void showNetPic(List<String> datas) {
        if(mAdapter.addDatas(datas)) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY_COMIC_DATA, mComicEntity);
        super.onSaveInstanceState(outState);
    }
}

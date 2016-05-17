package com.ahao.wnacg.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ahao.wnacg.R;
import com.ahao.wnacg.common.Common;
import com.ahao.wnacg.engine.LoadDataEngine;
import com.ahao.wnacg.engine.jsoup.HomeJsoupEngine;
import com.ahao.wnacg.entity.ComicData;
import com.ahao.wnacg.ui.activity.SearchActivity;
import com.ahao.wnacg.ui.handler.MyHandler;
import com.ahao.wnacg.ui.imageView.ComicImage;
import com.ahao.wnacg.ui.popupwindow.ImagePopup;
import com.ahao.wnacg.util.ComicDataManager;
import com.ahao.wnacg.engine.GlideEngine;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;


/**
 * Created by Avalon on 2016/5/2.
 */
public class PagerHomeFragment extends NetBaseFragment {
    public final static String className = PagerHomeFragment.class.getSimpleName();

    View rootView;
    @BindView(R.id.pager_home_frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.frame_loading)
    View loadingView;
    @BindView(R.id.frame_pager_home_scroll)
    View scrollView;
    @BindView(R.id.reLoadingBtn)
    Button reLoadBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindViews({R.id.home_plant_latest, R.id.home_plant_douJin, R.id.home_plant_tanKouBon, R.id.home_plant_magazine})
    LinearLayout[] plant;

    TextView[] plantTitle = new TextView[Common.HOME_PLANT_NUM];
    TextView[] plantMore  = new TextView[Common.HOME_PLANT_NUM];
    ComicImage[][] plantImage = new ComicImage[Common.HOME_PLANT_NUM][Common.PLANT_IMAGE_NUM];
    TextView[][] plantImageMainTitle = new TextView[Common.HOME_PLANT_NUM][Common.PLANT_IMAGE_NUM];
    TextView[][] plantImageSubTitle  = new TextView[Common.HOME_PLANT_NUM][Common.PLANT_IMAGE_NUM];


    int[] imageId = new int[]{R.id.home_plant_image1, R.id.home_plant_image2, R.id.home_plant_image3};
    int[] mainTitleId = new int[]{R.id.home_plant_mainTitle1, R.id.home_plant_mainTitle2, R.id.home_plant_mainTitle3};
    int[] subTitleId = new int[]{R.id.home_plant_subTitle1, R.id.home_plant_subTitle2, R.id.home_plant_subTitle3};


    MyHandler handler;
    boolean loaded = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(className, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_pager_home, container, false);
        ButterKnife.bind(this, rootView);

        Log.i(className, "onCreateView");

        loadDataFromNet();//读取网络数据

        initToolBar(rootView);//初始化toolbar
        initView(false);//读取view
        setListener();//设置监听器

        return rootView;
    }

    @Override
    protected void loadDataFromNet() {
        LoadDataEngine.getInstance().loadData(getActivity(),
                Common.home_m_org_mobile, new AbstractRequestCallback() {
                    @Override
                    public void onSuccess(String content) {
                        String[] plantName = getActivity().getResources().getStringArray(R.array.plant_name);
                        ComicData[][] comicDatas = HomeJsoupEngine.getComicDatas(Common.m_wnacg_org, content);
                        initView(true);

                        for (int i = 0; i < Common.HOME_PLANT_NUM; i++) {
                            plantTitle[i].setText(plantName[i]);
                            for (int j = 0; j < Common.PLANT_IMAGE_NUM; j++) {
                                GlideEngine.init(getActivity(), comicDatas[i][j].getThumbnailURL(), plantImage[i][j]);

                                plantImageMainTitle[i][j].setText(comicDatas[i][j].getTitle());
                                plantImageSubTitle[i][j].setText(comicDatas[i][j].getTimeAndPic());
                                plantImage[i][j].setParam(comicDatas[i][j]);

                                ComicDataManager.putComicData(comicDatas[i][j].getId(), comicDatas[i][j]);
                            }
                        }
                    }
                });
    }
    @Override
    protected void initToolBar(View rootView) {
        try {
            toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);

            toolbar.setTitle("");
            setSupportToolBar(toolbar);

            TextView toolBarTitle = (TextView) rootView.findViewById(R.id.toolbar_title);
            toolBarTitle.setText(getContext().getText(R.string.page_home));

            setHasOptionsMenu(true);//显示toolBarMenu
        } catch (Exception e) {
            Log.e(className, "初始化ToolBar失败");
            e.printStackTrace();
        }
    }

    @Override
    protected void initView(boolean loaded) {
        if (loaded) {
            loadingView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            for (int i = 0; i < Common.HOME_PLANT_NUM; i++) {
                plantTitle[i] = ButterKnife.findById(plant[i], R.id.home_plant_name);
                plantMore[i] = ButterKnife.findById(plant[i], R.id.home_plant_more);
                for (int j = 0; j < Common.PLANT_IMAGE_NUM; j++) {
                    plantImage[i][j] = ButterKnife.findById(plant[i], imageId[j]);
                    plantImageMainTitle[i][j] = ButterKnife.findById(plant[i], mainTitleId[j]);
                    plantImageSubTitle[i][j] = ButterKnife.findById(plant[i], subTitleId[j]);
                }
            }
        } else {
            scrollView.setVisibility(View.GONE);
            loadingView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void setListener() {
        if (loaded) {
            for (int i = 0; i < Common.HOME_PLANT_NUM; i++) {
                for (int j = 0; j < Common.PLANT_IMAGE_NUM; j++) {
                    if (plantImage[i][j] != null) {
                        final ComicImage cacheView = plantImage[i][j];
                        plantImage[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                Log.i(className, "设置长按监听器");
                                ImagePopup.setParam(getActivity()).show(rootView, cacheView);
                                return true;
                            }
                        });
                    }
                }
            }
        } else {
            reLoadBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadDataFromNet();
                    initView(true);
                }
            });
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_edit:
//                        ConnectUtil.inNet(getActivity());
                        startActivity(new Intent(getActivity(), SearchActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu_home, menu);
    }
}

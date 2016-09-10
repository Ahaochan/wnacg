package com.ahao.wnacg.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ahao.androidlib.adapter.recyclerView.CommonAdapter;
import com.ahao.androidlib.adapter.recyclerView.ViewHolder;
import com.ahao.wnacg.R;
import com.ahao.wnacg.entity.CateEntity;
import com.ahao.wnacg.ui.activity.CateListActivity;
import com.ahao.wnacg.ui.fragment.base.ComBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Avalon on 2016/9/7.
 */
public class CateHeaderFragment extends ComBaseFragment {

    @BindView(R.id.cate_recyclerView) RecyclerView cateView;

    private Context mContext;
    private List<CateEntity> mCateList;
    private int[] cateImage = new int[]{
            0,
            R.drawable.img_cate_01_doninshi_cn,
            R.drawable.img_cate_02_cg,
            R.drawable.img_cate_03_cos,
            0,
            R.drawable.img_cate_05_doninshi,
            R.drawable.img_cate_06_tankobon,
            R.drawable.img_cate_07_zasshi,
            0,
            R.drawable.img_cate_09_tankobon_cn,
            R.drawable.img_cate_10_zasshi_cn,
            0,
            R.drawable.img_cate_12_doninshi_jp,
            R.drawable.img_cate_13_tankobon_jp,
            R.drawable.img_cate_14_zasshi_jp};

    public static CateHeaderFragment newInstance() {
        Bundle args = new Bundle();
        CateHeaderFragment fragment = new CateHeaderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mCateList = new ArrayList<CateEntity>();
        String[] cateItem = mContext.getResources().getStringArray(R.array.cate_array);
        for (int i = 1; i < cateItem.length; i++) {
            if(!"null".equals(cateItem[i])){
                mCateList.add(new CateEntity(cateImage[i], cateItem[i], i));
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.frag_cate;
    }



    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        cateView.setAdapter(new CommonAdapter<CateEntity>(mContext, R.layout.list_item_cate, mCateList) {
            @Override
            protected void convert(ViewHolder holder, final CateEntity cateEntity, int position) {
                holder.setOnClickListener(R.id.cate_item_root, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CateListActivity.startAction(mContext, cateEntity.getCateNum());
                    }
                });
                holder.setOnLongClickListener(R.id.cate_item_root, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(mContext, "长点击"+cateEntity.getCateName(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                holder.setText(R.id.cate_item_name, cateEntity.getCateName());
                holder.setImageCircle(R.id.cate_item_image, cateEntity.getmImageResId());
            }
        });
        cateView.setLayoutManager(new GridLayoutManager(mContext, 3));
        cateView.setItemAnimator(new DefaultItemAnimator());
        cateView.setHasFixedSize(true);
    }
}

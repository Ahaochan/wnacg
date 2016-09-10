package com.ahao.androidlib.adapter.recyclerView;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ahao.androidlib.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;


/**
 * Created by Avalon on 2016/6/7.
 */
public class ViewHolder extends RecyclerView.ViewHolder{
    private static final String TAG = "ViewHolder";
    private SparseArrayCompat<View> mViews;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.mConvertView = itemView;
        this.mConvertView.setTag(R.string.app_name, this);
        this.mViews = new SparseArrayCompat<View>();
    }


    public static ViewHolder get(Context context, View itemView) {
        return new ViewHolder(context, itemView);
    }

    public static ViewHolder get(Context context, ViewGroup parent,@LayoutRes int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new ViewHolder(context, itemView);
    }


    /** 根据id获取itemView的子View */
    public <T extends View> T getView(@IdRes int viewId){
        View childView = mViews.get(viewId);
        if(childView == null){
            childView = mConvertView.findViewById(viewId);
            mViews.put(viewId, childView);
        }
        return (T) childView;
    }


    /** 返回itemView */
    public View getConvertView() {
        return mConvertView;
    }


    /**
     * 常用设置属性
     */


    /** 设置View */
    public ViewHolder setMargin(@IdRes int viewId, int margin){
        setMargin(viewId, margin, margin, margin, margin);
        return this;
    }
    public ViewHolder setMargin(@IdRes int viewId, int leftMargin, int topMargin, int rightMargin, int bottomMargin){
        View view = getView(viewId);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        lp.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
        view.setLayoutParams(lp);
        return this;
    }
    public ViewHolder setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }
    public ViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }
    public ViewHolder setAlpha(@IdRes int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }
    public ViewHolder setVisible(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
    public ViewHolder setTag(@IdRes int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }
    public ViewHolder setTag(@IdRes int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /** 设置文字 */
    public ViewHolder setText(@IdRes int viewId, String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
    public ViewHolder setTextColor(@IdRes int viewId, @ColorInt int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }
    public ViewHolder linkify(@IdRes int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }
    public ViewHolder setTypeface(Typeface typeface, @IdRes int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    /** 设置图片 */
    public ViewHolder setImage(@IdRes int viewId, @DrawableRes int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }
    public ViewHolder setImage(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }
    public ViewHolder setImage(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    /** 与Glide高耦合的设置图片 */
    public ViewHolder setImageCircle(@IdRes final int viewId, @DrawableRes int resId){
        final ImageView imageView = getView(viewId);
        Glide.with(mContext)
                .load(resId)
                .asBitmap()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable
                                = RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
        return this;
    }

    public ViewHolder setImage(@IdRes int viewId, String url){
        setImage(viewId, url, null);
        return this;
    }
    public ViewHolder setImage(@IdRes int viewId, String url, RequestListener listener){
        setImage(viewId, url, new ColorDrawable(Color.TRANSPARENT), listener);
        return this;
    }
    public ViewHolder setImage(@IdRes int viewId, String url, @DrawableRes int placeholder){
        setImage(viewId, url, ContextCompat.getDrawable(mContext, placeholder), null);
        return this;
    }
    public ViewHolder setImage(@IdRes int viewId, String url, Drawable placeholder, RequestListener listener){
        Glide.with(mContext)
                .load(url)
                .placeholder(placeholder)
                .listener(listener)
                .crossFade()
                .into((ImageView) getView(viewId));
        return this;
    }

    /** 设置Progress */
    public ViewHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }
    public ViewHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }
    public ViewHolder setMax(@IdRes int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }
    public ViewHolder setRating(@IdRes int viewId, float rating) {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }
    public ViewHolder setRating(@IdRes int viewId, float rating, int max) {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }


    /** 设置可选项 */
    public ViewHolder setChecked(@IdRes int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /** 设置事件监听器 */
    public ViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener){
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
    public ViewHolder setOnTouchListener(@IdRes int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }
    public ViewHolder setOnLongClickListener(@IdRes int viewId, View.OnLongClickListener listener){
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
}
